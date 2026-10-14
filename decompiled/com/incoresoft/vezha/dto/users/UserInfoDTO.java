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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.NotNull;

@NotBlank
@RolesWithThisIdsMustExist
@Schema(name="UserDTO", description="User class structure")
public final class UserInfoDTO {
    @Schema(name="id", description="User Id", implementation=Integer.class, example="7")
    private final Integer id;
    @JSONField(name="client_id")
    @Schema(name="client_id", description="User client id", implementation=Integer.class, example="7")
    private Integer clientId;
    @JSONField(serialize=false)
    @Schema(name="password", description="User password", implementation=String.class, example="password")
    private String password;
    @JSONField(name="conf_password", serialize=false)
    @Schema(name="conf_password", description="User confirmation password", implementation=String.class, example="password")
    private String confirmPassword;
    @JSONField(name="created_at")
    @Schema(name="created_at", description="User created at", implementation=Long.class, example="timestamp")
    private final Instant createdAt;
    @Schema(name="email", description="User email", implementation=String.class, example="test.mail@example.com")
    private final String email;
    @Schema(name="fullname", description="User fullname", implementation=String.class, example="User Name")
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
    @JSONField(name="role_id")
    @ArraySchema(schema=@Schema(name="role_ids", description="List of role ids", implementation=Integer.class, example="[1, 2, 3]"))
    @NotNull
    private final Set<Integer> roleIds;
    @Schema(name="settings", description="User settings", implementation=UserSettingsDTO.class)
    private final UserSettingsDTO settings;
    @Schema(name="status", description="User status", implementation=Integer.class, example="1")
    private final Integer status;
    @Schema(name="type", description="User type", implementation=String.class, example="basic", allowableValues={"basic", "ldap"})
    private final String type;
    @Schema(name="timezone", description="User timezone", implementation=String.class, example="Europe/Kiev")
    private final String timezone;
    @JSONField(name="permissions", deserialize=false)
    @Schema(name="permissions", description="List of all user permissions", type="object", example="[\"Permission 1\", \"Permission 2\"]")
    private final List<String> permissions;
    @JSONField(name="storage")
    @Schema(name="storage", description="Storage", implementation=String.class, example="")
    private final String storage;

    public UserInfoDTO(UserImpl user, Collection<? extends Permission> allPermissions, String storagePath) {
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
        this.storage = storagePath;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getClientId() {
        return this.clientId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
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

    public String getStorage() {
        return this.storage;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UserInfoDTO)) {
            return false;
        }
        UserInfoDTO other = (UserInfoDTO)o;
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
        String this$confirmPassword = this.getConfirmPassword();
        String other$confirmPassword = other.getConfirmPassword();
        if (this$confirmPassword == null ? other$confirmPassword != null : !this$confirmPassword.equals(other$confirmPassword)) {
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
        if (this$permissions == null ? other$permissions != null : !((Object)this$permissions).equals(other$permissions)) {
            return false;
        }
        String this$storage = this.getStorage();
        String other$storage = other.getStorage();
        return !(this$storage == null ? other$storage != null : !this$storage.equals(other$storage));
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
        String $confirmPassword = this.getConfirmPassword();
        result = result * 59 + ($confirmPassword == null ? 43 : $confirmPassword.hashCode());
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
        String $storage = this.getStorage();
        result = result * 59 + ($storage == null ? 43 : $storage.hashCode());
        return result;
    }

    public String toString() {
        return "UserInfoDTO(id=" + this.getId() + ", clientId=" + this.getClientId() + ", password=" + this.getPassword() + ", confirmPassword=" + this.getConfirmPassword() + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", email=" + this.getEmail() + ", fullname=" + this.getFullname() + ", ipParams=" + this.getIpParams() + ", lastIP=" + this.getLastIP() + ", lastLogin=" + String.valueOf(this.getLastLogin()) + ", roleIds=" + String.valueOf(this.getRoleIds()) + ", settings=" + String.valueOf(this.getSettings()) + ", status=" + this.getStatus() + ", type=" + this.getType() + ", timezone=" + this.getTimezone() + ", permissions=" + String.valueOf(this.getPermissions()) + ", storage=" + this.getStorage() + ")";
    }

    public UserInfoDTO(Integer id, Integer clientId, String password, String confirmPassword, Instant createdAt, String email, String fullname, String ipParams, String lastIP, Instant lastLogin, Set<Integer> roleIds, UserSettingsDTO settings, Integer status, String type, String timezone, List<String> permissions, String storage) {
        this.id = id;
        this.clientId = clientId;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
        this.storage = storage;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

