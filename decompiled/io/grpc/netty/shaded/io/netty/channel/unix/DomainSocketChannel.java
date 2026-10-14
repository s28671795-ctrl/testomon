/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.unix;

import io.grpc.netty.shaded.io.netty.channel.socket.DuplexChannel;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketAddress;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketChannelConfig;
import io.grpc.netty.shaded.io.netty.channel.unix.UnixChannel;

public interface DomainSocketChannel
extends UnixChannel,
DuplexChannel {
    @Override
    public DomainSocketAddress remoteAddress();

    @Override
    public DomainSocketAddress localAddress();

    @Override
    public DomainSocketChannelConfig config();
}

