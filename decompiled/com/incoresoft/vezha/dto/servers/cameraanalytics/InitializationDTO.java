/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers.cameraanalytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.dto.streams.ResolutionDTO;
import io.swagger.v3.oas.annotations.media.Schema;

public final class InitializationDTO {
    @JSONField(name="server_id")
    @Schema(name="server_id", description="Analytics server ID")
    private final String serverId;
    @JSONField(name="camera_uuid")
    @Schema(name="camera_uuid", description="Camera UUID")
    private final String cameraUUID;
    @JSONField(name="camera_name")
    @Schema(name="camera_name", description="Camera name")
    private final String cameraName;
    @JSONField(name="analytics_type")
    @Schema(name="analytics_type", description="Analytics type")
    private final AnalyticsTypes analyticsType;
    @JSONField(name="frame_image_base64")
    @Schema(name="frame_image_base64", description="Frame image as base64 string")
    private final String frameImage;
    @JSONField(name="resolution")
    @Schema(name="resolution", description="Camera resolution")
    private final ResolutionDTO resolution;

    public InitializationDTO(String serverId, String cameraUUID, String cameraName, AnalyticsTypes analyticsType, String frameImage, ResolutionDTO resolution) {
        this.serverId = serverId;
        this.cameraUUID = cameraUUID;
        this.cameraName = cameraName;
        this.analyticsType = analyticsType;
        this.frameImage = frameImage;
        this.resolution = resolution;
    }

    public String getServerId() {
        return this.serverId;
    }

    public String getCameraUUID() {
        return this.cameraUUID;
    }

    public String getCameraName() {
        return this.cameraName;
    }

    public AnalyticsTypes getAnalyticsType() {
        return this.analyticsType;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    public ResolutionDTO getResolution() {
        return this.resolution;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InitializationDTO)) {
            return false;
        }
        InitializationDTO other = (InitializationDTO)o;
        String this$serverId = this.getServerId();
        String other$serverId = other.getServerId();
        if (this$serverId == null ? other$serverId != null : !this$serverId.equals(other$serverId)) {
            return false;
        }
        String this$cameraUUID = this.getCameraUUID();
        String other$cameraUUID = other.getCameraUUID();
        if (this$cameraUUID == null ? other$cameraUUID != null : !this$cameraUUID.equals(other$cameraUUID)) {
            return false;
        }
        String this$cameraName = this.getCameraName();
        String other$cameraName = other.getCameraName();
        if (this$cameraName == null ? other$cameraName != null : !this$cameraName.equals(other$cameraName)) {
            return false;
        }
        AnalyticsTypes this$analyticsType = this.getAnalyticsType();
        AnalyticsTypes other$analyticsType = other.getAnalyticsType();
        if (this$analyticsType == null ? other$analyticsType != null : !this$analyticsType.equals(other$analyticsType)) {
            return false;
        }
        String this$frameImage = this.getFrameImage();
        String other$frameImage = other.getFrameImage();
        if (this$frameImage == null ? other$frameImage != null : !this$frameImage.equals(other$frameImage)) {
            return false;
        }
        ResolutionDTO this$resolution = this.getResolution();
        ResolutionDTO other$resolution = other.getResolution();
        return !(this$resolution == null ? other$resolution != null : !((Object)this$resolution).equals(other$resolution));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $serverId = this.getServerId();
        result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
        String $cameraUUID = this.getCameraUUID();
        result = result * 59 + ($cameraUUID == null ? 43 : $cameraUUID.hashCode());
        String $cameraName = this.getCameraName();
        result = result * 59 + ($cameraName == null ? 43 : $cameraName.hashCode());
        AnalyticsTypes $analyticsType = this.getAnalyticsType();
        result = result * 59 + ($analyticsType == null ? 43 : $analyticsType.hashCode());
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        ResolutionDTO $resolution = this.getResolution();
        result = result * 59 + ($resolution == null ? 43 : ((Object)$resolution).hashCode());
        return result;
    }

    public String toString() {
        return "InitializationDTO(serverId=" + this.getServerId() + ", cameraUUID=" + this.getCameraUUID() + ", cameraName=" + this.getCameraName() + ", analyticsType=" + String.valueOf(this.getAnalyticsType()) + ", frameImage=" + this.getFrameImage() + ", resolution=" + String.valueOf(this.getResolution()) + ")";
    }
}

