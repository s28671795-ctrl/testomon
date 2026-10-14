/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.motion.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.motion.MotionRuleType;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="detection")
public class DetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    int vaId;
    @JSONField(name="frame_image")
    String frameImage;
    @JSONField(name="thumbnail_image")
    String thumbnailImage;
    @JSONField(name="rule_id")
    String ruleId;
    @JSONField(name="rule_type")
    MotionRuleType ruleType;
    @JSONField(name="zone")
    Zone zone;
    @JSONField(name="timestamp")
    Instant timestamp;
    @JSONField(name="box")
    double[] box;
    @JSONField(name="dwell_time")
    int dwellTime;

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

    public String getRuleId() {
        return this.ruleId;
    }

    public MotionRuleType getRuleType() {
        return this.ruleType;
    }

    public Zone getZone() {
        return this.zone;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public double[] getBox() {
        return this.box;
    }

    public int getDwellTime() {
        return this.dwellTime;
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

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public void setRuleType(MotionRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setBox(double[] box) {
        this.box = box;
    }

    public void setDwellTime(int dwellTime) {
        this.dwellTime = dwellTime;
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
        String this$ruleId = this.getRuleId();
        String other$ruleId = other.getRuleId();
        if (this$ruleId == null ? other$ruleId != null : !this$ruleId.equals(other$ruleId)) {
            return false;
        }
        MotionRuleType this$ruleType = this.getRuleType();
        MotionRuleType other$ruleType = other.getRuleType();
        if (this$ruleType == null ? other$ruleType != null : !((Object)((Object)this$ruleType)).equals((Object)other$ruleType)) {
            return false;
        }
        Zone this$zone = this.getZone();
        Zone other$zone = other.getZone();
        if (this$zone == null ? other$zone != null : !this$zone.equals(other$zone)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        return Arrays.equals(this.getBox(), other.getBox());
    }

    protected boolean canEqual(Object other) {
        return other instanceof DetectionDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        result = result * 59 + this.getDwellTime();
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $thumbnailImage = this.getThumbnailImage();
        result = result * 59 + ($thumbnailImage == null ? 43 : $thumbnailImage.hashCode());
        String $ruleId = this.getRuleId();
        result = result * 59 + ($ruleId == null ? 43 : $ruleId.hashCode());
        MotionRuleType $ruleType = this.getRuleType();
        result = result * 59 + ($ruleType == null ? 43 : ((Object)((Object)$ruleType)).hashCode());
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        return result;
    }

    public String toString() {
        return "DetectionDTO(vaId=" + this.getVaId() + ", frameImage=" + this.getFrameImage() + ", thumbnailImage=" + this.getThumbnailImage() + ", ruleId=" + this.getRuleId() + ", ruleType=" + String.valueOf((Object)this.getRuleType()) + ", zone=" + String.valueOf(this.getZone()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", box=" + Arrays.toString(this.getBox()) + ", dwellTime=" + this.getDwellTime() + ")";
    }
}

