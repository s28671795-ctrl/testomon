/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.smartva.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.smartva.objects.DetectedObjectEncodingDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="detection")
public final class DetectionDTO
implements AnalyticsMetadata {
    @Schema(name="va_id", description="Video analytics id", implementation=Integer.class, example="1")
    @JSONField(name="va_id")
    private final int vaId;
    @ArraySchema(schema=@Schema(name="objects", description="Detected objects", implementation=DetectedObjectEncodingDTO.class))
    @JSONField(name="objects")
    private final DetectedObjectEncodingDTO[] objects;
    @Schema(name="frame_image", description="Frame image file name", implementation=String.class, example="frame_image.jpg")
    @JSONField(name="frame_image")
    private final String frameImage;
    @Schema(name="thumbnail_image", description="Thumbnail image file name", implementation=String.class, example="thumbnail_image.jpg")
    @JSONField(name="thumbnail_image")
    private final String thumbnailImage;
    @Schema(name="timestamp", description="Detection timestamp", implementation=Instant.class, example="2021-07-01T12:00:00Z")
    @JSONField(name="timestamp")
    private final Instant timestamp;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public DetectionDTO(int vaId, DetectedObjectEncodingDTO[] objects, String frameImage, String thumbnailImage, Instant timestamp) {
        this.vaId = vaId;
        this.objects = objects;
        this.frameImage = frameImage;
        this.thumbnailImage = thumbnailImage;
        this.timestamp = timestamp;
    }

    public int getVaId() {
        return this.vaId;
    }

    public DetectedObjectEncodingDTO[] getObjects() {
        return this.objects;
    }

    public String getFrameImage() {
        return this.frameImage;
    }

    public String getThumbnailImage() {
        return this.thumbnailImage;
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
        if (!Arrays.deepEquals(this.getObjects(), other.getObjects())) {
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
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        result = result * 59 + Arrays.deepHashCode(this.getObjects());
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $thumbnailImage = this.getThumbnailImage();
        result = result * 59 + ($thumbnailImage == null ? 43 : $thumbnailImage.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "DetectionDTO(vaId=" + this.getVaId() + ", objects=" + Arrays.deepToString(this.getObjects()) + ", frameImage=" + this.getFrameImage() + ", thumbnailImage=" + this.getThumbnailImage() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }
}

