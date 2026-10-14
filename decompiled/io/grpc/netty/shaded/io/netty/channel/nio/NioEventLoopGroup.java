/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.nio;

import io.grpc.netty.shaded.io.netty.channel.DefaultSelectStrategyFactory;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.channel.EventLoopTaskQueueFactory;
import io.grpc.netty.shaded.io.netty.channel.MultithreadEventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.SelectStrategyFactory;
import io.grpc.netty.shaded.io.netty.channel.nio.NioEventLoop;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutorChooserFactory;
import io.grpc.netty.shaded.io.netty.util.concurrent.RejectedExecutionHandler;
import io.grpc.netty.shaded.io.netty.util.concurrent.RejectedExecutionHandlers;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public class NioEventLoopGroup
extends MultithreadEventLoopGroup {
    public NioEventLoopGroup() {
        this(0);
    }

    public NioEventLoopGroup(int nThreads) {
        this(nThreads, (Executor)null);
    }

    public NioEventLoopGroup(ThreadFactory threadFactory) {
        this(0, threadFactory, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, Executor executor2) {
        this(nThreads, executor2, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
        this(nThreads, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int nThreads, Executor executor2, SelectorProvider selectorProvider) {
        this(nThreads, executor2, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, Executor executor2, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor2, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor2, chooserFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, executor2, chooserFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler);
    }

    public NioEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory taskQueueFactory) {
        super(nThreads, executor2, chooserFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler, taskQueueFactory);
    }

    public NioEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory taskQueueFactory, EventLoopTaskQueueFactory tailTaskQueueFactory) {
        super(nThreads, executor2, chooserFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory);
    }

    public void setIoRatio(int ioRatio) {
        for (EventExecutor e : this) {
            ((NioEventLoop)e).setIoRatio(ioRatio);
        }
    }

    public void rebuildSelectors() {
        for (EventExecutor e : this) {
            ((NioEventLoop)e).rebuildSelector();
        }
    }

    @Override
    protected EventLoop newChild(Executor executor2, Object ... args2) throws Exception {
        SelectorProvider selectorProvider = (SelectorProvider)args2[0];
        SelectStrategyFactory selectStrategyFactory = (SelectStrategyFactory)args2[1];
        RejectedExecutionHandler rejectedExecutionHandler = (RejectedExecutionHandler)args2[2];
        EventLoopTaskQueueFactory taskQueueFactory = null;
        EventLoopTaskQueueFactory tailTaskQueueFactory = null;
        int argsLength = args2.length;
        if (argsLength > 3) {
            taskQueueFactory = (EventLoopTaskQueueFactory)args2[3];
        }
        if (argsLength > 4) {
            tailTaskQueueFactory = (EventLoopTaskQueueFactory)args2[4];
        }
        return new NioEventLoop(this, executor2, selectorProvider, selectStrategyFactory.newSelectStrategy(), rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory);
    }
}

