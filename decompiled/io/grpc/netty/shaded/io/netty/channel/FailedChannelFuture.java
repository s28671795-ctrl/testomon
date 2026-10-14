/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.CompleteChannelFuture;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;

final class FailedChannelFuture
extends CompleteChannelFuture {
    private final Throwable cause;

    FailedChannelFuture(Channel channel, EventExecutor executor2, Throwable cause) {
        super(channel, executor2);
        this.cause = ObjectUtil.checkNotNull(cause, "cause");
    }

    @Override
    public Throwable cause() {
        return this.cause;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public ChannelFuture sync() {
        PlatformDependent.throwException(this.cause);
        return this;
    }

    @Override
    public ChannelFuture syncUninterruptibly() {
        PlatformDependent.throwException(this.cause);
        return this;
    }
}

