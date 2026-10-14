/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client;

import java.util.Optional;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;

public abstract class DirectClient
extends BaseClient {
    @Override
    protected void beforeInternalInit(boolean forceReinit) {
        if (this.saveProfileInSession == null) {
            this.saveProfileInSession = false;
        }
    }

    @Override
    protected final void afterInternalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("credentialsExtractor", this.getCredentialsExtractor());
        CommonHelper.assertNotNull("authenticator", this.getAuthenticator());
        CommonHelper.assertNotNull("profileCreator", this.getProfileCreator());
    }

    @Override
    public final Optional<RedirectionAction> getRedirectionAction(WebContext context, SessionStore sessionStore) {
        return Optional.empty();
    }

    @Override
    public final Optional<Credentials> getCredentials(WebContext context, SessionStore sessionStore) {
        this.init();
        return this.retrieveCredentials(context, sessionStore);
    }

    @Override
    public final Optional<RedirectionAction> getLogoutAction(WebContext context, SessionStore sessionStore, UserProfile currentProfile, String targetUrl) {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "name", this.getName(), "credentialsExtractor", this.getCredentialsExtractor(), "authenticator", this.getAuthenticator(), "profileCreator", this.getProfileCreator(), "authorizationGenerators", this.getAuthorizationGenerators());
    }
}

