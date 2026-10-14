/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import javax.annotation.PreDestroy;
import org.glassfish.jersey.spi.AbstractThreadPoolProvider;
import org.glassfish.jersey.spi.ScheduledExecutorServiceProvider;

public class ScheduledThreadPoolExecutorProvider
extends AbstractThreadPoolProvider<ScheduledThreadPoolExecutor>
implements ScheduledExecutorServiceProvider {
    public ScheduledThreadPoolExecutorProvider(String name) {
        super(name);
    }

    @Override
    public ScheduledExecutorService getExecutorService() {
        return (ScheduledExecutorService)super.getExecutor();
    }

    @Override
    protected ScheduledThreadPoolExecutor createExecutor(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, handler);
    }

    @Override
    public void dispose(ExecutorService executorService2) {
    }

    @PreDestroy
    public void preDestroy() {
        this.close();
    }
}

