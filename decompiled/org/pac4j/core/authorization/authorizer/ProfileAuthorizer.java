/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.List;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

public abstract class ProfileAuthorizer
implements Authorizer {
    public boolean isAllAuthorized(WebContext context, SessionStore sessionStore, List<UserProfile> profiles) {
        for (UserProfile profile : profiles) {
            if (this.isProfileAuthorized(context, sessionStore, profile)) continue;
            return this.handleError(context, sessionStore);
        }
        return true;
    }

    public boolean isAnyAuthorized(WebContext context, SessionStore sessionStore, List<UserProfile> profiles) {
        for (UserProfile profile : profiles) {
            if (!this.isProfileAuthorized(context, sessionStore, profile)) continue;
            return true;
        }
        return this.handleError(context, sessionStore);
    }

    protected abstract boolean isProfileAuthorized(WebContext var1, SessionStore var2, UserProfile var3);

    protected boolean handleError(WebContext context, SessionStore sessionStore) {
        return false;
    }
}

