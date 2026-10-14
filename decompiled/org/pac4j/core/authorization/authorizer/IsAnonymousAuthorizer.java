/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.List;
import org.pac4j.core.authorization.authorizer.AbstractCheckAuthenticationAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.AnonymousProfile;
import org.pac4j.core.profile.UserProfile;

public class IsAnonymousAuthorizer
extends AbstractCheckAuthenticationAuthorizer {
    public IsAnonymousAuthorizer() {
    }

    public IsAnonymousAuthorizer(String redirectionUrl) {
        super(redirectionUrl);
    }

    @Override
    public boolean isAuthorized(WebContext context, SessionStore sessionStore, List<UserProfile> profiles) {
        return this.isAllAuthorized(context, sessionStore, profiles);
    }

    @Override
    public boolean isProfileAuthorized(WebContext context, SessionStore sessionStore, UserProfile profile) {
        return profile == null || profile instanceof AnonymousProfile;
    }

    public static IsAnonymousAuthorizer isAnonymous() {
        return new IsAnonymousAuthorizer();
    }
}

