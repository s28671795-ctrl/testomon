/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.polygon.validations;

import com.incoresoft.middleware.analytics.models.polygon.Point;

public interface PointsValidatable {
    public static final String VALIDATING_FIELD = "points";

    public Point[] getPoints();
}

