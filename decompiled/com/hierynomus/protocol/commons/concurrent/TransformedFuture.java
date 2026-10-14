/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.protocol.commons.concurrent;

import com.hierynomus.protocol.commons.concurrent.AFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TransformedFuture<T, V>
extends AFuture<V> {
    private Future<T> wrapped;
    private AFuture.Function<T, V> function;

    public TransformedFuture(Future<T> wrapped, AFuture.Function<T, V> function) {
        this.wrapped = wrapped;
        this.function = function;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.wrapped.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return this.wrapped.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.wrapped.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.function.apply(this.wrapped.get());
    }

    @Override
    public V get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.function.apply(this.wrapped.get(timeout2, unit));
    }
}

