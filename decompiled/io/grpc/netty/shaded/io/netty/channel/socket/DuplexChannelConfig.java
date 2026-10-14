/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.socket;

import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.ChannelConfig;
import io.grpc.netty.shaded.io.netty.channel.MessageSizeEstimator;
import io.grpc.netty.shaded.io.netty.channel.RecvByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.WriteBufferWaterMark;

public interface DuplexChannelConfig
extends ChannelConfig {
    public boolean isAllowHalfClosure();

    public DuplexChannelConfig setAllowHalfClosure(boolean var1);

    @Override
    @Deprecated
    public DuplexChannelConfig setMaxMessagesPerRead(int var1);

    @Override
    public DuplexChannelConfig setWriteSpinCount(int var1);

    @Override
    public DuplexChannelConfig setAllocator(ByteBufAllocator var1);

    @Override
    public DuplexChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

    @Override
    public DuplexChannelConfig setAutoRead(boolean var1);

    @Override
    public DuplexChannelConfig setAutoClose(boolean var1);

    @Override
    public DuplexChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);

    @Override
    public DuplexChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark var1);
}

