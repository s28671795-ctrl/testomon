/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socks;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksMessageType;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksProtocolVersion;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

public abstract class SocksMessage {
    private final SocksMessageType type;
    private final SocksProtocolVersion protocolVersion = SocksProtocolVersion.SOCKS5;

    protected SocksMessage(SocksMessageType type) {
        this.type = ObjectUtil.checkNotNull(type, "type");
    }

    public SocksMessageType type() {
        return this.type;
    }

    public SocksProtocolVersion protocolVersion() {
        return this.protocolVersion;
    }

    @Deprecated
    public abstract void encodeAsByteBuf(ByteBuf var1);
}

