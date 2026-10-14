/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Spatial;

public final class Geography
implements Spatial {
    private final String data;

    private Geography(String data) {
        this.data = String.valueOf(data);
    }

    @Override
    @NotNull
    public final String data() {
        return this.data;
    }

    @NotNull
    public static final Geography valueOf(String data) {
        return new Geography(data);
    }

    @NotNull
    public static final Geography geography(String data) {
        return new Geography(data);
    }

    @Nullable
    public static final Geography geographyOrNull(String data) {
        return data == null ? null : Geography.geography(data);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Geography) {
            Geography j = (Geography)obj;
            return this.data.equals(j.data);
        }
        return false;
    }

    public String toString() {
        return String.valueOf(this.data);
    }
}

