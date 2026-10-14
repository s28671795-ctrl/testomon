/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.smokefire.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.smokefire.objects.DetectedObjectDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import java.time.Instant;
import java.util.List;

@AnalyticsToken(value="detection")
public class DetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    int vaId;
    @JSONField(name="frame_image")
    String frameImage;
    @JSONField(name="thumbnail_image")
    String thumbnailImage;
    @JSONField(name="zone")
    Zone zone;
    @JSONField(name="objects")
    List<DetectedObjectDTO> objects;
    @JSONField(name="timestamp")
    Instant timestamp;

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

    public List<DetectedObjectDTO> getObjects() {
        return this.objects;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public void setVaId(int vaId) {
        this.vaId = vaId;
    }

    public void setFrameImage(String frameImage) {
        this.frameImage = frameImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setObjects(List<DetectedObjectDTO> objects) {
        this.objects = objects;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DetectionDTO)) {
            return false;
        }
        DetectionDTO other = (DetectionDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
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
        List<DetectedObjectDTO> this$objects = this.getObjects();
        List<DetectedObjectDTO> other$objects = other.getObjects();
        if (this$objects == null ? other$objects != null : !((Object)this$objects).equals(other$objects)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DetectionDTO;
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
        List<DetectedObjectDTO> $objects = this.getObjects();
        result = result * 59 + ($objects == null ? 43 : ((Object)$objects).hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "DetectionDTO(vaId=" + this.getVaId() + ", frameImage=" + this.getFrameImage() + ", thumbnailImage=" + this.getThumbnailImage() + ", zone=" + String.valueOf(this.getZone()) + ", objects=" + String.valueOf(this.getObjects()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }
}

