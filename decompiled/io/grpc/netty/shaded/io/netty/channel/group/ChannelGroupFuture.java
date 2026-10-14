/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.group;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.group.ChannelGroup;
import io.grpc.netty.shaded.io.netty.channel.group.ChannelGroupException;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.GenericFutureListener;
import java.util.Iterator;

public interface ChannelGroupFuture
extends Future<Void>,
Iterable<ChannelFuture> {
    public ChannelGroup group();

    public ChannelFuture find(Channel var1);

    @Override
    public boolean isSuccess();

    @Override
    public ChannelGroupException cause();

    public boolean isPartialSuccess();

    public boolean isPartialFailure();

    public ChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> var1);

    public ChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>> ... var1);

    public ChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> var1);

    public ChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>> ... var1);

    public ChannelGroupFuture await() throws InterruptedException;

    public ChannelGroupFuture awaitUninterruptibly();

    public ChannelGroupFuture syncUninterruptibly();

    public ChannelGroupFuture sync() throws InterruptedException;

    @Override
    public Iterator<ChannelFuture> iterator();
}

