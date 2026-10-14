/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import org.glassfish.jersey.server.ContainerException;
import org.glassfish.jersey.server.ContainerResponse;

public interface ContainerResponseWriter {
    public OutputStream writeResponseStatusAndHeaders(long var1, ContainerResponse var3) throws ContainerException;

    public boolean suspend(long var1, TimeUnit var3, TimeoutHandler var4);

    public void setSuspendTimeout(long var1, TimeUnit var3) throws IllegalStateException;

    public void commit();

    public void failure(Throwable var1);

    public boolean enableResponseBuffering();

    public static interface TimeoutHandler {
        public void onTimeout(ContainerResponseWriter var1);
    }
}

