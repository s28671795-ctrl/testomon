/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.hardware;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.hardware.AnalyticsServerDTO;
import com.incoresoft.middleware.analytics.dto.hardware.GpuDTO;
import com.incoresoft.middleware.model.analytics.AnalyticsHardwareInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="AnalyticsHardwareInfoDTO", description="Analytics hardware (GPU and Server) class structure")
public record AnalyticsHardwareInfoDTO(@JsonProperty(value="server") @JSONField(name="server") @Schema(name="server", implementation=AnalyticsServerDTO.class) AnalyticsServerDTO server, @JsonProperty(value="gpu") @JSONField(name="gpu") @Schema(name="gpu", implementation=GpuDTO.class) GpuDTO gpu) {
    public AnalyticsHardwareInfoDTO(@NotNull AnalyticsHardwareInfo hardwareInfo) {
        this(new AnalyticsServerDTO(hardwareInfo.getAnalyticsServer()), hardwareInfo.getGpu() != null ? new GpuDTO(hardwareInfo.getGpu()) : null);
    }
}

