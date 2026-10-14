/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socks;

import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksMessageType;
import io.grpc.netty.shaded.io.netty.handler.codec.socks.SocksResponseType;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

public abstract class SocksResponse
extends SocksMessage {
    private final SocksResponseType responseType;

    protected SocksResponse(SocksResponseType responseType) {
        super(SocksMessageType.RESPONSE);
        this.responseType = ObjectUtil.checkNotNull(responseType, "responseType");
    }

    public SocksResponseType responseType() {
        return this.responseType;
    }
}

