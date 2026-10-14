/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.plugins.extensions;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.permission.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public final class ExtensionAnalyticsParametersDTO {
    @JSONField(name="analytics_managing_permission")
    @Schema(name="analytics_managing_permission", description="Analytics managing permission", implementation=String.class, example="ManageAlprAnalytics")
    private final String analyticsManagingPermissionId;

    public ExtensionAnalyticsParametersDTO(@Nullable Permission analyticsManagingPermission) {
        this.analyticsManagingPermissionId = Optional.ofNullable(analyticsManagingPermission).map(Permission::getId).orElse(null);
    }

    public String getAnalyticsManagingPermissionId() {
        return this.analyticsManagingPermissionId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExtensionAnalyticsParametersDTO)) {
            return false;
        }
        ExtensionAnalyticsParametersDTO other = (ExtensionAnalyticsParametersDTO)o;
        String this$analyticsManagingPermissionId = this.getAnalyticsManagingPermissionId();
        String other$analyticsManagingPermissionId = other.getAnalyticsManagingPermissionId();
        return !(this$analyticsManagingPermissionId == null ? other$analyticsManagingPermissionId != null : !this$analyticsManagingPermissionId.equals(other$analyticsManagingPermissionId));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $analyticsManagingPermissionId = this.getAnalyticsManagingPermissionId();
        result = result * 59 + ($analyticsManagingPermissionId == null ? 43 : $analyticsManagingPermissionId.hashCode());
        return result;
    }

    public String toString() {
        return "ExtensionAnalyticsParametersDTO(analyticsManagingPermissionId=" + this.getAnalyticsManagingPermissionId() + ")";
    }
}

