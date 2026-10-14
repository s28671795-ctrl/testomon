/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import java.util.concurrent.ScheduledExecutorService;
import org.glassfish.jersey.spi.Contract;
import org.glassfish.jersey.spi.ExecutorServiceProvider;

@Contract
public interface ScheduledExecutorServiceProvider
extends ExecutorServiceProvider {
    @Override
    public ScheduledExecutorService getExecutorService();
}

