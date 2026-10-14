/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.rtree;

public interface Spatial {
    public float min(int var1);

    public void setMin(int var1, float var2);

    public float max(int var1);

    public void setMax(int var1, float var2);

    public Spatial clone(long var1);

    public long getId();

    public boolean isNull();

    public boolean equalsIgnoringId(Spatial var1);
}

