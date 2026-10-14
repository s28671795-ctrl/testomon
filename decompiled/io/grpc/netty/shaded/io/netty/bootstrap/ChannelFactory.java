/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.bootstrap;

import io.grpc.netty.shaded.io.netty.channel.Channel;

@Deprecated
public interface ChannelFactory<T extends Channel> {
    public T newChannel();
}

