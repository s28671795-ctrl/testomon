/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.grpc.Points;
import java.util.List;

public final class ConditionFactory {
    private ConditionFactory() {
    }

    public static Points.Condition hasId(Points.PointId id) {
        return Points.Condition.newBuilder().setHasId(Points.HasIdCondition.newBuilder().addHasId(id).build()).build();
    }

    public static Points.Condition hasId(List<Points.PointId> ids) {
        return Points.Condition.newBuilder().setHasId(Points.HasIdCondition.newBuilder().addAllHasId(ids).build()).build();
    }

    public static Points.Condition isEmpty(String field2) {
        return Points.Condition.newBuilder().setIsEmpty(Points.IsEmptyCondition.newBuilder().setKey(field2).build()).build();
    }

    public static Points.Condition isNull(String field2) {
        return Points.Condition.newBuilder().setIsNull(Points.IsNullCondition.newBuilder().setKey(field2).build()).build();
    }

    public static Points.Condition matchKeyword(String field2, String keyword) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setMatch(Points.Match.newBuilder().setKeyword(keyword).build()).build()).build();
    }

    public static Points.Condition matchText(String field2, String text) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setMatch(Points.Match.newBuilder().setText(text).build()).build()).build();
    }

    public static Points.Condition match(String field2, boolean value) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setMatch(Points.Match.newBuilder().setBoolean(value).build()).build()).build();
    }

    public static Points.Condition match(String field2, long value) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setMatch(Points.Match.newBuilder().setInteger(value).build()).build()).build();
    }

    public static Points.Condition matchKeywords(String field2, List<String> keywords) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setMatch(Points.Match.newBuilder().setKeywords(Points.RepeatedStrings.newBuilder().addAllStrings(keywords).build()).build()).build()).build();
    }

    public static Points.Condition matchValues(String field2, List<Long> values2) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setMatch(Points.Match.newBuilder().setIntegers(Points.RepeatedIntegers.newBuilder().addAllIntegers(values2).build()).build()).build()).build();
    }

    public static Points.Condition matchExceptKeywords(String field2, List<String> keywords) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setMatch(Points.Match.newBuilder().setExceptKeywords(Points.RepeatedStrings.newBuilder().addAllStrings(keywords).build()).build()).build()).build();
    }

    public static Points.Condition matchExceptValues(String field2, List<Long> values2) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setMatch(Points.Match.newBuilder().setExceptIntegers(Points.RepeatedIntegers.newBuilder().addAllIntegers(values2).build()).build()).build()).build();
    }

    public static Points.Condition nested(String field2, Points.Condition condition) {
        return Points.Condition.newBuilder().setNested(Points.NestedCondition.newBuilder().setKey(field2).setFilter(Points.Filter.newBuilder().addMust(condition).build()).build()).build();
    }

    public static Points.Condition nested(String field2, Points.Filter filter2) {
        return Points.Condition.newBuilder().setNested(Points.NestedCondition.newBuilder().setKey(field2).setFilter(filter2)).build();
    }

    public static Points.Condition range(String field2, Points.Range range) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setRange(range).build()).build();
    }

    public static Points.Condition geoRadius(String field2, double latitude, double longitude, float radius) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setGeoRadius(Points.GeoRadius.newBuilder().setCenter(Points.GeoPoint.newBuilder().setLat(latitude).setLon(longitude).build()).setRadius(radius).build()).build()).build();
    }

    public static Points.Condition geoBoundingBox(String field2, double topLeftLatitude, double topLeftLongitude, double bottomRightLatitude, double bottomRightLongitude) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setGeoBoundingBox(Points.GeoBoundingBox.newBuilder().setTopLeft(Points.GeoPoint.newBuilder().setLat(topLeftLatitude).setLon(topLeftLongitude).build()).setBottomRight(Points.GeoPoint.newBuilder().setLat(bottomRightLatitude).setLon(bottomRightLongitude).build()).build()).build()).build();
    }

    public static Points.Condition geoPolygon(String field2, Points.GeoLineString exterior, List<Points.GeoLineString> interiors) {
        Points.GeoPolygon.Builder geoPolygonBuilder = Points.GeoPolygon.newBuilder().setExterior(exterior);
        if (!interiors.isEmpty()) {
            geoPolygonBuilder.addAllInteriors(interiors);
        }
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setGeoPolygon(geoPolygonBuilder.build()).build()).build();
    }

    public static Points.Condition valuesCount(String field2, Points.ValuesCount valuesCount) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setValuesCount(valuesCount).build()).build();
    }

    public static Points.Condition filter(Points.Filter filter2) {
        return Points.Condition.newBuilder().setFilter(filter2).build();
    }

    public static Points.Condition datetimeRange(String field2, Points.DatetimeRange datetimeRange) {
        return Points.Condition.newBuilder().setField(Points.FieldCondition.newBuilder().setKey(field2).setDatetimeRange(datetimeRange).build()).build();
    }
}

