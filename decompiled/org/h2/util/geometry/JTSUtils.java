/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.locationtech.jts.geom.CoordinateSequence
 *  org.locationtech.jts.geom.CoordinateSequenceFactory
 *  org.locationtech.jts.geom.Geometry
 *  org.locationtech.jts.geom.GeometryCollection
 *  org.locationtech.jts.geom.GeometryFactory
 *  org.locationtech.jts.geom.LineString
 *  org.locationtech.jts.geom.LinearRing
 *  org.locationtech.jts.geom.MultiLineString
 *  org.locationtech.jts.geom.MultiPoint
 *  org.locationtech.jts.geom.MultiPolygon
 *  org.locationtech.jts.geom.Point
 *  org.locationtech.jts.geom.Polygon
 *  org.locationtech.jts.geom.PrecisionModel
 *  org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory
 *  org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory
 */
package org.h2.util.geometry;

import java.io.ByteArrayOutputStream;
import org.h2.message.DbException;
import org.h2.util.geometry.EWKBUtils;
import org.h2.util.geometry.GeometryUtils;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory;

public final class JTSUtils {
    public static Geometry ewkb2geometry(byte[] byArray) {
        return JTSUtils.ewkb2geometry(byArray, EWKBUtils.getDimensionSystem(byArray));
    }

    public static Geometry ewkb2geometry(byte[] byArray, int n) {
        GeometryTarget geometryTarget = new GeometryTarget(n);
        EWKBUtils.parseEWKB(byArray, geometryTarget);
        return geometryTarget.getGeometry();
    }

    public static byte[] geometry2ewkb(Geometry geometry) {
        return JTSUtils.geometry2ewkb(geometry, JTSUtils.getDimensionSystem(geometry));
    }

    public static byte[] geometry2ewkb(Geometry geometry, int n) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        EWKBUtils.EWKBTarget eWKBTarget = new EWKBUtils.EWKBTarget(byteArrayOutputStream, n);
        JTSUtils.parseGeometry(geometry, eWKBTarget);
        return byteArrayOutputStream.toByteArray();
    }

    public static void parseGeometry(Geometry geometry, GeometryUtils.Target target) {
        JTSUtils.parseGeometry(geometry, target, 0);
    }

    private static void parseGeometry(Geometry geometry, GeometryUtils.Target target, int n) {
        if (n == 0) {
            target.init(geometry.getSRID());
        }
        if (geometry instanceof Point) {
            if (n != 0 && n != 4 && n != 7) {
                throw new IllegalArgumentException();
            }
            target.startPoint();
            Point point = (Point)geometry;
            if (point.isEmpty()) {
                target.addCoordinate(Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0, 1);
            } else {
                JTSUtils.addCoordinate(point.getCoordinateSequence(), target, 0, 1);
            }
            target.endObject(1);
        } else if (geometry instanceof LineString) {
            if (n != 0 && n != 5 && n != 7) {
                throw new IllegalArgumentException();
            }
            LineString lineString = (LineString)geometry;
            CoordinateSequence coordinateSequence = lineString.getCoordinateSequence();
            int n2 = coordinateSequence.size();
            if (n2 == 1) {
                throw new IllegalArgumentException();
            }
            target.startLineString(n2);
            for (int i = 0; i < n2; ++i) {
                JTSUtils.addCoordinate(coordinateSequence, target, i, n2);
            }
            target.endObject(2);
        } else if (geometry instanceof Polygon) {
            if (n != 0 && n != 6 && n != 7) {
                throw new IllegalArgumentException();
            }
            Polygon polygon = (Polygon)geometry;
            int n3 = polygon.getNumInteriorRing();
            CoordinateSequence coordinateSequence = polygon.getExteriorRing().getCoordinateSequence();
            int n4 = coordinateSequence.size();
            if (n4 >= 1 && n4 <= 3) {
                throw new IllegalArgumentException();
            }
            if (n4 == 0 && n3 > 0) {
                throw new IllegalArgumentException();
            }
            target.startPolygon(n3, n4);
            if (n4 > 0) {
                JTSUtils.addRing(coordinateSequence, target, n4);
                for (int i = 0; i < n3; ++i) {
                    coordinateSequence = polygon.getInteriorRingN(i).getCoordinateSequence();
                    n4 = coordinateSequence.size();
                    if (n4 >= 1 && n4 <= 3) {
                        throw new IllegalArgumentException();
                    }
                    target.startPolygonInner(n4);
                    JTSUtils.addRing(coordinateSequence, target, n4);
                }
                target.endNonEmptyPolygon();
            }
            target.endObject(3);
        } else if (geometry instanceof GeometryCollection) {
            if (n != 0 && n != 7) {
                throw new IllegalArgumentException();
            }
            GeometryCollection geometryCollection = (GeometryCollection)geometry;
            int n5 = geometryCollection instanceof MultiPoint ? 4 : (geometryCollection instanceof MultiLineString ? 5 : (geometryCollection instanceof MultiPolygon ? 6 : 7));
            int n6 = geometryCollection.getNumGeometries();
            target.startCollection(n5, n6);
            for (int i = 0; i < n6; ++i) {
                GeometryUtils.Target target2 = target.startCollectionItem(i, n6);
                JTSUtils.parseGeometry(geometryCollection.getGeometryN(i), target2, n5);
                target.endCollectionItem(target2, n5, i, n6);
            }
            target.endObject(n5);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static void addRing(CoordinateSequence coordinateSequence, GeometryUtils.Target target, int n) {
        if (n >= 4) {
            double d = GeometryUtils.toCanonicalDouble(coordinateSequence.getX(0));
            double d2 = GeometryUtils.toCanonicalDouble(coordinateSequence.getY(0));
            JTSUtils.addCoordinate(coordinateSequence, target, 0, n, d, d2);
            for (int i = 1; i < n - 1; ++i) {
                JTSUtils.addCoordinate(coordinateSequence, target, i, n);
            }
            double d3 = GeometryUtils.toCanonicalDouble(coordinateSequence.getX(n - 1));
            double d4 = GeometryUtils.toCanonicalDouble(coordinateSequence.getY(n - 1));
            if (d != d3 || d2 != d4) {
                throw new IllegalArgumentException();
            }
            JTSUtils.addCoordinate(coordinateSequence, target, n - 1, n, d3, d4);
        }
    }

    private static void addCoordinate(CoordinateSequence coordinateSequence, GeometryUtils.Target target, int n, int n2) {
        JTSUtils.addCoordinate(coordinateSequence, target, n, n2, GeometryUtils.toCanonicalDouble(coordinateSequence.getX(n)), GeometryUtils.toCanonicalDouble(coordinateSequence.getY(n)));
    }

    private static void addCoordinate(CoordinateSequence coordinateSequence, GeometryUtils.Target target, int n, int n2, double d, double d2) {
        double d3 = GeometryUtils.toCanonicalDouble(coordinateSequence.getZ(n));
        double d4 = GeometryUtils.toCanonicalDouble(coordinateSequence.getM(n));
        target.addCoordinate(d, d2, d3, d4, n, n2);
    }

    public static int getDimensionSystem(Geometry geometry) {
        int n = JTSUtils.getDimensionSystem1(geometry);
        return n >= 0 ? n : 0;
    }

    private static int getDimensionSystem1(Geometry geometry) {
        int n;
        if (geometry instanceof Point) {
            n = JTSUtils.getDimensionSystemFromSequence(((Point)geometry).getCoordinateSequence());
        } else if (geometry instanceof LineString) {
            n = JTSUtils.getDimensionSystemFromSequence(((LineString)geometry).getCoordinateSequence());
        } else if (geometry instanceof Polygon) {
            n = JTSUtils.getDimensionSystemFromSequence(((Polygon)geometry).getExteriorRing().getCoordinateSequence());
        } else if (geometry instanceof GeometryCollection) {
            n = -1;
            GeometryCollection geometryCollection = (GeometryCollection)geometry;
            int n2 = geometryCollection.getNumGeometries();
            for (int i = 0; i < n2 && (n = JTSUtils.getDimensionSystem1(geometryCollection.getGeometryN(i))) < 0; ++i) {
            }
        } else {
            throw new IllegalArgumentException();
        }
        return n;
    }

    private static int getDimensionSystemFromSequence(CoordinateSequence coordinateSequence) {
        int n = coordinateSequence.size();
        if (n > 0) {
            for (int i = 0; i < n; ++i) {
                int n2 = JTSUtils.getDimensionSystemFromCoordinate(coordinateSequence, i);
                if (n2 < 0) continue;
                return n2;
            }
        }
        return (coordinateSequence.hasZ() ? 1 : 0) | (coordinateSequence.hasM() ? 2 : 0);
    }

    private static int getDimensionSystemFromCoordinate(CoordinateSequence coordinateSequence, int n) {
        if (Double.isNaN(coordinateSequence.getX(n))) {
            return -1;
        }
        return (!Double.isNaN(coordinateSequence.getZ(n)) ? 1 : 0) | (!Double.isNaN(coordinateSequence.getM(n)) ? 2 : 0);
    }

    private JTSUtils() {
    }

    public static final class GeometryTarget
    extends GeometryUtils.Target {
        private final int dimensionSystem;
        private GeometryFactory factory;
        private int type;
        private CoordinateSequence coordinates;
        private CoordinateSequence[] innerCoordinates;
        private int innerOffset;
        private Geometry[] subgeometries;

        public GeometryTarget(int n) {
            this.dimensionSystem = n;
        }

        private GeometryTarget(int n, GeometryFactory geometryFactory) {
            this.dimensionSystem = n;
            this.factory = geometryFactory;
        }

        @Override
        protected void init(int n) {
            this.factory = new GeometryFactory(new PrecisionModel(), n, (CoordinateSequenceFactory)((this.dimensionSystem & 2) != 0 ? PackedCoordinateSequenceFactory.DOUBLE_FACTORY : CoordinateArraySequenceFactory.instance()));
        }

        @Override
        protected void startPoint() {
            this.type = 1;
            this.initCoordinates(1);
            this.innerOffset = -1;
        }

        @Override
        protected void startLineString(int n) {
            this.type = 2;
            this.initCoordinates(n);
            this.innerOffset = -1;
        }

        @Override
        protected void startPolygon(int n, int n2) {
            this.type = 3;
            this.initCoordinates(n2);
            this.innerCoordinates = new CoordinateSequence[n];
            this.innerOffset = -1;
        }

        @Override
        protected void startPolygonInner(int n) {
            this.innerCoordinates[++this.innerOffset] = this.createCoordinates(n);
        }

        @Override
        protected void startCollection(int n, int n2) {
            this.type = n;
            switch (n) {
                case 4: {
                    this.subgeometries = new Point[n2];
                    break;
                }
                case 5: {
                    this.subgeometries = new LineString[n2];
                    break;
                }
                case 6: {
                    this.subgeometries = new Polygon[n2];
                    break;
                }
                case 7: {
                    this.subgeometries = new Geometry[n2];
                    break;
                }
                default: {
                    throw new IllegalArgumentException();
                }
            }
        }

        @Override
        protected GeometryUtils.Target startCollectionItem(int n, int n2) {
            return new GeometryTarget(this.dimensionSystem, this.factory);
        }

        @Override
        protected void endCollectionItem(GeometryUtils.Target target, int n, int n2, int n3) {
            this.subgeometries[n2] = ((GeometryTarget)target).getGeometry();
        }

        private void initCoordinates(int n) {
            this.coordinates = this.createCoordinates(n);
        }

        private CoordinateSequence createCoordinates(int n) {
            int n2;
            int n3;
            switch (this.dimensionSystem) {
                case 0: {
                    n3 = 2;
                    n2 = 0;
                    break;
                }
                case 1: {
                    n3 = 3;
                    n2 = 0;
                    break;
                }
                case 2: {
                    n3 = 3;
                    n2 = 1;
                    break;
                }
                case 3: {
                    n3 = 4;
                    n2 = 1;
                    break;
                }
                default: {
                    throw DbException.getInternalError();
                }
            }
            return this.factory.getCoordinateSequenceFactory().create(n, n3, n2);
        }

        @Override
        protected void addCoordinate(double d, double d2, double d3, double d4, int n, int n2) {
            if (this.type == 1 && Double.isNaN(d) && Double.isNaN(d2) && Double.isNaN(d3) && Double.isNaN(d4)) {
                this.coordinates = this.createCoordinates(0);
                return;
            }
            CoordinateSequence coordinateSequence = this.innerOffset < 0 ? this.coordinates : this.innerCoordinates[this.innerOffset];
            coordinateSequence.setOrdinate(n, 0, GeometryUtils.checkFinite(d));
            coordinateSequence.setOrdinate(n, 1, GeometryUtils.checkFinite(d2));
            switch (this.dimensionSystem) {
                case 3: {
                    coordinateSequence.setOrdinate(n, 3, GeometryUtils.checkFinite(d4));
                }
                case 1: {
                    coordinateSequence.setOrdinate(n, 2, GeometryUtils.checkFinite(d3));
                    break;
                }
                case 2: {
                    coordinateSequence.setOrdinate(n, 2, GeometryUtils.checkFinite(d4));
                }
            }
        }

        Geometry getGeometry() {
            switch (this.type) {
                case 1: {
                    return new Point(this.coordinates, this.factory);
                }
                case 2: {
                    return new LineString(this.coordinates, this.factory);
                }
                case 3: {
                    LinearRing linearRing = new LinearRing(this.coordinates, this.factory);
                    int n = this.innerCoordinates.length;
                    LinearRing[] linearRingArray = new LinearRing[n];
                    for (int i = 0; i < n; ++i) {
                        linearRingArray[i] = new LinearRing(this.innerCoordinates[i], this.factory);
                    }
                    return new Polygon(linearRing, linearRingArray, this.factory);
                }
                case 4: {
                    return new MultiPoint((Point[])this.subgeometries, this.factory);
                }
                case 5: {
                    return new MultiLineString((LineString[])this.subgeometries, this.factory);
                }
                case 6: {
                    return new MultiPolygon((Polygon[])this.subgeometries, this.factory);
                }
                case 7: {
                    return new GeometryCollection(this.subgeometries, this.factory);
                }
            }
            throw new IllegalStateException();
        }
    }
}

