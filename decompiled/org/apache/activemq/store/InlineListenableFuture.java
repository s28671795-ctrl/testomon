/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.activemq.store.ListenableFuture;

public class InlineListenableFuture
implements ListenableFuture<Object> {
    public Object call() throws Exception {
        return null;
    }

    @Override
    public void addListener(Runnable listener) {
        listener.run();
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
        return true;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}

