/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socks;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksAddressType;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksCmdStatus;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksResponse;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksResponseType;
import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.grpc.netty.shaded.io.netty.util.NetUtil;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import java.net.IDN;

public final class SocksCmdResponse
extends SocksResponse {
    private final SocksCmdStatus cmdStatus;
    private final SocksAddressType addressType;
    private final String host;
    private final int port;
    private static final byte[] DOMAIN_ZEROED = new byte[]{0};
    private static final byte[] IPv4_HOSTNAME_ZEROED = new byte[]{0, 0, 0, 0};
    private static final byte[] IPv6_HOSTNAME_ZEROED = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public SocksCmdResponse(SocksCmdStatus cmdStatus, SocksAddressType addressType) {
        this(cmdStatus, addressType, null, 0);
    }

    public SocksCmdResponse(SocksCmdStatus cmdStatus, SocksAddressType addressType, String host2, int port) {
        super(SocksResponseType.CMD);
        ObjectUtil.checkNotNull(cmdStatus, "cmdStatus");
        ObjectUtil.checkNotNull(addressType, "addressType");
        if (host2 != null) {
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
        }
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException(port + " is not in bounds 0 <= x <= 65535");
        }
        this.cmdStatus = cmdStatus;
        this.addressType = addressType;
        this.host = host2;
        this.port = port;
    }

    public SocksCmdStatus cmdStatus() {
        return this.cmdStatus;
    }

    public SocksAddressType addressType() {
        return this.addressType;
    }

    public String host() {
        return this.host != null && this.addressType == SocksAddressType.DOMAIN ? IDN.toUnicode(this.host) : this.host;
    }

    public int port() {
        return this.port;
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(this.protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdStatus.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        switch (this.addressType) {
            case IPv4: {
                byte[] hostContent = this.host == null ? IPv4_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host);
                byteBuf.writeBytes(hostContent);
                ByteBufUtil.writeShortBE(byteBuf, this.port);
                break;
            }
            case DOMAIN: {
                if (this.host != null) {
                    byteBuf.writeByte(this.host.length());
                    byteBuf.writeCharSequence(this.host, CharsetUtil.US_ASCII);
                } else {
                    byteBuf.writeByte(DOMAIN_ZEROED.length);
                    byteBuf.writeBytes(DOMAIN_ZEROED);
                }
                ByteBufUtil.writeShortBE(byteBuf, this.port);
                break;
            }
            case IPv6: {
                byte[] hostContent = this.host == null ? IPv6_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host);
                byteBuf.writeBytes(hostContent);
                ByteBufUtil.writeShortBE(byteBuf, this.port);
                break;
            }
        }
    }
}

