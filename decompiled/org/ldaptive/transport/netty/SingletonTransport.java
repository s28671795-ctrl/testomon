/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.channel.EventLoopGroup;
import org.ldaptive.transport.netty.NettyConnectionFactoryTransport;
import org.ldaptive.transport.netty.NettyUtils;

public class SingletonTransport
extends NettyConnectionFactoryTransport {
    private static final EventLoopGroup SHARED_WORKER_GROUP = NettyUtils.createDefaultEventLoopGroup(SingletonTransport.class.getSimpleName(), 0);

    public SingletonTransport() {
        super(NettyUtils.getDefaultSocketChannelType(), SHARED_WORKER_GROUP);
    }

    @Override
    public void close() {
    }

    public static void shutdown() {
        NettyUtils.shutdownGracefully(SHARED_WORKER_GROUP);
    }
}

