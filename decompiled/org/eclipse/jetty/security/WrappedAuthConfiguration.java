/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.util.Set;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;

public class WrappedAuthConfiguration
implements Authenticator.AuthConfiguration {
    private final Authenticator.AuthConfiguration _configuration;

    public WrappedAuthConfiguration(Authenticator.AuthConfiguration configuration) {
        this._configuration = configuration;
    }

    @Override
    public String getAuthMethod() {
        return this._configuration.getAuthMethod();
    }

    @Override
    public String getRealmName() {
        return this._configuration.getRealmName();
    }

    @Override
    public String getInitParameter(String param2) {
        return this._configuration.getInitParameter(param2);
    }

    @Override
    public Set<String> getInitParameterNames() {
        return this._configuration.getInitParameterNames();
    }

    @Override
    public LoginService getLoginService() {
        return this._configuration.getLoginService();
    }

    @Override
    public IdentityService getIdentityService() {
        return this._configuration.getIdentityService();
    }

    @Override
    public boolean isSessionRenewedOnAuthentication() {
        return this._configuration.isSessionRenewedOnAuthentication();
    }

    @Override
    public int getSessionMaxInactiveIntervalOnAuthentication() {
        return this._configuration.getSessionMaxInactiveIntervalOnAuthentication();
    }
}

