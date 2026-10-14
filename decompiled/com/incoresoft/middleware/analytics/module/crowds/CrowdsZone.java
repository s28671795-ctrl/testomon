/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.crowds;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.zone.TimePeriod;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CrowdsZone
extends TimePeriodsZone {
    @JSONField(name="people_num")
    @Schema(name="people_num", description="The number of people in the crowd", implementation=Integer.class, example="3")
    @Min(value=2L)
    @Max(value=100L)
    @NotNull
    private final @Min(value=2L) @Max(value=100L) @NotNull Integer peopleNum;
    @JSONField(name="detect_time")
    @Schema(name="detect_time", description="Minimum crowd detection time, sec", implementation=Integer.class, example="5")
    @Min(value=1L)
    @NotNull
    private final @Min(value=1L) @NotNull Integer detectTime;
    @JSONField(name="alert_delay")
    @Schema(name="alert_delay", description="Interval between notification, min", implementation=Integer.class, example="1")
    @Min(value=1L)
    @NotNull
    private final @Min(value=1L) @NotNull Integer alertDelay;

    public CrowdsZone(String id, String name, Point[] points, String color, TimePeriod[] timePeriods, Integer peopleNum, Integer detectTime, Integer alertDelay) {
        super(id, name, points, color, timePeriods);
        this.peopleNum = peopleNum;
        this.detectTime = detectTime;
        this.alertDelay = alertDelay;
    }

    public Integer getPeopleNum() {
        return this.peopleNum;
    }

    public Integer getDetectTime() {
        return this.detectTime;
    }

    public Integer getAlertDelay() {
        return this.alertDelay;
    }
}

