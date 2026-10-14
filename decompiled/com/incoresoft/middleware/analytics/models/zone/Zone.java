/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.zone;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.Utils.StringUtils;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsMustBeNotNull;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsValidatable;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name="Zone", description="Zone polygon base class")
@PointsMustBeNotNull
public class Zone
implements PointsValidatable {
    @JsonProperty(value="id")
    @NotBlank
    @Schema(name="id", description="Zone ID", implementation=String.class, example="537f49859cd2")
    protected final String id;
    @JsonProperty(value="name")
    @JSONField(name="name", defaultValue="Zone 1")
    @Schema(name="name", description="Name of zone", implementation=String.class, example="Zone 1")
    @NotBlank
    protected final String name;
    @JsonProperty(value="points")
    @Valid
    @ArraySchema(arraySchema=@Schema(name="points", description="Points array of zone polygon"), schema=@Schema(implementation=Point.class))
    @Size(min=3, max=256)
    @NotNull
    protected final @Valid @Size(min=3, max=256) @NotNull Point[] points;

    @JsonCreator
    @JSONCreator
    public Zone(@JsonProperty(value="id") String id, @JsonProperty(value="name") String name, @JsonProperty(value="points") Point[] points) {
        this.id = this.getZoneId(id);
        this.name = name;
        this.points = points;
    }

    private String getZoneId(String id) {
        if (id == null || StringUtils.isBlank((CharSequence)id)) {
            String[] split = UUID.randomUUID().toString().split("-");
            id = split[0] + split[1];
        }
        return id;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Point[] getPoints() {
        return this.points;
    }
}

