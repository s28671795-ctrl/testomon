/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.channel.EventLoop;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutorGroup;

public interface EventLoopGroup
extends EventExecutorGroup {
    @Override
    public EventLoop next();

    public ChannelFuture register(Channel var1);

    public ChannelFuture register(ChannelPromise var1);

    @Deprecated
    public ChannelFuture register(Channel var1, ChannelPromise var2);
}

