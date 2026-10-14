/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.audit;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import com.incoresoft.vezha.dto.audit.AuditEventActionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="AuditEventCategoryDTO", description="Represents audit event category")
public record AuditEventCategoryDTO(@JsonProperty(value="category") @JSONField(name="category") @Schema(name="category", description="Audit event category", example="analytics_management") String category, @JsonProperty(value="locale_key") @JSONField(name="locale_key") @Schema(name="locale_key", description="Localization key of the audit event category", example="ANALYTICS_MANAGEMENT_AUDIT_CATEGORY") String localizedCategory, @JsonProperty(value="actions") @JSONField(name="actions") @Schema(name="actions", description="Available audit event actions for this category") List<AuditEventActionDTO> actions) {
    public AuditEventCategoryDTO(AuditEventCategory category, List<AuditEventAction> actions) {
        this(category.toString(), category.getLocalizationKey(), actions.stream().map(it -> new AuditEventActionDTO(it.toString(), it.getLocalizationKey())).toList());
    }
}

