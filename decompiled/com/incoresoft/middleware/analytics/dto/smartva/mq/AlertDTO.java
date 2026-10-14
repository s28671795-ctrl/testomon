/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.smartva.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.smartva.objects.AlertDetectedObjectEncodingDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.smartva.PresenceActionType;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Trigger;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="alert")
public final class AlertDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    @Schema(name="va_id", description="Video analytics ID", example="1")
    private final int vaId;
    @JSONField(name="trigger")
    @Schema(name="trigger", description="Trigger", implementation=Trigger.class)
    private final Trigger trigger;
    @JSONField(name="presence_action_type")
    @Schema(name="presence_action_type", description="Presence action type", implementation=PresenceActionType.class)
    private final PresenceActionType presenceActionType;
    @JSONField(name="objects")
    @Schema(name="objects", description="Detected objects", implementation=AlertDetectedObjectEncodingDTO[].class)
    private final AlertDetectedObjectEncodingDTO[] objects;
    @JSONField(name="frame_image")
    @Schema(name="frame_image", description="Frame image", implementation=String.class, example="frame.jpg")
    private final String frameImage;
    @JSONField(name="thumbnail_image")
    @Schema(name="thumbnail_image", description="Thumbnail image", implementation=String.class, example="thumbnail.jpg")
    private final String thumbnailImage;
    @JSONField(name="timestamp")
    @Schema(name="timestamp", description="Unix timestamp(in milliseconds)", implementation=Instant.class, example="1660220795000")
    private final Instant timestamp;
    @JSONField(name="rule_id")
    @Schema(name="rule_id", description="Rule ID", implementation=String.class, example="537f49859cd2")
    private final String ruleId;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public AlertDTO(int vaId, Trigger trigger, PresenceActionType presenceActionType, AlertDetectedObjectEncodingDTO[] objects, String frameImage, String thumbnailImage, Instant timestamp, String ruleId) {
        this.vaId = vaId;
        this.trigger = trigger;
        this.presenceActionType = presenceActionType;
        this.objects = objects;
        this.frameImage = frameImage;
        this.thumbnailImage = thumbnailImage;
        this.timestamp = timestamp;
        this.ruleId = ruleId;
    }

    public int getVaId() {
        return this.vaId;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }

    public PresenceActionType getPresenceActionType() {
        return this.presenceActionType;
    }

    public AlertDetectedObjectEncodingDTO[] getObjects() {
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

    public String getRuleId() {
        return this.ruleId;
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
        Trigger this$trigger = this.getTrigger();
        Trigger other$trigger = other.getTrigger();
        if (this$trigger == null ? other$trigger != null : !this$trigger.equals(other$trigger)) {
            return false;
        }
        PresenceActionType this$presenceActionType = this.getPresenceActionType();
        PresenceActionType other$presenceActionType = other.getPresenceActionType();
        if (this$presenceActionType == null ? other$presenceActionType != null : !((Object)((Object)this$presenceActionType)).equals((Object)other$presenceActionType)) {
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
        String this$ruleId = this.getRuleId();
        String other$ruleId = other.getRuleId();
        return !(this$ruleId == null ? other$ruleId != null : !this$ruleId.equals(other$ruleId));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        Trigger $trigger = this.getTrigger();
        result = result * 59 + ($trigger == null ? 43 : $trigger.hashCode());
        PresenceActionType $presenceActionType = this.getPresenceActionType();
        result = result * 59 + ($presenceActionType == null ? 43 : ((Object)((Object)$presenceActionType)).hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getObjects());
        String $frameImage = this.getFrameImage();
        result = result * 59 + ($frameImage == null ? 43 : $frameImage.hashCode());
        String $thumbnailImage = this.getThumbnailImage();
        result = result * 59 + ($thumbnailImage == null ? 43 : $thumbnailImage.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        String $ruleId = this.getRuleId();
        result = result * 59 + ($ruleId == null ? 43 : $ruleId.hashCode());
        return result;
    }

    public String toString() {
        return "AlertDTO(vaId=" + this.getVaId() + ", trigger=" + String.valueOf(this.getTrigger()) + ", presenceActionType=" + String.valueOf((Object)this.getPresenceActionType()) + ", objects=" + Arrays.deepToString(this.getObjects()) + ", frameImage=" + this.getFrameImage() + ", thumbnailImage=" + this.getThumbnailImage() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", ruleId=" + this.getRuleId() + ")";
    }
}

