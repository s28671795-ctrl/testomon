/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.roles;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.vezha.dto.roles.PermissionDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.List;

@Schema(name="PermissionGroupDTO", description="Permission group class structure")
public final class PermissionGroupDTO {
    @JSONField(name="group_name")
    @Schema(name="group_name", description="Localized name of this group", implementation=String.class, example="Settings")
    private final String groupName;
    @JSONField(name="permissions")
    @ArraySchema(arraySchema=@Schema(description="List of permissions in this group"), schema=@Schema(implementation=PermissionDTO.class))
    private final List<PermissionDTO> permissions;

    public PermissionGroupDTO(String groupName, Collection<? extends Permission> permissions) {
        this.groupName = groupName;
        this.permissions = permissions.stream().map(PermissionDTO::new).toList();
    }

    public String getGroupName() {
        return this.groupName;
    }

    public List<PermissionDTO> getPermissions() {
        return this.permissions;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PermissionGroupDTO)) {
            return false;
        }
        PermissionGroupDTO other = (PermissionGroupDTO)o;
        String this$groupName = this.getGroupName();
        String other$groupName = other.getGroupName();
        if (this$groupName == null ? other$groupName != null : !this$groupName.equals(other$groupName)) {
            return false;
        }
        List<PermissionDTO> this$permissions = this.getPermissions();
        List<PermissionDTO> other$permissions = other.getPermissions();
        return !(this$permissions == null ? other$permissions != null : !((Object)this$permissions).equals(other$permissions));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $groupName = this.getGroupName();
        result = result * 59 + ($groupName == null ? 43 : $groupName.hashCode());
        List<PermissionDTO> $permissions = this.getPermissions();
        result = result * 59 + ($permissions == null ? 43 : ((Object)$permissions).hashCode());
        return result;
    }

    public String toString() {
        return "PermissionGroupDTO(groupName=" + this.getGroupName() + ", permissions=" + String.valueOf(this.getPermissions()) + ")";
    }
}

