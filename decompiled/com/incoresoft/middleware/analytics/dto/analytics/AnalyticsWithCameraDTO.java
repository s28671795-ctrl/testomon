/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="AnalyticsWithCameraDTO", description="Analytics with camera class structure")
public class AnalyticsWithCameraDTO
extends AnalyticsDTO {
    @Schema(name="camera_id", description="Camera ID Field", example="1", implementation=Integer.class)
    @JsonProperty(value="camera_id")
    private Integer cameraId;

    public AnalyticsWithCameraDTO(@NotNull User user, @NotNull Analytics inner, @NotNull Stream stream, @NotNull VideoFileProgress progress, Integer cameraId) {
        super(user, inner, stream, progress);
        this.cameraId = cameraId;
    }

    public Integer getCameraId() {
        return this.cameraId;
    }
}

