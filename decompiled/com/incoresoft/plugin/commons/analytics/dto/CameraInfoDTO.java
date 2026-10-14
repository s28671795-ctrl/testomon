/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugin.commons.analytics.model.CameraContainer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public record CameraInfoDTO(@Schema(name="id", description="Camera ID Field", example="1", implementation=Integer.class) @JsonProperty(value="id") Integer cameraId, @Schema(name="enabled", description="Indicates if camera is enabled", example="true", implementation=Boolean.class) @JsonProperty(value="enabled") Boolean cameraEnabled, @Schema(name="state", description="Camera state", example="active", implementation=String.class) @JsonProperty(value="state") String cameraState) {
    public CameraInfoDTO(@Nullable CameraContainer container) {
        this(Optional.ofNullable(container).map(CameraContainer::id).orElse(null), Optional.ofNullable(container).map(CameraContainer::enabled).orElse(null), Optional.ofNullable(container).map(CameraContainer::state).orElse(null));
    }
}

