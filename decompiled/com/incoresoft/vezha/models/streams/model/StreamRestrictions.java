/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.model;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.permissions.RoleUserRestrictionsImpl;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StreamRestrictions
extends RoleUserRestrictionsImpl {
    private static final Set<Permission> STREAM_PERMISSIONS = Collections.unmodifiableSet(new LinkedHashSet<CorePermissions>(List.of(CorePermissions.ViewStreams, CorePermissions.ManageStreams)));

    public StreamRestrictions(@Nullable User user, @NotNull Map<Role, Set<Permission>> grantedRoleRestrictions, @NotNull Map<User, Set<Permission>> grantedUserRestrictions, @NotNull Set<Permission> grantedDefaultRestrictions) {
        super(user, STREAM_PERMISSIONS, grantedRoleRestrictions, grantedUserRestrictions, grantedDefaultRestrictions);
    }

    public StreamRestrictions(@Nullable User user, @NotNull Map<Role, Set<Permission>> grantedRoleRestrictions, @NotNull Map<User, Set<Permission>> grantedUserRestrictions) {
        this(user, grantedRoleRestrictions, grantedUserRestrictions, STREAM_PERMISSIONS);
    }

    public StreamRestrictions(@Nullable User user) {
        this(user, Collections.emptyMap(), Collections.emptyMap());
    }

    public StreamRestrictions() {
        this(null);
    }

    public static class Converter {
        private final RolesManager roleManager;
        private final UsersManager usersManager;

        @Inject
        public Converter(@NotNull RolesManager roleManager, @NotNull UsersManager usersManager) {
            this.roleManager = Objects.requireNonNull(roleManager);
            this.usersManager = Objects.requireNonNull(usersManager);
        }

        @NotNull
        public StreamRestrictions convert(@Nullable ItemRestrictionsDTO itemRestrictionsDTO) {
            if (itemRestrictionsDTO == null) {
                return new StreamRestrictions();
            }
            Integer creatorId = itemRestrictionsDTO.getCreatorId();
            User creator = null;
            if (creatorId != null && (creator = (User)this.usersManager.get(creatorId)) == null) {
                creator = (User)this.usersManager.get(1);
            }
            return this.convert(creator, itemRestrictionsDTO);
        }

        @NotNull
        public StreamRestrictions convert(@Nullable User creator, @Nullable ItemRestrictionsDTO itemRestrictionsDTO) {
            if (itemRestrictionsDTO == null) {
                return new StreamRestrictions();
            }
            Map allPermissions = this.roleManager.getPermissions().stream().collect(Collectors.toUnmodifiableMap(Permission::getId, Function.identity()));
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
            return new StreamRestrictions(creator, rolePermissions, userPermissions, defaultPermissions);
        }
    }

    public static class Serializer
    implements ObjectSerializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            if (object instanceof StreamRestrictions) {
                serializer.write(new ItemRestrictionsDTO((RoleUserRestrictions)object));
            } else {
                serializer.writeNull();
            }
        }
    }
}

