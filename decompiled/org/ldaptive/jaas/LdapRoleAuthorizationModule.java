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
import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapException;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchRequest;
import org.ldaptive.jaas.AbstractLoginModule;
import org.ldaptive.jaas.LdapDnPrincipal;
import org.ldaptive.jaas.LdapPrincipal;
import org.ldaptive.jaas.LdapRole;
import org.ldaptive.jaas.PropertiesRoleResolverFactory;
import org.ldaptive.jaas.RoleResolver;
import org.ldaptive.jaas.RoleResolverFactory;

public class LdapRoleAuthorizationModule
extends AbstractLoginModule {
    private String roleFilter;
    private String[] roleAttribute = ReturnAttributes.NONE.value();
    private boolean noResultsIsError;
    private RoleResolverFactory roleResolverFactory;
    private RoleResolver roleResolver;
    private SearchRequest searchRequest;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        super.initialize(subject, callbackHandler, sharedState, options);
        for (String key : options.keySet()) {
            String value = (String)options.get(key);
            if ("roleFilter".equalsIgnoreCase(key)) {
                this.roleFilter = value;
                continue;
            }
            if ("roleAttribute".equalsIgnoreCase(key)) {
                if ("".equals(value)) {
                    this.roleAttribute = ReturnAttributes.NONE.value();
                    continue;
                }
                if ("*".equals(value)) {
                    this.roleAttribute = ReturnAttributes.ALL_USER.value();
                    continue;
                }
                this.roleAttribute = value.split(",");
                continue;
            }
            if ("noResultsIsError".equalsIgnoreCase(key)) {
                this.noResultsIsError = Boolean.valueOf(value);
                continue;
            }
            if (!"roleResolverFactory".equalsIgnoreCase(key)) continue;
            try {
                this.roleResolverFactory = (RoleResolverFactory)Class.forName(value).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        if (this.roleResolverFactory == null) {
            this.roleResolverFactory = new PropertiesRoleResolverFactory();
        }
        this.logger.trace("roleResolverFactory = {}, roleFilter = {}, roleAttribute = {}, noResultsIsError = {}", this.roleResolverFactory, this.roleFilter, Arrays.toString(this.roleAttribute), this.noResultsIsError);
        this.roleResolver = this.roleResolverFactory.createRoleResolver(options);
        this.logger.debug("Retrieved role resolver from factory: {}", (Object)this.roleResolver);
        this.searchRequest = this.roleResolverFactory.createSearchRequest(options);
        this.searchRequest.setReturnAttributes(this.roleAttribute);
        this.logger.debug("Retrieved search request from factory: {}", (Object)this.searchRequest);
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
            if ((loginDn = (String)this.sharedState.get("org.ldaptive.jaas.login.entryDn")) != null && this.setLdapDnPrincipal) {
                this.principals.add(new LdapDnPrincipal(loginDn, null));
                this.loginSuccess = true;
            }
            FilterTemplate template = new FilterTemplate(this.roleFilter);
            template.setParameter("dn", (Object)loginDn);
            template.setParameter("user", (Object)loginName);
            this.searchRequest.setFilter(template);
            Set<LdapRole> lr = this.roleResolver.search(this.searchRequest);
            if (lr.isEmpty() && this.noResultsIsError) {
                this.loginSuccess = false;
                throw new LoginException("Could not find roles using " + this.roleFilter);
            }
            this.roles.addAll(lr);
            if (this.defaultRole != null && !this.defaultRole.isEmpty()) {
                this.roles.addAll(this.defaultRole);
            }
            if (!this.roles.isEmpty()) {
                this.loginSuccess = true;
            }
            this.storeCredentials(nameCb, passCb, null);
        }
        catch (LdapException e) {
            this.logger.debug("Error occurred attempting role lookup", e);
            this.loginSuccess = false;
            throw new LoginException(e.getMessage());
        }
        return true;
    }

    public static void main(String[] args2) throws Exception {
        String name = "ldaptive-role";
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

