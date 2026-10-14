/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.roles;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.vezha.models.role.validations.RoleDoesNotContainRequiredPermissions;
import com.incoresoft.vezha.models.role.validations.RoleWithNameAlreadyExists;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

@RoleDoesNotContainRequiredPermissions
@RoleWithNameAlreadyExists
@Schema(name="RoleDTO", description="Role class structure")
public final class RoleDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Role Id", implementation=Integer.class, example="2")
    private final int id;
    @NotBlank
    @JSONField(name="name")
    @Schema(name="name", description="Role name", implementation=String.class, example="Role 1")
    private final String name;
    @JSONField(name="description")
    @Schema(name="description", description="Role description", implementation=String.class, example="Role 1 description")
    private final String description;
    @JSONField(name="permissions")
    @Schema(name="permissions", description="Role permissions array", type="object", example="[\"Permission 1\", \"Permission 2\"]")
    private final Set<String> permissions;
    @JSONField(serialize=false, deserialize=false)
    @Schema(hidden=true)
    private final int clientId;

    public RoleDTO(@NotNull Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
        this.permissions = Set.copyOf(role.getPermissions());
        this.clientId = role.getClientId();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Set<String> getPermissions() {
        return this.permissions;
    }

    public int getClientId() {
        return this.clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RoleDTO)) {
            return false;
        }
        RoleDTO other = (RoleDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        Set<String> this$permissions = this.getPermissions();
        Set<String> other$permissions = other.getPermissions();
        return !(this$permissions == null ? other$permissions != null : !((Object)this$permissions).equals(other$permissions));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getClientId();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Set<String> $permissions = this.getPermissions();
        result = result * 59 + ($permissions == null ? 43 : ((Object)$permissions).hashCode());
        return result;
    }

    public String toString() {
        return "RoleDTO(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", permissions=" + String.valueOf(this.getPermissions()) + ", clientId=" + this.getClientId() + ")";
    }

    public RoleDTO(int id, String name, String description, Set<String> permissions, int clientId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.permissions = permissions;
        this.clientId = clientId;
    }
}

