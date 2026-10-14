/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.objectinzone;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.objectinzone.ExclusionZone;
import com.incoresoft.middleware.analytics.module.objectinzone.ObjectInZoneTimePeriod;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(name="ObjectInZonePolygon", description="Object Detection zone polygon class structure ")
public class ObjectInZonePolygon
extends TimePeriodsZone {
    @NotEmpty
    @ArraySchema(arraySchema=@Schema(name="types", description="<div><p>Objects types allowed in analytics.</p><p>Valid values are:</p><ul><li>0 - person<li>1 - bicycle<li>2 - car<li>3 - motorbike<li>4 - bus<li>5 - truck<li>6 - animal<li>7 - bags</ul></div>", example="[0, 1, 2, 3]"), schema=@Schema(name="types", description="Types", implementation=Integer.class, example="1"))
    private final int[] types;
    @JSONField(name="time_periods")
    @ArraySchema(arraySchema=@Schema(name="time_periods", description="Time periods array"), schema=@Schema(name="time_periods", description="Time periods settings", implementation=ObjectInZoneTimePeriod.class))
    @NotEmpty
    @Valid
    private final ObjectInZoneTimePeriod[] timePeriods;
    @JSONField(name="excluded")
    @Size(max=3)
    @ArraySchema(arraySchema=@Schema(name="excluded", description="Zones that will be excluded from analysis"), schema=@Schema(name="excluded", description="Exclusion zone", implementation=ExclusionZone.class))
    private final @Size(max=3) ExclusionZone[] excluded;

    @JSONCreator
    public ObjectInZonePolygon(String id, String name, String color, Point[] points, ObjectInZoneTimePeriod[] timePeriods, int[] types, ExclusionZone[] excluded) {
        super(id, name, points, color, timePeriods);
        this.timePeriods = timePeriods;
        this.types = types;
        this.excluded = excluded;
    }

    public int[] getTypes() {
        return this.types;
    }

    public ObjectInZoneTimePeriod[] getTimePeriods() {
        return this.timePeriods;
    }

    public ExclusionZone[] getExcluded() {
        return this.excluded;
    }
}

