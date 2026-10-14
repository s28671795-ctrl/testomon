/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.role;

import com.google.inject.Inject;
import com.incoresoft.extensions.ExtensionListener;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.permission.PermissionGroup;
import com.incoresoft.middleware.model.permission.PluginPermissions;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.plugin.InitializableExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.vezha.database.RolesRepository;
import com.incoresoft.vezha.models.role.MissedPermissionsValidationError;
import com.incoresoft.vezha.models.role.PermissionUtils;
import com.incoresoft.vezha.models.role.RoleImpl;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleManagerImpl
implements RolesManager,
ExtensionListener {
    private static final Logger log = LoggerFactory.getLogger(RoleManagerImpl.class);
    public static final RoleImpl ROOT = new RoleImpl(1, "root");
    private final Map<Integer, RoleImpl> data = new ConcurrentHashMap<Integer, RoleImpl>();
    private final Set<Permission> allPermissions = new ConcurrentSkipListSet<Permission>(Comparator.comparing(Permission::getId));
    private final RolesRepository repository;

    public static boolean isRoot(int roleId) {
        return ROOT.getId() == roleId;
    }

    @Inject
    public void init() {
        List<RoleImpl> roles = this.repository.getRoles();
        roles.forEach(r -> this.data.put(r.getId(), (RoleImpl)r));
        this.allPermissions.addAll(Arrays.stream(CorePermissions.values()).collect(Collectors.toCollection(LinkedList::new)));
        this.validatePermissions();
    }

    public void validatePermissions() {
        for (RoleImpl role : this.data.values()) {
            if (role.getId() == ROOT.getId()) {
                role.setPermissions(this.allPermissions.stream().map(Permission::getId).collect(Collectors.toSet()));
                continue;
            }
            Set<Permission> savedPermissions = this.allPermissions.stream().filter(it -> role.getPermissions().contains(it.getId())).collect(Collectors.toUnmodifiableSet());
            Optional<MissedPermissionsValidationError> validationResult = PermissionUtils.validate(savedPermissions);
            if (validationResult.isEmpty()) {
                role.setPermissions(savedPermissions.stream().map(Permission::getId).collect(Collectors.toSet()));
                continue;
            }
            throw new IllegalArgumentException(String.format("Permission has unsatisfied dependencies. Permission id - %s, permission dependencies - %s", validationResult.get().getTarget().getId(), validationResult.get().getMissed().stream().map(Permission::getId).collect(Collectors.joining(", "))));
        }
    }

    public Role addRole(Role role) {
        return this.upsertRole(role);
    }

    public RoleImpl upsertRole(Role role) {
        RoleImpl upsertRole = this.repository.upsertRole(role);
        this.data.put(upsertRole.getId(), upsertRole);
        return upsertRole;
    }

    public Role deleteRole(Integer roleId) {
        this.repository.deleteRole(roleId);
        return this.data.remove(roleId);
    }

    @Override
    public Collection<? extends Permission> getPermissions() {
        return this.allPermissions;
    }

    @Override
    public Role get(Integer id) {
        return this.data.get(id);
    }

    @Override
    public Collection<? extends Role> getAll() {
        return this.data.values().stream().toList();
    }

    @Override
    public Role add(Role role) {
        return this.addRole(role);
    }

    @Override
    public Role update(Role role) {
        return this.upsertRole(role);
    }

    @Override
    public Role delete(Integer id) {
        return this.deleteRole(id);
    }

    @Override
    public Role delete(Role role) {
        return this.delete(role.getId());
    }

    @Override
    public void onExtensionInit(InitializableExtension<?> extension, String pluginId) {
        if (extension instanceof VezhaExtension) {
            VezhaExtension vezhaExtension = (VezhaExtension)extension;
            PluginPermissions pluginPermission = vezhaExtension.customPermissions();
            if (pluginPermission == null) {
                return;
            }
            if (pluginPermission.getPermissions() != null) {
                this.allPermissions.addAll(pluginPermission.getPermissions());
            }
            if (pluginPermission.getGroups() != null) {
                for (PermissionGroup group : pluginPermission.getGroups()) {
                    if (group == null || group.getPermissions() == null) continue;
                    this.allPermissions.addAll(group.getPermissions());
                }
            }
            this.init();
        }
    }

    @Override
    public void onExtensionTerminate(InitializableExtension<?> extension, String pluginId) {
        if (extension instanceof VezhaExtension) {
            VezhaExtension vezhaExtension = (VezhaExtension)extension;
            PluginPermissions pluginPermission = vezhaExtension.customPermissions();
            this.allPermissions.removeAll(pluginPermission.getPermissions());
            for (PermissionGroup group : pluginPermission.getGroups()) {
                this.allPermissions.removeAll(group.getPermissions());
            }
            this.init();
        }
    }

    @Inject
    public RoleManagerImpl(RolesRepository repository) {
        this.repository = repository;
    }
}

