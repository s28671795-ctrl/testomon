/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.CompleteChannelFuture;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;

final class SucceededChannelFuture
extends CompleteChannelFuture {
    SucceededChannelFuture(Channel channel, EventExecutor executor2) {
        super(channel, executor2);
    }

    @Override
    public Throwable cause() {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}

