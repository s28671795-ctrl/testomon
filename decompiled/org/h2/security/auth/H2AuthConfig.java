/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

import java.util.ArrayList;
import java.util.List;
import org.h2.security.auth.RealmConfig;
import org.h2.security.auth.UserToRolesMapperConfig;

public class H2AuthConfig {
    private boolean allowUserRegistration = true;
    private boolean createMissingRoles = true;
    private List<RealmConfig> realms;
    private List<UserToRolesMapperConfig> userToRolesMappers;

    public boolean isAllowUserRegistration() {
        return this.allowUserRegistration;
    }

    public void setAllowUserRegistration(boolean bl) {
        this.allowUserRegistration = bl;
    }

    public boolean isCreateMissingRoles() {
        return this.createMissingRoles;
    }

    public void setCreateMissingRoles(boolean bl) {
        this.createMissingRoles = bl;
    }

    public List<RealmConfig> getRealms() {
        if (this.realms == null) {
            this.realms = new ArrayList<RealmConfig>();
        }
        return this.realms;
    }

    public void setRealms(List<RealmConfig> list) {
        this.realms = list;
    }

    public List<UserToRolesMapperConfig> getUserToRolesMappers() {
        if (this.userToRolesMappers == null) {
            this.userToRolesMappers = new ArrayList<UserToRolesMapperConfig>();
        }
        return this.userToRolesMappers;
    }

    public void setUserToRolesMappers(List<UserToRolesMapperConfig> list) {
        this.userToRolesMappers = list;
    }
}

