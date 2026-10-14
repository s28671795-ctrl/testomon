/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.CompleteChannelFuture;
import io.netty.util.concurrent.EventExecutor;

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

