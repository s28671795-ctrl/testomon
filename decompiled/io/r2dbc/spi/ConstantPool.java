/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

abstract class ConstantPool<T> {
    private final ConcurrentMap<String, T> constants = new ConcurrentHashMap<String, T>();

    ConstantPool() {
    }

    public String toString() {
        return "ConstantPool{constants=" + this.constants + '}';
    }

    abstract T createConstant(String var1, boolean var2);

    final T valueOf(String name, boolean sensitive) {
        Assert.requireNonNull(name, "name must not be null");
        Assert.requireNonEmpty(name, "name must not be empty");
        return (T)this.constants.computeIfAbsent(name, n -> this.createConstant((String)n, sensitive));
    }
}

