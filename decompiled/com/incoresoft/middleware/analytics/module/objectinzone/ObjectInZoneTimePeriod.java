/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.objectinzone;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.objectinzone.Trigger;
import com.incoresoft.middleware.analytics.models.zone.TimePeriod;
import com.incoresoft.middleware.analytics.module.objectinzone.validators.dwelltime.DwellTimeMustBeNonNegative;
import com.incoresoft.middleware.analytics.module.objectinzone.validators.objectcounter.ObjectCounterLimitMustBeGreaterThanZero;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;

@Schema(name="DayAndTimePeriod", description="Day and time periods array for rule settings")
@ObjectCounterLimitMustBeGreaterThanZero
@DwellTimeMustBeNonNegative
public class ObjectInZoneTimePeriod
extends TimePeriod {
    @NotNull
    @JSONField(name="trigger")
    @Schema(name="trigger", description="Trigger type for the current period", implementation=Trigger.class)
    private final Trigger trigger;
    @JSONField(name="dwell_time", defaultValue="0")
    @Schema(name="dwell_time", description="Dwell time", implementation=Integer.class, example="5")
    private final Integer dwellTime;
    @JSONField(name="object_counter_limit", defaultValue="0")
    @Schema(name="object_counter_limit", description="Min object limit for object counter trigger", implementation=Integer.class, example="5")
    private final Integer objectCounterLimit;

    public ObjectInZoneTimePeriod(String startTime, String endTime, int[] selectedDays, Trigger trigger, Integer dwellTime, Integer objectCounterLimit) {
        super(startTime, endTime, selectedDays);
        this.trigger = trigger;
        this.dwellTime = dwellTime;
        this.objectCounterLimit = objectCounterLimit;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }

    public Integer getDwellTime() {
        return this.dwellTime;
    }

    public Integer getObjectCounterLimit() {
        return this.objectCounterLimit;
    }
}

