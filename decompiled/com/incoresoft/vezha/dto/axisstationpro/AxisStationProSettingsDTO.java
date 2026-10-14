/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.axisstationpro;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.axisstationpro.AxisStationProSettings;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AxisStationProSettingsDTO(@NotBlank @JSONField(name="host") @Schema(name="host", description="Host of the Axis Station Pro server", example="example.com") String host, @NotNull @JSONField(name="port") @Schema(name="port", description="Port of the Axis Station Pro server", example="8080") Integer port, @NotBlank @JSONField(name="username") @Schema(name="username", description="Username for Axis Station Pro authentication", example="admin") String username, @NotBlank @JSONField(name="password") @Schema(name="password", description="Password for Axis Station Pro authentication", example="password123") String password) {
    public AxisStationProSettingsDTO(AxisStationProSettings settings) {
        this(settings.host(), settings.port(), settings.username(), settings.password());
    }
}

