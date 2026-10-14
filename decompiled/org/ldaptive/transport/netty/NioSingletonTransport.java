/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import org.ldaptive.transport.netty.NettyConnectionFactoryTransport;
import org.ldaptive.transport.netty.NettyUtils;

public class NioSingletonTransport
extends NettyConnectionFactoryTransport {
    private static final EventLoopGroup SHARED_WORKER_GROUP = new NioEventLoopGroup(0, new ThreadPerTaskExecutor(new DefaultThreadFactory(NioSingletonTransport.class, true, 5)));

    public NioSingletonTransport() {
        super(NioSocketChannel.class, SHARED_WORKER_GROUP);
    }

    @Override
    public void close() {
    }

    public static void shutdown() {
        NettyUtils.shutdownGracefully(SHARED_WORKER_GROUP);
    }
}

