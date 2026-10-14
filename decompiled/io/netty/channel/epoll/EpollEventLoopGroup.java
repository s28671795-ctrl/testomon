/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel.epoll;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopTaskQueueFactory;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public final class EpollEventLoopGroup
extends MultithreadEventLoopGroup {
    public EpollEventLoopGroup() {
        this(0);
    }

    public EpollEventLoopGroup(int nThreads) {
        this(nThreads, (ThreadFactory)null);
    }

    public EpollEventLoopGroup(ThreadFactory threadFactory) {
        this(0, threadFactory, 0);
    }

    public EpollEventLoopGroup(int nThreads, SelectStrategyFactory selectStrategyFactory) {
        this(nThreads, (ThreadFactory)null, selectStrategyFactory);
    }

    public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, 0);
    }

    public EpollEventLoopGroup(int nThreads, Executor executor2) {
        this(nThreads, executor2, DefaultSelectStrategyFactory.INSTANCE);
    }

    public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectStrategyFactory selectStrategyFactory) {
        this(nThreads, threadFactory, 0, selectStrategyFactory);
    }

    @Deprecated
    public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce) {
        this(nThreads, threadFactory, maxEventsAtOnce, DefaultSelectStrategyFactory.INSTANCE);
    }

    @Deprecated
    public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, maxEventsAtOnce, selectStrategyFactory, RejectedExecutionHandlers.reject());
        Epoll.ensureAvailability();
    }

    public EpollEventLoopGroup(int nThreads, Executor executor2, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor2, 0, selectStrategyFactory, RejectedExecutionHandlers.reject());
        Epoll.ensureAvailability();
    }

    public EpollEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor2, chooserFactory, 0, selectStrategyFactory, RejectedExecutionHandlers.reject());
        Epoll.ensureAvailability();
    }

    public EpollEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, executor2, chooserFactory, 0, selectStrategyFactory, rejectedExecutionHandler);
        Epoll.ensureAvailability();
    }

    public EpollEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory queueFactory) {
        super(nThreads, executor2, chooserFactory, 0, selectStrategyFactory, rejectedExecutionHandler, queueFactory);
        Epoll.ensureAvailability();
    }

    public EpollEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory taskQueueFactory, EventLoopTaskQueueFactory tailTaskQueueFactory) {
        super(nThreads, executor2, chooserFactory, 0, selectStrategyFactory, rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory);
        Epoll.ensureAvailability();
    }

    public void setIoRatio(int ioRatio) {
        for (EventExecutor e : this) {
            ((EpollEventLoop)e).setIoRatio(ioRatio);
        }
    }

    @Override
    protected EventLoop newChild(Executor executor2, Object ... args2) throws Exception {
        Integer maxEvents = (Integer)args2[0];
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
        return new EpollEventLoop(this, executor2, maxEvents, selectStrategyFactory.newSelectStrategy(), rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory);
    }
}

