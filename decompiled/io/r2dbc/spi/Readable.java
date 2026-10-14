/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Nullable;

public interface Readable {
    @Nullable
    default public Object get(int index) {
        return this.get(index, Object.class);
    }

    @Nullable
    public <T> T get(int var1, Class<T> var2);

    @Nullable
    default public Object get(String name) {
        return this.get(name, Object.class);
    }

    @Nullable
    public <T> T get(String var1, Class<T> var2);
}

