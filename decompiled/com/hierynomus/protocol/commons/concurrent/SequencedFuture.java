/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.protocol.commons.concurrent;

import com.hierynomus.protocol.commons.concurrent.AFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SequencedFuture<V>
extends AFuture<List<V>> {
    private List<Future<V>> futures;

    public SequencedFuture(List<Future<V>> futures) {
        this.futures = futures;
    }

    @Override
    public boolean isCancelled() {
        for (Future<V> future : this.futures) {
            if (future.isCancelled()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        boolean allCancelled = true;
        for (Future<V> future : this.futures) {
            allCancelled = allCancelled && future.cancel(mayInterruptIfRunning);
        }
        return allCancelled;
    }

    @Override
    public boolean isDone() {
        for (Future<V> future : this.futures) {
            if (future.isDone()) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<V> get() throws InterruptedException, ExecutionException {
        ArrayList<V> collector = new ArrayList<V>();
        for (Future<V> future : this.futures) {
            collector.add(future.get());
        }
        return collector;
    }

    @Override
    public List<V> get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        ArrayList<V> collector = new ArrayList<V>();
        for (Future<V> future : this.futures) {
            collector.add(future.get(timeout2, unit));
        }
        return collector;
    }
}

