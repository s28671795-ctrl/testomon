/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.hardware;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.model.analytics.AnalyticsServer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

@Schema(name="AnalyticsServerDTO", description="Server analytics information")
public record AnalyticsServerDTO(@JsonProperty(value="id") @JSONField(name="id") @Schema(name="id", description="Server ID", example="1") String id, @JsonProperty(value="last_heart_beat") @JSONField(name="last_heart_beat") @Schema(name="last_heart_beat", description="Last heartbeat in seconds", example="10") long lastHeartbeatSeconds) {
    public AnalyticsServerDTO(@NotNull AnalyticsServer server) {
        this(server.getId(), Duration.between(server.lastHeartBeat(), Instant.now()).toSeconds());
    }
}

