/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.socket;

import io.grpc.netty.shaded.io.netty.channel.socket.DuplexChannel;
import io.grpc.netty.shaded.io.netty.channel.socket.ServerSocketChannel;
import io.grpc.netty.shaded.io.netty.channel.socket.SocketChannelConfig;
import java.net.InetSocketAddress;

public interface SocketChannel
extends DuplexChannel {
    @Override
    public ServerSocketChannel parent();

    @Override
    public SocketChannelConfig config();

    @Override
    public InetSocketAddress localAddress();

    @Override
    public InetSocketAddress remoteAddress();
}

