/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.geometry;

import org.h2.util.geometry.EWKBUtils;

public final class GeometryUtils {
    public static final int POINT = 1;
    public static final int LINE_STRING = 2;
    public static final int POLYGON = 3;
    public static final int MULTI_POINT = 4;
    public static final int MULTI_LINE_STRING = 5;
    public static final int MULTI_POLYGON = 6;
    public static final int GEOMETRY_COLLECTION = 7;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int M = 3;
    public static final int DIMENSION_SYSTEM_XY = 0;
    public static final int DIMENSION_SYSTEM_XYZ = 1;
    public static final int DIMENSION_SYSTEM_XYM = 2;
    public static final int DIMENSION_SYSTEM_XYZM = 3;
    public static final int MIN_X = 0;
    public static final int MAX_X = 1;
    public static final int MIN_Y = 2;
    public static final int MAX_Y = 3;

    public static double[] getEnvelope(byte[] byArray) {
        EnvelopeTarget envelopeTarget = new EnvelopeTarget();
        EWKBUtils.parseEWKB(byArray, envelopeTarget);
        return envelopeTarget.getEnvelope();
    }

    public static boolean intersects(double[] dArray, double[] dArray2) {
        return dArray != null && dArray2 != null && dArray[1] >= dArray2[0] && dArray[0] <= dArray2[1] && dArray[3] >= dArray2[2] && dArray[2] <= dArray2[3];
    }

    public static double[] union(double[] dArray, double[] dArray2) {
        double[] dArray3;
        if (dArray == null) {
            return dArray2;
        }
        if (dArray2 == null) {
            return dArray;
        }
        double d = dArray[0];
        double d2 = dArray[1];
        double d3 = dArray[2];
        double d4 = dArray[3];
        double d5 = dArray2[0];
        double d6 = dArray2[1];
        double d7 = dArray2[2];
        double d8 = dArray2[3];
        boolean bl = false;
        if (d > d5) {
            d = d5;
            bl = true;
        }
        if (d2 < d6) {
            d2 = d6;
            bl = true;
        }
        if (d3 > d7) {
            d3 = d7;
            bl = true;
        }
        if (d4 < d8) {
            d4 = d8;
            bl = true;
        }
        if (bl) {
            double[] dArray4 = new double[4];
            dArray4[0] = d;
            dArray4[1] = d2;
            dArray4[2] = d3;
            dArray3 = dArray4;
            dArray4[3] = d4;
        } else {
            dArray3 = dArray;
        }
        return dArray3;
    }

    static double toCanonicalDouble(double d) {
        return Double.isNaN(d) ? Double.NaN : (d == 0.0 ? 0.0 : d);
    }

    static double checkFinite(double d) {
        if (!Double.isFinite(d)) {
            throw new IllegalArgumentException();
        }
        return d;
    }

    private GeometryUtils() {
    }

    public static final class DimensionSystemTarget
    extends Target {
        private boolean hasZ;
        private boolean hasM;

        @Override
        protected void dimensionSystem(int n) {
            if ((n & 1) != 0) {
                this.hasZ = true;
            }
            if ((n & 2) != 0) {
                this.hasM = true;
            }
        }

        @Override
        protected void addCoordinate(double d, double d2, double d3, double d4, int n, int n2) {
            if (!this.hasZ && !Double.isNaN(d3)) {
                this.hasZ = true;
            }
            if (!this.hasM && !Double.isNaN(d4)) {
                this.hasM = true;
            }
        }

        public int getDimensionSystem() {
            return (this.hasZ ? 1 : 0) | (this.hasM ? 2 : 0);
        }
    }

    public static final class EnvelopeTarget
    extends Target {
        private boolean enabled;
        private boolean set;
        private double minX;
        private double maxX;
        private double minY;
        private double maxY;

        @Override
        protected void startPoint() {
            this.enabled = true;
        }

        @Override
        protected void startLineString(int n) {
            this.enabled = true;
        }

        @Override
        protected void startPolygon(int n, int n2) {
            this.enabled = true;
        }

        @Override
        protected void startPolygonInner(int n) {
            this.enabled = false;
        }

        @Override
        protected void addCoordinate(double d, double d2, double d3, double d4, int n, int n2) {
            if (this.enabled && !Double.isNaN(d) && !Double.isNaN(d2)) {
                if (!this.set) {
                    this.minX = this.maxX = d;
                    this.minY = this.maxY = d2;
                    this.set = true;
                } else {
                    if (this.minX > d) {
                        this.minX = d;
                    }
                    if (this.maxX < d) {
                        this.maxX = d;
                    }
                    if (this.minY > d2) {
                        this.minY = d2;
                    }
                    if (this.maxY < d2) {
                        this.maxY = d2;
                    }
                }
            }
        }

        public double[] getEnvelope() {
            double[] dArray;
            if (this.set) {
                double[] dArray2 = new double[4];
                dArray2[0] = this.minX;
                dArray2[1] = this.maxX;
                dArray2[2] = this.minY;
                dArray = dArray2;
                dArray2[3] = this.maxY;
            } else {
                dArray = null;
            }
            return dArray;
        }
    }

    public static abstract class Target {
        protected void init(int n) {
        }

        protected void dimensionSystem(int n) {
        }

        protected void startPoint() {
        }

        protected void startLineString(int n) {
        }

        protected void startPolygon(int n, int n2) {
        }

        protected void startPolygonInner(int n) {
        }

        protected void endNonEmptyPolygon() {
        }

        protected void startCollection(int n, int n2) {
        }

        protected Target startCollectionItem(int n, int n2) {
            return this;
        }

        protected void endCollectionItem(Target target, int n, int n2, int n3) {
        }

        protected void endObject(int n) {
        }

        protected abstract void addCoordinate(double var1, double var3, double var5, double var7, int var9, int var10);
    }
}

