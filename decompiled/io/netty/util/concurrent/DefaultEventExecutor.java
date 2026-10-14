/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public final class DefaultEventExecutor
extends SingleThreadEventExecutor {
    public DefaultEventExecutor() {
        this((EventExecutorGroup)null);
    }

    public DefaultEventExecutor(ThreadFactory threadFactory) {
        this(null, threadFactory);
    }

    public DefaultEventExecutor(Executor executor2) {
        this(null, executor2);
    }

    public DefaultEventExecutor(EventExecutorGroup parent) {
        this(parent, new DefaultThreadFactory(DefaultEventExecutor.class));
    }

    public DefaultEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory) {
        super(parent, threadFactory, true);
    }

    public DefaultEventExecutor(EventExecutorGroup parent, Executor executor2) {
        super(parent, executor2, true);
    }

    public DefaultEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, threadFactory, true, maxPendingTasks, rejectedExecutionHandler);
    }

    public DefaultEventExecutor(EventExecutorGroup parent, Executor executor2, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, executor2, true, maxPendingTasks, rejectedExecutionHandler);
    }

    @Override
    protected void run() {
        do {
            Runnable task;
            if ((task = this.takeTask()) == null) continue;
            task.run();
            this.updateLastExecutionTime();
        } while (!this.confirmShutdown());
    }
}

