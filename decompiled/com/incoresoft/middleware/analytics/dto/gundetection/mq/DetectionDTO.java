/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.gundetection.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.gundetection.objects.DetectedObjectDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="detection")
@Schema(name="DetectionDTO", description="Gun detection notification class structure")
public final class DetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    @Schema(name="va_id", description="Video analytics ID", implementation=Integer.class, example="1")
    private final int vaId;
    @JSONField(name="frame_image")
    @Schema(name="frame_image", description="Event frame image file name", implementation=String.class, example="frame_image.jpg")
    private final String frameImage;
    @JSONField(name="thumbnail_image")
    @Schema(name="thumbnail_image", description="Event thumbnail image file name", implementation=String.class, example="thumbnail_image.jpg")
    private final String thumbnailImage;
    @JSONField(name="zone")
    @Schema(name="zone", description="Zone object", implementation=Zone.class)
    private final Zone zone;
    @JSONField(name="objects")
    @ArraySchema(arraySchema=@Schema(description="List of objects"), schema=@Schema(name="objects", implementation=DetectedObjectDTO.class))
    private final DetectedObjectDTO[] objects;
    @JSONField(name="timestamp")
    @Schema(name="timestamp", description="Timestamp", implementation=Long.class, example="timestamp")
    private final Instant timestamp;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public int getVaId() {
        return this.vaId;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    public String getThumbnailImage() {
        return this.thumbnailImage;
    }

    public Zone getZone() {
        return this.zone;
    }

    public DetectedObjectDTO[] getObjects() {
        return this.objects;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
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
        String this$thumbnailImage = this.getThumbnailImage();
        String other$thumbnailImage = other.getThumbnailImage();
        if (this$thumbnailImage == null ? other$thumbnailImage != null : !this$thumbnailImage.equals(other$thumbnailImage)) {
            return false;
        }
        Zone this$zone = this.getZone();
        Zone other$zone = other.getZone();
        if (this$zone == null ? other$zone != null : !this$zone.equals(other$zone)) {
            return false;
        }
        if (!Arrays.deepEquals(this.getObjects(), other.getObjects())) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $thumbnailImage = this.getThumbnailImage();
        result = result * 59 + ($thumbnailImage == null ? 43 : $thumbnailImage.hashCode());
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getObjects());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "DetectionDTO(vaId=" + this.getVaId() + ", frameImage=" + this.getFrameImage() + ", thumbnailImage=" + this.getThumbnailImage() + ", zone=" + String.valueOf(this.getZone()) + ", objects=" + Arrays.deepToString(this.getObjects()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }

    public DetectionDTO(int vaId, String frameImage, String thumbnailImage, Zone zone, DetectedObjectDTO[] objects, Instant timestamp) {
        this.vaId = vaId;
        this.frameImage = frameImage;
        this.thumbnailImage = thumbnailImage;
        this.zone = zone;
        this.objects = objects;
        this.timestamp = timestamp;
    }
}

