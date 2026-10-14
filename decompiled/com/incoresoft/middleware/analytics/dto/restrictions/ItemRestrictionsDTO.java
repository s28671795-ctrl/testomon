/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.restrictions;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.restrictions.validation.UserMustSetOwnPermissions;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UserMustSetOwnPermissions
@Schema(name="ItemRestrictionsDTO", description="Item restriction class structure")
public final class ItemRestrictionsDTO {
    public static final ItemRestrictionsDTO DEFAULT_DTO = new ItemRestrictionsDTO(1, null, null, null);
    private static final String USER_ID_JSON_FIELD_NAME = "creator_id";
    private static final String ROLE_PERMISSIONS_JSON_FIELD_NAME = "role_permissions";
    private static final String USER_PERMISSIONS_JSON_FIELD_NAME = "user_permissions";
    private static final String DEFAULT_PERMISSIONS_JSON_FIELD_NAME = "default_permissions";
    @JSONField(name="creator_id")
    @Schema(name="creator_id", description="ID of user who created this permissions", example="1")
    private Integer creatorId;
    @JSONField(name="role_permissions")
    @Schema(name="role_permissions", description="Permissions for role", example="{}")
    private final Map<Integer, Map<String, Boolean>> rolePermissions;
    @JSONField(name="user_permissions")
    @Schema(name="user_permissions", description="Permissions for user", example="{}")
    private final Map<Integer, Map<String, Boolean>> userPermissions;
    @JSONField(name="default_permissions")
    @Schema(name="default_permissions", description="Default permissions (if the permissions settings of roles or users are not active)", example="{}")
    private final Map<String, Boolean> defaultPermissions;

    @JsonCreator
    public ItemRestrictionsDTO(@JsonProperty(value="creator_id") @Nullable Integer creatorId, @JsonProperty(value="role_permissions") @Nullable Map<Integer, Map<String, Boolean>> rolePermissions, @JsonProperty(value="user_permissions") @Nullable Map<Integer, Map<String, Boolean>> userPermissions, @JsonProperty(value="default_permissions") @Nullable Map<String, Boolean> defaultPermissions) {
        this.creatorId = creatorId;
        this.rolePermissions = Objects.requireNonNullElse(rolePermissions, new HashMap());
        this.userPermissions = Objects.requireNonNullElse(userPermissions, new HashMap());
        this.defaultPermissions = Objects.requireNonNullElse(defaultPermissions, new HashMap());
    }

    public ItemRestrictionsDTO(@NotNull RoleUserRestrictions inner) {
        this(inner.itemCreator().map(User::getId).orElse(null), inner.grantedRoleRestrictions().entrySet().stream().collect(Collectors.toMap(entry -> ((Role)entry.getKey()).getId(), it -> ItemRestrictionsDTO.permissionsSelectionStatusMap(inner.eligiblePermissions(), (Set)it.getValue()))), inner.grantedUserRestrictions().entrySet().stream().collect(Collectors.toMap(entry -> ((User)entry.getKey()).getId(), it -> ItemRestrictionsDTO.permissionsSelectionStatusMap(inner.eligiblePermissions(), (Set)it.getValue()))), ItemRestrictionsDTO.permissionsSelectionStatusMap(inner.eligiblePermissions(), inner.grantedDefaultRestrictions()));
    }

    @NotNull
    private static Map<String, Boolean> permissionsSelectionStatusMap(Set<Permission> eligiblePermissions, Set<Permission> permissionsToCheck) {
        Set permissionToCheckIds = permissionsToCheck.stream().map(Permission::getId).collect(Collectors.toUnmodifiableSet());
        LinkedHashMap<String, Boolean> permissionsSelectionStatuses = new LinkedHashMap<String, Boolean>();
        for (Permission key : eligiblePermissions) {
            permissionsSelectionStatuses.put(key.getId(), permissionToCheckIds.contains(key.getId()));
        }
        return Collections.unmodifiableMap(permissionsSelectionStatuses);
    }

    public Integer getCreatorId() {
        return this.creatorId;
    }

    public Map<Integer, Map<String, Boolean>> getRolePermissions() {
        return this.rolePermissions;
    }

    public Map<Integer, Map<String, Boolean>> getUserPermissions() {
        return this.userPermissions;
    }

    public Map<String, Boolean> getDefaultPermissions() {
        return this.defaultPermissions;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemRestrictionsDTO)) {
            return false;
        }
        ItemRestrictionsDTO other = (ItemRestrictionsDTO)o;
        Integer this$creatorId = this.getCreatorId();
        Integer other$creatorId = other.getCreatorId();
        if (this$creatorId == null ? other$creatorId != null : !((Object)this$creatorId).equals(other$creatorId)) {
            return false;
        }
        Map<Integer, Map<String, Boolean>> this$rolePermissions = this.getRolePermissions();
        Map<Integer, Map<String, Boolean>> other$rolePermissions = other.getRolePermissions();
        if (this$rolePermissions == null ? other$rolePermissions != null : !((Object)this$rolePermissions).equals(other$rolePermissions)) {
            return false;
        }
        Map<Integer, Map<String, Boolean>> this$userPermissions = this.getUserPermissions();
        Map<Integer, Map<String, Boolean>> other$userPermissions = other.getUserPermissions();
        if (this$userPermissions == null ? other$userPermissions != null : !((Object)this$userPermissions).equals(other$userPermissions)) {
            return false;
        }
        Map<String, Boolean> this$defaultPermissions = this.getDefaultPermissions();
        Map<String, Boolean> other$defaultPermissions = other.getDefaultPermissions();
        return !(this$defaultPermissions == null ? other$defaultPermissions != null : !((Object)this$defaultPermissions).equals(other$defaultPermissions));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $creatorId = this.getCreatorId();
        result = result * 59 + ($creatorId == null ? 43 : ((Object)$creatorId).hashCode());
        Map<Integer, Map<String, Boolean>> $rolePermissions = this.getRolePermissions();
        result = result * 59 + ($rolePermissions == null ? 43 : ((Object)$rolePermissions).hashCode());
        Map<Integer, Map<String, Boolean>> $userPermissions = this.getUserPermissions();
        result = result * 59 + ($userPermissions == null ? 43 : ((Object)$userPermissions).hashCode());
        Map<String, Boolean> $defaultPermissions = this.getDefaultPermissions();
        result = result * 59 + ($defaultPermissions == null ? 43 : ((Object)$defaultPermissions).hashCode());
        return result;
    }

    public String toString() {
        return "ItemRestrictionsDTO(creatorId=" + this.getCreatorId() + ", rolePermissions=" + String.valueOf(this.getRolePermissions()) + ", userPermissions=" + String.valueOf(this.getUserPermissions()) + ", defaultPermissions=" + String.valueOf(this.getDefaultPermissions()) + ")";
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    static {
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)ItemRestrictionsDTO.class), new Serializer());
    }

    public static class Serializer
    implements ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            JSONObject jsonDTO = parser.parseObject();
            Integer creatorId = jsonDTO.getInteger(ItemRestrictionsDTO.USER_ID_JSON_FIELD_NAME);
            Map rolePermissions = (Map)jsonDTO.getObject(ItemRestrictionsDTO.ROLE_PERMISSIONS_JSON_FIELD_NAME, new TypeReference<Map<Integer, Map<String, Boolean>>>(this){});
            Map userPermissions = (Map)jsonDTO.getObject(ItemRestrictionsDTO.USER_PERMISSIONS_JSON_FIELD_NAME, new TypeReference<Map<Integer, Map<String, Boolean>>>(this){});
            Map defaultPermissions = (Map)jsonDTO.getObject(ItemRestrictionsDTO.DEFAULT_PERMISSIONS_JSON_FIELD_NAME, new TypeReference<Map<String, Boolean>>(this){});
            return (T)new ItemRestrictionsDTO(creatorId, rolePermissions, userPermissions, defaultPermissions);
        }

        @Override
        public int getFastMatchToken() {
            return 12;
        }
    }
}

