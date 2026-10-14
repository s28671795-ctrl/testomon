/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users.ldap;

import com.google.inject.Inject;
import com.incoresoft.dto.ldap.LDAPConnectionParams;
import com.incoresoft.dto.ldap.LDAPImportParams;
import com.incoresoft.dto.ldap.LDAPUserData;
import com.incoresoft.ldap.LdapAuthorizationResponse;
import com.incoresoft.ldap.LdapClient;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VezhaLdapClient
extends LdapClient {
    private static final Logger log = LoggerFactory.getLogger(VezhaLdapClient.class);
    private final UsersManagerImpl usersManager;

    @Override
    @Nullable
    public User addUser(LDAPImportParams importParams, LDAPUserData userData, int clientId) {
        try {
            return this.usersManager.addLdapUser(importParams, userData, clientId);
        }
        catch (Exception e) {
            log.error("Failed to import user {} from LDAP", (Object)userData.login(), (Object)e);
            return null;
        }
    }

    @Override
    public LdapAuthorizationResponse authorize(User user, String password) {
        UserImpl userImpl = (UserImpl)user;
        return VezhaLdapClient.authorize(new LDAPConnectionParams(userImpl.getLdapServer(), userImpl.getEmail(), password, userImpl.getLdapBaseDN(), "(userprincipalname={user})"));
    }

    @Inject
    public VezhaLdapClient(UsersManagerImpl usersManager) {
        this.usersManager = usersManager;
    }
}

