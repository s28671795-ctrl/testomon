/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security.authentication;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoginAuthenticator
implements Authenticator {
    private static final Logger LOG = LoggerFactory.getLogger(LoginAuthenticator.class);
    protected LoginService _loginService;
    protected IdentityService _identityService;
    private boolean _sessionRenewedOnAuthentication;
    private int _sessionMaxInactiveIntervalOnAuthentication;

    protected LoginAuthenticator() {
    }

    @Override
    public void prepareRequest(ServletRequest request) {
    }

    public UserIdentity login(String username, Object password, ServletRequest servletRequest) {
        UserIdentity user = this._loginService.login(username, password, servletRequest);
        if (user != null) {
            Request request;
            this.renewSession(request, (request = Request.getBaseRequest(servletRequest)) == null ? null : request.getResponse());
            return user;
        }
        return null;
    }

    public void logout(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute("org.eclipse.jetty.security.sessionCreatedSecure");
    }

    @Override
    public void setConfiguration(Authenticator.AuthConfiguration configuration) {
        this._loginService = configuration.getLoginService();
        if (this._loginService == null) {
            throw new IllegalStateException("No LoginService for " + this + " in " + configuration);
        }
        this._identityService = configuration.getIdentityService();
        if (this._identityService == null) {
            throw new IllegalStateException("No IdentityService for " + this + " in " + configuration);
        }
        this._sessionRenewedOnAuthentication = configuration.isSessionRenewedOnAuthentication();
        this._sessionMaxInactiveIntervalOnAuthentication = configuration.getSessionMaxInactiveIntervalOnAuthentication();
    }

    public LoginService getLoginService() {
        return this._loginService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected HttpSession renewSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null && (this._sessionRenewedOnAuthentication || this._sessionMaxInactiveIntervalOnAuthentication != 0)) {
            HttpSession httpSession = session;
            synchronized (httpSession) {
                if (this._sessionMaxInactiveIntervalOnAuthentication != 0) {
                    session.setMaxInactiveInterval(this._sessionMaxInactiveIntervalOnAuthentication < 0 ? -1 : this._sessionMaxInactiveIntervalOnAuthentication);
                }
                if (this._sessionRenewedOnAuthentication && session.getAttribute("org.eclipse.jetty.security.sessionCreatedSecure") != Boolean.TRUE) {
                    if (session instanceof Session) {
                        Session s2 = (Session)session;
                        String oldId = s2.getId();
                        s2.renewId(request);
                        s2.setAttribute("org.eclipse.jetty.security.sessionCreatedSecure", Boolean.TRUE);
                        if (s2.isIdChanged() && response instanceof Response) {
                            ((Response)response).replaceCookie(s2.getSessionHandler().getSessionCookie(s2, request.getContextPath(), request.isSecure()));
                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("renew {}->{}", (Object)oldId, (Object)s2.getId());
                        }
                    } else {
                        LOG.warn("Unable to renew session {}", (Object)session);
                    }
                    return session;
                }
            }
        }
        return session;
    }
}

