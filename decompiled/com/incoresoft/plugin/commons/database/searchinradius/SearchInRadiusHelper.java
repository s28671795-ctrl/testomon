/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.database.searchinradius;

import com.incoresoft.middleware.model.Coordinates;
import com.incoresoft.plugin.commons.params.AnalyticsDetectionParams;
import java.math.BigDecimal;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

public class SearchInRadiusHelper {
    private static final int EARTH_RADIUS_KM = 6371;

    public static Condition inRadius(AnalyticsDetectionParams params, Field<BigDecimal> latitude, Field<BigDecimal> longitude) {
        if (!params.areLocationParamsSpecified()) {
            return DSL.trueCondition();
        }
        String sin = "SIN(RADIANS({0})) * SIN(RADIANS({2}))";
        String cos = "COS(RADIANS({0})) * COS(RADIANS({2})) * COS((RADIANS({1}-{3})))";
        String distance = String.format("6371 * ACOS(%s + %s)", sin, cos);
        Field<BigDecimal> distanceField = DSL.field(distance, BigDecimal.class, latitude, longitude, params.getLatitude(), params.getLongitude());
        return distanceField.lessOrEqual(BigDecimal.valueOf(params.getRadius()));
    }

    public static boolean inRadius(AnalyticsDetectionParams params, Coordinates coordinates) {
        double cos;
        if (params == null || !params.areLocationParamsSpecified()) {
            return true;
        }
        double lat1 = Math.toRadians(params.getLatitude());
        double lat2 = Math.toRadians(coordinates.getLatitude());
        double lng1 = Math.toRadians(params.getLongitude());
        double lng2 = Math.toRadians(coordinates.getLongitude());
        double sin = Math.sin(lat1) * Math.sin(lat2);
        return Math.acos(sin + (cos = Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1))) * 6371.0 < params.getRadius();
    }
}

