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

public class CheckProfileTypeAuthorizer
extends AbstractRequireAnyAuthorizer<Class> {
    public CheckProfileTypeAuthorizer() {
    }

    public CheckProfileTypeAuthorizer(Class ... types) {
        this.setElements(types);
    }

    public CheckProfileTypeAuthorizer(List<Class> types) {
        this.setElements(types);
    }

    public CheckProfileTypeAuthorizer(Set<Class> types) {
        this.setElements(types);
    }

    @Override
    protected boolean check(WebContext context, SessionStore sessionStore, UserProfile profile, Class element) {
        return profile.getClass().isAssignableFrom(element);
    }

    public static CheckProfileTypeAuthorizer checkProfileType(Class ... types) {
        return new CheckProfileTypeAuthorizer(types);
    }

    public static CheckProfileTypeAuthorizer checkProfileType(List<Class> types) {
        return new CheckProfileTypeAuthorizer(types);
    }

    public static CheckProfileTypeAuthorizer checkProfileType(Set<Class> types) {
        return new CheckProfileTypeAuthorizer(types);
    }
}

