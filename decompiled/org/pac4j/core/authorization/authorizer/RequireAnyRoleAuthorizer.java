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

public class RequireAnyRoleAuthorizer
extends AbstractRequireAnyAuthorizer<String> {
    public RequireAnyRoleAuthorizer() {
    }

    public RequireAnyRoleAuthorizer(String ... roles) {
        this.setElements(roles);
    }

    public RequireAnyRoleAuthorizer(List<String> roles) {
        this.setElements(roles);
    }

    public RequireAnyRoleAuthorizer(Set<String> roles) {
        this.setElements(roles);
    }

    @Override
    protected boolean check(WebContext context, SessionStore sessionStore, UserProfile profile, String element) {
        Set<String> profileRoles = profile.getRoles();
        if (profileRoles.isEmpty()) {
            return false;
        }
        if (element == null) {
            return true;
        }
        return profileRoles.contains(element);
    }

    public static RequireAnyRoleAuthorizer requireAnyRole(String ... roles) {
        return new RequireAnyRoleAuthorizer(roles);
    }

    public static RequireAnyRoleAuthorizer requireAnyRole(List<String> roles) {
        return new RequireAnyRoleAuthorizer(roles);
    }

    public static RequireAnyRoleAuthorizer requireAnyRole(Set<String> roles) {
        return new RequireAnyRoleAuthorizer(roles);
    }
}

