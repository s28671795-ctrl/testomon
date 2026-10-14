/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public record GroupAnalyticsActionResponseDTO(@JSONField(name="skipped_analytics") @JsonProperty(value="skipped_analytics") @Schema(name="skipped_analytics", description="Set of analytics IDs that were skipped due to the action being already applied, due to user unavailable to execute said action on these analytics, or other reasons.") Set<Integer> skippedAnalytics) {
}

