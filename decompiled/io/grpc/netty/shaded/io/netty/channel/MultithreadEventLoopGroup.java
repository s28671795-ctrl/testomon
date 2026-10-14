/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.util.NettyRuntime;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultThreadFactory;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutorChooserFactory;
import io.grpc.netty.shaded.io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.grpc.netty.shaded.io.netty.util.internal.SystemPropertyUtil;
import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLogger;
import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public abstract class MultithreadEventLoopGroup
extends MultithreadEventExecutorGroup
implements EventLoopGroup {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
    private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.grpc.netty.shaded.io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));

    protected MultithreadEventLoopGroup(int nThreads, Executor executor2, Object ... args2) {
        super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor2, args2);
    }

    protected MultithreadEventLoopGroup(int nThreads, ThreadFactory threadFactory, Object ... args2) {
        super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, threadFactory, args2);
    }

    protected MultithreadEventLoopGroup(int nThreads, Executor executor2, EventExecutorChooserFactory chooserFactory, Object ... args2) {
        super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor2, chooserFactory, args2);
    }

    @Override
    protected ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(this.getClass(), 10);
    }

    @Override
    public EventLoop next() {
        return (EventLoop)super.next();
    }

    @Override
    protected abstract EventLoop newChild(Executor var1, Object ... var2) throws Exception;

    @Override
    public ChannelFuture register(Channel channel) {
        return this.next().register(channel);
    }

    @Override
    public ChannelFuture register(ChannelPromise promise) {
        return this.next().register(promise);
    }

    @Override
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        return this.next().register(channel, promise);
    }

    static {
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.eventLoopThreads: {}", (Object)DEFAULT_EVENT_LOOP_THREADS);
        }
    }
}

