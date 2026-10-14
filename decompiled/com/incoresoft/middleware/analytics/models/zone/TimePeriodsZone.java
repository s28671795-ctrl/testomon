/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.zone;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.zone.TimePeriod;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;

@Schema(name="TimePeriodsZone", description="Day and time periods zone class structure")
public class TimePeriodsZone
extends Zone {
    @JSONField(name="color", defaultValue="#A347FF")
    @Schema(name="color", description="Color of zone", implementation=String.class, example="#ffffff")
    private final String color;
    @JSONField(name="time_periods")
    @ArraySchema(schema=@Schema(name="time_periods", description="Time periods for zone", implementation=TimePeriod.class))
    @NotEmpty
    private final TimePeriod[] timePeriods;

    @JSONCreator
    public TimePeriodsZone(String id, String name, Point[] points, String color, TimePeriod[] timePeriods) {
        super(id, name, points);
        this.color = color;
        this.timePeriods = timePeriods;
    }

    public String getColor() {
        return this.color;
    }

    public TimePeriod[] getTimePeriods() {
        return this.timePeriods;
    }
}

