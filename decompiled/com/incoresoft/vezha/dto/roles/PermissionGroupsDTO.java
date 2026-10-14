/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.roles;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.vezha.dto.roles.PermissionGroupDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Schema(name="PermissionGroupsDTO", description="Permission groups class structure")
public final class PermissionGroupsDTO {
    @JSONField(name="groups")
    @ArraySchema(arraySchema=@Schema(description="List of all permission group in system"), schema=@Schema(implementation=PermissionGroupDTO.class))
    private final List<PermissionGroupDTO> groups;

    public PermissionGroupsDTO(Map<String, Collection<? extends Permission>> permissionsMap) {
        this.groups = permissionsMap.entrySet().stream().map(it -> new PermissionGroupDTO((String)it.getKey(), (Collection)it.getValue())).toList();
    }

    public List<PermissionGroupDTO> getGroups() {
        return this.groups;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PermissionGroupsDTO)) {
            return false;
        }
        PermissionGroupsDTO other = (PermissionGroupsDTO)o;
        List<PermissionGroupDTO> this$groups = this.getGroups();
        List<PermissionGroupDTO> other$groups = other.getGroups();
        return !(this$groups == null ? other$groups != null : !((Object)this$groups).equals(other$groups));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<PermissionGroupDTO> $groups = this.getGroups();
        result = result * 59 + ($groups == null ? 43 : ((Object)$groups).hashCode());
        return result;
    }

    public String toString() {
        return "PermissionGroupsDTO(groups=" + String.valueOf(this.getGroups()) + ")";
    }
}

