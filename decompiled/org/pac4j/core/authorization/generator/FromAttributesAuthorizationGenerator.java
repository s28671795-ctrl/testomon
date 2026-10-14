/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.StringTokenizer;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

public class FromAttributesAuthorizationGenerator
implements AuthorizationGenerator {
    private Collection<String> roleAttributes;
    private Collection<String> permissionAttributes;
    private String splitChar = ",";

    public FromAttributesAuthorizationGenerator() {
        this.roleAttributes = new ArrayList<String>();
        this.permissionAttributes = new ArrayList<String>();
    }

    public FromAttributesAuthorizationGenerator(Collection<String> roleAttributes, Collection<String> permissionAttributes) {
        this.roleAttributes = roleAttributes;
        this.permissionAttributes = permissionAttributes;
    }

    public FromAttributesAuthorizationGenerator(String[] roleAttributes, String[] permissionAttributes) {
        this.roleAttributes = roleAttributes != null ? Arrays.asList(roleAttributes) : null;
        this.permissionAttributes = permissionAttributes != null ? Arrays.asList(permissionAttributes) : null;
    }

    @Override
    public Optional<UserProfile> generate(WebContext context, SessionStore sessionStore, UserProfile profile) {
        this.generateAuth(profile, this.roleAttributes, true);
        this.generateAuth(profile, this.permissionAttributes, false);
        return Optional.of(profile);
    }

    private void generateAuth(UserProfile profile, Iterable<String> attributes, boolean isRole) {
        if (attributes == null) {
            return;
        }
        for (String attribute : attributes) {
            Object value = profile.getAttribute(attribute);
            if (value == null) continue;
            if (value instanceof String) {
                StringTokenizer st = new StringTokenizer((String)value, this.splitChar);
                while (st.hasMoreTokens()) {
                    this.addRoleOrPermissionToProfile(profile, st.nextToken(), isRole);
                }
                continue;
            }
            if (value.getClass().isArray() && value.getClass().getComponentType().isAssignableFrom(String.class)) {
                for (Object item : (Object[])value) {
                    this.addRoleOrPermissionToProfile(profile, item.toString(), isRole);
                }
                continue;
            }
            if (!Collection.class.isAssignableFrom(value.getClass())) continue;
            for (Object item : (Collection)value) {
                if (!item.getClass().isAssignableFrom(String.class)) continue;
                this.addRoleOrPermissionToProfile(profile, item.toString(), isRole);
            }
        }
    }

    private void addRoleOrPermissionToProfile(UserProfile profile, String value, boolean isRole) {
        if (isRole) {
            profile.addRole(value);
        } else {
            profile.addPermission(value);
        }
    }

    public String getSplitChar() {
        return this.splitChar;
    }

    public void setSplitChar(String splitChar) {
        this.splitChar = splitChar;
    }

    public void setRoleAttributes(String roleAttributesStr) {
        this.roleAttributes = Arrays.asList(roleAttributesStr.split(this.splitChar));
    }

    public void setPermissionAttributes(String permissionAttributesStr) {
        this.permissionAttributes = Arrays.asList(permissionAttributesStr.split(this.splitChar));
    }
}

