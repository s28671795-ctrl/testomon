/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import java.util.concurrent.ExecutorService;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface ExecutorServiceProvider {
    public ExecutorService getExecutorService();

    public void dispose(ExecutorService var1);
}

