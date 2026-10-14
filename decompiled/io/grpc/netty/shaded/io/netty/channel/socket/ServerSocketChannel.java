/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.socket;

import io.grpc.netty.shaded.io.netty.channel.ServerChannel;
import io.grpc.netty.shaded.io.netty.channel.socket.ServerSocketChannelConfig;
import java.net.InetSocketAddress;

public interface ServerSocketChannel
extends ServerChannel {
    @Override
    public ServerSocketChannelConfig config();

    @Override
    public InetSocketAddress localAddress();

    @Override
    public InetSocketAddress remoteAddress();
}

