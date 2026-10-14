/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.pool;

import io.grpc.netty.shaded.io.netty.channel.Channel;

public interface ChannelPoolHandler {
    public void channelReleased(Channel var1) throws Exception;

    public void channelAcquired(Channel var1) throws Exception;

    public void channelCreated(Channel var1) throws Exception;
}

