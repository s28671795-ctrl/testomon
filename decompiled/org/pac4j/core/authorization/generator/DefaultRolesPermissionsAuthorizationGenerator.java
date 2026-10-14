/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.generator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

public class DefaultRolesPermissionsAuthorizationGenerator
implements AuthorizationGenerator {
    private final Collection<String> defaultRoles;
    private final Collection<String> defaultPermissions;

    public DefaultRolesPermissionsAuthorizationGenerator(Collection<String> defaultRoles, Collection<String> defaultPermissions) {
        this.defaultRoles = defaultRoles;
        this.defaultPermissions = defaultPermissions;
    }

    public DefaultRolesPermissionsAuthorizationGenerator(String[] defaultRoles, String[] defaultPermissions) {
        this.defaultRoles = defaultRoles != null ? Arrays.asList(defaultRoles) : null;
        this.defaultPermissions = defaultPermissions != null ? Arrays.asList(defaultPermissions) : null;
    }

    @Override
    public Optional<UserProfile> generate(WebContext context, SessionStore sessionStore, UserProfile profile) {
        if (this.defaultRoles != null) {
            profile.addRoles(this.defaultRoles);
        }
        if (this.defaultPermissions != null) {
            profile.addPermissions(this.defaultPermissions);
        }
        return Optional.of(profile);
    }
}

