/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.locationtech.jts.geom.Geometry
 */
package org.h2.value;

import org.h2.message.DbException;
import org.h2.util.Bits;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.geometry.EWKBUtils;
import org.h2.util.geometry.EWKTUtils;
import org.h2.util.geometry.GeometryUtils;
import org.h2.util.geometry.JTSUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBytesBase;
import org.h2.value.ValueNull;
import org.locationtech.jts.geom.Geometry;

public final class ValueGeometry
extends ValueBytesBase {
    private static final double[] UNKNOWN_ENVELOPE = new double[0];
    private final int typeAndDimensionSystem;
    private final int srid;
    private double[] envelope;
    private Object geometry;

    private ValueGeometry(byte[] byArray, double[] dArray) {
        super(byArray);
        if (byArray.length < 9 || byArray[0] != 0) {
            throw DbException.get(22018, StringUtils.convertBytesToHex(byArray));
        }
        this.value = byArray;
        this.envelope = dArray;
        int n = Bits.readInt(byArray, 1);
        this.srid = (n & 0x20000000) != 0 ? Bits.readInt(byArray, 5) : 0;
        this.typeAndDimensionSystem = (n & 0xFFFF) % 1000 + EWKBUtils.type2dimensionSystem(n) * 1000;
    }

    public static ValueGeometry getFromGeometry(Object object) {
        try {
            Geometry geometry = (Geometry)object;
            return (ValueGeometry)Value.cache(new ValueGeometry(JTSUtils.geometry2ewkb(geometry), UNKNOWN_ENVELOPE));
        }
        catch (RuntimeException runtimeException) {
            throw DbException.get(22018, String.valueOf(object));
        }
    }

    public static ValueGeometry get(String string) {
        try {
            return (ValueGeometry)Value.cache(new ValueGeometry(EWKTUtils.ewkt2ewkb(string), UNKNOWN_ENVELOPE));
        }
        catch (RuntimeException runtimeException) {
            throw DbException.get(22018, string);
        }
    }

    public static ValueGeometry get(byte[] byArray) {
        return (ValueGeometry)Value.cache(new ValueGeometry(byArray, UNKNOWN_ENVELOPE));
    }

    public static ValueGeometry getFromEWKB(byte[] byArray) {
        try {
            return (ValueGeometry)Value.cache(new ValueGeometry(EWKBUtils.ewkb2ewkb(byArray), UNKNOWN_ENVELOPE));
        }
        catch (RuntimeException runtimeException) {
            throw DbException.get(22018, StringUtils.convertBytesToHex(byArray));
        }
    }

    public static Value fromEnvelope(double[] dArray) {
        return dArray != null ? Value.cache(new ValueGeometry(EWKBUtils.envelope2wkb(dArray), dArray)) : ValueNull.INSTANCE;
    }

    public Geometry getGeometry() {
        if (this.geometry == null) {
            try {
                this.geometry = JTSUtils.ewkb2geometry(this.value, this.getDimensionSystem());
            }
            catch (RuntimeException runtimeException) {
                throw DbException.convert(runtimeException);
            }
        }
        return ((Geometry)this.geometry).copy();
    }

    public int getTypeAndDimensionSystem() {
        return this.typeAndDimensionSystem;
    }

    public int getGeometryType() {
        return this.typeAndDimensionSystem % 1000;
    }

    public int getDimensionSystem() {
        return this.typeAndDimensionSystem / 1000;
    }

    public int getSRID() {
        return this.srid;
    }

    public double[] getEnvelopeNoCopy() {
        if (this.envelope == UNKNOWN_ENVELOPE) {
            GeometryUtils.EnvelopeTarget envelopeTarget = new GeometryUtils.EnvelopeTarget();
            EWKBUtils.parseEWKB(this.value, envelopeTarget);
            this.envelope = envelopeTarget.getEnvelope();
        }
        return this.envelope;
    }

    public boolean intersectsBoundingBox(ValueGeometry valueGeometry) {
        return GeometryUtils.intersects(this.getEnvelopeNoCopy(), valueGeometry.getEnvelopeNoCopy());
    }

    public Value getEnvelopeUnion(ValueGeometry valueGeometry) {
        return ValueGeometry.fromEnvelope(GeometryUtils.union(this.getEnvelopeNoCopy(), valueGeometry.getEnvelopeNoCopy()));
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_GEOMETRY;
    }

    @Override
    public int getValueType() {
        return 37;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append("GEOMETRY ");
        if ((n & 8) != 0) {
            EWKBUtils.parseEWKB(this.value, new EWKTUtils.EWKTTarget(stringBuilder.append('\''), this.getDimensionSystem()));
            stringBuilder.append('\'');
        } else {
            super.getSQL(stringBuilder, 0);
        }
        return stringBuilder;
    }

    @Override
    public String getString() {
        return EWKTUtils.ewkb2ewkt(this.value, this.getDimensionSystem());
    }

    @Override
    public int getMemory() {
        return MathUtils.convertLongToInt((long)this.value.length * 20L + 24L);
    }
}

