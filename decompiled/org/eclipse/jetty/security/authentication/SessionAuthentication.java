/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security.authentication;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionActivationListener;
import jakarta.servlet.http.HttpSessionBindingListener;
import jakarta.servlet.http.HttpSessionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.eclipse.jetty.security.AbstractUserAuthentication;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.server.UserIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionAuthentication
extends AbstractUserAuthentication
implements Serializable,
HttpSessionActivationListener,
HttpSessionBindingListener {
    private static final Logger LOG = LoggerFactory.getLogger(SessionAuthentication.class);
    private static final long serialVersionUID = -4643200685888258706L;
    public static final String __J_AUTHENTICATED = "org.eclipse.jetty.security.UserIdentity";
    private final String _name;
    private final Object _credentials;
    private transient HttpSession _session;

    public SessionAuthentication(String method2, UserIdentity userIdentity, Object credentials) {
        super(method2, userIdentity);
        this._name = userIdentity.getUserPrincipal().getName();
        this._credentials = credentials;
    }

    @Override
    public UserIdentity getUserIdentity() {
        if (this._userIdentity == null) {
            throw new IllegalStateException("!UserIdentity");
        }
        return super.getUserIdentity();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        SecurityHandler security = SecurityHandler.getCurrentSecurityHandler();
        if (security == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("!SecurityHandler");
            }
            return;
        }
        Authenticator authenticator = security.getAuthenticator();
        LoginService loginService = authenticator instanceof LoginAuthenticator ? ((LoginAuthenticator)authenticator).getLoginService() : security.getLoginService();
        if (loginService == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("!LoginService");
            }
            return;
        }
        this._userIdentity = loginService.login(this._name, this._credentials, null);
        LOG.debug("Deserialized and relogged in {}", (Object)this);
    }

    public String toString() {
        return String.format("%s@%x{%s,%s}", this.getClass().getSimpleName(), this.hashCode(), this._session == null ? "-" : this._session.getId(), this._userIdentity);
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        if (this._session == null) {
            this._session = se.getSession();
        }
    }
}

