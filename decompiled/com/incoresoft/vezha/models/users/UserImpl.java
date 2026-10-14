/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.Utils.MD5;
import com.incoresoft.dto.ldap.LDAPConnectionParams;
import com.incoresoft.dto.ldap.LDAPUserData;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.user.UserFirstDayOfWeek;
import com.incoresoft.user.UserSettings;
import com.incoresoft.vezha.dto.users.UserDTO;
import com.incoresoft.vezha.dto.users.VmsCreateLdapUserDTO;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class UserImpl
implements User {
    public static final String BASIC_USER = "basic";
    public static final String LDAP_USER = "ldap";
    public static final int ARCHIVED = -1;
    public static final int ENABLED = 1;
    public static final int DISABLED = 2;
    public static final int ROOT_ID = 1;
    private int id;
    private String email;
    private String fullname;
    private String password;
    private String lastIP;
    private Instant lastLogin;
    private String ipParams;
    private Integer status;
    private Instant createdAt;
    private UserSettings settings;
    private int clientId;
    private String type;
    private Set<Integer> roleIds;
    @JSONField(serialize=false, deserialize=false)
    private RolesManager roleManager;
    private ZoneId timezone;

    public UserImpl(UserDTO userDTO, RolesManager roleManager) {
        this.id = userDTO.getId() != null ? userDTO.getId() : 0;
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.fullname = userDTO.getFullname();
        this.roleIds = userDTO.getRoleIds();
        this.clientId = userDTO.getClientId();
        this.settings = Optional.ofNullable(userDTO.getSettings()).map(it -> new UserSettings(null, userDTO.getSettings())).orElse(new UserSettings());
        this.status = userDTO.getStatus();
        this.type = BASIC_USER;
        this.roleManager = roleManager;
    }

    public UserImpl(VmsCreateLdapUserDTO userDTO, RolesManager roleManager) {
        this.id = 0;
        this.email = userDTO.getEmail();
        this.password = "";
        this.fullname = userDTO.getFullname();
        this.roleIds = userDTO.getRoleIds();
        this.clientId = userDTO.getClientId();
        this.settings = new UserSettings();
        this.status = 1;
        this.type = BASIC_USER;
        this.roleManager = roleManager;
    }

    public UserImpl(LDAPUserData userData, Set<Integer> roleIds, RolesManager roleManager, LDAPConnectionParams connectionParams, int clientId) {
        this.id = 0;
        this.email = userData.email();
        this.password = "";
        this.fullname = userData.name();
        this.createdAt = Instant.now();
        this.roleIds = roleIds;
        this.settings = new UserSettings();
        this.settings.setLdapSid(userData.sid());
        this.settings.setLdapServer(connectionParams.url());
        this.settings.setLdapBaseDN(connectionParams.base());
        this.roleManager = roleManager;
        this.status = 1;
        this.type = LDAP_USER;
        this.clientId = clientId;
        this.ipParams = null;
        this.lastIP = null;
        this.lastLogin = null;
    }

    @Override
    public boolean isRoot() {
        return this.roleIds.contains(1);
    }

    public String lang() {
        String lang;
        return switch (lang = this.settings.getLang()) {
            case "ua" -> "uk";
            case "cz" -> "cs";
            case "vt" -> "vi";
            default -> lang;
        };
    }

    public boolean checkPassword(String password) {
        String[] data = this.password.split(":");
        String crypt = MD5.getMD5(MD5.getMD5(password) + data[1]);
        return data[0].equals(crypt);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getFullname() {
        return this.fullname;
    }

    @Override
    public int getRoleId() {
        return (Integer)this.roleIds.stream().findAny().orElseThrow();
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    @Override
    public boolean isSoundEnabled() {
        return this.settings.isSoundsEnabled();
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return this.isRoot() || this.roleIds.stream().anyMatch(roleId -> ((Role)this.roleManager.get((Integer)roleId)).hasPermission(permission));
    }

    @Override
    public boolean hasAllPermissions(Collection<? extends Permission> permissions) {
        return this.isRoot() || this.roleIds.stream().anyMatch(roleId -> ((Role)this.roleManager.get((Integer)roleId)).hasAllPermissions(permissions));
    }

    @Override
    public boolean hasAnyPermission(Collection<? extends Permission> permissions) {
        return this.isRoot() || this.roleIds.stream().anyMatch(roleId -> ((Role)this.roleManager.get((Integer)roleId)).hasAnyPermission(permissions));
    }

    public boolean ownsItem(long clientId) {
        return this.isRoot() || (long)this.getClientId() == clientId;
    }

    public String getLdapSid() {
        return this.settings.getLdapSid();
    }

    public void archive() {
        this.status = -1;
    }

    public String getLdapServer() {
        return this.settings.getLdapServer();
    }

    public String getLdapBaseDN() {
        return this.settings.getLdapBaseDN();
    }

    @Override
    public DayOfWeek getFirstDayOfWeek() {
        if (this.settings == null || this.settings.getFirstDayOfWeek() == null) {
            return User.super.getFirstDayOfWeek();
        }
        return switch (this.settings.getFirstDayOfWeek()) {
            default -> throw new MatchException(null, null);
            case UserFirstDayOfWeek.SUNDAY -> DayOfWeek.SUNDAY;
            case UserFirstDayOfWeek.MONDAY -> DayOfWeek.MONDAY;
        };
    }

    public String toString() {
        return "UserImpl(id=" + this.getId() + ", email=" + this.getEmail() + ", fullname=" + this.getFullname() + ")";
    }

    public String getPassword() {
        return this.password;
    }

    public String getLastIP() {
        return this.lastIP;
    }

    public Instant getLastLogin() {
        return this.lastLogin;
    }

    public String getIpParams() {
        return this.ipParams;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public UserSettings getSettings() {
        return this.settings;
    }

    public String getType() {
        return this.type;
    }

    public Set<Integer> getRoleIds() {
        return this.roleIds;
    }

    public RolesManager getRoleManager() {
        return this.roleManager;
    }

    @Override
    public ZoneId getTimezone() {
        return this.timezone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setIpParams(String ipParams) {
        this.ipParams = ipParams;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRoleIds(Set<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public void setRoleManager(RolesManager roleManager) {
        this.roleManager = roleManager;
    }

    public void setTimezone(ZoneId timezone) {
        this.timezone = timezone;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UserImpl)) {
            return false;
        }
        UserImpl other = (UserImpl)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
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
        String this$password = this.getPassword();
        String other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) {
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
        String this$ipParams = this.getIpParams();
        String other$ipParams = other.getIpParams();
        if (this$ipParams == null ? other$ipParams != null : !this$ipParams.equals(other$ipParams)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt)) {
            return false;
        }
        UserSettings this$settings = this.getSettings();
        UserSettings other$settings = other.getSettings();
        if (this$settings == null ? other$settings != null : !this$settings.equals(other$settings)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        Set<Integer> this$roleIds = this.getRoleIds();
        Set<Integer> other$roleIds = other.getRoleIds();
        if (this$roleIds == null ? other$roleIds != null : !((Object)this$roleIds).equals(other$roleIds)) {
            return false;
        }
        RolesManager this$roleManager = this.getRoleManager();
        RolesManager other$roleManager = other.getRoleManager();
        if (this$roleManager == null ? other$roleManager != null : !this$roleManager.equals(other$roleManager)) {
            return false;
        }
        ZoneId this$timezone = this.getTimezone();
        ZoneId other$timezone = other.getTimezone();
        return !(this$timezone == null ? other$timezone != null : !((Object)this$timezone).equals(other$timezone));
    }

    protected boolean canEqual(Object other) {
        return other instanceof UserImpl;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getClientId();
        Integer $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)$status).hashCode());
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $fullname = this.getFullname();
        result = result * 59 + ($fullname == null ? 43 : $fullname.hashCode());
        String $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        String $lastIP = this.getLastIP();
        result = result * 59 + ($lastIP == null ? 43 : $lastIP.hashCode());
        Instant $lastLogin = this.getLastLogin();
        result = result * 59 + ($lastLogin == null ? 43 : ((Object)$lastLogin).hashCode());
        String $ipParams = this.getIpParams();
        result = result * 59 + ($ipParams == null ? 43 : $ipParams.hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        UserSettings $settings = this.getSettings();
        result = result * 59 + ($settings == null ? 43 : $settings.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Set<Integer> $roleIds = this.getRoleIds();
        result = result * 59 + ($roleIds == null ? 43 : ((Object)$roleIds).hashCode());
        RolesManager $roleManager = this.getRoleManager();
        result = result * 59 + ($roleManager == null ? 43 : $roleManager.hashCode());
        ZoneId $timezone = this.getTimezone();
        result = result * 59 + ($timezone == null ? 43 : ((Object)$timezone).hashCode());
        return result;
    }

    public UserImpl() {
    }
}

