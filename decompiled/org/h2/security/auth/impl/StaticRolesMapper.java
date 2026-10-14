/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import org.h2.api.UserToRolesMapper;
import org.h2.security.auth.AuthenticationException;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.security.auth.ConfigProperties;

public class StaticRolesMapper
implements UserToRolesMapper {
    private Collection<String> roles;

    public StaticRolesMapper() {
    }

    public StaticRolesMapper(String ... stringArray) {
        this.roles = Arrays.asList(stringArray);
    }

    @Override
    public void configure(ConfigProperties configProperties) {
        String string = configProperties.getStringValue("roles", "");
        if (string != null) {
            this.roles = new HashSet<String>(Arrays.asList(string.split(",")));
        }
    }

    @Override
    public Collection<String> mapUserToRoles(AuthenticationInfo authenticationInfo) throws AuthenticationException {
        return this.roles;
    }
}

