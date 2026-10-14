/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.smokefire;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.analytics.ShortAnalyticsDTO;
import com.incoresoft.middleware.analytics.dto.smokefire.objects.DetectedObjectDTO;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import com.incoresoft.middleware.notifications.Notification;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

@Schema(name="NotificationDTO", description="Smoke and fire notification class structure")
public record NotificationDTO(@JsonProperty(value="id") @JSONField(name="id") @Schema(name="id", description="Notification ID", implementation=Long.class, example="1") long id, @JsonProperty(value="objects") @JSONField(name="objects") @ArraySchema(schema=@Schema(name="objects", description="Array of detected objects", implementation=DetectedObjectDTO.class)) List<DetectedObjectDTO> objects, @JsonProperty(value="timestamp") @JSONField(name="timestamp") @Schema(name="timestamp", description="Unix timestamp", implementation=Long.class, example="123456789") Instant timestamp, @JsonProperty(value="frame_image") @JSONField(name="frame_image") @Schema(name="frame_image", description="Frame image", implementation=String.class, example="frame_image.jpg") String frameImage, @JsonProperty(value="thumbnail_image") @JSONField(name="thumbnail_image") @Schema(name="thumbnail_image", description="Thumbnail image", implementation=String.class, example="thumbnail_image.jpg") String thumbnailImage, @JsonProperty(value="zone") @JSONField(name="zone") @Schema(name="zone", description="Zone class", implementation=Zone.class) Zone zone, @JsonProperty(value="analytics") @Schema(name="analytics", description="Video analytics", implementation=ShortAnalyticsDTO.class) @JSONField(name="analytics") ShortAnalyticsDTO analytics, @JsonProperty(value="va_id") @JSONField(name="va_id") @Schema(name="va_id", description="Video analytics ID", implementation=Integer.class, example="1") Integer vaId, @JsonProperty(value="location") @Schema(name="location", description="Location", implementation=CoordinatesDTO.class) @JSONField(name="location") CoordinatesDTO location, @JsonIgnore @JSONField(serialize=false, deserialize=false) @Schema(hidden=true) int clientId) implements Notification
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

