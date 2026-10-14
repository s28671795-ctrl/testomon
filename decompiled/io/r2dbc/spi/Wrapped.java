/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.Nullable;

public interface Wrapped<T> {
    public T unwrap();

    @Nullable
    default public <E> E unwrap(Class<E> targetClass) {
        Assert.requireNonNull(targetClass, "targetClass must not be null");
        if (targetClass.isInstance(this)) {
            return (E)this;
        }
        T unwrapped = this.unwrap();
        if (!(unwrapped instanceof Wrapped)) {
            return null;
        }
        return ((Wrapped)unwrapped).unwrap(targetClass);
    }
}

