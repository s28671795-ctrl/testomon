/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.Channel;

public interface ChannelFactory<T extends Channel>
extends io.grpc.netty.shaded.io.netty.bootstrap.ChannelFactory<T> {
    @Override
    public T newChannel();
}

