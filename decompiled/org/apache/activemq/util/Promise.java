/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.activemq.util.PromiseCallback;

public class Promise<T>
extends PromiseCallback<T> {
    ArrayList<PromiseCallback<T>> callbacks = new ArrayList(1);
    T value;
    Throwable error;
    Future<T> future = null;

    public Future<T> future() {
        if (this.future == null) {
            PromiseFuture future = new PromiseFuture();
            this.watch(future);
            this.future = future;
        }
        return this.future;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void watch(PromiseCallback<T> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        boolean queued = false;
        Promise promise = this;
        synchronized (promise) {
            if (this.callbacks != null) {
                this.callbacks.add(callback);
                queued = true;
            }
        }
        if (!queued) {
            callback.onComplete(this.value, this.error);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onComplete(T value, Throwable error) {
        ArrayList<PromiseCallback<T>> callbacks;
        if (value != null && error != null) {
            throw new IllegalArgumentException("You can not have both a vaule and error");
        }
        Promise promise = this;
        synchronized (promise) {
            callbacks = this.callbacks;
            if (callbacks != null) {
                this.value = value;
                this.error = error;
                this.callbacks = null;
            }
        }
        if (callbacks != null) {
            for (PromiseCallback promiseCallback : callbacks) {
                promiseCallback.onComplete(this.value, this.error);
            }
        }
    }

    private class PromiseFuture
    extends PromiseCallback<T>
    implements Future<T> {
        CountDownLatch latch = new CountDownLatch(1);

        private PromiseFuture() {
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
            return this.latch.getCount() == 0L;
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            this.latch.await();
            return this.value();
        }

        @Override
        public T get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            if (this.latch.await(timeout2, unit)) {
                return this.value();
            }
            throw new TimeoutException();
        }

        @Override
        public void onComplete(T value, Throwable error) {
            this.latch.countDown();
        }

        private T value() throws ExecutionException {
            if (Promise.this.error != null) {
                throw new ExecutionException(Promise.this.error);
            }
            return Promise.this.value;
        }
    }
}

