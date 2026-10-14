/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.model.stream.Stream;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotBlank;

public class ShortStreamDTO {
    @JsonProperty(value="id")
    @JSONField(name="id")
    @Schema(name="id", description="Video stream Id", example="1")
    private int id;
    @JsonProperty(value="uuid")
    @JSONField(name="uuid")
    @Schema(name="uuid", description="Video stream Id", example="1")
    private UUID uuid;
    @JsonProperty(value="name")
    @JSONField(name="name")
    @Schema(name="name", description="Video stream name", example="Main stream", implementation=String.class)
    @NotBlank
    private String name;
    @JsonProperty(value="location")
    @JSONField(name="location")
    @Schema(name="location", description="Camera location (coordinates)", implementation=CoordinatesDTO.class)
    private CoordinatesDTO location;

    public ShortStreamDTO(Stream stream) {
        this.id = stream.getId();
        this.uuid = stream.getUUID();
        this.name = stream.getName();
        this.location = Optional.ofNullable(stream.getLocation()).map(it -> new CoordinatesDTO(it.getLatitude(), it.getLongitude())).orElse(null);
    }

    public int getId() {
        return this.id;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public CoordinatesDTO getLocation() {
        return this.location;
    }

    public ShortStreamDTO() {
    }

    public ShortStreamDTO(int id, UUID uuid, String name, CoordinatesDTO location) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.location = location;
    }
}

