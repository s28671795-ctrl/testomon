/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

import io.grpc.netty.shaded.io.netty.util.AttributeKey;

public interface Attribute<T> {
    public AttributeKey<T> key();

    public T get();

    public void set(T var1);

    public T getAndSet(T var1);

    public T setIfAbsent(T var1);

    @Deprecated
    public T getAndRemove();

    public boolean compareAndSet(T var1, T var2);

    @Deprecated
    public void remove();
}

