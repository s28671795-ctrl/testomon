/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.credentials.password.PasswordEncoder;
import org.pac4j.core.exception.AccountNotFoundException;
import org.pac4j.core.exception.BadCredentialsException;
import org.pac4j.core.exception.MultipleAccountsFoundException;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.AttributeLocation;
import org.pac4j.core.profile.BasicUserProfile;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.definition.ProfileDefinitionAware;
import org.pac4j.core.profile.service.ProfileService;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProfileService<U extends CommonProfile>
extends ProfileDefinitionAware
implements ProfileService<U>,
Authenticator {
    public static final String ID = "id";
    public static final String LINKEDID = "linkedid";
    public static final String SERIALIZED_PROFILE = "serializedprofile";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String usernameAttribute = "username";
    private String passwordAttribute = "password";
    private String idAttribute = "id";
    private PasswordEncoder passwordEncoder;
    private Serializer serializer;
    private String attributes;
    protected String[] attributeNames;

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("profileDefinition", this.getProfileDefinition());
        CommonHelper.assertNotBlank("usernameAttribute", this.usernameAttribute);
        CommonHelper.assertNotBlank("passwordAttribute", this.passwordAttribute);
        CommonHelper.assertNotBlank("idAttribute", this.idAttribute);
        CommonHelper.assertNotNull("serializer", this.serializer);
        if (CommonHelper.isNotBlank(this.attributes)) {
            for (String attributeName : this.attributeNames = this.attributes.split(",")) {
                if (!this.getIdAttribute().equalsIgnoreCase(attributeName) && !LINKEDID.equalsIgnoreCase(attributeName) && !this.getUsernameAttribute().equalsIgnoreCase(attributeName) && !this.getPasswordAttribute().equalsIgnoreCase(attributeName) && !SERIALIZED_PROFILE.equalsIgnoreCase(attributeName)) continue;
                throw new TechnicalException("The 'getIdAttribute()', linkedid, 'getUsernameAttribute()', 'getPasswordAttribute()' and serializedprofile attributes are not allowed");
            }
        } else {
            this.attributeNames = new String[0];
        }
    }

    @Override
    public void create(U profile, String password) {
        this.init();
        CommonHelper.assertNotNull("profile", profile);
        CommonHelper.assertNotBlank("password", password);
        CommonHelper.assertNotBlank(ID, ((BasicUserProfile)profile).getId());
        CommonHelper.assertNotBlank("username", ((CommonProfile)profile).getUsername());
        Map<String, Object> attributes = this.convertProfileAndPasswordToAttributes(profile, password);
        this.insert(attributes);
    }

    @Override
    public void update(U profile, String password) {
        this.init();
        CommonHelper.assertNotNull("profile", profile);
        CommonHelper.assertNotBlank(ID, ((BasicUserProfile)profile).getId());
        CommonHelper.assertNotBlank("username", ((CommonProfile)profile).getUsername());
        Map<String, Object> attributes = this.convertProfileAndPasswordToAttributes(profile, password);
        this.update(attributes);
    }

    @Override
    public void remove(U profile) {
        this.init();
        CommonHelper.assertNotNull("profile", profile);
        this.removeById(((BasicUserProfile)profile).getId());
    }

    @Override
    public void removeById(String id) {
        this.init();
        CommonHelper.assertNotBlank(ID, id);
        this.deleteById(id);
    }

    protected Map<String, Object> convertProfileAndPasswordToAttributes(U profile, String password) {
        HashMap<String, Object> storageAttributes = new HashMap<String, Object>();
        storageAttributes.put(this.getIdAttribute(), ((BasicUserProfile)profile).getId());
        storageAttributes.put(LINKEDID, ((BasicUserProfile)profile).getLinkedId());
        storageAttributes.put(this.getUsernameAttribute(), ((CommonProfile)profile).getUsername());
        if (CommonHelper.isNotBlank(password)) {
            String encodedPassword = this.passwordEncoder != null ? this.passwordEncoder.encode(password) : password;
            storageAttributes.put(this.getPasswordAttribute(), encodedPassword);
        }
        if (this.isLegacyMode()) {
            for (String attributeName : this.attributeNames) {
                storageAttributes.put(attributeName, ((BasicUserProfile)profile).getAttribute(attributeName));
            }
        } else {
            storageAttributes.put(SERIALIZED_PROFILE, this.serializer.serializeToString(profile));
        }
        return storageAttributes;
    }

    protected abstract void insert(Map<String, Object> var1);

    protected abstract void update(Map<String, Object> var1);

    protected abstract void deleteById(String var1);

    @Override
    public U findById(String id) {
        this.init();
        CommonHelper.assertNotBlank(this.getIdAttribute(), id);
        List<Map<String, Object>> listAttributes = this.read(this.defineAttributesToRead(), this.getIdAttribute(), id);
        return this.convertAttributesToProfile(listAttributes, null);
    }

    @Override
    public U findByLinkedId(String linkedId) {
        this.init();
        CommonHelper.assertNotBlank(LINKEDID, linkedId);
        List<Map<String, Object>> listAttributes = this.read(this.defineAttributesToRead(), LINKEDID, linkedId);
        return this.convertAttributesToProfile(listAttributes, null);
    }

    protected List<String> defineAttributesToRead() {
        ArrayList<String> names = new ArrayList<String>();
        names.add(this.getIdAttribute());
        names.add(LINKEDID);
        if (this.isLegacyMode()) {
            names.add(this.getUsernameAttribute());
            names.addAll(Arrays.asList(this.attributeNames));
        } else {
            names.add(SERIALIZED_PROFILE);
        }
        return names;
    }

    protected U convertAttributesToProfile(List<Map<String, Object>> listStorageAttributes, String username) {
        if (listStorageAttributes == null || listStorageAttributes.size() == 0) {
            return null;
        }
        Map<String, Object> storageAttributes = listStorageAttributes.get(0);
        String linkedId = (String)storageAttributes.get(LINKEDID);
        if (this.isLegacyMode()) {
            CommonProfile profile = (CommonProfile)this.getProfileDefinition().newProfile(new Object[0]);
            for (String attributeName : this.attributeNames) {
                this.getProfileDefinition().convertAndAdd(profile, AttributeLocation.PROFILE_ATTRIBUTE, attributeName, storageAttributes.get(attributeName));
            }
            Object retrievedUsername = storageAttributes.get(this.getUsernameAttribute());
            if (retrievedUsername != null) {
                profile.setId(ProfileHelper.sanitizeIdentifier(retrievedUsername));
            } else {
                profile.setId(username);
            }
            if (CommonHelper.isNotBlank(linkedId)) {
                profile.setLinkedId(linkedId);
            }
            return (U)profile;
        }
        String serializedProfile = (String)storageAttributes.get(SERIALIZED_PROFILE);
        if (serializedProfile == null) {
            throw new TechnicalException("No serialized profile found. You should certainly define the explicit attribute names you want to retrieve");
        }
        CommonProfile profile = (CommonProfile)this.serializer.deserializeFromString(serializedProfile);
        if (profile == null) {
            throw new TechnicalException("No deserialized profile available. You should certainly define the explicit attribute names you want to retrieve");
        }
        Object id = storageAttributes.get(this.getIdAttribute());
        if (CommonHelper.isBlank(profile.getId()) && id != null) {
            profile.setId(ProfileHelper.sanitizeIdentifier(id));
        }
        if (CommonHelper.isBlank(profile.getLinkedId()) && CommonHelper.isNotBlank(linkedId)) {
            profile.setLinkedId(linkedId);
        }
        return (U)profile;
    }

    protected abstract List<Map<String, Object>> read(List<String> var1, String var2, String var3);

    @Override
    public void validate(Credentials cred, WebContext context, SessionStore sessionStore) {
        this.init();
        CommonHelper.assertNotNull("credentials", cred);
        UsernamePasswordCredentials credentials = (UsernamePasswordCredentials)cred;
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        CommonHelper.assertNotBlank("username", username);
        CommonHelper.assertNotBlank("password", password);
        List<String> attributesToRead = this.defineAttributesToRead();
        attributesToRead.add(this.getPasswordAttribute());
        try {
            List<Map<String, Object>> listAttributes = this.read(attributesToRead, this.getUsernameAttribute(), username);
            if (listAttributes == null || listAttributes.isEmpty()) {
                throw new AccountNotFoundException("No account found for: " + username);
            }
            if (listAttributes.size() > 1) {
                throw new MultipleAccountsFoundException("Too many accounts found for: " + username);
            }
            String retrievedPassword = (String)listAttributes.get(0).get(this.getPasswordAttribute());
            if (!this.passwordEncoder.matches(password, retrievedPassword)) {
                throw new BadCredentialsException("Bad credentials for: " + username);
            }
            U profile = this.convertAttributesToProfile(listAttributes, null);
            credentials.setUserProfile((UserProfile)profile);
        }
        catch (TechnicalException e) {
            this.logger.debug("Authentication error", e);
            throw e;
        }
    }

    protected boolean isLegacyMode() {
        return this.attributes != null;
    }

    public PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String getAttributes() {
        return this.attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Serializer getSerializer() {
        return this.serializer;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    public void setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
    }

    public String getPasswordAttribute() {
        return this.passwordAttribute;
    }

    public void setPasswordAttribute(String passwordAttribute) {
        this.passwordAttribute = passwordAttribute;
    }

    public String getIdAttribute() {
        return this.idAttribute;
    }

    public void setIdAttribute(String idAttribute) {
        this.idAttribute = idAttribute;
    }
}

