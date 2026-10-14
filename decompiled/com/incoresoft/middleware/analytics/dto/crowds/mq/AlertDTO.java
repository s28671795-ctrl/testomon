/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.crowds.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import java.time.Instant;

@AnalyticsToken(value="alert")
public class AlertDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    int vaId;
    @JSONField(name="frame_image")
    String frameImage;
    @JSONField(name="thumbnail_image")
    String thumbnailImage;
    @JSONField(name="people_num")
    int peopleNum;
    @JSONField(name="dwell_time")
    int dwellTime;
    @JSONField(name="zone")
    Zone zone;
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

    public int getPeopleNum() {
        return this.peopleNum;
    }

    public int getDwellTime() {
        return this.dwellTime;
    }

    public Zone getZone() {
        return this.zone;
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

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public void setDwellTime(int dwellTime) {
        this.dwellTime = dwellTime;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AlertDTO)) {
            return false;
        }
        AlertDTO other = (AlertDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (this.getPeopleNum() != other.getPeopleNum()) {
            return false;
        }
        if (this.getDwellTime() != other.getDwellTime()) {
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
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AlertDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        result = result * 59 + this.getPeopleNum();
        result = result * 59 + this.getDwellTime();
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $thumbnailImage = this.getThumbnailImage();
        result = result * 59 + ($thumbnailImage == null ? 43 : $thumbnailImage.hashCode());
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "AlertDTO(vaId=" + this.getVaId() + ", frameImage=" + this.getFrameImage() + ", thumbnailImage=" + this.getThumbnailImage() + ", peopleNum=" + this.getPeopleNum() + ", dwellTime=" + this.getDwellTime() + ", zone=" + String.valueOf(this.getZone()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }
}

