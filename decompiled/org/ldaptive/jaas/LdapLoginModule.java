/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import com.sun.security.auth.callback.TextCallbackHandler;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.ldaptive.Credential;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.User;
import org.ldaptive.jaas.AbstractLoginModule;
import org.ldaptive.jaas.AuthenticatorFactory;
import org.ldaptive.jaas.LdapCredential;
import org.ldaptive.jaas.LdapDnPrincipal;
import org.ldaptive.jaas.LdapPrincipal;
import org.ldaptive.jaas.LdapRole;
import org.ldaptive.jaas.PropertiesAuthenticatorFactory;

public class LdapLoginModule
extends AbstractLoginModule {
    private String[] userRoleAttribute = ReturnAttributes.NONE.value();
    private AuthenticatorFactory authenticatorFactory;
    private Authenticator auth;
    private AuthenticationRequest authRequest;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.setLdapPrincipal = true;
        this.setLdapCredential = true;
        super.initialize(subject, callbackHandler, sharedState, options);
        for (String key : options.keySet()) {
            String value = (String)options.get(key);
            if ("userRoleAttribute".equalsIgnoreCase(key)) {
                if ("".equals(value)) {
                    this.userRoleAttribute = ReturnAttributes.NONE.value();
                    continue;
                }
                if ("*".equals(value)) {
                    this.userRoleAttribute = ReturnAttributes.ALL_USER.value();
                    continue;
                }
                this.userRoleAttribute = value.split(",");
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
        this.logger.trace("authenticatorFactory = {}, userRoleAttribute = {}", (Object)this.authenticatorFactory, (Object)Arrays.toString(this.userRoleAttribute));
        this.auth = this.authenticatorFactory.createAuthenticator(options);
        this.logger.debug("Retrieved authenticator from factory: {}", (Object)this.auth);
        this.authRequest = this.authenticatorFactory.createAuthenticationRequest(options);
        this.authRequest.setReturnAttributes(this.userRoleAttribute);
        this.logger.debug("Retrieved authentication request from factory: {}", (Object)this.authRequest);
    }

    @Override
    protected boolean login(NameCallback nameCb, PasswordCallback passCb) throws LoginException {
        try {
            String loginDn;
            this.getCredentials(nameCb, passCb, false);
            this.authRequest.setUser(new User(nameCb.getName()));
            this.authRequest.setCredential(new Credential(passCb.getPassword()));
            AuthenticationResponse response = this.auth.authenticate(this.authRequest);
            LdapEntry entry = null;
            if (response.isSuccess()) {
                entry = response.getLdapEntry();
                if (entry != null) {
                    this.roles.addAll(LdapRole.toRoles(entry));
                    if (this.defaultRole != null && !this.defaultRole.isEmpty()) {
                        this.roles.addAll(this.defaultRole);
                    }
                }
                this.loginSuccess = true;
            } else if (this.tryFirstPass) {
                this.getCredentials(nameCb, passCb, true);
                response = this.auth.authenticate(this.authRequest);
                if (response.isSuccess()) {
                    entry = response.getLdapEntry();
                    if (entry != null) {
                        this.roles.addAll(LdapRole.toRoles(entry));
                    }
                    if (this.defaultRole != null && !this.defaultRole.isEmpty()) {
                        this.roles.addAll(this.defaultRole);
                    }
                    this.loginSuccess = true;
                } else {
                    this.loginSuccess = false;
                }
            } else {
                this.loginSuccess = false;
            }
            if (!this.loginSuccess) {
                this.logger.debug("Authentication failed: " + response);
                throw new LoginException("Authentication failed: " + response);
            }
            if (this.setLdapPrincipal) {
                this.principals.add(new LdapPrincipal(nameCb.getName(), entry));
            }
            if ((loginDn = response.getResolvedDn()) != null && this.setLdapDnPrincipal) {
                this.principals.add(new LdapDnPrincipal(loginDn, entry));
            }
            if (this.setLdapCredential) {
                this.credentials.add(new LdapCredential(passCb.getPassword()));
            }
            this.storeCredentials(nameCb, passCb, loginDn);
        }
        catch (LdapException e) {
            this.logger.debug("Error occurred attempting authentication", e);
            this.loginSuccess = false;
            throw new LoginException(e.getMessage());
        }
        return true;
    }

    public static void main(String[] args2) throws Exception {
        String name = "ldaptive";
        if (args2.length > 0) {
            name = args2[0];
        }
        LoginContext lc = new LoginContext(name, new TextCallbackHandler());
        lc.login();
        System.out.println("Authentication/Authorization succeeded");
        Set<Principal> principals = lc.getSubject().getPrincipals();
        System.out.println("Subject Principal(s): ");
        for (Principal p : principals) {
            System.out.println("  " + p);
        }
        lc.logout();
    }
}

