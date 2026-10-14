/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.ChannelCredentials;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiator;

final class NettyChannelCredentials
extends ChannelCredentials {
    private final ProtocolNegotiator.ClientFactory negotiator;

    public static ChannelCredentials create(ProtocolNegotiator.ClientFactory negotiator) {
        return new NettyChannelCredentials(negotiator);
    }

    private NettyChannelCredentials(ProtocolNegotiator.ClientFactory negotiator) {
        this.negotiator = Preconditions.checkNotNull(negotiator, "negotiator");
    }

    public ProtocolNegotiator.ClientFactory getNegotiator() {
        return this.negotiator;
    }

    @Override
    public ChannelCredentials withoutBearerTokens() {
        return this;
    }
}

