/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFlushPromiseNotifier;
import io.grpc.netty.shaded.io.netty.channel.ChannelProgressivePromise;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultProgressivePromise;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.GenericFutureListener;

public class DefaultChannelProgressivePromise
extends DefaultProgressivePromise<Void>
implements ChannelProgressivePromise,
ChannelFlushPromiseNotifier.FlushCheckpoint {
    private final Channel channel;
    private long checkpoint;

    public DefaultChannelProgressivePromise(Channel channel) {
        this.channel = channel;
    }

    public DefaultChannelProgressivePromise(Channel channel, EventExecutor executor2) {
        super(executor2);
        this.channel = channel;
    }

    @Override
    protected EventExecutor executor() {
        EventExecutor e = super.executor();
        if (e == null) {
            return this.channel().eventLoop();
        }
        return e;
    }

    @Override
    public Channel channel() {
        return this.channel;
    }

    @Override
    public ChannelProgressivePromise setSuccess() {
        return this.setSuccess(null);
    }

    @Override
    public ChannelProgressivePromise setSuccess(Void result) {
        super.setSuccess(result);
        return this;
    }

    @Override
    public boolean trySuccess() {
        return this.trySuccess(null);
    }

    @Override
    public ChannelProgressivePromise setFailure(Throwable cause) {
        super.setFailure(cause);
        return this;
    }

    @Override
    public ChannelProgressivePromise setProgress(long progress, long total) {
        super.setProgress(progress, total);
        return this;
    }

    @Override
    public ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.addListener(listener);
        return this;
    }

    @Override
    public ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>> ... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.removeListener(listener);
        return this;
    }

    @Override
    public ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>> ... listeners) {
        super.removeListeners(listeners);
        return this;
    }

    @Override
    public ChannelProgressivePromise sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override
    public ChannelProgressivePromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override
    public ChannelProgressivePromise await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override
    public ChannelProgressivePromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override
    public long flushCheckpoint() {
        return this.checkpoint;
    }

    @Override
    public void flushCheckpoint(long checkpoint) {
        this.checkpoint = checkpoint;
    }

    @Override
    public ChannelProgressivePromise promise() {
        return this;
    }

    @Override
    protected void checkDeadLock() {
        if (this.channel().isRegistered()) {
            super.checkDeadLock();
        }
    }

    @Override
    public ChannelProgressivePromise unvoid() {
        return this;
    }

    @Override
    public boolean isVoid() {
        return false;
    }
}

