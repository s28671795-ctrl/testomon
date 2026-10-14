/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.Arrays;
import org.h2.engine.CastDataProvider;
import org.h2.mvstore.rtree.Spatial;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class SpatialKey
extends Value
implements Spatial {
    private final long id;
    private final float[] minMax;

    public SpatialKey(long l, float ... fArray) {
        this.id = l;
        this.minMax = fArray;
    }

    public SpatialKey(long l, SpatialKey spatialKey) {
        this.id = l;
        this.minMax = (float[])spatialKey.minMax.clone();
    }

    @Override
    public float min(int n) {
        return this.minMax[n + n];
    }

    @Override
    public void setMin(int n, float f) {
        this.minMax[n + n] = f;
    }

    @Override
    public float max(int n) {
        return this.minMax[n + n + 1];
    }

    @Override
    public void setMax(int n, float f) {
        this.minMax[n + n + 1] = f;
    }

    @Override
    public Spatial clone(long l) {
        return new SpatialKey(l, this);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public boolean isNull() {
        return this.minMax.length == 0;
    }

    @Override
    public String toString() {
        return this.getString();
    }

    @Override
    public int hashCode() {
        return (int)(this.id >>> 32 ^ this.id);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpatialKey)) {
            return false;
        }
        SpatialKey spatialKey = (SpatialKey)object;
        if (this.id != spatialKey.id) {
            return false;
        }
        return this.equalsIgnoringId(spatialKey);
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equalsIgnoringId(Spatial spatial) {
        return Arrays.equals(this.minMax, ((SpatialKey)spatial).minMax);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.id).append(": (");
        for (int i = 0; i < this.minMax.length; i += 2) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.minMax[i]).append('/').append(this.minMax[i + 1]);
        }
        stringBuilder.append(")");
        return stringBuilder;
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
    public String getString() {
        return this.getTraceSQL();
    }
}

