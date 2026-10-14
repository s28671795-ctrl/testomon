/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.unix;

import io.grpc.netty.shaded.io.netty.channel.ServerChannel;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketAddress;
import io.grpc.netty.shaded.io.netty.channel.unix.UnixChannel;

public interface ServerDomainSocketChannel
extends ServerChannel,
UnixChannel {
    @Override
    public DomainSocketAddress remoteAddress();

    @Override
    public DomainSocketAddress localAddress();
}

