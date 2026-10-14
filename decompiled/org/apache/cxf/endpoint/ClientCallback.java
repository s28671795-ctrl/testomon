/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.cxf.message.Message;

public class ClientCallback
implements Future<Object[]> {
    protected final CompletableFuture<Object[]> delegate = new CompletableFuture();
    protected Map<String, Object> context;
    protected boolean started;

    public void start(Message msg) {
        this.started = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleResponse(Map<String, Object> ctx, Object[] res) {
        this.context = ctx;
        this.delegate.complete(res);
        ClientCallback clientCallback = this;
        synchronized (clientCallback) {
            this.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleException(Map<String, Object> ctx, Throwable ex) {
        this.context = ctx;
        this.delegate.completeExceptionally(ex);
        ClientCallback clientCallback = this;
        synchronized (clientCallback) {
            this.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!this.started) {
            this.delegate.cancel(mayInterruptIfRunning);
            ClientCallback clientCallback = this;
            synchronized (clientCallback) {
                this.notifyAll();
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, Object> getResponseContext() throws InterruptedException, ExecutionException {
        ClientCallback clientCallback = this;
        synchronized (clientCallback) {
            if (!this.delegate.isDone()) {
                this.wait();
            }
        }
        if (this.delegate.isCancelled()) {
            throw new InterruptedException("Operation Cancelled");
        }
        if (this.delegate.isCompletedExceptionally()) {
            this.delegate.get();
        }
        return this.context;
    }

    @Override
    public Object[] get() throws InterruptedException, ExecutionException {
        try {
            return this.delegate.get();
        }
        catch (CancellationException ex) {
            throw new InterruptedException("Operation has been cancelled");
        }
    }

    @Override
    public Object[] get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        try {
            return this.delegate.get(timeout2, unit);
        }
        catch (CancellationException ex) {
            throw new InterruptedException("Operation has been cancelled");
        }
    }

    @Override
    public boolean isCancelled() {
        return this.delegate.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.delegate.isDone();
    }
}

