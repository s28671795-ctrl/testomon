/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import org.ldaptive.transport.netty.NettyConnectionFactoryTransport;

public class NioConnectionFactoryTransport
extends NettyConnectionFactoryTransport {
    public NioConnectionFactoryTransport() {
        this(0);
    }

    public NioConnectionFactoryTransport(int ioThreads) {
        this(NioConnectionFactoryTransport.class.getSimpleName(), ioThreads);
    }

    public NioConnectionFactoryTransport(String name, int ioThreads) {
        super(NioSocketChannel.class, new NioEventLoopGroup(ioThreads, new ThreadPerTaskExecutor(new DefaultThreadFactory(name, true, 5))), null);
    }

    public NioConnectionFactoryTransport(int ioThreads, int messageThreads) {
        this(NioConnectionFactoryTransport.class.getSimpleName(), ioThreads, messageThreads);
    }

    public NioConnectionFactoryTransport(String name, int ioThreads, int messageThreads) {
        super(NioSocketChannel.class, new NioEventLoopGroup(ioThreads, new ThreadPerTaskExecutor(new DefaultThreadFactory(name + "-io", true, 5))), new NioEventLoopGroup(messageThreads, new ThreadPerTaskExecutor(new DefaultThreadFactory(name + "-messages", true, 5))));
    }
}

