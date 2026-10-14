/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client;

import java.util.Optional;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.http.ajax.DefaultAjaxRequestResolver;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.callback.QueryParameterCallbackUrlResolver;
import org.pac4j.core.http.url.DefaultUrlResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.logout.LogoutActionBuilder;
import org.pac4j.core.logout.NoLogoutActionBuilder;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.redirect.RedirectionActionBuilder;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.HttpActionHelper;

public abstract class IndirectClient
extends BaseClient {
    public static final String ATTEMPTED_AUTHENTICATION_SUFFIX = "$attemptedAuthentication";
    private static final String STATE_SESSION_PARAMETER = "$stateSessionParameter";
    private static final String NONCE_SESSION_PARAMETER = "$nonceSessionParameter";
    private static final String CODE_VERIFIER_SESSION_PARAMETER = "$codeVerifierSessionParameter";
    protected String callbackUrl;
    protected UrlResolver urlResolver;
    protected CallbackUrlResolver callbackUrlResolver;
    private AjaxRequestResolver ajaxRequestResolver;
    private RedirectionActionBuilder redirectionActionBuilder;
    private LogoutActionBuilder logoutActionBuilder = NoLogoutActionBuilder.INSTANCE;
    private boolean checkAuthenticationAttempt = true;

    @Override
    protected void beforeInternalInit(boolean forceReinit) {
        CommonHelper.assertNotBlank("callbackUrl", this.callbackUrl, "set it up either on this IndirectClient or on the global Config");
        if (this.urlResolver == null) {
            this.urlResolver = new DefaultUrlResolver();
        }
        if (this.callbackUrlResolver == null) {
            this.callbackUrlResolver = this.newDefaultCallbackUrlResolver();
        }
        if (this.ajaxRequestResolver == null) {
            this.ajaxRequestResolver = new DefaultAjaxRequestResolver();
        }
        if (this.saveProfileInSession == null) {
            this.saveProfileInSession = true;
        }
    }

    @Override
    protected final void afterInternalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("redirectionActionBuilder", this.redirectionActionBuilder);
        CommonHelper.assertNotNull("credentialsExtractor", this.getCredentialsExtractor());
        CommonHelper.assertNotNull("authenticator", this.getAuthenticator());
        CommonHelper.assertNotNull("profileCreator", this.getProfileCreator());
        CommonHelper.assertNotNull("logoutActionBuilder", this.logoutActionBuilder);
    }

    protected CallbackUrlResolver newDefaultCallbackUrlResolver() {
        return new QueryParameterCallbackUrlResolver();
    }

    @Override
    public final Optional<RedirectionAction> getRedirectionAction(WebContext context, SessionStore sessionStore) {
        this.init();
        if (this.ajaxRequestResolver.isAjax(context, sessionStore)) {
            HttpAction httpAction = this.ajaxRequestResolver.buildAjaxResponse(context, sessionStore, this.redirectionActionBuilder);
            this.logger.debug("AJAX request detected -> returning " + httpAction + " for " + context.getFullRequestURL());
            this.cleanRequestedUrl(context, sessionStore);
            throw httpAction;
        }
        Optional<Object> attemptedAuth = sessionStore.get(context, this.getName() + ATTEMPTED_AUTHENTICATION_SUFFIX);
        if (attemptedAuth.isPresent() && !"".equals(attemptedAuth.get())) {
            this.logger.debug("authentication already attempted -> 401");
            this.cleanAttemptedAuthentication(context, sessionStore);
            this.cleanRequestedUrl(context, sessionStore);
            throw HttpActionHelper.buildUnauthenticatedAction(context);
        }
        return this.redirectionActionBuilder.getRedirectionAction(context, sessionStore);
    }

    private void cleanRequestedUrl(WebContext context, SessionStore sessionStore) {
        this.logger.debug("clean requested URL from session");
        sessionStore.set(context, "pac4jRequestedUrl", null);
    }

    private void cleanAttemptedAuthentication(WebContext context, SessionStore sessionStore) {
        this.logger.debug("clean authentication attempt from session");
        sessionStore.set(context, this.getName() + ATTEMPTED_AUTHENTICATION_SUFFIX, null);
    }

    private void saveAttemptedAuthentication(WebContext context, SessionStore sessionStore) {
        if (this.checkAuthenticationAttempt) {
            this.logger.debug("save authentication attempt in session");
            sessionStore.set(context, this.getName() + ATTEMPTED_AUTHENTICATION_SUFFIX, "true");
        }
    }

    @Override
    public final Optional<Credentials> getCredentials(WebContext context, SessionStore sessionStore) {
        this.init();
        Optional<Credentials> optCredentials = this.retrieveCredentials(context, sessionStore);
        if (!optCredentials.isPresent() && this.getProfileFactoryWhenNotAuthenticated() == null) {
            this.logger.debug("no credentials and profile returned -> remember the authentication attempt");
            this.saveAttemptedAuthentication(context, sessionStore);
        } else {
            this.cleanAttemptedAuthentication(context, sessionStore);
        }
        return optCredentials;
    }

    @Override
    public final Optional<RedirectionAction> getLogoutAction(WebContext context, SessionStore sessionStore, UserProfile currentProfile, String targetUrl) {
        this.init();
        return this.logoutActionBuilder.getLogoutAction(context, sessionStore, currentProfile, targetUrl);
    }

    public String computeFinalCallbackUrl(WebContext context) {
        this.init();
        return this.callbackUrlResolver.compute(this.urlResolver, this.callbackUrl, this.getName(), context);
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackUrl() {
        return this.callbackUrl;
    }

    public UrlResolver getUrlResolver() {
        return this.urlResolver;
    }

    public void setUrlResolver(UrlResolver urlResolver) {
        this.urlResolver = urlResolver;
    }

    public CallbackUrlResolver getCallbackUrlResolver() {
        return this.callbackUrlResolver;
    }

    public void setCallbackUrlResolver(CallbackUrlResolver callbackUrlResolver) {
        this.callbackUrlResolver = callbackUrlResolver;
    }

    public AjaxRequestResolver getAjaxRequestResolver() {
        return this.ajaxRequestResolver;
    }

    public void setAjaxRequestResolver(AjaxRequestResolver ajaxRequestResolver) {
        this.ajaxRequestResolver = ajaxRequestResolver;
    }

    public RedirectionActionBuilder getRedirectionActionBuilder() {
        return this.redirectionActionBuilder;
    }

    protected void defaultRedirectionActionBuilder(RedirectionActionBuilder redirectActionBuilder) {
        if (this.redirectionActionBuilder == null) {
            this.redirectionActionBuilder = redirectActionBuilder;
        }
    }

    public LogoutActionBuilder getLogoutActionBuilder() {
        return this.logoutActionBuilder;
    }

    protected void defaultLogoutActionBuilder(LogoutActionBuilder logoutActionBuilder) {
        if (this.logoutActionBuilder == null || this.logoutActionBuilder == NoLogoutActionBuilder.INSTANCE) {
            this.logoutActionBuilder = logoutActionBuilder;
        }
    }

    public void setRedirectionActionBuilder(RedirectionActionBuilder redirectionActionBuilder) {
        this.redirectionActionBuilder = redirectionActionBuilder;
    }

    public void setLogoutActionBuilder(LogoutActionBuilder logoutActionBuilder) {
        this.logoutActionBuilder = logoutActionBuilder;
    }

    public String getStateSessionAttributeName() {
        return this.getName() + STATE_SESSION_PARAMETER;
    }

    public String getNonceSessionAttributeName() {
        return this.getName() + NONCE_SESSION_PARAMETER;
    }

    public String getCodeVerifierSessionAttributeName() {
        return this.getName() + CODE_VERIFIER_SESSION_PARAMETER;
    }

    public boolean isCheckAuthenticationAttempt() {
        return this.checkAuthenticationAttempt;
    }

    public void setCheckAuthenticationAttempt(boolean checkAuthenticationAttempt) {
        this.checkAuthenticationAttempt = checkAuthenticationAttempt;
    }

    @Override
    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "name", this.getName(), "callbackUrl", this.callbackUrl, "urlResolver", this.urlResolver, "callbackUrlResolver", this.callbackUrlResolver, "ajaxRequestResolver", this.ajaxRequestResolver, "redirectionActionBuilder", this.redirectionActionBuilder, "credentialsExtractor", this.getCredentialsExtractor(), "authenticator", this.getAuthenticator(), "profileCreator", this.getProfileCreator(), "logoutActionBuilder", this.logoutActionBuilder, "authorizationGenerators", this.getAuthorizationGenerators(), "checkAuthenticationAttempt", this.checkAuthenticationAttempt);
    }
}

