/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security.authentication;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Objects;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.security.authentication.DeferredAuthentication;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class SslClientCertAuthenticator
extends LoginAuthenticator {
    private final SslContextFactory sslContextFactory;
    private boolean validateCerts = true;

    public SslClientCertAuthenticator(SslContextFactory sslContextFactory) {
        this.sslContextFactory = Objects.requireNonNull(sslContextFactory);
    }

    @Override
    public String getAuthMethod() {
        return "CLIENT_CERT";
    }

    @Override
    public Authentication validateRequest(ServletRequest req, ServletResponse res, boolean mandatory) throws ServerAuthException {
        if (!mandatory) {
            return new DeferredAuthentication(this);
        }
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        X509Certificate[] certs = (X509Certificate[])request.getAttribute("jakarta.servlet.request.X509Certificate");
        try {
            if (certs != null && certs.length > 0) {
                if (this.validateCerts) {
                    this.sslContextFactory.validateCerts(certs);
                }
                for (X509Certificate cert : certs) {
                    String username;
                    UserIdentity user;
                    if (cert == null) continue;
                    Principal principal = cert.getSubjectDN();
                    if (principal == null) {
                        principal = cert.getIssuerDN();
                    }
                    if ((user = this.login(username = principal == null ? "clientcert" : principal.getName(), "", req)) != null) {
                        return new UserAuthentication(this.getAuthMethod(), user);
                    }
                    user = this.login(username, null, req);
                    if (user != null) {
                        return new UserAuthentication(this.getAuthMethod(), user);
                    }
                    char[] credential = Base64.getEncoder().encodeToString(cert.getSignature()).toCharArray();
                    user = this.login(username, credential, req);
                    if (user == null) continue;
                    return new UserAuthentication(this.getAuthMethod(), user);
                }
            }
            if (!DeferredAuthentication.isDeferred(response)) {
                response.sendError(403);
                return Authentication.SEND_FAILURE;
            }
            return Authentication.UNAUTHENTICATED;
        }
        catch (Exception e) {
            throw new ServerAuthException(e.getMessage());
        }
    }

    @Override
    public boolean secureResponse(ServletRequest req, ServletResponse res, boolean mandatory, Authentication.User validatedUser) throws ServerAuthException {
        return true;
    }

    public boolean isValidateCerts() {
        return this.validateCerts;
    }

    public void setValidateCerts(boolean validateCerts) {
        this.validateCerts = validateCerts;
    }
}

