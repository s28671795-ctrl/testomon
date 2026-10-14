/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.gundetection;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.zone.TimePeriod;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;

public class GunDetectionZone
extends TimePeriodsZone {
    @JSONField(name="types")
    @ArraySchema(schema=@Schema(name="types", description="Types of objects for zone", implementation=Integer.class, example="0"))
    @NotEmpty
    private final int[] types;

    public GunDetectionZone(String id, String name, Point[] points, String color, int[] types, TimePeriod[] timePeriods) {
        super(id, name, points, color, Objects.requireNonNullElse(timePeriods, new TimePeriod[0]));
        this.types = Objects.requireNonNullElse(types, new int[0]);
    }

    public int[] getTypes() {
        return this.types;
    }
}

