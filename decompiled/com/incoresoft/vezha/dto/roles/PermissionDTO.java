/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.roles;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.permission.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

@Schema(name="PermissionDTO", description="Permission class structure")
public final class PermissionDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Unique permission id", implementation=String.class, example="StartAnalytics")
    private final String id;
    @JSONField(name="locale_key")
    @Schema(name="locale_key", description="Localization key of this permission", implementation=String.class, example="MANAGE_ANALYTIC")
    private final String localeKey;
    @JSONField(name="required_permissions")
    @Schema(name="required_permissions", description="Array of other permissions ids required for this permission to be active")
    private final Set<String> required;

    public PermissionDTO(@NotNull Permission permission) {
        this.id = permission.getId();
        this.localeKey = permission.getLocaleKey();
        this.required = permission.getRequired();
    }

    public String getId() {
        return this.id;
    }

    public String getLocaleKey() {
        return this.localeKey;
    }

    public Set<String> getRequired() {
        return this.required;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PermissionDTO)) {
            return false;
        }
        PermissionDTO other = (PermissionDTO)o;
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$localeKey = this.getLocaleKey();
        String other$localeKey = other.getLocaleKey();
        if (this$localeKey == null ? other$localeKey != null : !this$localeKey.equals(other$localeKey)) {
            return false;
        }
        Set<String> this$required = this.getRequired();
        Set<String> other$required = other.getRequired();
        return !(this$required == null ? other$required != null : !((Object)this$required).equals(other$required));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $localeKey = this.getLocaleKey();
        result = result * 59 + ($localeKey == null ? 43 : $localeKey.hashCode());
        Set<String> $required = this.getRequired();
        result = result * 59 + ($required == null ? 43 : ((Object)$required).hashCode());
        return result;
    }

    public String toString() {
        return "PermissionDTO(id=" + this.getId() + ", localeKey=" + this.getLocaleKey() + ", required=" + String.valueOf(this.getRequired()) + ")";
    }
}

