/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.gundetection;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.analytics.ShortAnalyticsDTO;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import com.incoresoft.middleware.notifications.Notification;
import com.incoresoft.plugin.commons.plugins.dto.gundetection.DetectedObjectDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

@Schema(name="GunDetectionNotificationDTO", description="Gun detection notification class structure")
public record NotificationDTO(@JSONField(name="id") @JsonProperty(value="id") @Schema(name="id", description="Notification ID", implementation=Integer.class, example="1") long id, @JSONField(name="objects") @JsonProperty(value="objects") @ArraySchema(schema=@Schema(name="objects", description="Array of detected objects", implementation=DetectedObjectDTO.class)) List<DetectedObjectDTO> objects, @JSONField(name="timestamp") @JsonProperty(value="timestamp") @Schema(name="timestamp", description="Unix timestamp", implementation=Long.class, example="123456789") Instant timestamp, @JSONField(name="frame_image") @JsonProperty(value="frame_image") @Schema(name="frame_image", description="Frame image", implementation=String.class, example="frame_image.jpg") String frameImage, @JSONField(name="thumbnail_image") @JsonProperty(value="thumbnail_image") @Schema(name="thumbnail_image", description="Thumbnail image", implementation=String.class, example="thumbnail_image.jpg") String thumbnailImage, @JSONField(name="zone") @JsonProperty(value="zone") @Schema(name="zone", description="Zone class", implementation=Zone.class) Zone zone, @Schema(name="analytics", description="Video analytics", implementation=ShortAnalyticsDTO.class) @JsonProperty(value="analytics") @JSONField(name="analytics") ShortAnalyticsDTO analytics, @JSONField(name="va_id") @JsonProperty(value="va_id") @Schema(name="va_id", description="Video analytics ID", implementation=Integer.class, example="1") Integer vaId, @Schema(name="location", description="Location", implementation=CoordinatesDTO.class) @JSONField(name="location") @JsonProperty(value="location") CoordinatesDTO location, @JsonIgnore @JSONField(serialize=false, deserialize=false) @Schema(hidden=true) int clientId) implements Notification
{
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public Integer getVaId() {
        return this.vaId;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }
}

