/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.CompleteFuture;
import io.netty.util.concurrent.EventExecutor;

public final class SucceededFuture<V>
extends CompleteFuture<V> {
    private final V result;

    public SucceededFuture(EventExecutor executor2, V result) {
        super(executor2);
        this.result = result;
    }

    @Override
    public Throwable cause() {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public V getNow() {
        return this.result;
    }
}

