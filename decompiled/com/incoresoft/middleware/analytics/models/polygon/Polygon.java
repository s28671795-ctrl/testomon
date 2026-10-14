/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.polygon;

import com.incoresoft.middleware.analytics.models.polygon.Point;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Polygon {
    protected String id;
    @Size(min=3)
    @Valid
    protected @Size(min=3) @Valid Point[] points;
    @NotBlank
    protected String name;

    public String getId() {
        return this.id;
    }

    public Point[] getPoints() {
        return this.points;
    }

    public String getName() {
        return this.name;
    }

    public Polygon(String id, Point[] points, String name) {
        this.id = id;
        this.points = points;
        this.name = name;
    }
}

