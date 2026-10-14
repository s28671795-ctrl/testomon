/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.internal.ObjectPool;

public final class FixedObjectPool<T>
implements ObjectPool<T> {
    private final T object;

    public FixedObjectPool(T object) {
        this.object = Preconditions.checkNotNull(object, "object");
    }

    @Override
    public T getObject() {
        return this.object;
    }

    @Override
    public T returnObject(Object returned) {
        return null;
    }
}

