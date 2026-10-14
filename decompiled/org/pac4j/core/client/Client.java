/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.profile.UserProfile;

public interface Client {
    public String getName();

    public Optional<RedirectionAction> getRedirectionAction(WebContext var1, SessionStore var2);

    public Optional<Credentials> getCredentials(WebContext var1, SessionStore var2);

    public Optional<UserProfile> getUserProfile(Credentials var1, WebContext var2, SessionStore var3);

    public Optional<UserProfile> renewUserProfile(UserProfile var1, WebContext var2, SessionStore var3);

    public Optional<RedirectionAction> getLogoutAction(WebContext var1, SessionStore var2, UserProfile var3, String var4);
}

