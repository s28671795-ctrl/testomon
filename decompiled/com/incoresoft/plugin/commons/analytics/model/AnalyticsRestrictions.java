/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.model;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.permissions.RoleUserRestrictionsImpl;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnalyticsRestrictions
extends RoleUserRestrictionsImpl {
    public AnalyticsRestrictions(@Nullable User user, @NotNull Set<Permission> eligiblePermissions, @NotNull Map<Role, Set<Permission>> grantedRoleRestrictions, @NotNull Map<User, Set<Permission>> grantedUserRestrictions, @NotNull Set<Permission> grantedDefaultRestrictions) {
        super(user, eligiblePermissions, grantedRoleRestrictions, grantedUserRestrictions, grantedDefaultRestrictions);
    }

    public AnalyticsRestrictions(Set<Permission> defaultPermissions) {
        this(null, defaultPermissions, Collections.emptyMap(), Collections.emptyMap(), defaultPermissions);
    }
}

