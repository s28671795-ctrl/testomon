/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.objectinzone.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.objectinzone.objects.DetectedObjectEncodingDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.objectinzone.ActionType;
import com.incoresoft.middleware.analytics.models.objectinzone.Trigger;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="alert")
public final class AlertDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    private final int vaId;
    @JSONField(name="trigger")
    private final Trigger trigger;
    @JSONField(name="dwell_time")
    private final int dwellTime;
    @JSONField(name="action")
    private final ActionType action;
    @JSONField(name="objects")
    private final DetectedObjectEncodingDTO[] objects;
    @JSONField(name="frame_image")
    private final String frameImage;
    @JSONField(name="thumbnail_image")
    private final String thumbnailImage;
    @JSONField(name="timestamp")
    private final Instant timestamp;
    @JSONField(name="zone")
    private final Zone zone;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public AlertDTO(int vaId, Trigger trigger, int dwellTime, ActionType action, DetectedObjectEncodingDTO[] objects, String frameImage, String thumbnailImage, Instant timestamp, Zone zone) {
        this.vaId = vaId;
        this.trigger = trigger;
        this.dwellTime = dwellTime;
        this.action = action;
        this.objects = objects;
        this.frameImage = frameImage;
        this.thumbnailImage = thumbnailImage;
        this.timestamp = timestamp;
        this.zone = zone;
    }

    public int getVaId() {
        return this.vaId;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }

    public int getDwellTime() {
        return this.dwellTime;
    }

    public ActionType getAction() {
        return this.action;
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

    public Zone getZone() {
        return this.zone;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AlertDTO)) {
            return false;
        }
        AlertDTO other = (AlertDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (this.getDwellTime() != other.getDwellTime()) {
            return false;
        }
        Trigger this$trigger = this.getTrigger();
        Trigger other$trigger = other.getTrigger();
        if (this$trigger == null ? other$trigger != null : !((Object)((Object)this$trigger)).equals((Object)other$trigger)) {
            return false;
        }
        ActionType this$action = this.getAction();
        ActionType other$action = other.getAction();
        if (this$action == null ? other$action != null : !((Object)((Object)this$action)).equals((Object)other$action)) {
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
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        Zone this$zone = this.getZone();
        Zone other$zone = other.getZone();
        return !(this$zone == null ? other$zone != null : !this$zone.equals(other$zone));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        result = result * 59 + this.getDwellTime();
        Trigger $trigger = this.getTrigger();
        result = result * 59 + ($trigger == null ? 43 : ((Object)((Object)$trigger)).hashCode());
        ActionType $action = this.getAction();
        result = result * 59 + ($action == null ? 43 : ((Object)((Object)$action)).hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getObjects());
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $thumbnailImage = this.getThumbnailImage();
        result = result * 59 + ($thumbnailImage == null ? 43 : $thumbnailImage.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        return result;
    }

    public String toString() {
        return "AlertDTO(vaId=" + this.getVaId() + ", trigger=" + String.valueOf((Object)this.getTrigger()) + ", dwellTime=" + this.getDwellTime() + ", action=" + String.valueOf((Object)this.getAction()) + ", objects=" + Arrays.deepToString(this.getObjects()) + ", frameImage=" + this.getFrameImage() + ", thumbnailImage=" + this.getThumbnailImage() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", zone=" + String.valueOf(this.getZone()) + ")";
    }
}

