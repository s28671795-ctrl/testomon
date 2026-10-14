/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.ObjectPool;
import io.grpc.internal.SharedResourceHolder;

public final class SharedResourcePool<T>
implements ObjectPool<T> {
    private final SharedResourceHolder.Resource<T> resource;

    private SharedResourcePool(SharedResourceHolder.Resource<T> resource) {
        this.resource = resource;
    }

    public static <T> SharedResourcePool<T> forResource(SharedResourceHolder.Resource<T> resource) {
        return new SharedResourcePool<T>(resource);
    }

    @Override
    public T getObject() {
        return SharedResourceHolder.get(this.resource);
    }

    @Override
    public T returnObject(Object object) {
        SharedResourceHolder.release(this.resource, object);
        return null;
    }
}

