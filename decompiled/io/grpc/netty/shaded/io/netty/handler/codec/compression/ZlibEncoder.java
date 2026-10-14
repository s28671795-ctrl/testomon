/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.compression;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageToByteEncoder;

public abstract class ZlibEncoder
extends MessageToByteEncoder<ByteBuf> {
    protected ZlibEncoder() {
        super(false);
    }

    public abstract boolean isClosed();

    public abstract ChannelFuture close();

    public abstract ChannelFuture close(ChannelPromise var1);
}

