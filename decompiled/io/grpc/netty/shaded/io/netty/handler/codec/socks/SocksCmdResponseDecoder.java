/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socks;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.ReplayingDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksAddressType;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksCmdResponse;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksCmdStatus;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksCommonUtils;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksProtocolVersion;
import io.grpc.netty.shaded.io.netty.util.NetUtil;
import java.util.List;

public class SocksCmdResponseDecoder
extends ReplayingDecoder<State> {
    private SocksCmdStatus cmdStatus;
    private SocksAddressType addressType;

    public SocksCmdResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        block0 : switch ((State)((Object)this.state())) {
            case CHECK_PROTOCOL_VERSION: {
                if (byteBuf.readByte() != SocksProtocolVersion.SOCKS5.byteValue()) {
                    out.add(SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE);
                    break;
                }
                this.checkpoint(State.READ_CMD_HEADER);
            }
            case READ_CMD_HEADER: {
                this.cmdStatus = SocksCmdStatus.valueOf(byteBuf.readByte());
                byteBuf.skipBytes(1);
                this.addressType = SocksAddressType.valueOf(byteBuf.readByte());
                this.checkpoint(State.READ_CMD_ADDRESS);
            }
            case READ_CMD_ADDRESS: {
                switch (this.addressType) {
                    case IPv4: {
                        String host2 = NetUtil.intToIpAddress(ByteBufUtil.readIntBE(byteBuf));
                        int port = ByteBufUtil.readUnsignedShortBE(byteBuf);
                        out.add(new SocksCmdResponse(this.cmdStatus, this.addressType, host2, port));
                        break block0;
                    }
                    case DOMAIN: {
                        byte fieldLength = byteBuf.readByte();
                        String host3 = SocksCommonUtils.readUsAscii(byteBuf, fieldLength);
                        int port = ByteBufUtil.readUnsignedShortBE(byteBuf);
                        out.add(new SocksCmdResponse(this.cmdStatus, this.addressType, host3, port));
                        break block0;
                    }
                    case IPv6: {
                        byte[] bytes = new byte[16];
                        byteBuf.readBytes(bytes);
                        String host4 = SocksCommonUtils.ipv6toStr(bytes);
                        int port = ByteBufUtil.readUnsignedShortBE(byteBuf);
                        out.add(new SocksCmdResponse(this.cmdStatus, this.addressType, host4, port));
                        break block0;
                    }
                    case UNKNOWN: {
                        out.add(SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE);
                        break block0;
                    }
                }
                throw new Error();
            }
            default: {
                throw new Error();
            }
        }
        ctx.pipeline().remove(this);
    }

    public static enum State {
        CHECK_PROTOCOL_VERSION,
        READ_CMD_HEADER,
        READ_CMD_ADDRESS;

    }
}

