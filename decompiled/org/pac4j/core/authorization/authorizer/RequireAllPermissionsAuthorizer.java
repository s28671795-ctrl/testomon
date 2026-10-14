/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.List;
import java.util.Set;
import org.pac4j.core.authorization.authorizer.AbstractRequireAllAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

public class RequireAllPermissionsAuthorizer
extends AbstractRequireAllAuthorizer<String> {
    public RequireAllPermissionsAuthorizer() {
    }

    public RequireAllPermissionsAuthorizer(String ... permissions) {
        this.setElements(permissions);
    }

    public RequireAllPermissionsAuthorizer(List<String> permissions) {
        this.setElements(permissions);
    }

    public RequireAllPermissionsAuthorizer(Set<String> permissions) {
        this.setElements(permissions);
    }

    @Override
    protected boolean check(WebContext context, SessionStore sessionStore, UserProfile profile, String element) {
        Set<String> profilePermissions = profile.getPermissions();
        return profilePermissions.contains(element);
    }

    public static RequireAllPermissionsAuthorizer requireAllPermissions(String ... permissions) {
        return new RequireAllPermissionsAuthorizer(permissions);
    }

    public static RequireAllPermissionsAuthorizer requireAllPermissions(List<String> permissions) {
        return new RequireAllPermissionsAuthorizer(permissions);
    }

    public static RequireAllPermissionsAuthorizer requireAllPermissions(Set<String> permissions) {
        return new RequireAllPermissionsAuthorizer(permissions);
    }
}

