/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.analytics.dto.CameraInfoDTO;
import com.incoresoft.plugin.commons.analytics.model.CameraContainer;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import org.jetbrains.annotations.Nullable;

@Schema(name="AnalyticsWithCameraDTO", description="Analytics with camera class structure")
public class VmsAnalyticsDTO
extends AnalyticsDTO {
    @Schema(name="camera", description="Camera ID info", implementation=CameraInfoDTO.class)
    @JsonProperty(value="camera")
    private CameraInfoDTO cameraInfo;
    @Schema(hidden=true)
    @JsonIgnore
    private final String h264Url;
    @Schema(hidden=true)
    @JsonIgnore
    private final String mpegPreview;

    public VmsAnalyticsDTO(@Nullable Context context, @NotNull User user, @NotNull Analytics inner, @NotNull Stream stream, @NotNull VideoFileProgress progress, @Nullable CameraContainer container) {
        super(user, inner, stream, progress);
        this.cameraInfo = new CameraInfoDTO(container);
        this.h264Url = inner.getH264Preview(context);
        this.mpegPreview = inner.getMJPEGPreview(context);
    }

    @Override
    @JSONField(name="h264_url")
    @JsonProperty(value="h264_url")
    @Schema(name="h264_url", description="H264 preview URL", nullable=true, implementation=String.class, example="http://195.34.205.115:3020/h264/1")
    public String getH264Url() {
        return this.h264Url;
    }

    @JSONField(name="mjpeg_url")
    @JsonProperty(value="mjpeg_url")
    @Schema(name="mjpeg_url", description="MJPEG preview URL", nullable=true, implementation=String.class, example="http://127.0.0.1:3020/1")
    public String getMPEGPreview() {
        return this.mpegPreview;
    }

    public CameraInfoDTO getCameraInfo() {
        return this.cameraInfo;
    }
}

