/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Exception;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameListener;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2FrameSizePolicy;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2HeadersDecoder;
import java.io.Closeable;

public interface Http2FrameReader
extends Closeable {
    public void readFrame(ChannelHandlerContext var1, ByteBuf var2, Http2FrameListener var3) throws Http2Exception;

    public Configuration configuration();

    @Override
    public void close();

    public static interface Configuration {
        public Http2HeadersDecoder.Configuration headersConfiguration();

        public Http2FrameSizePolicy frameSizePolicy();
    }
}

