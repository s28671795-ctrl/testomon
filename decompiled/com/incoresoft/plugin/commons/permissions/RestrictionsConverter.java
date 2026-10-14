/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.permissions;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RestrictionsConverter {
    private static final Logger log = LoggerFactory.getLogger(RestrictionsConverter.class);
    private static final AtomicReference<RestrictionsConverter> converterContainer = new AtomicReference();
    protected final RolesManager roleManager;
    protected final UsersManager usersManager;

    @Inject
    protected RestrictionsConverter(@NotNull RolesManager roleManager, @NotNull UsersManager usersManager) {
        this.roleManager = Objects.requireNonNull(roleManager);
        this.usersManager = Objects.requireNonNull(usersManager);
    }

    @Inject
    public static void setConverter(@NotNull RestrictionsConverter converter) {
        converterContainer.set(converter);
    }

    @NotNull
    public RoleUserRestrictions convert(@Nullable ItemRestrictionsDTO itemRestrictionsDTO) {
        if (itemRestrictionsDTO == null) {
            return this.getRoleUserRestrictions();
        }
        Integer creatorId = itemRestrictionsDTO.getCreatorId();
        User creator = null;
        if (creatorId != null && (creator = (User)this.usersManager.get(creatorId)) == null) {
            log.error(String.format("User with id %d does not exist. Item will be associated with admin", creatorId));
            creator = (User)this.usersManager.get(1);
        }
        return this.convert(creator, itemRestrictionsDTO);
    }

    @NotNull
    public RoleUserRestrictions convert(@Nullable User creator, @Nullable ItemRestrictionsDTO itemRestrictionsDTO) {
        if (itemRestrictionsDTO == null) {
            return this.getRoleUserRestrictions();
        }
        Map allPermissions = this.eligiblePermissions().stream().collect(Collectors.toUnmodifiableMap(Permission::getId, Function.identity()));
        HashMap<Role, Set<Permission>> rolePermissions = new HashMap<Role, Set<Permission>>();
        for (Map.Entry<Integer, Map<String, Boolean>> entry : itemRestrictionsDTO.getRolePermissions().entrySet()) {
            Role role = (Role)this.roleManager.get(entry.getKey());
            if (role == null) continue;
            Set currentRolePermissions = ((Map)entry.getValue()).entrySet().stream().filter(Map.Entry::getValue).map(it -> (Permission)allPermissions.get(it.getKey())).filter(Objects::nonNull).collect(Collectors.toUnmodifiableSet());
            rolePermissions.put(role, currentRolePermissions);
        }
        HashMap<User, Set<Permission>> userPermissions = new HashMap<User, Set<Permission>>();
        for (Map.Entry entry : itemRestrictionsDTO.getUserPermissions().entrySet()) {
            User user = (User)this.usersManager.get((Integer)entry.getKey());
            if (user == null) continue;
            Set currentUserPermissions = ((Map)entry.getValue()).entrySet().stream().filter(Map.Entry::getValue).map(it -> (Permission)allPermissions.get(it.getKey())).filter(Objects::nonNull).collect(Collectors.toUnmodifiableSet());
            userPermissions.put(user, currentUserPermissions);
        }
        Set<Permission> defaultPermissions = itemRestrictionsDTO.getDefaultPermissions().entrySet().stream().filter(Map.Entry::getValue).map(it -> (Permission)allPermissions.get(it.getKey())).filter(Objects::nonNull).collect(Collectors.toUnmodifiableSet());
        return this.getRoleUserRestrictions(creator, rolePermissions, userPermissions, defaultPermissions);
    }

    @NotNull
    public static RoleUserRestrictions fromDTO(@Nullable ItemRestrictionsDTO itemRestrictionsDTO) {
        if (converterContainer.get() == null) {
            throw new RuntimeException("Restrictions converter must be set via setConverter method first.");
        }
        return converterContainer.get().convert(itemRestrictionsDTO);
    }

    protected abstract Set<Permission> eligiblePermissions();

    protected abstract RoleUserRestrictions getRoleUserRestrictions();

    protected abstract RoleUserRestrictions getRoleUserRestrictions(@Nullable User var1, @NotNull Map<Role, Set<Permission>> var2, @NotNull Map<User, Set<Permission>> var3, @NotNull Set<Permission> var4);
}

