/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.checker;

import java.util.List;
import java.util.Map;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.client.Client;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

@FunctionalInterface
public interface AuthorizationChecker {
    public boolean isAuthorized(WebContext var1, SessionStore var2, List<UserProfile> var3, String var4, Map<String, Authorizer> var5, List<Client> var6);
}

