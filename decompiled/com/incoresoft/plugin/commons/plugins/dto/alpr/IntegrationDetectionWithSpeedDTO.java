/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.alpr;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.alpr.makemodel.MakeModelDTO;
import com.incoresoft.middleware.analytics.dto.analytics.ShortAnalyticsWithStreamDTO;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.analytics.models.alpr.Color;
import com.incoresoft.plugin.commons.plugins.dto.alpr.IntegrationListItemDTO;
import com.incoresoft.plugin.commons.plugins.dto.alpr.IntegrationShortListDTO;
import com.incoresoft.plugin.commons.plugins.dto.alpr.IntegrationShortSpeedEventDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.NotNull;

@Schema(name="DetectionWithSpeedDTO", description="Plate class structure")
public record IntegrationDetectionWithSpeedDTO(@Schema(name="id", description="Plate unique ID in DB", implementation=Long.class, example="1") @JsonProperty(value="id") @JSONField(name="id") long id, @Schema(name="analytics", description="Video analytics", implementation=ShortAnalyticsWithStreamDTO.class) @JsonProperty(value="analytics") @JSONField(name="analytics") ShortAnalyticsWithStreamDTO analytics, @Schema(name="number", description="License plate number", implementation=String.class, example="AB1234AA") @JsonProperty(value="number") @JSONField(name="number") String number, @Schema(name="arabic_number", description="License plate number in Arabic (unicode format)", implementation=String.class, example="U+0623U+06281234U+0623U+0623") @JsonProperty(value="arabic_number") @JSONField(name="arabic_number") String arabicNumber, @Schema(name="adr", description="Vehicle ADR", implementation=String.class, example="991234") @JsonProperty(value="adr") @JSONField(name="adr") String adr, @Schema(name="box", description="Bounding box of license plate", type="object", example="[0.5927083492279053, 0.008333333767950535, 0.6510416865348816, 0.03888889029622078]") @JsonProperty(value="box") @JSONField(name="box") double[] box, @Schema(name="country_pattern", description="Country pattern of license plate", implementation=String.class, example="UKRAINE-1") @JsonProperty(value="country_pattern") @JSONField(name="country_pattern") String countryPattern, @Schema(name="country", description="Country code by ISO 3166-1 (two-letter country codes)", implementation=String.class, example="UA") @JsonProperty(value="country") @JSONField(name="country") String country, @Schema(name="make_model", description="Make and model array list", implementation=MakeModelDTO.class) @JsonProperty(value="make_model") @JSONField(name="make_model") MakeModelDTO makeModel, @Schema(name="vehicle_type", description="Vehicle type", implementation=Integer.class, example="1") @JsonProperty(value="vehicle_type") @JSONField(name="vehicle_type") Integer vehicleTypeId, @Schema(name="direction", description="Vehicle direction relative to frame 0 - 360 degree (0 - top, 90 - right, 180 - bottom, 270 - left)", implementation=Integer.class, example="45") @JsonProperty(value="direction") @JSONField(name="direction") int direction, @Schema(name="timestamp", description="Unix timestamp", implementation=Long.class, example="123456789") @JsonProperty(value="timestamp") @JSONField(name="timestamp") long timestamp, @ArraySchema(schema=@Schema(name="lists", description="Array of lists in which this license plate", implementation=IntegrationShortListDTO.class)) @JsonProperty(value="lists") @JSONField(name="lists") List<IntegrationShortListDTO> lists, @ArraySchema(schema=@Schema(name="list_items", description="Array of license plate ID in lists DB (license plate record / card)", implementation=IntegrationListItemDTO.class)) @JsonProperty(value="list_items") @JSONField(name="list_items") List<IntegrationListItemDTO> listItems, @Schema(name="color", description="Vehicle color ID", implementation=Color.class, example="black") @JsonProperty(value="color") @JSONField(name="color") Color color, @Schema(name="location", description="Location", implementation=CoordinatesDTO.class) @JsonProperty(value="location") @JSONField(name="location") CoordinatesDTO location, @ArraySchema(schema=@Schema(name="speed_events", description="Vehicle speed events", implementation=IntegrationShortSpeedEventDTO.class)) @JsonProperty(value="speed_events") @JSONField(name="speed_events") List<IntegrationShortSpeedEventDTO> events, @Schema(name="plate_image", description="Vehicle licence plate image") @JsonProperty(value="plate_image") @JSONField(name="plate_image") String plateImage, @Schema(name="frame_image", description="Vehicle frame image") @JsonProperty(value="frame_image") @JSONField(name="frame_image") String frameImage) {
    public IntegrationDetectionWithSpeedDTO(@NotNull IntegrationDetectionWithSpeedDTO detection, @NotNull List<IntegrationShortListDTO> allowedLists, @NotNull List<IntegrationListItemDTO> allowedItems) {
        this(detection.id(), detection.analytics(), detection.number(), detection.arabicNumber(), detection.adr(), detection.box(), detection.countryPattern(), detection.country(), detection.makeModel(), detection.vehicleTypeId(), detection.direction(), detection.timestamp(), allowedLists.stream().sorted(Comparator.comparing(IntegrationShortListDTO::getName)).toList(), allowedItems, detection.color(), detection.location(), detection.events(), detection.plateImage(), detection.frameImage());
    }

    public IntegrationDetectionWithSpeedDTO withLists(List<IntegrationShortListDTO> lists) {
        return this.lists == lists ? this : new IntegrationDetectionWithSpeedDTO(this.id, this.analytics, this.number, this.arabicNumber, this.adr, this.box, this.countryPattern, this.country, this.makeModel, this.vehicleTypeId, this.direction, this.timestamp, lists, this.listItems, this.color, this.location, this.events, this.plateImage, this.frameImage);
    }

    public IntegrationDetectionWithSpeedDTO withListItems(List<IntegrationListItemDTO> listItems) {
        return this.listItems == listItems ? this : new IntegrationDetectionWithSpeedDTO(this.id, this.analytics, this.number, this.arabicNumber, this.adr, this.box, this.countryPattern, this.country, this.makeModel, this.vehicleTypeId, this.direction, this.timestamp, this.lists, listItems, this.color, this.location, this.events, this.plateImage, this.frameImage);
    }

    public IntegrationDetectionWithSpeedDTO withEvents(List<IntegrationShortSpeedEventDTO> events) {
        return this.events == events ? this : new IntegrationDetectionWithSpeedDTO(this.id, this.analytics, this.number, this.arabicNumber, this.adr, this.box, this.countryPattern, this.country, this.makeModel, this.vehicleTypeId, this.direction, this.timestamp, this.lists, this.listItems, this.color, this.location, events, this.plateImage, this.frameImage);
    }
}

