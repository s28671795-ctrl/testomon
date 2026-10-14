/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Spatial;

public final class Geometry
implements Spatial {
    private final String data;

    private Geometry(String data) {
        this.data = String.valueOf(data);
    }

    @Override
    @NotNull
    public final String data() {
        return this.data;
    }

    @NotNull
    public static final Geometry valueOf(String data) {
        return new Geometry(data);
    }

    @NotNull
    public static final Geometry geometry(String data) {
        return new Geometry(data);
    }

    @Nullable
    public static final Geometry geometryOrNull(String data) {
        return data == null ? null : Geometry.geometry(data);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Geometry) {
            Geometry j = (Geometry)obj;
            return this.data.equals(j.data);
        }
        return false;
    }

    public String toString() {
        return String.valueOf(this.data);
    }
}

