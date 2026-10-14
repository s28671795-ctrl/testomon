/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.users;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.models.users.validations.RolesWithThisIdsMustExist;
import com.incoresoft.vezha.models.users.validations.role.RoleIdsContainer;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RolesWithThisIdsMustExist
@Schema(name="VmsCreateLdapUserDTO", description="VMS LDAP user creation class structure")
public final class VmsCreateLdapUserDTO
implements RoleIdsContainer {
    @JSONField(name="client_id")
    @Schema(name="client_id", description="User client id", implementation=Integer.class, example="0")
    private final Integer clientId;
    @JSONField(name="email")
    @Schema(name="email", description="User email", implementation=String.class, example="test.mail@example.com")
    @NotBlank
    private final String email;
    @JSONField(name="fullname")
    @Schema(name="fullname", description="User fullname", implementation=String.class, example="User Name")
    @NotBlank
    private final String fullname;
    @JSONField(name="role_ids")
    @ArraySchema(schema=@Schema(name="role_ids", description="List of role ids", implementation=Integer.class, example="[1, 2, 3]"))
    @NotEmpty
    private final Set<Integer> roleIds;
    @JSONField(name="status")
    @Schema(name="status", description="User status", implementation=Integer.class, example="1")
    private final Integer status;

    public Integer getClientId() {
        return this.clientId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullname() {
        return this.fullname;
    }

    @Override
    public Set<Integer> getRoleIds() {
        return this.roleIds;
    }

    public Integer getStatus() {
        return this.status;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VmsCreateLdapUserDTO)) {
            return false;
        }
        VmsCreateLdapUserDTO other = (VmsCreateLdapUserDTO)o;
        Integer this$clientId = this.getClientId();
        Integer other$clientId = other.getClientId();
        if (this$clientId == null ? other$clientId != null : !((Object)this$clientId).equals(other$clientId)) {
            return false;
        }
        Integer this$status = this.getStatus();
        Integer other$status = other.getStatus();
        if (this$status == null ? other$status != null : !((Object)this$status).equals(other$status)) {
            return false;
        }
        String this$email = this.getEmail();
        String other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
            return false;
        }
        String this$fullname = this.getFullname();
        String other$fullname = other.getFullname();
        if (this$fullname == null ? other$fullname != null : !this$fullname.equals(other$fullname)) {
            return false;
        }
        Set<Integer> this$roleIds = this.getRoleIds();
        Set<Integer> other$roleIds = other.getRoleIds();
        return !(this$roleIds == null ? other$roleIds != null : !((Object)this$roleIds).equals(other$roleIds));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $clientId = this.getClientId();
        result = result * 59 + ($clientId == null ? 43 : ((Object)$clientId).hashCode());
        Integer $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)$status).hashCode());
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $fullname = this.getFullname();
        result = result * 59 + ($fullname == null ? 43 : $fullname.hashCode());
        Set<Integer> $roleIds = this.getRoleIds();
        result = result * 59 + ($roleIds == null ? 43 : ((Object)$roleIds).hashCode());
        return result;
    }

    public String toString() {
        return "VmsCreateLdapUserDTO(clientId=" + this.getClientId() + ", email=" + this.getEmail() + ", fullname=" + this.getFullname() + ", roleIds=" + String.valueOf(this.getRoleIds()) + ", status=" + this.getStatus() + ")";
    }

    public VmsCreateLdapUserDTO(Integer clientId, String email, String fullname, Set<Integer> roleIds, Integer status) {
        this.clientId = clientId;
        this.email = email;
        this.fullname = fullname;
        this.roleIds = roleIds;
        this.status = status;
    }
}

