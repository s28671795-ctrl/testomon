/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.xml.ws.Response;

public class ServerAsyncResponse<T>
implements Response<T> {
    T value;
    boolean done;
    Throwable throwable;

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
        return this.done;
    }

    public void set(T t2) {
        this.value = t2;
        this.done = true;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        if (this.throwable != null) {
            throw new ExecutionException(this.throwable);
        }
        return this.value;
    }

    @Override
    public T get(long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.value;
    }

    public void exception(Throwable ex) {
        this.throwable = ex;
        this.done = true;
    }

    @Override
    public Map<String, Object> getContext() {
        return null;
    }
}

