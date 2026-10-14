/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.protocol.commons.concurrent;

import com.hierynomus.protocol.commons.concurrent.AFuture;
import com.hierynomus.protocol.commons.concurrent.Promise;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PromiseBackedFuture<V>
extends AFuture<V> {
    private Promise<V, ?> promise;

    public PromiseBackedFuture(Promise<V, ?> promise) {
        this.promise = promise;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return this.promise.isDelivered();
    }

    @Override
    public V get() throws ExecutionException {
        try {
            return this.promise.retrieve();
        }
        catch (Throwable t2) {
            throw new ExecutionException(t2);
        }
    }

    @Override
    public V get(long timeout2, TimeUnit unit) throws ExecutionException {
        try {
            return this.promise.retrieve(timeout2, unit);
        }
        catch (Throwable t2) {
            throw new ExecutionException(t2);
        }
    }
}

