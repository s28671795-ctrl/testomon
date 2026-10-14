/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socks;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksRequest;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksRequestType;

public final class UnknownSocksRequest
extends SocksRequest {
    public UnknownSocksRequest() {
        super(SocksRequestType.UNKNOWN);
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
    }
}

