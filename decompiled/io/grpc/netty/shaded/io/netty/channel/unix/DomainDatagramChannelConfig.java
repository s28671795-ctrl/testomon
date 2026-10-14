/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.unix;

import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.ChannelConfig;
import io.grpc.netty.shaded.io.netty.channel.MessageSizeEstimator;
import io.grpc.netty.shaded.io.netty.channel.RecvByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.WriteBufferWaterMark;

public interface DomainDatagramChannelConfig
extends ChannelConfig {
    @Override
    public DomainDatagramChannelConfig setAllocator(ByteBufAllocator var1);

    @Override
    public DomainDatagramChannelConfig setAutoClose(boolean var1);

    @Override
    public DomainDatagramChannelConfig setAutoRead(boolean var1);

    @Override
    public DomainDatagramChannelConfig setConnectTimeoutMillis(int var1);

    @Override
    @Deprecated
    public DomainDatagramChannelConfig setMaxMessagesPerRead(int var1);

    @Override
    public DomainDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);

    @Override
    public DomainDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

    public DomainDatagramChannelConfig setSendBufferSize(int var1);

    public int getSendBufferSize();

    @Override
    public DomainDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark var1);

    @Override
    public DomainDatagramChannelConfig setWriteSpinCount(int var1);
}

