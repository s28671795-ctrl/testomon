/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.util.concurrent.OrderedEventExecutor;

public interface EventLoop
extends OrderedEventExecutor,
EventLoopGroup {
    @Override
    public EventLoopGroup parent();
}

