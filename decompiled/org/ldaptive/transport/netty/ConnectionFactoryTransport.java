/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import org.ldaptive.transport.netty.NettyConnectionFactoryTransport;
import org.ldaptive.transport.netty.NettyUtils;

public class ConnectionFactoryTransport
extends NettyConnectionFactoryTransport {
    public ConnectionFactoryTransport() {
        this(0);
    }

    public ConnectionFactoryTransport(int ioThreads) {
        this(ConnectionFactoryTransport.class.getSimpleName(), ioThreads);
    }

    public ConnectionFactoryTransport(String name, int ioThreads) {
        super(NettyUtils.getDefaultSocketChannelType(), NettyUtils.createDefaultEventLoopGroup(name + "-io", ioThreads), null);
    }

    public ConnectionFactoryTransport(int ioThreads, int messageThreads) {
        this(ConnectionFactoryTransport.class.getSimpleName(), ioThreads, messageThreads);
    }

    public ConnectionFactoryTransport(String name, int ioThreads, int messageThreads) {
        super(NettyUtils.getDefaultSocketChannelType(), NettyUtils.createDefaultEventLoopGroup(name + "-io", ioThreads), NettyUtils.createDefaultEventLoopGroup(name + "-messages", messageThreads));
    }

    public static class DualThread
    extends ConnectionFactoryTransport {
        public DualThread() {
            super(DualThread.class.getSimpleName(), 2);
        }
    }

    public static class SingleThread
    extends ConnectionFactoryTransport {
        public SingleThread() {
            super(SingleThread.class.getSimpleName(), 1);
        }
    }
}

