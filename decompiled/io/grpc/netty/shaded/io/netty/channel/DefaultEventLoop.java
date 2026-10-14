/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.SingleThreadEventLoop;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultThreadFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public class DefaultEventLoop
extends SingleThreadEventLoop {
    public DefaultEventLoop() {
        this((EventLoopGroup)null);
    }

    public DefaultEventLoop(ThreadFactory threadFactory) {
        this(null, threadFactory);
    }

    public DefaultEventLoop(Executor executor2) {
        this(null, executor2);
    }

    public DefaultEventLoop(EventLoopGroup parent) {
        this(parent, new DefaultThreadFactory(DefaultEventLoop.class));
    }

    public DefaultEventLoop(EventLoopGroup parent, ThreadFactory threadFactory) {
        super(parent, threadFactory, true);
    }

    public DefaultEventLoop(EventLoopGroup parent, Executor executor2) {
        super(parent, executor2, true);
    }

    @Override
    protected void run() {
        do {
            Runnable task;
            if ((task = this.takeTask()) == null) continue;
            DefaultEventLoop.runTask(task);
            this.updateLastExecutionTime();
        } while (!this.confirmShutdown());
    }
}

