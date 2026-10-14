/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.xml.ws.Response;
import org.apache.cxf.endpoint.ClientCallback;

class JaxwsResponseCallback<T>
implements Response<T> {
    ClientCallback callback;

    JaxwsResponseCallback(ClientCallback cb) {
        this.callback = cb;
    }

    @Override
    public Map<String, Object> getContext() {
        try {
            return this.callback.getResponseContext();
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.callback.cancel(mayInterruptIfRunning);
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return (T)this.callback.get()[0];
    }

    @Override
    public T get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return (T)this.callback.get(timeout2, unit)[0];
    }

    @Override
    public boolean isCancelled() {
        return this.callback.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.callback.isDone();
    }
}

