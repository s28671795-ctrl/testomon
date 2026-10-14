/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.pool;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.pool.ChannelPoolHandler;

public abstract class AbstractChannelPoolHandler
implements ChannelPoolHandler {
    @Override
    public void channelAcquired(Channel ch) throws Exception {
    }

    @Override
    public void channelReleased(Channel ch) throws Exception {
    }
}

