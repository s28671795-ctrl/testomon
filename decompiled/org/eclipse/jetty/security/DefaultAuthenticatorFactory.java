/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import jakarta.servlet.ServletContext;
import java.util.Collection;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.security.authentication.ClientCertAuthenticator;
import org.eclipse.jetty.security.authentication.ConfigurableSpnegoAuthenticator;
import org.eclipse.jetty.security.authentication.DigestAuthenticator;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.security.authentication.SslClientCertAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAuthenticatorFactory
implements Authenticator.Factory {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticatorFactory.class);
    LoginService _loginService;

    @Override
    public Authenticator getAuthenticator(Server server, ServletContext context, Authenticator.AuthConfiguration configuration, IdentityService identityService, LoginService loginService) {
        String auth = configuration.getAuthMethod();
        LoginAuthenticator authenticator = null;
        if ("BASIC".equalsIgnoreCase(auth)) {
            authenticator = new BasicAuthenticator();
        } else if ("DIGEST".equalsIgnoreCase(auth)) {
            authenticator = new DigestAuthenticator();
        } else if ("FORM".equalsIgnoreCase(auth)) {
            authenticator = new FormAuthenticator();
        } else if ("SPNEGO".equalsIgnoreCase(auth)) {
            authenticator = new ConfigurableSpnegoAuthenticator();
        } else if ("NEGOTIATE".equalsIgnoreCase(auth)) {
            authenticator = new ConfigurableSpnegoAuthenticator("NEGOTIATE");
        }
        if ("CLIENT_CERT".equalsIgnoreCase(auth) || "CLIENT-CERT".equalsIgnoreCase(auth)) {
            Collection<SslContextFactory> sslContextFactories = server.getBeans(SslContextFactory.class);
            if (sslContextFactories.size() != 1) {
                if (sslContextFactories.size() > 1) {
                    LOG.info("Multiple SslContextFactory instances discovered. Directly configure a SslClientCertAuthenticator to use one.");
                } else {
                    LOG.debug("No SslContextFactory instances discovered. Directly configure a SslClientCertAuthenticator to use one.");
                }
                authenticator = new ClientCertAuthenticator();
            } else {
                authenticator = new SslClientCertAuthenticator(sslContextFactories.iterator().next());
            }
        }
        return authenticator;
    }

    public LoginService getLoginService() {
        return this._loginService;
    }

    public void setLoginService(LoginService loginService) {
        this._loginService = loginService;
    }
}

