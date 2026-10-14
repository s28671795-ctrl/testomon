/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.ChannelCredentials;
import io.grpc.ExperimentalApi;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelCredentials;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;

@ExperimentalApi(value="There is no plan to make this API stable, given transport API instability")
public final class NettySslContextChannelCredentials {
    private NettySslContextChannelCredentials() {
    }

    public static ChannelCredentials create(SslContext sslContext) {
        Preconditions.checkArgument(sslContext.isClient(), "Server SSL context can not be used for client channel");
        GrpcSslContexts.ensureAlpnAndH2Enabled(sslContext.applicationProtocolNegotiator());
        return NettyChannelCredentials.create(ProtocolNegotiators.tlsClientFactory(sslContext));
    }
}

