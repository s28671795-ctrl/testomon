/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.transport.Transport;
import org.ldaptive.transport.netty.NettyConnection;
import org.ldaptive.transport.netty.NettyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionTransport
implements Transport {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private int numIoThreads;
    private int numMessageThreads = -1;

    public ConnectionTransport() {
        this(0);
    }

    public ConnectionTransport(int ioThreads) {
        this.numIoThreads = ioThreads;
    }

    public ConnectionTransport(int ioThreads, int messageThreads) {
        this.numIoThreads = ioThreads;
        this.numMessageThreads = messageThreads;
    }

    protected Class<? extends Channel> getSocketChannelType() {
        return NettyUtils.getDefaultSocketChannelType();
    }

    protected EventLoopGroup createEventLoopGroup(String name, int numThreads) {
        return NettyUtils.createDefaultEventLoopGroup(name, numThreads);
    }

    @Override
    public Connection create(ConnectionConfig cc) {
        if (this.numMessageThreads != -1) {
            return new NettyConnection(cc, this.getSocketChannelType(), this.createEventLoopGroup(this.getClass().getSimpleName() + "@" + this.hashCode() + "-io", this.numIoThreads), this.createEventLoopGroup(this.getClass().getSimpleName() + "@" + this.hashCode() + "-messages", this.numMessageThreads), true);
        }
        return new NettyConnection(cc, this.getSocketChannelType(), this.createEventLoopGroup(this.getClass().getSimpleName() + "@" + this.hashCode() + "-io", this.numIoThreads), null, true);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "numIoThreads=" + this.numIoThreads + ", " + "numMessageThreads=" + this.numMessageThreads + "]";
    }

    public static class DualThread
    extends ConnectionTransport {
        public DualThread() {
            super(2);
        }
    }

    public static class SingleThread
    extends ConnectionTransport {
        public SingleThread() {
            super(1);
        }
    }
}

