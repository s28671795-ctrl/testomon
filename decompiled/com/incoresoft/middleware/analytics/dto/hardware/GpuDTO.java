/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.hardware;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.model.analytics.Gpu;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="GpuDTO", description="GPU class structure")
public record GpuDTO(@JsonProperty(value="id") @JSONField(name="id") @Schema(description="GPU ID", example="0") int id) {
    public GpuDTO(@NotNull Gpu gpu) {
        this(gpu.getId());
    }
}

