/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.List;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

@FunctionalInterface
public interface Authorizer {
    public boolean isAuthorized(WebContext var1, SessionStore var2, List<UserProfile> var3);
}

