/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import org.apache.cxf.endpoint.ClientCallback;

class JaxwsClientCallback<T>
extends ClientCallback {
    final AsyncHandler<T> handler;
    final Object proxy;

    JaxwsClientCallback(AsyncHandler<T> handler, Object p) {
        this.handler = handler;
        this.proxy = p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleResponse(Map<String, Object> ctx, final Object[] res) {
        this.context = ctx;
        if (this.handler != null) {
            this.handler.handleResponse(new Response<T>(){
                protected boolean cancelled;

                @Override
                public Map<String, Object> getContext() {
                    return JaxwsClientCallback.this.context;
                }

                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    this.cancelled = true;
                    return true;
                }

                @Override
                public T get() throws InterruptedException, ExecutionException {
                    return res[0];
                }

                @Override
                public T get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                    return res[0];
                }

                @Override
                public boolean isCancelled() {
                    return this.cancelled;
                }

                @Override
                public boolean isDone() {
                    return true;
                }
            });
        }
        this.delegate.complete(res);
        JaxwsClientCallback jaxwsClientCallback = this;
        synchronized (jaxwsClientCallback) {
            this.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleException(Map<String, Object> ctx, final Throwable ex) {
        this.context = ctx;
        if (this.handler != null) {
            this.handler.handleResponse(new Response<T>(){
                protected boolean cancelled;

                @Override
                public Map<String, Object> getContext() {
                    return JaxwsClientCallback.this.context;
                }

                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    this.cancelled = true;
                    return true;
                }

                @Override
                public T get() throws InterruptedException, ExecutionException {
                    throw new ExecutionException(ex);
                }

                @Override
                public T get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                    throw new ExecutionException(ex);
                }

                @Override
                public boolean isCancelled() {
                    return this.cancelled;
                }

                @Override
                public boolean isDone() {
                    return true;
                }
            });
        }
        this.delegate.completeExceptionally(this.mapThrowable(ex));
        JaxwsClientCallback jaxwsClientCallback = this;
        synchronized (jaxwsClientCallback) {
            this.notifyAll();
        }
    }

    protected Throwable mapThrowable(Throwable t2) {
        return t2;
    }
}

