/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.ChannelCredentials;
import io.grpc.Internal;
import io.grpc.ManagedChannelProvider;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiators;
import java.net.SocketAddress;
import java.util.Collection;

@Internal
public final class NettyChannelProvider
extends ManagedChannelProvider {
    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public int priority() {
        return 5;
    }

    public NettyChannelBuilder builderForAddress(String name, int port) {
        return NettyChannelBuilder.forAddress(name, port);
    }

    public NettyChannelBuilder builderForTarget(String target) {
        return NettyChannelBuilder.forTarget(target);
    }

    @Override
    public ManagedChannelProvider.NewChannelBuilderResult newChannelBuilder(String target, ChannelCredentials creds) {
        ProtocolNegotiators.FromChannelCredentialsResult result = ProtocolNegotiators.from(creds);
        if (result.error != null) {
            return ManagedChannelProvider.NewChannelBuilderResult.error(result.error);
        }
        return ManagedChannelProvider.NewChannelBuilderResult.channelBuilder(new NettyChannelBuilder(target, creds, result.callCredentials, result.negotiator));
    }

    @Override
    protected Collection<Class<? extends SocketAddress>> getSupportedSocketAddressTypes() {
        return NettyChannelBuilder.getSupportedSocketAddressTypes();
    }
}

