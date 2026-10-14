/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.analytics.model.AnalyticsRestrictions;
import com.incoresoft.plugin.commons.permissions.RestrictionsConverter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoreAnalyticsRestrictionsConverter
extends RestrictionsConverter {
    private final Set<Permission> checkedPermissions;
    private final ItemRestrictionsDTO itemRestrictionsDTO;

    public CoreAnalyticsRestrictionsConverter(RolesManager rolesManager, UsersManager usersManager, ItemRestrictionsDTO itemRestrictionsDTO) {
        super(rolesManager, usersManager);
        HashSet<String> allPresentPermissions = new HashSet<String>();
        allPresentPermissions.addAll(itemRestrictionsDTO.getDefaultPermissions().keySet());
        allPresentPermissions.addAll(itemRestrictionsDTO.getRolePermissions().values().stream().flatMap(it -> it.keySet().stream()).collect(Collectors.toSet()));
        allPresentPermissions.addAll(itemRestrictionsDTO.getUserPermissions().values().stream().flatMap(it -> it.keySet().stream()).collect(Collectors.toSet()));
        this.checkedPermissions = rolesManager.getPermissions().stream().filter(it -> allPresentPermissions.contains(it.getId())).collect(Collectors.toSet());
        this.itemRestrictionsDTO = itemRestrictionsDTO;
    }

    public RoleUserRestrictions convert() {
        return this.convert(this.itemRestrictionsDTO);
    }

    protected Set<Permission> checkedPermissions() {
        return this.checkedPermissions;
    }

    @Override
    protected Set<Permission> eligiblePermissions() {
        return this.checkedPermissions;
    }

    @Override
    protected RoleUserRestrictions getRoleUserRestrictions() {
        return new AnalyticsRestrictions(this.checkedPermissions());
    }

    @Override
    protected RoleUserRestrictions getRoleUserRestrictions(@Nullable User user, @NotNull Map<Role, Set<Permission>> grantedRolePermissions, @NotNull Map<User, Set<Permission>> grantedUserPermissions, @NotNull Set<Permission> grantedDefaultRestrictions) {
        return new AnalyticsRestrictions(user, this.checkedPermissions(), grantedRolePermissions, grantedUserPermissions, grantedDefaultRestrictions);
    }
}

