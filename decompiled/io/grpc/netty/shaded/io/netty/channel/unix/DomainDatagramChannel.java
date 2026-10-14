/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.unix;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainDatagramChannelConfig;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketAddress;
import io.grpc.netty.shaded.io.netty.channel.unix.UnixChannel;

public interface DomainDatagramChannel
extends UnixChannel,
Channel {
    @Override
    public DomainDatagramChannelConfig config();

    public boolean isConnected();

    @Override
    public DomainSocketAddress localAddress();

    @Override
    public DomainSocketAddress remoteAddress();
}

