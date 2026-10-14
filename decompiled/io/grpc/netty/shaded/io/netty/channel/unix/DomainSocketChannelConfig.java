/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.unix;

import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.ChannelConfig;
import io.grpc.netty.shaded.io.netty.channel.MessageSizeEstimator;
import io.grpc.netty.shaded.io.netty.channel.RecvByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.WriteBufferWaterMark;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketReadMode;

public interface DomainSocketChannelConfig
extends ChannelConfig {
    @Override
    @Deprecated
    public DomainSocketChannelConfig setMaxMessagesPerRead(int var1);

    @Override
    public DomainSocketChannelConfig setConnectTimeoutMillis(int var1);

    @Override
    public DomainSocketChannelConfig setWriteSpinCount(int var1);

    @Override
    public DomainSocketChannelConfig setAllocator(ByteBufAllocator var1);

    @Override
    public DomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

    @Override
    public DomainSocketChannelConfig setAutoRead(boolean var1);

    @Override
    public DomainSocketChannelConfig setAutoClose(boolean var1);

    @Override
    @Deprecated
    public DomainSocketChannelConfig setWriteBufferHighWaterMark(int var1);

    @Override
    @Deprecated
    public DomainSocketChannelConfig setWriteBufferLowWaterMark(int var1);

    @Override
    public DomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark var1);

    @Override
    public DomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);

    public DomainSocketChannelConfig setReadMode(DomainSocketReadMode var1);

    public DomainSocketReadMode getReadMode();
}

