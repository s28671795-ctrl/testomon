/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Nullability;
import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.Type;

public interface ReadableMetadata {
    @Nullable
    default public Class<?> getJavaType() {
        return null;
    }

    public Type getType();

    public String getName();

    @Nullable
    default public Object getNativeTypeMetadata() {
        return null;
    }

    default public Nullability getNullability() {
        return Nullability.UNKNOWN;
    }

    @Nullable
    default public Integer getPrecision() {
        return null;
    }

    @Nullable
    default public Integer getScale() {
        return null;
    }
}

