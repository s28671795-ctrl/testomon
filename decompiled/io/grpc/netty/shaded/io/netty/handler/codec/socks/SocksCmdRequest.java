/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socks;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksAddressType;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksCmdType;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksRequest;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksRequestType;
import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.grpc.netty.shaded.io.netty.util.NetUtil;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import java.net.IDN;

public final class SocksCmdRequest
extends SocksRequest {
    private final SocksCmdType cmdType;
    private final SocksAddressType addressType;
    private final String host;
    private final int port;

    public SocksCmdRequest(SocksCmdType cmdType, SocksAddressType addressType, String host2, int port) {
        super(SocksRequestType.CMD);
        ObjectUtil.checkNotNull(cmdType, "cmdType");
        ObjectUtil.checkNotNull(addressType, "addressType");
        ObjectUtil.checkNotNull(host2, "host");
        switch (addressType) {
            case IPv4: {
                if (NetUtil.isValidIpV4Address(host2)) break;
                throw new IllegalArgumentException(host2 + " is not a valid IPv4 address");
            }
            case DOMAIN: {
                String asciiHost = IDN.toASCII(host2);
                if (asciiHost.length() > 255) {
                    throw new IllegalArgumentException(host2 + " IDN: " + asciiHost + " exceeds 255 char limit");
                }
                host2 = asciiHost;
                break;
            }
            case IPv6: {
                if (NetUtil.isValidIpV6Address(host2)) break;
                throw new IllegalArgumentException(host2 + " is not a valid IPv6 address");
            }
        }
        if (port <= 0 || port >= 65536) {
            throw new IllegalArgumentException(port + " is not in bounds 0 < x < 65536");
        }
        this.cmdType = cmdType;
        this.addressType = addressType;
        this.host = host2;
        this.port = port;
    }

    public SocksCmdType cmdType() {
        return this.cmdType;
    }

    public SocksAddressType addressType() {
        return this.addressType;
    }

    public String host() {
        return this.addressType == SocksAddressType.DOMAIN ? IDN.toUnicode(this.host) : this.host;
    }

    public int port() {
        return this.port;
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(this.protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdType.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        switch (this.addressType) {
            case IPv4: {
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
                ByteBufUtil.writeShortBE(byteBuf, this.port);
                break;
            }
            case DOMAIN: {
                byteBuf.writeByte(this.host.length());
                byteBuf.writeCharSequence(this.host, CharsetUtil.US_ASCII);
                ByteBufUtil.writeShortBE(byteBuf, this.port);
                break;
            }
            case IPv6: {
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
                ByteBufUtil.writeShortBE(byteBuf, this.port);
            }
        }
    }
}

