/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import com.sun.security.auth.callback.TextCallbackHandler;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.ldaptive.LdapException;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.User;
import org.ldaptive.jaas.AbstractLoginModule;
import org.ldaptive.jaas.AuthenticatorFactory;
import org.ldaptive.jaas.LdapDnPrincipal;
import org.ldaptive.jaas.LdapPrincipal;
import org.ldaptive.jaas.PropertiesAuthenticatorFactory;

public class LdapDnAuthorizationModule
extends AbstractLoginModule {
    private boolean noResultsIsError;
    private AuthenticatorFactory authenticatorFactory;
    private Authenticator auth;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        super.initialize(subject, callbackHandler, sharedState, options);
        for (String key : options.keySet()) {
            String value = (String)options.get(key);
            if ("noResultsIsError".equalsIgnoreCase(key)) {
                this.noResultsIsError = Boolean.valueOf(value);
                continue;
            }
            if (!"authenticatorFactory".equalsIgnoreCase(key)) continue;
            try {
                this.authenticatorFactory = (AuthenticatorFactory)Class.forName(value).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        if (this.authenticatorFactory == null) {
            this.authenticatorFactory = new PropertiesAuthenticatorFactory();
        }
        this.logger.trace("authenticatorFactory = {}, noResultsIsError = {}", (Object)this.authenticatorFactory, (Object)this.noResultsIsError);
        this.auth = this.authenticatorFactory.createAuthenticator(options);
        this.logger.debug("Retrieved authenticator from factory: {}", (Object)this.auth);
    }

    @Override
    protected boolean login(NameCallback nameCb, PasswordCallback passCb) throws LoginException {
        try {
            String loginDn;
            String loginName;
            this.getCredentials(nameCb, passCb, false);
            if (nameCb.getName() == null && this.tryFirstPass) {
                this.getCredentials(nameCb, passCb, true);
            }
            if ((loginName = nameCb.getName()) != null && this.setLdapPrincipal) {
                this.principals.add(new LdapPrincipal(loginName, null));
                this.loginSuccess = true;
            }
            if ((loginDn = this.auth.resolveDn(new User(nameCb.getName()))) == null && this.noResultsIsError) {
                this.loginSuccess = false;
                throw new LoginException("Could not find DN for " + nameCb.getName());
            }
            if (loginDn != null && this.setLdapDnPrincipal) {
                this.principals.add(new LdapDnPrincipal(loginDn, null));
                this.loginSuccess = true;
            }
            if (this.defaultRole != null && !this.defaultRole.isEmpty()) {
                this.roles.addAll(this.defaultRole);
                this.loginSuccess = true;
            }
            this.storeCredentials(nameCb, passCb, loginDn);
        }
        catch (LdapException e) {
            this.logger.debug("Error occurred attempting DN lookup", e);
            this.loginSuccess = false;
            throw new LoginException(e.getMessage());
        }
        return true;
    }

    public static void main(String[] args2) throws Exception {
        String name = "ldaptive-dn";
        if (args2.length > 0) {
            name = args2[0];
        }
        LoginContext lc = new LoginContext(name, new TextCallbackHandler());
        lc.login();
        Set<Principal> principals = lc.getSubject().getPrincipals();
        System.out.println("Subject Principal(s): ");
        for (Principal p : principals) {
            System.out.println("  " + p.getName());
        }
        lc.logout();
    }
}

