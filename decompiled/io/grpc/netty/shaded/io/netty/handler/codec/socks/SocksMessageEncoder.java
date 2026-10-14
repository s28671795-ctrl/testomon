/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socks;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageToByteEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksMessage;

@ChannelHandler.Sharable
public class SocksMessageEncoder
extends MessageToByteEncoder<SocksMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SocksMessage msg, ByteBuf out) throws Exception {
        msg.encodeAsByteBuf(out);
    }
}

