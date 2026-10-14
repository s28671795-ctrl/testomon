/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth.impl;

import java.util.Arrays;
import java.util.Collection;
import org.h2.api.UserToRolesMapper;
import org.h2.security.auth.AuthenticationException;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.security.auth.ConfigProperties;

public class AssignRealmNameRole
implements UserToRolesMapper {
    private String roleNameFormat;

    public AssignRealmNameRole() {
        this("@%s");
    }

    public AssignRealmNameRole(String string) {
        this.roleNameFormat = string;
    }

    @Override
    public void configure(ConfigProperties configProperties) {
        this.roleNameFormat = configProperties.getStringValue("roleNameFormat", this.roleNameFormat);
    }

    @Override
    public Collection<String> mapUserToRoles(AuthenticationInfo authenticationInfo) throws AuthenticationException {
        return Arrays.asList(String.format(this.roleNameFormat, authenticationInfo.getRealm()));
    }
}

