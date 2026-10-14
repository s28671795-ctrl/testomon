/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.ServerCredentials;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;

final class NettyServerCredentials
extends ServerCredentials {
    private final ProtocolNegotiator.ServerFactory negotiator;

    public static ServerCredentials create(ProtocolNegotiator.ServerFactory negotiator) {
        return new NettyServerCredentials(negotiator);
    }

    private NettyServerCredentials(ProtocolNegotiator.ServerFactory negotiator) {
        this.negotiator = Preconditions.checkNotNull(negotiator, "negotiator");
    }

    public ProtocolNegotiator.ServerFactory getNegotiator() {
        return this.negotiator;
    }
}

