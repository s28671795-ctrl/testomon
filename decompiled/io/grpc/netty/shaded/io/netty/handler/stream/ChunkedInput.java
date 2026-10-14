/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.stream;

import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;

public interface ChunkedInput<B> {
    public boolean isEndOfInput() throws Exception;

    public void close() throws Exception;

    @Deprecated
    public B readChunk(ChannelHandlerContext var1) throws Exception;

    public B readChunk(ByteBufAllocator var1) throws Exception;

    public long length();

    public long progress();
}

