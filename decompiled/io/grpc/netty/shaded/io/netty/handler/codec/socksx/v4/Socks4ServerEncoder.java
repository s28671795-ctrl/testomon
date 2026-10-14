/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageToByteEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4.Socks4CommandResponse;
import io.grpc.netty.shaded.io.netty.util.NetUtil;

@ChannelHandler.Sharable
public final class Socks4ServerEncoder
extends MessageToByteEncoder<Socks4CommandResponse> {
    public static final Socks4ServerEncoder INSTANCE = new Socks4ServerEncoder();
    private static final byte[] IPv4_HOSTNAME_ZEROED = new byte[]{0, 0, 0, 0};

    private Socks4ServerEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Socks4CommandResponse msg, ByteBuf out) throws Exception {
        out.writeByte(0);
        out.writeByte(msg.status().byteValue());
        ByteBufUtil.writeShortBE(out, msg.dstPort());
        out.writeBytes(msg.dstAddr() == null ? IPv4_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(msg.dstAddr()));
    }
}

