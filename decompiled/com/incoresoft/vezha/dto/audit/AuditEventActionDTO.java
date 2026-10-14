/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.audit;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="LogActionDTO", description="Represents action of audit or system logs")
public record AuditEventActionDTO(@JsonProperty(value="action") @JSONField(name="action") @Schema(name="event_action", description="Action of the audit event", example="analytics_added") String eventAction, @JsonProperty(value="locale_key") @JSONField(name="locale_key") @Schema(name="locale_key", description="Localization key of the audit event action", example="ANALYTICS_ADDED_AUDIT_ACTION") String localizedEventAction) {
}

