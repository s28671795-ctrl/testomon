/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.socket.oio;

import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.MessageSizeEstimator;
import io.grpc.netty.shaded.io.netty.channel.RecvByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.WriteBufferWaterMark;
import io.grpc.netty.shaded.io.netty.channel.socket.ServerSocketChannelConfig;

@Deprecated
public interface OioServerSocketChannelConfig
extends ServerSocketChannelConfig {
    public OioServerSocketChannelConfig setSoTimeout(int var1);

    public int getSoTimeout();

    @Override
    public OioServerSocketChannelConfig setBacklog(int var1);

    @Override
    public OioServerSocketChannelConfig setReuseAddress(boolean var1);

    @Override
    public OioServerSocketChannelConfig setReceiveBufferSize(int var1);

    @Override
    public OioServerSocketChannelConfig setPerformancePreferences(int var1, int var2, int var3);

    @Override
    public OioServerSocketChannelConfig setConnectTimeoutMillis(int var1);

    @Override
    @Deprecated
    public OioServerSocketChannelConfig setMaxMessagesPerRead(int var1);

    @Override
    public OioServerSocketChannelConfig setWriteSpinCount(int var1);

    @Override
    public OioServerSocketChannelConfig setAllocator(ByteBufAllocator var1);

    @Override
    public OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

    @Override
    public OioServerSocketChannelConfig setAutoRead(boolean var1);

    @Override
    public OioServerSocketChannelConfig setAutoClose(boolean var1);

    @Override
    public OioServerSocketChannelConfig setWriteBufferHighWaterMark(int var1);

    @Override
    public OioServerSocketChannelConfig setWriteBufferLowWaterMark(int var1);

    @Override
    public OioServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark var1);

    @Override
    public OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);
}

