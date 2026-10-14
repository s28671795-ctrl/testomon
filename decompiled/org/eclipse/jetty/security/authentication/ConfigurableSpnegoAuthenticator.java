/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security.authentication;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.SpnegoUserIdentity;
import org.eclipse.jetty.security.SpnegoUserPrincipal;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.security.authentication.DeferredAuthentication;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurableSpnegoAuthenticator
extends LoginAuthenticator {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurableSpnegoAuthenticator.class);
    private final String _authMethod;
    private Duration _authenticationDuration = Duration.ofNanos(-1L);

    public ConfigurableSpnegoAuthenticator() {
        this("SPNEGO");
    }

    public ConfigurableSpnegoAuthenticator(String authMethod) {
        this._authMethod = authMethod;
    }

    @Override
    public String getAuthMethod() {
        return this._authMethod;
    }

    public Duration getAuthenticationDuration() {
        return this._authenticationDuration;
    }

    public void setAuthenticationDuration(Duration authenticationDuration) {
        this._authenticationDuration = authenticationDuration;
    }

    @Override
    public UserIdentity login(String username, Object password, ServletRequest servletRequest) {
        SpnegoUserIdentity user = (SpnegoUserIdentity)this._loginService.login(username, password, servletRequest);
        if (user != null && user.isEstablished()) {
            Request request;
            this.renewSession(request, (request = Request.getBaseRequest(servletRequest)) == null ? null : request.getResponse());
        }
        return user;
    }

    @Override
    public Authentication validateRequest(ServletRequest req, ServletResponse res, boolean mandatory) throws ServerAuthException {
        Duration authnDuration;
        UserIdentity identity;
        UserIdentityHolder holder;
        if (!mandatory) {
            return new DeferredAuthentication(this);
        }
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String header = request.getHeader(HttpHeader.AUTHORIZATION.asString());
        String spnegoToken = this.getSpnegoToken(header);
        HttpSession httpSession = request.getSession(false);
        if (header != null && spnegoToken != null) {
            SpnegoUserIdentity identity2 = (SpnegoUserIdentity)this.login(null, spnegoToken, request);
            if (identity2.isEstablished()) {
                Duration authnDuration2;
                if (!DeferredAuthentication.isDeferred(response)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Sending final token");
                    }
                    SpnegoUserPrincipal principal = (SpnegoUserPrincipal)identity2.getUserPrincipal();
                    this.setSpnegoToken(response, principal.getEncodedToken());
                }
                if (!(authnDuration2 = this.getAuthenticationDuration()).isNegative()) {
                    if (httpSession == null) {
                        httpSession = request.getSession(true);
                    }
                    httpSession.setAttribute(UserIdentityHolder.ATTRIBUTE, new UserIdentityHolder(identity2));
                }
                return new UserAuthentication(this.getAuthMethod(), identity2);
            }
            if (DeferredAuthentication.isDeferred(response)) {
                return Authentication.UNAUTHENTICATED;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending intermediate challenge");
            }
            SpnegoUserPrincipal principal = (SpnegoUserPrincipal)identity2.getUserPrincipal();
            this.sendChallenge(response, principal.getEncodedToken());
            return Authentication.SEND_CONTINUE;
        }
        if (httpSession != null && (holder = (UserIdentityHolder)httpSession.getAttribute(UserIdentityHolder.ATTRIBUTE)) != null && (identity = holder._userIdentity) != null && !(authnDuration = this.getAuthenticationDuration()).isNegative()) {
            boolean expired;
            boolean bl = expired = !authnDuration.isZero() && Instant.now().isAfter(holder._validFrom.plus(authnDuration));
            if (!expired || !HttpMethod.GET.is(request.getMethod())) {
                return new UserAuthentication(this.getAuthMethod(), identity);
            }
        }
        if (DeferredAuthentication.isDeferred(response)) {
            return Authentication.UNAUTHENTICATED;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending initial challenge");
        }
        this.sendChallenge(response, null);
        return Authentication.SEND_CONTINUE;
    }

    private void sendChallenge(HttpServletResponse response, String token) throws ServerAuthException {
        try {
            this.setSpnegoToken(response, token);
            response.sendError(401);
        }
        catch (IOException x) {
            throw new ServerAuthException(x);
        }
    }

    private void setSpnegoToken(HttpServletResponse response, String token) {
        Object value = HttpHeader.NEGOTIATE.asString();
        if (token != null) {
            value = (String)value + " " + token;
        }
        response.setHeader(HttpHeader.WWW_AUTHENTICATE.asString(), (String)value);
    }

    private String getSpnegoToken(String header) {
        if (header == null) {
            return null;
        }
        String scheme2 = HttpHeader.NEGOTIATE.asString() + " ";
        if (header.regionMatches(true, 0, scheme2, 0, scheme2.length())) {
            return header.substring(scheme2.length()).trim();
        }
        return null;
    }

    @Override
    public boolean secureResponse(ServletRequest request, ServletResponse response, boolean mandatory, Authentication.User validatedUser) {
        return true;
    }

    private static class UserIdentityHolder
    implements Serializable {
        private static final String ATTRIBUTE = UserIdentityHolder.class.getName();
        private final transient Instant _validFrom = Instant.now();
        private final transient UserIdentity _userIdentity;

        private UserIdentityHolder(UserIdentity userIdentity) {
            this._userIdentity = userIdentity;
        }
    }
}

