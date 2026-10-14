/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.Arrays;
import java.util.List;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;

public class OrAuthorizer
implements Authorizer {
    private final List<Authorizer> authorizers;

    public OrAuthorizer(List<Authorizer> authorizers) {
        this.authorizers = authorizers;
    }

    @Override
    public boolean isAuthorized(WebContext context, SessionStore sessionStore, List<UserProfile> profiles) {
        for (Authorizer authorizer : this.authorizers) {
            if (!authorizer.isAuthorized(context, sessionStore, profiles)) continue;
            return true;
        }
        return false;
    }

    public static OrAuthorizer or(Authorizer ... authorizers) {
        return new OrAuthorizer(Arrays.asList(authorizers));
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "authorizers", this.authorizers);
    }
}

