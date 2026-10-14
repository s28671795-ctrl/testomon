/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import org.ldaptive.transport.netty.ConnectionTransport;

public class NioConnectionTransport
extends ConnectionTransport {
    public NioConnectionTransport() {
        this(0);
    }

    public NioConnectionTransport(int ioThreads) {
        super(ioThreads);
    }

    public NioConnectionTransport(int ioThreads, int messageThreads) {
        super(ioThreads, messageThreads);
    }

    @Override
    protected Class<? extends Channel> getSocketChannelType() {
        return NioSocketChannel.class;
    }

    @Override
    protected EventLoopGroup createEventLoopGroup(String name, int numThreads) {
        return new NioEventLoopGroup(numThreads, new ThreadPerTaskExecutor(new DefaultThreadFactory(name, true, 5)));
    }
}

