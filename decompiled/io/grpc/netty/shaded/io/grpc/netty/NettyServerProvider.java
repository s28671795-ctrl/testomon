/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.Internal;
import io.grpc.ServerCredentials;
import io.grpc.ServerProvider;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;
import java.net.InetSocketAddress;

@Internal
public final class NettyServerProvider
extends ServerProvider {
    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 5;
    }

    protected NettyServerBuilder builderForPort(int port) {
        return NettyServerBuilder.forPort(port);
    }

    @Override
    protected ServerProvider.NewServerBuilderResult newServerBuilderForPort(int port, ServerCredentials creds) {
        ProtocolNegotiators.FromServerCredentialsResult result = ProtocolNegotiators.from(creds);
        if (result.error != null) {
            return ServerProvider.NewServerBuilderResult.error(result.error);
        }
        return ServerProvider.NewServerBuilderResult.serverBuilder(new NettyServerBuilder(new InetSocketAddress(port), result.negotiator));
    }
}

