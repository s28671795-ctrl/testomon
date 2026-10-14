/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.string;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageToMessageDecoder;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import java.nio.charset.Charset;
import java.util.List;

@ChannelHandler.Sharable
public class StringDecoder
extends MessageToMessageDecoder<ByteBuf> {
    private final Charset charset;

    public StringDecoder() {
        this(Charset.defaultCharset());
    }

    public StringDecoder(Charset charset) {
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(msg.toString(this.charset));
    }
}

