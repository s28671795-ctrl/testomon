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

public class NettyConnectionFactoryTransport
implements Transport {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Class<? extends Channel> channelType;
    private final EventLoopGroup ioWorkerGroup;
    private final EventLoopGroup messageWorkerGroup;
    private boolean shutdownOnClose = true;

    public NettyConnectionFactoryTransport(Class<? extends Channel> type, EventLoopGroup ioGroup) {
        this(type, ioGroup, null);
    }

    public NettyConnectionFactoryTransport(Class<? extends Channel> type, EventLoopGroup ioGroup, EventLoopGroup messageGroup) {
        this.channelType = type;
        this.ioWorkerGroup = ioGroup;
        this.messageWorkerGroup = messageGroup;
    }

    public void setShutdownOnClose(boolean b) {
        this.shutdownOnClose = b;
    }

    @Override
    public Connection create(ConnectionConfig cc) {
        return new NettyConnection(cc, this.channelType, this.ioWorkerGroup, this.messageWorkerGroup, false);
    }

    @Override
    public void close() {
        if (this.shutdownOnClose) {
            if (!this.ioWorkerGroup.isShutdown()) {
                NettyUtils.shutdownGracefully(this.ioWorkerGroup);
                this.logger.trace("shutdown worker group {}", (Object)this.ioWorkerGroup);
            }
            if (this.messageWorkerGroup != null && !this.messageWorkerGroup.isShutdown()) {
                NettyUtils.shutdownGracefully(this.messageWorkerGroup);
                this.logger.trace("shutdown worker group {}", (Object)this.messageWorkerGroup);
            }
        }
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "channelType=" + this.channelType + ", " + "ioWorkerGroup=" + this.ioWorkerGroup + ", " + "messageWorkerGroup=" + this.messageWorkerGroup + ", " + "shutdownOnClose=" + this.shutdownOnClose + "]";
    }
}

