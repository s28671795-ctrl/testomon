/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ObjectPool<T> {
    public T getObject();

    public T returnObject(Object var1);
}

