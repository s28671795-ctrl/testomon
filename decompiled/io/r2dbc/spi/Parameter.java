/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.Type;

public interface Parameter {
    public Type getType();

    @Nullable
    public Object getValue();

    public static interface Out {
    }

    public static interface In {
    }
}

