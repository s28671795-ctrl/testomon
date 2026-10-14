/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.model;

import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.analytics.model.AnalyticsRestrictions;
import com.incoresoft.plugin.commons.permissions.RestrictionsConverter;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AnalyticsRestrictionsConverter
extends RestrictionsConverter {
    private final Set<Permission> eligiblePermissions;

    protected AnalyticsRestrictionsConverter(@NotNull RolesManager roleManager, @NotNull UsersManager usersManager, @NotNull Set<Permission> eligiblePermissions) {
        super(roleManager, usersManager);
        this.eligiblePermissions = Objects.requireNonNull(eligiblePermissions);
    }

    @Override
    protected Set<Permission> eligiblePermissions() {
        return this.eligiblePermissions;
    }

    @Override
    protected RoleUserRestrictions getRoleUserRestrictions() {
        return new AnalyticsRestrictions(this.eligiblePermissions);
    }

    @Override
    protected RoleUserRestrictions getRoleUserRestrictions(@Nullable User user, @NotNull Map<Role, Set<Permission>> grantedRoleRestrictions, @NotNull Map<User, Set<Permission>> grantedUserRestrictions, @NotNull Set<Permission> grantedDefaultRestrictions) {
        return new AnalyticsRestrictions(user, this.eligiblePermissions, grantedRoleRestrictions, grantedUserRestrictions, grantedDefaultRestrictions);
    }
}

