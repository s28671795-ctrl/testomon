/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile;

import com.google.common.collect.Streams;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.pac4j.core.profile.Pac4JPrincipal;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicUserProfile
implements UserProfile,
Externalizable {
    private static final long serialVersionUID = 9020114478664816338L;
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());
    private String id;
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private Map<String, Object> authenticationAttributes = new HashMap<String, Object>();
    private boolean isRemembered = false;
    private Set<String> roles = new HashSet<String>();
    private Set<String> permissions = new HashSet<String>();
    private String clientName;
    private String linkedId;
    private final boolean canAttributesBeMerged;

    public BasicUserProfile() {
        this(true);
    }

    public BasicUserProfile(boolean canAttributesBeMerged) {
        this.canAttributesBeMerged = canAttributesBeMerged;
    }

    public void build(Object id, Map<String, Object> attributes) {
        this.setId(ProfileHelper.sanitizeIdentifier(id));
        this.addAttributes(attributes);
    }

    public void build(Object id, Map<String, Object> attributes, Map<String, Object> authenticationAttributes) {
        this.build(id, attributes);
        this.addAuthenticationAttributes(authenticationAttributes);
    }

    @Override
    public void setId(String id) {
        CommonHelper.assertNotBlank("id", id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTypedId() {
        return this.getClass().getName() + "#" + this.id;
    }

    @Override
    public String getUsername() {
        return null;
    }

    private void addAttributeToMap(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            this.logger.debug("adding => key: {} / value: {} / {}", key, value, value.getClass());
            Object valueForMap = this.getValueForMap(map, key, value);
            map.put(key, valueForMap);
        }
    }

    private Object getValueForMap(Map<String, Object> map, String key, Object preparedValue) {
        if (this.canMergeAttributes(map, key, preparedValue)) {
            Collection existingCollection = (Collection)map.get(key);
            Collection newCollection = (Collection)preparedValue;
            return this.mergeCollectionAttributes(existingCollection, newCollection);
        }
        return preparedValue;
    }

    private boolean canMergeAttributes(Map<String, Object> map, String key, Object preparedValue) {
        return this.canAttributesBeMerged && preparedValue instanceof Collection && map.get(key) instanceof Collection;
    }

    private <T> Collection<T> mergeCollectionAttributes(Collection<T> existingCollection, Collection<T> newCollection) {
        return Streams.concat(existingCollection.stream(), newCollection.stream()).collect(Collectors.toList());
    }

    @Override
    public void addAttribute(String key, Object value) {
        this.addAttributeToMap(this.attributes, key, value);
    }

    @Override
    public void addAuthenticationAttribute(String key, Object value) {
        this.addAttributeToMap(this.authenticationAttributes, key, value);
    }

    public void addAttributes(Map<String, Object> attributes) {
        if (attributes != null) {
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                this.addAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    public void addAuthenticationAttributes(Map<String, Object> attributeMap) {
        if (attributeMap != null) {
            for (Map.Entry<String, Object> entry : attributeMap.entrySet()) {
                this.addAuthenticationAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void removeAttribute(String key) {
        CommonHelper.assertNotNull("key", key);
        this.attributes.remove(key);
    }

    @Override
    public void removeAuthenticationAttribute(String key) {
        CommonHelper.assertNotNull("key", key);
        this.authenticationAttributes.remove(key);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return BasicUserProfile.getAttributeMap(this.attributes);
    }

    public Map<String, Object> getAuthenticationAttributes() {
        return BasicUserProfile.getAttributeMap(this.authenticationAttributes);
    }

    private static Map<String, Object> getAttributeMap(Map<String, Object> attributeMap) {
        HashMap<String, Object> newAttributes = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entries : attributeMap.entrySet()) {
            String key = entries.getKey();
            Object value = attributeMap.get(key);
            newAttributes.put(key, value);
        }
        return newAttributes;
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public List<String> extractAttributeValues(String name) {
        Object value = this.getAttribute(name);
        if (value instanceof String) {
            return Collections.singletonList((String)value);
        }
        if (value instanceof String[]) {
            return Arrays.asList((String[])value);
        }
        if (value instanceof List) {
            return (List)value;
        }
        return null;
    }

    public Object getAuthenticationAttribute(String name) {
        return this.authenticationAttributes.get(name);
    }

    @Override
    public boolean containsAttribute(String name) {
        CommonHelper.assertNotNull("name", name);
        return this.attributes.containsKey(name);
    }

    public boolean containsAuthenicationAttribute(String name) {
        CommonHelper.assertNotNull("name", name);
        return this.authenticationAttributes.containsKey(name);
    }

    public <T> T getAttribute(String name, Class<T> clazz) {
        Object attribute = this.getAttribute(name);
        return this.getAttributeByType(name, clazz, attribute);
    }

    public <T> T getAuthenticationAttribute(String name, Class<T> clazz) {
        Object attribute = this.getAuthenticationAttribute(name);
        return this.getAttributeByType(name, clazz, attribute);
    }

    private <T> T getAttributeByType(String name, Class<T> clazz, Object attribute) {
        if (attribute == null) {
            return null;
        }
        if (!clazz.isAssignableFrom(attribute.getClass())) {
            throw new ClassCastException("Attribute [" + name + " is of type " + attribute.getClass() + " when we were expecting " + clazz);
        }
        return (T)attribute;
    }

    @Override
    public void addRole(String role) {
        CommonHelper.assertNotBlank("role", role);
        this.roles.add(role);
    }

    @Override
    public void addRoles(Collection<String> roles) {
        CommonHelper.assertNotNull("roles", roles);
        this.roles.addAll(roles);
    }

    @Override
    public Set<String> getRoles() {
        return new LinkedHashSet<String>(this.roles);
    }

    public void setRoles(Set<String> roles) {
        CommonHelper.assertNotNull("roles", roles);
        this.roles = roles;
    }

    @Override
    public void addPermission(String permission) {
        CommonHelper.assertNotBlank("permission", permission);
        this.permissions.add(permission);
    }

    @Override
    public void addPermissions(Collection<String> permissions) {
        CommonHelper.assertNotNull("permissions", permissions);
        this.permissions.addAll(permissions);
    }

    @Override
    public Set<String> getPermissions() {
        return new LinkedHashSet<String>(this.permissions);
    }

    public void setPermissions(Set<String> permissions) {
        CommonHelper.assertNotNull("permissions", permissions);
        this.permissions = permissions;
    }

    @Override
    public void setRemembered(boolean rme) {
        this.isRemembered = rme;
    }

    @Override
    public boolean isRemembered() {
        return this.isRemembered;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "id", this.id, "attributes", this.attributes, "authenticationAttributes", this.authenticationAttributes, "roles", this.roles, "permissions", this.permissions, "isRemembered", this.isRemembered, "clientName", this.clientName, "linkedId", this.linkedId);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.id);
        out.writeObject(this.attributes);
        out.writeObject(this.authenticationAttributes);
        out.writeBoolean(this.isRemembered);
        out.writeObject(this.roles);
        out.writeObject(this.permissions);
        out.writeObject(this.clientName);
        out.writeObject(this.linkedId);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = (String)in.readObject();
        this.attributes = (Map)in.readObject();
        this.authenticationAttributes = (Map)in.readObject();
        this.isRemembered = in.readBoolean();
        this.roles = (Set)in.readObject();
        this.permissions = (Set)in.readObject();
        this.clientName = (String)in.readObject();
        this.linkedId = (String)in.readObject();
    }

    public void removeLoginData() {
    }

    @Override
    public String getClientName() {
        return this.clientName;
    }

    @Override
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String getLinkedId() {
        return this.linkedId;
    }

    @Override
    public void setLinkedId(String linkedId) {
        this.linkedId = linkedId;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public Principal asPrincipal() {
        return new Pac4JPrincipal(this);
    }
}

