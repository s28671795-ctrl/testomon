/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.GenericFutureListener;
import io.grpc.netty.shaded.io.netty.util.concurrent.ProgressiveFuture;

public interface ChannelProgressiveFuture
extends ChannelFuture,
ProgressiveFuture<Void> {
    @Override
    public ChannelProgressiveFuture addListener(GenericFutureListener<? extends Future<? super Void>> var1);

    @Override
    public ChannelProgressiveFuture addListeners(GenericFutureListener<? extends Future<? super Void>> ... var1);

    @Override
    public ChannelProgressiveFuture removeListener(GenericFutureListener<? extends Future<? super Void>> var1);

    @Override
    public ChannelProgressiveFuture removeListeners(GenericFutureListener<? extends Future<? super Void>> ... var1);

    @Override
    public ChannelProgressiveFuture sync() throws InterruptedException;

    @Override
    public ChannelProgressiveFuture syncUninterruptibly();

    @Override
    public ChannelProgressiveFuture await() throws InterruptedException;

    @Override
    public ChannelProgressiveFuture awaitUninterruptibly();
}

