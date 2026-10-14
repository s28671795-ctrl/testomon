/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.geometry;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import org.h2.message.DbException;
import org.h2.util.geometry.EWKBUtils;
import org.h2.util.geometry.GeometryUtils;
import org.h2.util.json.JSONArray;
import org.h2.util.json.JSONByteArrayTarget;
import org.h2.util.json.JSONBytesSource;
import org.h2.util.json.JSONNull;
import org.h2.util.json.JSONNumber;
import org.h2.util.json.JSONObject;
import org.h2.util.json.JSONString;
import org.h2.util.json.JSONValue;
import org.h2.util.json.JSONValueTarget;

public final class GeoJsonUtils {
    static final String[] TYPES = new String[]{"Point", "LineString", "Polygon", "MultiPoint", "MultiLineString", "MultiPolygon", "GeometryCollection"};

    public static byte[] ewkbToGeoJson(byte[] byArray, int n) {
        JSONByteArrayTarget jSONByteArrayTarget = new JSONByteArrayTarget();
        GeoJsonTarget geoJsonTarget = new GeoJsonTarget(jSONByteArrayTarget, n);
        EWKBUtils.parseEWKB(byArray, geoJsonTarget);
        return jSONByteArrayTarget.getResult();
    }

    public static byte[] geoJsonToEwkb(byte[] byArray, int n) {
        JSONValue jSONValue = JSONBytesSource.parse(byArray, new JSONValueTarget());
        GeometryUtils.DimensionSystemTarget dimensionSystemTarget = new GeometryUtils.DimensionSystemTarget();
        GeoJsonUtils.parse(jSONValue, dimensionSystemTarget);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        EWKBUtils.EWKBTarget eWKBTarget = new EWKBUtils.EWKBTarget(byteArrayOutputStream, dimensionSystemTarget.getDimensionSystem());
        eWKBTarget.init(n);
        GeoJsonUtils.parse(jSONValue, eWKBTarget);
        return byteArrayOutputStream.toByteArray();
    }

    private static void parse(JSONValue jSONValue, GeometryUtils.Target target) {
        if (jSONValue instanceof JSONNull) {
            target.startPoint();
            target.addCoordinate(Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0, 1);
            target.endObject(1);
        } else if (jSONValue instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject)jSONValue;
            JSONValue jSONValue2 = jSONObject.getFirst("type");
            if (!(jSONValue2 instanceof JSONString)) {
                throw new IllegalArgumentException();
            }
            switch (((JSONString)jSONValue2).getString()) {
                case "Point": {
                    GeoJsonUtils.parse(jSONObject, target, 1);
                    break;
                }
                case "LineString": {
                    GeoJsonUtils.parse(jSONObject, target, 2);
                    break;
                }
                case "Polygon": {
                    GeoJsonUtils.parse(jSONObject, target, 3);
                    break;
                }
                case "MultiPoint": {
                    GeoJsonUtils.parse(jSONObject, target, 4);
                    break;
                }
                case "MultiLineString": {
                    GeoJsonUtils.parse(jSONObject, target, 5);
                    break;
                }
                case "MultiPolygon": {
                    GeoJsonUtils.parse(jSONObject, target, 6);
                    break;
                }
                case "GeometryCollection": {
                    GeoJsonUtils.parseGeometryCollection(jSONObject, target);
                    break;
                }
                default: {
                    throw new IllegalArgumentException();
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static void parse(JSONObject jSONObject, GeometryUtils.Target target, int n) {
        JSONValue jSONValue = jSONObject.getFirst("coordinates");
        if (!(jSONValue instanceof JSONArray)) {
            throw new IllegalArgumentException();
        }
        JSONArray jSONArray = (JSONArray)jSONValue;
        switch (n) {
            case 1: {
                target.startPoint();
                GeoJsonUtils.parseCoordinate(jSONArray, target, 0, 1);
                target.endObject(1);
                break;
            }
            case 2: {
                GeoJsonUtils.parseLineString(jSONArray, target);
                break;
            }
            case 3: {
                GeoJsonUtils.parsePolygon(jSONArray, target);
                break;
            }
            case 4: {
                JSONValue[] jSONValueArray = jSONArray.getArray();
                int n2 = jSONValueArray.length;
                target.startCollection(4, n2);
                for (int i = 0; i < n2; ++i) {
                    target.startPoint();
                    GeoJsonUtils.parseCoordinate(jSONValueArray[i], target, 0, 1);
                    target.endObject(1);
                    target.endCollectionItem(target, 4, i, n2);
                }
                target.endObject(4);
                break;
            }
            case 5: {
                JSONValue[] jSONValueArray = jSONArray.getArray();
                int n3 = jSONValueArray.length;
                target.startCollection(5, n3);
                for (int i = 0; i < n3; ++i) {
                    JSONValue jSONValue2 = jSONValueArray[i];
                    if (!(jSONValue2 instanceof JSONArray)) {
                        throw new IllegalArgumentException();
                    }
                    GeoJsonUtils.parseLineString((JSONArray)jSONValue2, target);
                    target.endCollectionItem(target, 5, i, n3);
                }
                target.endObject(5);
                break;
            }
            case 6: {
                JSONValue[] jSONValueArray = jSONArray.getArray();
                int n4 = jSONValueArray.length;
                target.startCollection(6, n4);
                for (int i = 0; i < n4; ++i) {
                    JSONValue jSONValue3 = jSONValueArray[i];
                    if (!(jSONValue3 instanceof JSONArray)) {
                        throw new IllegalArgumentException();
                    }
                    GeoJsonUtils.parsePolygon((JSONArray)jSONValue3, target);
                    target.endCollectionItem(target, 6, i, n4);
                }
                target.endObject(6);
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    private static void parseGeometryCollection(JSONObject jSONObject, GeometryUtils.Target target) {
        JSONValue jSONValue = jSONObject.getFirst("geometries");
        if (!(jSONValue instanceof JSONArray)) {
            throw new IllegalArgumentException();
        }
        JSONArray jSONArray = (JSONArray)jSONValue;
        JSONValue[] jSONValueArray = jSONArray.getArray();
        int n = jSONValueArray.length;
        target.startCollection(7, n);
        for (int i = 0; i < n; ++i) {
            JSONValue jSONValue2 = jSONValueArray[i];
            GeoJsonUtils.parse(jSONValue2, target);
            target.endCollectionItem(target, 7, i, n);
        }
        target.endObject(7);
    }

    private static void parseLineString(JSONArray jSONArray, GeometryUtils.Target target) {
        JSONValue[] jSONValueArray = jSONArray.getArray();
        int n = jSONValueArray.length;
        target.startLineString(n);
        for (int i = 0; i < n; ++i) {
            GeoJsonUtils.parseCoordinate(jSONValueArray[i], target, i, n);
        }
        target.endObject(2);
    }

    private static void parsePolygon(JSONArray jSONArray, GeometryUtils.Target target) {
        JSONValue[] jSONValueArray = jSONArray.getArray();
        int n = jSONValueArray.length;
        if (n == 0) {
            target.startPolygon(0, 0);
        } else {
            JSONValue jSONValue = jSONValueArray[0];
            if (!(jSONValue instanceof JSONArray)) {
                throw new IllegalArgumentException();
            }
            JSONValue[] jSONValueArray2 = ((JSONArray)jSONValue).getArray();
            target.startPolygon(n - 1, jSONValueArray2.length);
            GeoJsonUtils.parseRing(jSONValueArray2, target);
            for (int i = 1; i < n; ++i) {
                jSONValue = jSONValueArray[i];
                if (!(jSONValue instanceof JSONArray)) {
                    throw new IllegalArgumentException();
                }
                jSONValueArray2 = ((JSONArray)jSONValue).getArray();
                target.startPolygonInner(jSONValueArray2.length);
                GeoJsonUtils.parseRing(jSONValueArray2, target);
            }
            target.endNonEmptyPolygon();
        }
        target.endObject(3);
    }

    private static void parseRing(JSONValue[] jSONValueArray, GeometryUtils.Target target) {
        int n = jSONValueArray.length;
        for (int i = 0; i < n; ++i) {
            GeoJsonUtils.parseCoordinate(jSONValueArray[i], target, i, n);
        }
    }

    private static void parseCoordinate(JSONValue jSONValue, GeometryUtils.Target target, int n, int n2) {
        if (jSONValue instanceof JSONNull) {
            target.addCoordinate(Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0, 1);
            return;
        }
        if (!(jSONValue instanceof JSONArray)) {
            throw new IllegalArgumentException();
        }
        JSONValue[] jSONValueArray = ((JSONArray)jSONValue).getArray();
        int n3 = jSONValueArray.length;
        if (n3 < 2) {
            throw new IllegalArgumentException();
        }
        target.addCoordinate(GeoJsonUtils.readCoordinate(jSONValueArray, 0), GeoJsonUtils.readCoordinate(jSONValueArray, 1), GeoJsonUtils.readCoordinate(jSONValueArray, 2), GeoJsonUtils.readCoordinate(jSONValueArray, 3), n, n2);
    }

    private static double readCoordinate(JSONValue[] jSONValueArray, int n) {
        if (n >= jSONValueArray.length) {
            return Double.NaN;
        }
        JSONValue jSONValue = jSONValueArray[n];
        if (!(jSONValue instanceof JSONNumber)) {
            throw new IllegalArgumentException();
        }
        return ((JSONNumber)jSONValue).getBigDecimal().doubleValue();
    }

    private GeoJsonUtils() {
    }

    public static final class GeoJsonTarget
    extends GeometryUtils.Target {
        private final JSONByteArrayTarget output;
        private final int dimensionSystem;
        private int type;
        private boolean inMulti;
        private boolean inMultiLine;
        private boolean wasEmpty;

        public GeoJsonTarget(JSONByteArrayTarget jSONByteArrayTarget, int n) {
            if (n == 2) {
                throw DbException.get(22018, "M (XYM) dimension system is not supported in GeoJson");
            }
            this.output = jSONByteArrayTarget;
            this.dimensionSystem = n;
        }

        @Override
        protected void startPoint() {
            this.type = 1;
            this.wasEmpty = false;
        }

        @Override
        protected void startLineString(int n) {
            this.writeHeader(2);
            if (n == 0) {
                this.output.endArray();
            }
        }

        @Override
        protected void startPolygon(int n, int n2) {
            this.writeHeader(3);
            if (n2 == 0) {
                this.output.endArray();
            } else {
                this.output.startArray();
            }
        }

        @Override
        protected void startPolygonInner(int n) {
            this.output.startArray();
            if (n == 0) {
                this.output.endArray();
            }
        }

        @Override
        protected void endNonEmptyPolygon() {
            this.output.endArray();
        }

        @Override
        protected void startCollection(int n, int n2) {
            this.writeHeader(n);
            if (n != 7) {
                this.inMulti = true;
                if (n == 5 || n == 6) {
                    this.inMultiLine = true;
                }
            }
        }

        @Override
        protected GeometryUtils.Target startCollectionItem(int n, int n2) {
            if (this.inMultiLine) {
                this.output.startArray();
            }
            return this;
        }

        @Override
        protected void endObject(int n) {
            switch (n) {
                case 4: 
                case 5: 
                case 6: {
                    this.inMulti = false;
                    this.inMultiLine = false;
                }
                case 7: {
                    this.output.endArray();
                }
            }
            if (!this.inMulti && !this.wasEmpty) {
                this.output.endObject();
            }
        }

        private void writeHeader(int n) {
            this.type = n;
            this.wasEmpty = false;
            if (!this.inMulti) {
                this.writeStartObject(n);
            }
        }

        @Override
        protected void addCoordinate(double d, double d2, double d3, double d4, int n, int n2) {
            if (this.type == 1) {
                if (Double.isNaN(d) && Double.isNaN(d2) && Double.isNaN(d3) && Double.isNaN(d4)) {
                    this.wasEmpty = true;
                    this.output.valueNull();
                    return;
                }
                if (!this.inMulti) {
                    this.writeStartObject(1);
                }
            }
            this.output.startArray();
            this.writeDouble(d);
            this.writeDouble(d2);
            if ((this.dimensionSystem & 1) != 0) {
                this.writeDouble(d3);
            }
            if ((this.dimensionSystem & 2) != 0) {
                this.writeDouble(d4);
            }
            this.output.endArray();
            if (this.type != 1 && n + 1 == n2) {
                this.output.endArray();
            }
        }

        private void writeStartObject(int n) {
            this.output.startObject();
            this.output.member("type");
            this.output.valueString(TYPES[n - 1]);
            this.output.member(n != 7 ? "coordinates" : "geometries");
            if (n != 1) {
                this.output.startArray();
            }
        }

        private void writeDouble(double d) {
            this.output.valueNumber(BigDecimal.valueOf(GeometryUtils.checkFinite(d)).stripTrailingZeros());
        }
    }
}

