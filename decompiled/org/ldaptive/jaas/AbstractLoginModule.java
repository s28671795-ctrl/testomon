/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import org.ldaptive.jaas.LdapCredential;
import org.ldaptive.jaas.LdapDnPrincipal;
import org.ldaptive.jaas.LdapGroup;
import org.ldaptive.jaas.LdapPrincipal;
import org.ldaptive.jaas.LdapRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLoginModule
implements LoginModule {
    public static final String LOGIN_NAME = "javax.security.auth.login.name";
    public static final String LOGIN_DN = "org.ldaptive.jaas.login.entryDn";
    public static final String LOGIN_PASSWORD = "javax.security.auth.login.password";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final List<LdapRole> defaultRole = new ArrayList<LdapRole>();
    protected Subject subject;
    protected CallbackHandler callbackHandler;
    protected Map sharedState;
    protected boolean useFirstPass;
    protected boolean tryFirstPass;
    protected boolean storePass;
    protected boolean clearPass;
    protected boolean setLdapPrincipal;
    protected boolean setLdapDnPrincipal;
    protected boolean setLdapCredential;
    protected String principalGroupName;
    protected String roleGroupName;
    protected boolean loginSuccess;
    protected boolean commitSuccess;
    protected Set<Principal> principals;
    protected Set<LdapCredential> credentials;
    protected Set<Principal> roles;

    @Override
    public void initialize(Subject subj, CallbackHandler handler, Map<String, ?> state, Map<String, ?> options) {
        this.logger.trace("begin initialize");
        this.subject = subj;
        this.callbackHandler = handler;
        this.sharedState = state;
        for (String key : options.keySet()) {
            String value = (String)options.get(key);
            if ("useFirstPass".equalsIgnoreCase(key)) {
                this.useFirstPass = Boolean.valueOf(value);
                continue;
            }
            if ("tryFirstPass".equalsIgnoreCase(key)) {
                this.tryFirstPass = Boolean.valueOf(value);
                continue;
            }
            if ("storePass".equalsIgnoreCase(key)) {
                this.storePass = Boolean.valueOf(value);
                continue;
            }
            if ("clearPass".equalsIgnoreCase(key)) {
                this.clearPass = Boolean.valueOf(value);
                continue;
            }
            if ("setLdapPrincipal".equalsIgnoreCase(key)) {
                this.setLdapPrincipal = Boolean.valueOf(value);
                continue;
            }
            if ("setLdapDnPrincipal".equalsIgnoreCase(key)) {
                this.setLdapDnPrincipal = Boolean.valueOf(value);
                continue;
            }
            if ("setLdapCredential".equalsIgnoreCase(key)) {
                this.setLdapCredential = Boolean.valueOf(value);
                continue;
            }
            if ("defaultRole".equalsIgnoreCase(key)) {
                for (String s2 : value.split(",")) {
                    this.defaultRole.add(new LdapRole(s2.trim()));
                }
                continue;
            }
            if ("principalGroupName".equalsIgnoreCase(key)) {
                this.principalGroupName = value;
                continue;
            }
            if (!"roleGroupName".equalsIgnoreCase(key)) continue;
            this.roleGroupName = value;
        }
        this.logger.trace("useFirstPass = {}, tryFirstPass = {}, storePass = {}, clearPass = {}, setLdapPrincipal = {}, setLdapDnPrincipal = {}, setLdapCredential = {}, defaultRole = {}, principalGroupName = {}, roleGroupName = {}", this.useFirstPass, this.tryFirstPass, this.storePass, this.clearPass, this.setLdapPrincipal, this.setLdapDnPrincipal, this.setLdapCredential, this.defaultRole, this.principalGroupName, this.roleGroupName);
        this.principals = new TreeSet<Principal>();
        this.credentials = new HashSet<LdapCredential>();
        this.roles = new TreeSet<Principal>();
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback nameCb = new NameCallback("Enter user: ");
        PasswordCallback passCb = new PasswordCallback("Enter user password: ", false);
        return this.login(nameCb, passCb);
    }

    protected abstract boolean login(NameCallback var1, PasswordCallback var2) throws LoginException;

    @Override
    public boolean commit() throws LoginException {
        LdapGroup group;
        this.logger.trace("begin commit");
        if (!this.loginSuccess) {
            this.logger.debug("Login failed");
            return false;
        }
        if (this.subject.isReadOnly()) {
            this.clearState();
            throw new LoginException("Subject is read-only.");
        }
        this.subject.getPrincipals().addAll(this.principals);
        this.logger.debug("Committed the following principals: {}", (Object)this.principals);
        this.subject.getPrivateCredentials().addAll(this.credentials);
        this.subject.getPrincipals().addAll(this.roles);
        this.logger.debug("Committed the following roles: {}", (Object)this.roles);
        if (this.principalGroupName != null) {
            group = new LdapGroup(this.principalGroupName);
            this.principals.forEach(group::addMember);
            this.subject.getPrincipals().add(group);
            this.logger.debug("Committed the following principal group: {}", (Object)group);
        }
        if (this.roleGroupName != null) {
            group = new LdapGroup(this.roleGroupName);
            this.roles.forEach(group::addMember);
            this.subject.getPrincipals().add(group);
            this.logger.debug("Committed the following role group: {}", (Object)group);
        }
        this.clearState();
        this.commitSuccess = true;
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        this.logger.trace("begin abort");
        if (!this.loginSuccess) {
            return false;
        }
        if (!this.commitSuccess) {
            this.loginSuccess = false;
            this.clearState();
        } else {
            this.logout();
        }
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        this.logger.trace("begin logout");
        if (this.subject.isReadOnly()) {
            this.clearState();
            throw new LoginException("Subject is read-only.");
        }
        for (LdapPrincipal ldapPrincipal : this.subject.getPrincipals(LdapPrincipal.class)) {
            this.subject.getPrincipals().remove(ldapPrincipal);
        }
        for (LdapDnPrincipal ldapDnPrincipal : this.subject.getPrincipals(LdapDnPrincipal.class)) {
            this.subject.getPrincipals().remove(ldapDnPrincipal);
        }
        for (LdapRole ldapRole : this.subject.getPrincipals(LdapRole.class)) {
            this.subject.getPrincipals().remove(ldapRole);
        }
        for (LdapGroup ldapGroup : this.subject.getPrincipals(LdapGroup.class)) {
            this.subject.getPrincipals().remove(ldapGroup);
        }
        for (LdapCredential ldapCredential : this.subject.getPrivateCredentials(LdapCredential.class)) {
            this.subject.getPrivateCredentials().remove(ldapCredential);
        }
        this.clearState();
        this.loginSuccess = false;
        this.commitSuccess = false;
        return true;
    }

    protected void clearState() {
        this.principals.clear();
        this.credentials.clear();
        this.roles.clear();
        if (this.clearPass) {
            this.sharedState.remove(LOGIN_NAME);
            this.sharedState.remove(LOGIN_PASSWORD);
            this.sharedState.remove(LOGIN_DN);
        }
    }

    protected void getCredentials(NameCallback nameCb, PasswordCallback passCb, boolean useCallback) throws LoginException {
        block5: {
            this.logger.trace("begin getCredentials: useFistPass = {}, tryFistPass = {}, useCallback = {}, callbackhandler class = {}, name callback class = {}, password callback class = {}", this.useFirstPass, this.tryFirstPass, useCallback, this.callbackHandler.getClass().getName(), nameCb.getClass().getName(), passCb.getClass().getName());
            try {
                if ((this.useFirstPass || this.tryFirstPass) && !useCallback) {
                    nameCb.setName((String)this.sharedState.get(LOGIN_NAME));
                    passCb.setPassword((char[])this.sharedState.get(LOGIN_PASSWORD));
                    break block5;
                }
                if (this.callbackHandler != null) {
                    this.callbackHandler.handle(new Callback[]{nameCb, passCb});
                    break block5;
                }
                throw new LoginException("No CallbackHandler available. Set useFirstPass, tryFirstPass, or provide a CallbackHandler");
            }
            catch (IOException e) {
                this.logger.error("Error reading data from callback handler", e);
                this.loginSuccess = false;
                throw new LoginException(e.getMessage());
            }
            catch (UnsupportedCallbackException e) {
                this.logger.error("Unsupported callback", e);
                this.loginSuccess = false;
                throw new LoginException(e.getMessage());
            }
        }
    }

    protected void storeCredentials(NameCallback nameCb, PasswordCallback passCb, String loginDn) {
        if (this.storePass) {
            if (nameCb != null && nameCb.getName() != null) {
                this.sharedState.put(LOGIN_NAME, nameCb.getName());
            }
            if (passCb != null && passCb.getPassword() != null) {
                this.sharedState.put(LOGIN_PASSWORD, passCb.getPassword());
            }
            if (loginDn != null) {
                this.sharedState.put(LOGIN_DN, loginDn);
            }
        }
    }
}

