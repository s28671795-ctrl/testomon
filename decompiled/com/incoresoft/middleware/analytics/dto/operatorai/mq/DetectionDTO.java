/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.operatorai.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="detection")
public final class DetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    @JsonProperty(value="va_id")
    @Schema(name="va_id", description="Video Analytics ID", implementation=Integer.class, example="12345")
    private final int vaId;
    @JSONField(name="frame_image")
    @JsonProperty(value="frame_image")
    @Schema(name="frame_image", description="Frame image", implementation=String.class, example="frame_image.jpg")
    private final String frameImage;
    @JSONField(name="timestamp")
    @JsonProperty(value="timestamp")
    @Schema(name="timestamp", description="Timestamp", implementation=Long.class, example="1691687997000")
    @JsonFormat(shape=JsonFormat.Shape.NUMBER)
    private final Instant timestamp;
    @JSONField(name="box")
    @JsonProperty(value="box")
    @Schema(name="box", description="Object bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;
    @JSONField(name="uuid")
    @JsonProperty(value="uuid")
    @Schema(name="uuid", description="Object UUID", implementation=String.class, example="1dee2937-f160-4d00-bc82-61454dd99997")
    private final String uuid;
    @JSONField(name="zone")
    @JsonProperty(value="zone")
    @Schema(name="zone", description="Zone where the object was detected", implementation=Zone.class)
    private final Zone zone;
    @JSONField(name="message")
    @JsonProperty(value="message")
    @Schema(name="message", description="Message describing the detection")
    private final String message;

    @Override
    @JSONField(serialize=false, deserialize=false)
    @JsonIgnore
    @Schema(hidden=true)
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public DetectionDTO(int vaId, String frameImage, Instant timestamp, double[] box, String uuid, Zone zone, String message) {
        this.vaId = vaId;
        this.frameImage = frameImage;
        this.timestamp = timestamp;
        this.box = box;
        this.uuid = uuid;
        this.zone = zone;
        this.message = message;
    }

    public int getVaId() {
        return this.vaId;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public double[] getBox() {
        return this.box;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Zone getZone() {
        return this.zone;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DetectionDTO)) {
            return false;
        }
        DetectionDTO other = (DetectionDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        String this$frameImage = this.getFrameImage();
        String other$frameImage = other.getFrameImage();
        if (this$frameImage == null ? other$frameImage != null : !this$frameImage.equals(other$frameImage)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        if (!Arrays.equals(this.getBox(), other.getBox())) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
            return false;
        }
        Zone this$zone = this.getZone();
        Zone other$zone = other.getZone();
        if (this$zone == null ? other$zone != null : !this$zone.equals(other$zone)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        return !(this$message == null ? other$message != null : !this$message.equals(other$message));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "DetectionDTO(vaId=" + this.getVaId() + ", frameImage=" + this.getFrameImage() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", box=" + Arrays.toString(this.getBox()) + ", uuid=" + this.getUuid() + ", zone=" + String.valueOf(this.getZone()) + ", message=" + this.getMessage() + ")";
    }
}

