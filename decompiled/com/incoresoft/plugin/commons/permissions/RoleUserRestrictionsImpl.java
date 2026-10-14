/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.permissions;

import com.alibaba.fastjson.JSON;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.user.User;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RoleUserRestrictionsImpl
implements RoleUserRestrictions {
    private final User user;
    private final Set<Permission> eligiblePermissions;
    private final Map<Role, Set<Permission>> grantedRoleRestrictions;
    private final Map<User, Set<Permission>> grantedUserRestrictions;
    private final Set<Permission> grantedDefaultRestrictions;
    private final Set<String> eligiblePermissionIds;
    private final Map<Integer, Set<String>> grantedRoleRestrictionsIds;
    private final Map<Integer, Set<String>> grantedUserRestrictionsIds;
    private final Set<String> grantedDefaultRestrictionsIds;

    public RoleUserRestrictionsImpl(@Nullable User user, @NotNull Set<Permission> eligiblePermissions, @NotNull Map<Role, Set<Permission>> grantedRoleRestrictions, @NotNull Map<User, Set<Permission>> grantedUserRestrictions, @NotNull Set<Permission> grantedDefaultRestrictions) {
        this.user = user;
        this.eligiblePermissions = Objects.requireNonNull(eligiblePermissions);
        this.grantedRoleRestrictions = Objects.requireNonNull(grantedRoleRestrictions);
        this.grantedUserRestrictions = Objects.requireNonNull(grantedUserRestrictions);
        this.grantedDefaultRestrictions = Objects.requireNonNull(grantedDefaultRestrictions);
        this.eligiblePermissionIds = RoleUserRestrictionsImpl.convertToIdsSet(eligiblePermissions);
        this.grantedRoleRestrictionsIds = RoleUserRestrictionsImpl.convertRoleRestrictionsMap(eligiblePermissions, grantedRoleRestrictions);
        this.grantedUserRestrictionsIds = RoleUserRestrictionsImpl.convertUserRestrictionsMap(eligiblePermissions, grantedUserRestrictions);
        this.grantedDefaultRestrictionsIds = RoleUserRestrictionsImpl.convertToFilteredIdsSet(eligiblePermissions, grantedDefaultRestrictions);
    }

    @Override
    public Optional<User> itemCreator() {
        return Optional.ofNullable(this.user);
    }

    @Override
    public Set<Permission> eligiblePermissions() {
        return this.eligiblePermissions;
    }

    @Override
    public Map<Role, Set<Permission>> grantedRoleRestrictions() {
        return this.grantedRoleRestrictions;
    }

    @Override
    public Map<User, Set<Permission>> grantedUserRestrictions() {
        return this.grantedUserRestrictions;
    }

    @Override
    public Set<Permission> grantedDefaultRestrictions() {
        return this.grantedDefaultRestrictions;
    }

    @Override
    public boolean isAllowed(@NotNull User user, @NotNull Permission permission) {
        return this.isAllowed(user, permission.getId());
    }

    @Override
    public boolean isAllowed(@NotNull User user, @NotNull String permissionId) {
        if (user.isRoot()) {
            return true;
        }
        if (!this.eligiblePermissionIds.contains(permissionId)) {
            return true;
        }
        if (this.grantedRoleRestrictionsIds.isEmpty() && this.grantedUserRestrictionsIds.isEmpty()) {
            return this.grantedDefaultRestrictionsIds.contains(permissionId);
        }
        boolean permissionsSpecifiedForUser = this.grantedUserRestrictionsIds.containsKey(user.getId());
        boolean allowedForUser = permissionsSpecifiedForUser && this.grantedUserRestrictionsIds.get(user.getId()).contains(permissionId);
        boolean allowedForRole = this.grantedRoleRestrictionsIds.containsKey(user.getRoleId()) && this.grantedRoleRestrictionsIds.get(user.getRoleId()).contains(permissionId);
        return permissionsSpecifiedForUser ? allowedForUser : allowedForRole;
    }

    @NotNull
    private static Map<Integer, Set<String>> convertUserRestrictionsMap(@NotNull Set<Permission> allowedPermissions, @NotNull Map<User, Set<Permission>> map) {
        return map.entrySet().stream().collect(Collectors.toUnmodifiableMap(it -> ((User)it.getKey()).getId(), it -> RoleUserRestrictionsImpl.convertToFilteredIdsSet(allowedPermissions, (Set)it.getValue())));
    }

    @NotNull
    private static Map<Integer, Set<String>> convertRoleRestrictionsMap(@NotNull Set<Permission> allowedPermissions, @NotNull Map<Role, Set<Permission>> map) {
        return map.entrySet().stream().collect(Collectors.toUnmodifiableMap(it -> ((Role)it.getKey()).getId(), it -> RoleUserRestrictionsImpl.convertToFilteredIdsSet(allowedPermissions, (Set)it.getValue())));
    }

    @NotNull
    private static Set<String> convertToFilteredIdsSet(@NotNull Set<Permission> allowedPermissions, @NotNull Set<Permission> permissions) {
        Set<String> permissionIdsSet;
        Set<String> allowedPermissionsIds = RoleUserRestrictionsImpl.convertToIdsSet(allowedPermissions);
        if (!allowedPermissionsIds.containsAll(permissionIdsSet = RoleUserRestrictionsImpl.convertToIdsSet(permissions))) {
            throw new IllegalArgumentException(String.format("Excluded permissions found. Allowed permissions: %s. Passed permissions %s", JSON.toJSONString(allowedPermissionsIds), JSON.toJSONString(permissionIdsSet)));
        }
        return permissionIdsSet;
    }

    @NotNull
    private static Set<String> convertToIdsSet(@NotNull Set<Permission> permissions) {
        return Collections.unmodifiableSet(permissions.stream().map(Permission::getId).collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public User getUser() {
        return this.user;
    }

    public Set<Permission> getEligiblePermissions() {
        return this.eligiblePermissions;
    }

    public Map<Role, Set<Permission>> getGrantedRoleRestrictions() {
        return this.grantedRoleRestrictions;
    }

    public Map<User, Set<Permission>> getGrantedUserRestrictions() {
        return this.grantedUserRestrictions;
    }

    public Set<Permission> getGrantedDefaultRestrictions() {
        return this.grantedDefaultRestrictions;
    }
}

