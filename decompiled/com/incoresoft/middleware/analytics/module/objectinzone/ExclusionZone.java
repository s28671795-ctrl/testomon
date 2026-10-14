/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.objectinzone;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsMustBeNotNull;
import com.incoresoft.middleware.analytics.models.polygon.validations.PointsValidatable;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name="ExclusionZone", description="Exclusion polygon structure")
@PointsMustBeNotNull
public class ExclusionZone
implements PointsValidatable {
    @Valid
    @ArraySchema(arraySchema=@Schema(name="points", description="Points array of zone polygon"), schema=@Schema(implementation=Point.class))
    @Size(min=3, max=256)
    @NotNull
    private final @Valid @Size(min=3, max=256) @NotNull Point[] points;

    @JSONCreator
    public ExclusionZone(Point[] points) {
        this.points = points;
    }

    @Override
    public Point[] getPoints() {
        return this.points;
    }
}

