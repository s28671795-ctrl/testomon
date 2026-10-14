/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.List;
import java.util.Set;
import org.pac4j.core.authorization.authorizer.AbstractRequireAnyAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

public class RequireAnyPermissionAuthorizer
extends AbstractRequireAnyAuthorizer<String> {
    public RequireAnyPermissionAuthorizer() {
    }

    public RequireAnyPermissionAuthorizer(String ... permissions) {
        this.setElements(permissions);
    }

    public RequireAnyPermissionAuthorizer(List<String> permissions) {
        this.setElements(permissions);
    }

    public RequireAnyPermissionAuthorizer(Set<String> permissions) {
        this.setElements(permissions);
    }

    @Override
    protected boolean check(WebContext context, SessionStore sessionStore, UserProfile profile, String element) {
        Set<String> profilePermissions = profile.getPermissions();
        if (profilePermissions.isEmpty()) {
            return false;
        }
        if (element == null) {
            return true;
        }
        return profilePermissions.contains(element);
    }

    public static RequireAnyPermissionAuthorizer requireAnyPermission(String ... permissions) {
        return new RequireAnyPermissionAuthorizer(permissions);
    }

    public static RequireAnyPermissionAuthorizer requireAnyPermission(List<String> permissions) {
        return new RequireAnyPermissionAuthorizer(permissions);
    }

    public static RequireAnyPermissionAuthorizer requireAnyPermission(Set<String> permissions) {
        return new RequireAnyPermissionAuthorizer(permissions);
    }
}

