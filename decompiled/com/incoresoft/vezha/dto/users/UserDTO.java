/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.users;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.dto.user.UserSettingsDTO;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.validations.RolesWithThisIdsMustExist;
import com.incoresoft.vezha.models.users.validations.password.NotBlank;
import com.incoresoft.vezha.models.users.validations.password.PasswordTooShort;
import com.incoresoft.vezha.models.users.validations.password.PasswordValidatable;
import com.incoresoft.vezha.models.users.validations.password.PasswordsDoNotMatch;
import com.incoresoft.vezha.models.users.validations.role.RoleIdsContainer;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@NotBlank
@PasswordTooShort
@PasswordsDoNotMatch
@com.incoresoft.vezha.models.users.validations.email.NotBlank
@RolesWithThisIdsMustExist
@Schema(name="UserDTO", description="User class structure")
public final class UserDTO
implements RoleIdsContainer,
PasswordValidatable {
    @JSONField(name="id")
    @Schema(name="id", description="User Id", implementation=Integer.class, example="7")
    private final Integer id;
    @JSONField(name="client_id")
    @Schema(name="client_id", description="User client id", implementation=Integer.class, example="0")
    private Integer clientId;
    @JSONField(serialize=false)
    @Schema(name="password", description="User password", implementation=String.class, example="password")
    private String password;
    @JSONField(name="conf_password", serialize=false)
    @Schema(name="conf_password", description="User confirmation password", implementation=String.class, example="password")
    private String confirmationPassword;
    @JSONField(name="created_at")
    @Schema(name="created_at", description="User created at", implementation=Long.class, example="timestamp")
    private final Instant createdAt;
    @JSONField(name="email")
    @Schema(name="email", description="User email", implementation=String.class, example="test.mail@example.com")
    private final String email;
    @JSONField(name="fullname")
    @Schema(name="fullname", description="User fullname", implementation=String.class, example="User Name")
    @javax.validation.constraints.NotBlank
    private final String fullname;
    @JSONField(name="ip_params", alternateNames={"ipParams"})
    @Schema(name="ip_params", description="User ip params", implementation=String.class)
    private final String ipParams;
    @JSONField(name="last_ip", alternateNames={"lastIP"})
    @Schema(name="last_ip", description="User last IP", implementation=String.class, example="0.0.0.1")
    private final String lastIP;
    @JSONField(name="last_login", alternateNames={"lastLogin"})
    @Schema(name="last_login", description="User last login", implementation=Long.class, example="timestamp")
    private final Instant lastLogin;
    @JSONField(name="role_ids")
    @ArraySchema(schema=@Schema(name="role_ids", description="List of role ids", implementation=Integer.class, example="[1, 2, 3]"))
    @NotEmpty
    private final Set<Integer> roleIds;
    @JSONField(name="settings")
    @Schema(name="settings", description="User settings", implementation=UserSettingsDTO.class)
    @Valid
    private final UserSettingsDTO settings;
    @JSONField(name="status")
    @Schema(name="status", description="User status", implementation=Integer.class, example="1")
    private final Integer status;
    @JSONField(name="type")
    @Schema(name="type", description="User type", implementation=String.class, example="basic", allowableValues={"basic", "ldap"})
    private final String type;
    @JSONField(name="timezone")
    @Schema(name="timezone", description="User timezone", implementation=String.class, example="Europe/Kiev")
    private final String timezone;
    @JSONField(name="permissions", deserialize=false)
    @Schema(name="permissions", description="List of all user permissions", type="object", example="[\"Permission 1\", \"Permission 2\"]")
    private final List<String> permissions;

    public UserDTO(UserImpl user, Collection<? extends Permission> allPermissions) {
        this.id = user.getId();
        this.clientId = user.getClientId();
        this.createdAt = Optional.ofNullable(user.getCreatedAt()).orElse(Instant.now());
        this.email = user.getEmail();
        this.fullname = user.getFullname();
        this.ipParams = user.getIpParams();
        this.lastIP = user.getLastIP();
        this.lastLogin = user.getLastLogin();
        this.roleIds = user.getRoleIds();
        this.settings = new UserSettingsDTO(user.getSettings());
        this.status = user.getStatus();
        this.type = user.getType();
        this.timezone = Optional.ofNullable(user.getTimezone()).map(ZoneId::getId).orElse(ZoneId.systemDefault().getId());
        this.permissions = allPermissions.stream().filter(user::hasPermission).map(Permission::getId).toList();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public Integer getClientId() {
        return this.clientId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getConfirmationPassword() {
        return this.confirmationPassword;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullname() {
        return this.fullname;
    }

    public String getIpParams() {
        return this.ipParams;
    }

    public String getLastIP() {
        return this.lastIP;
    }

    public Instant getLastLogin() {
        return this.lastLogin;
    }

    @Override
    public Set<Integer> getRoleIds() {
        return this.roleIds;
    }

    public UserSettingsDTO getSettings() {
        return this.settings;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UserDTO)) {
            return false;
        }
        UserDTO other = (UserDTO)o;
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
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
        String this$password = this.getPassword();
        String other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) {
            return false;
        }
        String this$confirmationPassword = this.getConfirmationPassword();
        String other$confirmationPassword = other.getConfirmationPassword();
        if (this$confirmationPassword == null ? other$confirmationPassword != null : !this$confirmationPassword.equals(other$confirmationPassword)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt)) {
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
        String this$ipParams = this.getIpParams();
        String other$ipParams = other.getIpParams();
        if (this$ipParams == null ? other$ipParams != null : !this$ipParams.equals(other$ipParams)) {
            return false;
        }
        String this$lastIP = this.getLastIP();
        String other$lastIP = other.getLastIP();
        if (this$lastIP == null ? other$lastIP != null : !this$lastIP.equals(other$lastIP)) {
            return false;
        }
        Instant this$lastLogin = this.getLastLogin();
        Instant other$lastLogin = other.getLastLogin();
        if (this$lastLogin == null ? other$lastLogin != null : !((Object)this$lastLogin).equals(other$lastLogin)) {
            return false;
        }
        Set<Integer> this$roleIds = this.getRoleIds();
        Set<Integer> other$roleIds = other.getRoleIds();
        if (this$roleIds == null ? other$roleIds != null : !((Object)this$roleIds).equals(other$roleIds)) {
            return false;
        }
        UserSettingsDTO this$settings = this.getSettings();
        UserSettingsDTO other$settings = other.getSettings();
        if (this$settings == null ? other$settings != null : !((Object)this$settings).equals(other$settings)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$timezone = this.getTimezone();
        String other$timezone = other.getTimezone();
        if (this$timezone == null ? other$timezone != null : !this$timezone.equals(other$timezone)) {
            return false;
        }
        List<String> this$permissions = this.getPermissions();
        List<String> other$permissions = other.getPermissions();
        return !(this$permissions == null ? other$permissions != null : !((Object)this$permissions).equals(other$permissions));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Integer $clientId = this.getClientId();
        result = result * 59 + ($clientId == null ? 43 : ((Object)$clientId).hashCode());
        Integer $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)$status).hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        String $confirmationPassword = this.getConfirmationPassword();
        result = result * 59 + ($confirmationPassword == null ? 43 : $confirmationPassword.hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $fullname = this.getFullname();
        result = result * 59 + ($fullname == null ? 43 : $fullname.hashCode());
        String $ipParams = this.getIpParams();
        result = result * 59 + ($ipParams == null ? 43 : $ipParams.hashCode());
        String $lastIP = this.getLastIP();
        result = result * 59 + ($lastIP == null ? 43 : $lastIP.hashCode());
        Instant $lastLogin = this.getLastLogin();
        result = result * 59 + ($lastLogin == null ? 43 : ((Object)$lastLogin).hashCode());
        Set<Integer> $roleIds = this.getRoleIds();
        result = result * 59 + ($roleIds == null ? 43 : ((Object)$roleIds).hashCode());
        UserSettingsDTO $settings = this.getSettings();
        result = result * 59 + ($settings == null ? 43 : ((Object)$settings).hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $timezone = this.getTimezone();
        result = result * 59 + ($timezone == null ? 43 : $timezone.hashCode());
        List<String> $permissions = this.getPermissions();
        result = result * 59 + ($permissions == null ? 43 : ((Object)$permissions).hashCode());
        return result;
    }

    public String toString() {
        return "UserDTO(id=" + this.getId() + ", clientId=" + this.getClientId() + ", password=" + this.getPassword() + ", confirmationPassword=" + this.getConfirmationPassword() + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", email=" + this.getEmail() + ", fullname=" + this.getFullname() + ", ipParams=" + this.getIpParams() + ", lastIP=" + this.getLastIP() + ", lastLogin=" + String.valueOf(this.getLastLogin()) + ", roleIds=" + String.valueOf(this.getRoleIds()) + ", settings=" + String.valueOf(this.getSettings()) + ", status=" + this.getStatus() + ", type=" + this.getType() + ", timezone=" + this.getTimezone() + ", permissions=" + String.valueOf(this.getPermissions()) + ")";
    }

    public UserDTO(Integer id, Integer clientId, String password, String confirmationPassword, Instant createdAt, String email, String fullname, String ipParams, String lastIP, Instant lastLogin, Set<Integer> roleIds, UserSettingsDTO settings, Integer status, String type, String timezone, List<String> permissions) {
        this.id = id;
        this.clientId = clientId;
        this.password = password;
        this.confirmationPassword = confirmationPassword;
        this.createdAt = createdAt;
        this.email = email;
        this.fullname = fullname;
        this.ipParams = ipParams;
        this.lastIP = lastIP;
        this.lastLogin = lastLogin;
        this.roleIds = roleIds;
        this.settings = settings;
        this.status = status;
        this.type = type;
        this.timezone = timezone;
        this.permissions = permissions;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

