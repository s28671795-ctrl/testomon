/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelException;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.channel.DefaultChannelPromise;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.FailedChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ThreadPerChannelEventLoop;
import io.grpc.netty.shaded.io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultPromise;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultThreadFactory;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.FutureListener;
import io.grpc.netty.shaded.io.netty.util.concurrent.GlobalEventExecutor;
import io.grpc.netty.shaded.io.netty.util.concurrent.Promise;
import io.grpc.netty.shaded.io.netty.util.concurrent.ThreadPerTaskExecutor;
import io.grpc.netty.shaded.io.netty.util.internal.EmptyArrays;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;
import io.grpc.netty.shaded.io.netty.util.internal.ReadOnlyIterator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Deprecated
public class ThreadPerChannelEventLoopGroup
extends AbstractEventExecutorGroup
implements EventLoopGroup {
    private final Object[] childArgs;
    private final int maxChannels;
    final Executor executor;
    final Set<EventLoop> activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
    final Queue<EventLoop> idleChildren = new ConcurrentLinkedQueue<EventLoop>();
    private final ChannelException tooManyChannels;
    private volatile boolean shuttingDown;
    private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
    private final FutureListener<Object> childTerminationListener = new FutureListener<Object>(){

        @Override
        public void operationComplete(Future<Object> future) throws Exception {
            if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
                ThreadPerChannelEventLoopGroup.this.terminationFuture.trySuccess(null);
            }
        }
    };

    protected ThreadPerChannelEventLoopGroup() {
        this(0);
    }

    protected ThreadPerChannelEventLoopGroup(int maxChannels) {
        this(maxChannels, (ThreadFactory)null, new Object[0]);
    }

    protected ThreadPerChannelEventLoopGroup(int maxChannels, ThreadFactory threadFactory, Object ... args2) {
        this(maxChannels, threadFactory == null ? null : new ThreadPerTaskExecutor(threadFactory), args2);
    }

    protected ThreadPerChannelEventLoopGroup(int maxChannels, Executor executor2, Object ... args2) {
        ObjectUtil.checkPositiveOrZero(maxChannels, "maxChannels");
        if (executor2 == null) {
            executor2 = new ThreadPerTaskExecutor(new DefaultThreadFactory(this.getClass()));
        }
        this.childArgs = args2 == null ? EmptyArrays.EMPTY_OBJECTS : (Object[])args2.clone();
        this.maxChannels = maxChannels;
        this.executor = executor2;
        this.tooManyChannels = ChannelException.newStatic("too many channels (max: " + maxChannels + ')', ThreadPerChannelEventLoopGroup.class, "nextChild()");
    }

    protected EventLoop newChild(Object ... args2) throws Exception {
        return new ThreadPerChannelEventLoop(this);
    }

    @Override
    public Iterator<EventExecutor> iterator() {
        return new ReadOnlyIterator<EventExecutor>(this.activeChildren.iterator());
    }

    @Override
    public EventLoop next() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Future<?> shutdownGracefully(long quietPeriod, long timeout2, TimeUnit unit) {
        this.shuttingDown = true;
        for (EventLoop l : this.activeChildren) {
            l.shutdownGracefully(quietPeriod, timeout2, unit);
        }
        for (EventLoop l : this.idleChildren) {
            l.shutdownGracefully(quietPeriod, timeout2, unit);
        }
        if (this.isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
        return this.terminationFuture();
    }

    @Override
    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Override
    @Deprecated
    public void shutdown() {
        this.shuttingDown = true;
        for (EventLoop l : this.activeChildren) {
            l.shutdown();
        }
        for (EventLoop l : this.idleChildren) {
            l.shutdown();
        }
        if (this.isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
    }

    @Override
    public boolean isShuttingDown() {
        for (EventLoop l : this.activeChildren) {
            if (l.isShuttingDown()) continue;
            return false;
        }
        for (EventLoop l : this.idleChildren) {
            if (l.isShuttingDown()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isShutdown() {
        for (EventLoop l : this.activeChildren) {
            if (l.isShutdown()) continue;
            return false;
        }
        for (EventLoop l : this.idleChildren) {
            if (l.isShutdown()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isTerminated() {
        for (EventLoop l : this.activeChildren) {
            if (l.isTerminated()) continue;
            return false;
        }
        for (EventLoop l : this.idleChildren) {
            if (l.isTerminated()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean awaitTermination(long timeout2, TimeUnit unit) throws InterruptedException {
        long timeLeft;
        long deadline = System.nanoTime() + unit.toNanos(timeout2);
        for (EventLoop l : this.activeChildren) {
            do {
                if ((timeLeft = deadline - System.nanoTime()) > 0L) continue;
                return this.isTerminated();
            } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        for (EventLoop l : this.idleChildren) {
            do {
                if ((timeLeft = deadline - System.nanoTime()) > 0L) continue;
                return this.isTerminated();
            } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        return this.isTerminated();
    }

    @Override
    public ChannelFuture register(Channel channel) {
        ObjectUtil.checkNotNull(channel, "channel");
        try {
            EventLoop l = this.nextChild();
            return l.register(new DefaultChannelPromise(channel, l));
        }
        catch (Throwable t2) {
            return new FailedChannelFuture(channel, GlobalEventExecutor.INSTANCE, t2);
        }
    }

    @Override
    public ChannelFuture register(ChannelPromise promise) {
        try {
            return this.nextChild().register(promise);
        }
        catch (Throwable t2) {
            promise.setFailure(t2);
            return promise;
        }
    }

    @Override
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        ObjectUtil.checkNotNull(channel, "channel");
        try {
            return this.nextChild().register(channel, promise);
        }
        catch (Throwable t2) {
            promise.setFailure(t2);
            return promise;
        }
    }

    private EventLoop nextChild() throws Exception {
        if (this.shuttingDown) {
            throw new RejectedExecutionException("shutting down");
        }
        EventLoop loop = this.idleChildren.poll();
        if (loop == null) {
            if (this.maxChannels > 0 && this.activeChildren.size() >= this.maxChannels) {
                throw this.tooManyChannels;
            }
            loop = this.newChild(this.childArgs);
            loop.terminationFuture().addListener(this.childTerminationListener);
        }
        this.activeChildren.add(loop);
        return loop;
    }
}

