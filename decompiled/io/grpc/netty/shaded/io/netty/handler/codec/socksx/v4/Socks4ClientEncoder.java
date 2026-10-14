/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageToByteEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4.Socks4CommandRequest;
import io.grpc.netty.shaded.io.netty.util.NetUtil;

@ChannelHandler.Sharable
public final class Socks4ClientEncoder
extends MessageToByteEncoder<Socks4CommandRequest> {
    public static final Socks4ClientEncoder INSTANCE = new Socks4ClientEncoder();
    private static final byte[] IPv4_DOMAIN_MARKER = new byte[]{0, 0, 0, 1};

    private Socks4ClientEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Socks4CommandRequest msg, ByteBuf out) throws Exception {
        out.writeByte(msg.version().byteValue());
        out.writeByte(msg.type().byteValue());
        ByteBufUtil.writeShortBE(out, msg.dstPort());
        if (NetUtil.isValidIpV4Address(msg.dstAddr())) {
            out.writeBytes(NetUtil.createByteArrayFromIpAddressString(msg.dstAddr()));
            ByteBufUtil.writeAscii(out, (CharSequence)msg.userId());
            out.writeByte(0);
        } else {
            out.writeBytes(IPv4_DOMAIN_MARKER);
            ByteBufUtil.writeAscii(out, (CharSequence)msg.userId());
            out.writeByte(0);
            ByteBufUtil.writeAscii(out, (CharSequence)msg.dstAddr());
            out.writeByte(0);
        }
    }
}

