/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.role;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleImpl
implements Role {
    private int id;
    private String roleName;
    private String description;
    private Set<String> permissions;
    private int clientId;

    public RoleImpl(int id, String name, String description, Set<Permission> permissions, int clientId) {
        this.id = id;
        this.roleName = name;
        this.description = description;
        this.clientId = clientId;
        this.setPermissions(permissions.stream().map(Permission::getId).collect(Collectors.toSet()));
    }

    public RoleImpl(String name, String description, Set<Permission> permissions, int clientId) {
        this.roleName = name;
        this.description = description;
        this.clientId = clientId;
        this.setPermissions(permissions.stream().map(Permission::getId).collect(Collectors.toSet()));
    }

    public RoleImpl(int id, String name) {
        this.id = id;
        this.roleName = name;
        this.permissions = new HashSet<String>();
    }

    @Override
    public String getName() {
        return this.roleName;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission.getId());
    }

    @Override
    public boolean hasAllPermissions(Collection<? extends Permission> permissions) {
        if (permissions.isEmpty()) {
            return true;
        }
        return permissions.stream().allMatch(this::hasPermission);
    }

    @Override
    public boolean hasAnyPermission(Collection<? extends Permission> permissions) {
        if (permissions.isEmpty()) {
            return true;
        }
        return permissions.stream().anyMatch(this::hasPermission);
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getRoleName() {
        return this.roleName;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public Set<String> getPermissions() {
        return this.permissions;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RoleImpl)) {
            return false;
        }
        RoleImpl other = (RoleImpl)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        String this$roleName = this.getRoleName();
        String other$roleName = other.getRoleName();
        if (this$roleName == null ? other$roleName != null : !this$roleName.equals(other$roleName)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        Collection this$permissions = this.getPermissions();
        Collection other$permissions = other.getPermissions();
        return !(this$permissions == null ? other$permissions != null : !((Object)this$permissions).equals(other$permissions));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RoleImpl;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getClientId();
        String $roleName = this.getRoleName();
        result = result * 59 + ($roleName == null ? 43 : $roleName.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Collection $permissions = this.getPermissions();
        result = result * 59 + ($permissions == null ? 43 : ((Object)$permissions).hashCode());
        return result;
    }

    public String toString() {
        return "RoleImpl(id=" + this.getId() + ", roleName=" + this.getRoleName() + ", description=" + this.getDescription() + ", permissions=" + String.valueOf(this.getPermissions()) + ", clientId=" + this.getClientId() + ")";
    }

    public RoleImpl() {
    }
}

