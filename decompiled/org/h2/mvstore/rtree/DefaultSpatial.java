/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.rtree;

import java.util.Arrays;
import org.h2.mvstore.rtree.Spatial;

final class DefaultSpatial
implements Spatial {
    private final long id;
    private final float[] minMax;

    public DefaultSpatial(long l, float ... fArray) {
        this.id = l;
        this.minMax = fArray;
    }

    private DefaultSpatial(long l, DefaultSpatial defaultSpatial) {
        this.id = l;
        this.minMax = (float[])defaultSpatial.minMax.clone();
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
        return new DefaultSpatial(l, this);
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
    public boolean equalsIgnoringId(Spatial spatial) {
        return Arrays.equals(this.minMax, ((DefaultSpatial)spatial).minMax);
    }
}

