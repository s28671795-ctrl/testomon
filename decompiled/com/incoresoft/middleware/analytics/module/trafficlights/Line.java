/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.trafficlights;

public class Line {
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final int direction;

    public Line(double x1, double y1, double x2, double y2, int direction) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.direction = direction;
    }

    public double getX1() {
        return this.x1;
    }

    public double getY1() {
        return this.y1;
    }

    public double getX2() {
        return this.x2;
    }

    public double getY2() {
        return this.y2;
    }

    public int getDirection() {
        return this.direction;
    }
}

