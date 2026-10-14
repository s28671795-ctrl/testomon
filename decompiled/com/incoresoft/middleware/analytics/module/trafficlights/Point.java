/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.trafficlights;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.module.trafficlights.TrafficLightColor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Point {
    @NotNull
    private final TrafficLightColor color;
    @JSONField(defaultValue="1")
    private final double radius;
    @Min(value=0L)
    private final @Min(value=0L) double x;
    @Min(value=0L)
    private final @Min(value=0L) double y;

    public TrafficLightColor getColor() {
        return this.color;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Point(TrafficLightColor color, double radius, double x, double y) {
        this.color = color;
        this.radius = radius;
        this.x = x;
        this.y = y;
    }
}

