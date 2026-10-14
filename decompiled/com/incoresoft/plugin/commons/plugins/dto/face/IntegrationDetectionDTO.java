/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.face;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.analytics.ShortAnalyticsWithStreamDTO;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.analytics.models.face.gender.Gender;
import com.incoresoft.middleware.analytics.models.face.race.Race;
import com.incoresoft.plugin.commons.plugins.dto.face.IntegrationShortListItemDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(name="DetectionDTO", description="Face detection     class structure")
public record IntegrationDetectionDTO(@Schema(name="id", description="Face ID", implementation=Long.class, example="1") @JSONField(name="id") @JsonProperty(value="id") long id, @Schema(name="analytics", description="Video analytics", implementation=ShortAnalyticsWithStreamDTO.class) @JsonProperty(value="analytics") @JSONField(name="analytics") ShortAnalyticsWithStreamDTO analytics, @Schema(name="list_item", description="Face list item", implementation=IntegrationShortListItemDTO.class) @JSONField(name="list_item") @JsonProperty(value="list_item") IntegrationShortListItemDTO listItem, @Schema(name="list_item_confidence", description="Similarity confidence with list item", implementation=Integer.class, example="95") @JSONField(name="list_item_confidence") @JsonProperty(value="list_item_confidence") int listItemConfidence, @Schema(name="confidence", description="Similarity confidence with detection", implementation=Float.class, example="95") @JSONField(name="confidence") @JsonProperty(value="confidence") float similarityConfidence, @Schema(name="box", description="Bounding box of face", type="object", example="[0.592708, 0.0083333, 0.6510416, 0.0388888]") @JSONField(name="box") @JsonProperty(value="box") double[] box, @Schema(name="timestamp", description="Timestamp", implementation=Long.class, example="1234567890") @JSONField(name="timestamp") @JsonProperty(value="timestamp") Instant timestamp, @Schema(name="millis_from_start", description="Only for files: millis from file's start", implementation=Integer.class, example="0") @JSONField(name="millis_from_start") @JsonProperty(value="millis_from_start") Integer millisFromStart, @Schema(name="age", description="Person age", implementation=String.class, example="30") @JSONField(name="age") @JsonProperty(value="age") String age, @Schema(name="gender", description="Person gender", implementation=Gender.class) @JSONField(name="gender") @JsonProperty(value="gender") Gender gender, @Schema(name="race", description="Person race", implementation=Race.class) @JSONField(name="race") @JsonProperty(value="race") Race race, @Schema(name="spoofed", description="Spoofed face", implementation=Boolean.class, example="false") @JSONField(name="spoofed") @JsonProperty(value="spoofed") Boolean spoofed, @Schema(name="mask", description="Mask presence", implementation=Boolean.class, example="false") @JSONField(name="mask") @JsonProperty(value="mask") Boolean mask, @Schema(name="location", description="Location", implementation=CoordinatesDTO.class) @JSONField(name="location") @JsonProperty(value="location") CoordinatesDTO location, @Schema(name="face_image", description="Face image", implementation=String.class) @JSONField(name="face_image") @JsonProperty(value="face_image") String faceImage, @Schema(name="frame_image", description="Frame image", implementation=String.class) @JSONField(name="frame_image") @JsonProperty(value="frame_image") String frameImage) {
    public boolean inList() {
        return this.listItem != null;
    }

    public IntegrationDetectionDTO withListItem(IntegrationShortListItemDTO listItem) {
        return this.listItem == listItem ? this : new IntegrationDetectionDTO(this.id, this.analytics, listItem, this.listItemConfidence, this.similarityConfidence, this.box, this.timestamp, this.millisFromStart, this.age, this.gender, this.race, this.spoofed, this.mask, this.location, this.faceImage, this.frameImage);
    }
}

