/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.ldap;

import com.incoresoft.dto.ldap.LDAPConnectionParams;
import com.incoresoft.dto.ldap.LDAPImportParams;
import com.incoresoft.dto.ldap.LDAPUserData;
import com.incoresoft.errors.CoreErrors;
import com.incoresoft.ldap.LdapAuthorizationResponse;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.ConnectException;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.Credential;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.ResultCode;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.SearchDnResolver;
import org.ldaptive.auth.SimpleBindAuthenticationHandler;
import org.ldaptive.auth.User;
import org.ldaptive.filter.FilterParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LdapClient {
    private static final Logger log = LoggerFactory.getLogger(LdapClient.class);
    public static final Duration TIMEOUT_DURATION = Duration.ofSeconds(5L);
    protected static final String AUTH_FILTER = "(userprincipalname={user})";

    public LdapAuthorizationResponse testConnection(LDAPConnectionParams connectionParams) {
        return LdapClient.authorize(connectionParams);
    }

    public List<com.incoresoft.middleware.model.user.User> importUsers(com.incoresoft.middleware.model.user.User user, LDAPImportParams importParams) {
        List<LDAPUserData> fetchedUsers = this.fetchAllLdapUsers(importParams.connectionParams());
        return fetchedUsers.stream().filter(it -> importParams.importParams().sids().contains(it.sid())).map(it -> this.addUser(importParams, (LDAPUserData)it, user.getClientId())).filter(Objects::nonNull).map(com.incoresoft.middleware.model.user.User.class::cast).toList();
    }

    @Nullable
    public abstract com.incoresoft.middleware.model.user.User addUser(LDAPImportParams var1, LDAPUserData var2, int var3);

    public abstract LdapAuthorizationResponse authorize(com.incoresoft.middleware.model.user.User var1, String var2);

    protected static LdapAuthorizationResponse authorize(LDAPConnectionParams connectionParams) {
        ConnectionConfig connectionConfig = LdapClient.createConnectionConfig(connectionParams);
        DefaultConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
        SearchDnResolver dnResolver = SearchDnResolver.builder().factory(connectionFactory).dn(connectionParams.base()).filter(AUTH_FILTER).subtreeSearch(true).build();
        SimpleBindAuthenticationHandler authHandler = new SimpleBindAuthenticationHandler(connectionFactory);
        Authenticator authenticator = new Authenticator(dnResolver, authHandler);
        try {
            return new LdapAuthorizationResponse(authenticator.authenticate(new AuthenticationRequest(connectionParams.login(), new Credential(connectionParams.password()))));
        }
        catch (ConnectException e) {
            if (e.getResultCode() == ResultCode.SERVER_DOWN) {
                return LdapAuthorizationResponse.connectionError();
            }
            if (e.getResultCode() == ResultCode.CONNECT_ERROR) {
                return LdapAuthorizationResponse.invalidCredentials();
            }
            return LdapAuthorizationResponse.invalidDn();
        }
        catch (IllegalArgumentException | LdapException e) {
            return LdapAuthorizationResponse.invalidDn();
        }
    }

    public List<LDAPUserData> fetchAllLdapUsers(LDAPConnectionParams connectionParams) {
        SearchResponse searchResponse;
        DefaultConnectionFactory connectionFactory;
        block6: {
            ConnectionConfig connectionConfig = LdapClient.createConnectionConfig(connectionParams);
            connectionFactory = new DefaultConnectionFactory(connectionConfig);
            SearchOperation searchOperation = new SearchOperation((ConnectionFactory)connectionFactory, connectionParams.base());
            String filter2 = StringUtils.isNotBlank(connectionParams.filter()) ? connectionParams.filter() : "(&(objectClass=user)(objectCategory=person))";
            searchResponse = null;
            try {
                searchResponse = searchOperation.execute(filter2);
            }
            catch (FilterParseException e) {
                throw new ValidationException(CoreErrors.INVALID_LDAP_FILTER, "filter", new Object[0]);
            }
            catch (LdapException e) {
                if (e.getResultCode() == ResultCode.SERVER_DOWN) {
                    throw new ValidationException(CoreErrors.LDAP_SERVER_CONNECTION_FAILED, "url", new Object[0]);
                }
                if (e.getResultCode() != ResultCode.CONNECT_ERROR) break block6;
                throw new ValidationException(CoreErrors.WRONG_LDAP_LOGIN_OR_PASSWORD, "login", new Object[0]);
            }
        }
        try {
            SearchDnResolver.builder().factory(connectionFactory).dn(connectionParams.base()).filter(AUTH_FILTER).build().resolve(new User(connectionParams.login()));
        }
        catch (LdapException e) {
            throw new ValidationException(CoreErrors.INVALID_LDAP_BASE, "base", new Object[0]);
        }
        return ((Collection)Optional.ofNullable(searchResponse).map(SearchResponse::getEntries).orElse(List.of())).stream().filter(it -> it.getAttribute("userPrincipalName") != null).map(LDAPUserData::new).toList();
    }

    private static ConnectionConfig createConnectionConfig(LDAPConnectionParams connectionParams) {
        return ConnectionConfig.builder().url(connectionParams.url()).connectTimeout(TIMEOUT_DURATION).responseTimeout(TIMEOUT_DURATION).connectionInitializers(BindConnectionInitializer.builder().dn(connectionParams.login()).credential(connectionParams.password()).build()).build();
    }
}

