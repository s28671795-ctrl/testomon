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

public class RequireAllRolesAuthorizer
extends AbstractRequireAllAuthorizer<String> {
    public RequireAllRolesAuthorizer() {
    }

    public RequireAllRolesAuthorizer(String ... roles) {
        this.setElements(roles);
    }

    public RequireAllRolesAuthorizer(List<String> roles) {
        this.setElements(roles);
    }

    public RequireAllRolesAuthorizer(Set<String> roles) {
        this.setElements(roles);
    }

    @Override
    protected boolean check(WebContext context, SessionStore sessionStore, UserProfile profile, String element) {
        Set<String> profileRoles = profile.getRoles();
        return profileRoles.contains(element);
    }

    public static RequireAllRolesAuthorizer requireAllRoles(String ... roles) {
        return new RequireAllRolesAuthorizer(roles);
    }

    public static RequireAllRolesAuthorizer requireAllRoles(List<String> roles) {
        return new RequireAllRolesAuthorizer(roles);
    }

    public static RequireAllRolesAuthorizer requireAllRoles(Set<String> roles) {
        return new RequireAllRolesAuthorizer(roles);
    }
}

