/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.ExperimentalApi;
import io.grpc.ServerCredentials;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerCredentials;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;

@ExperimentalApi(value="There is no plan to make this API stable, given transport API instability")
public final class NettySslContextServerCredentials {
    private NettySslContextServerCredentials() {
    }

    public static ServerCredentials create(SslContext sslContext) {
        Preconditions.checkArgument(sslContext.isServer(), "Client SSL context can not be used for server");
        GrpcSslContexts.ensureAlpnAndH2Enabled(sslContext.applicationProtocolNegotiator());
        return NettyServerCredentials.create(ProtocolNegotiators.serverTlsFactory(sslContext));
    }
}

