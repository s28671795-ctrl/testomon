/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.channel.RecvByteBufAllocator;

public interface MaxMessagesRecvByteBufAllocator
extends RecvByteBufAllocator {
    public int maxMessagesPerRead();

    public MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int var1);
}

