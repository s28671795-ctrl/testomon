/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.ChannelCredentials;
import io.grpc.Internal;
import io.grpc.ManagedChannelProvider;
import io.grpc.internal.SharedResourcePool;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelProvider;
import io.grpc.netty.shaded.io.grpc.netty.Utils;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;

@Internal
public final class UdsNettyChannelProvider
extends ManagedChannelProvider {
    @Override
    public boolean isAvailable() {
        return Utils.EPOLL_DOMAIN_CLIENT_CHANNEL_TYPE != null;
    }

    @Override
    public int priority() {
        return 3;
    }

    public NettyChannelBuilder builderForAddress(String name, int port) {
        throw new AssertionError((Object)"NettyChannelProvider shadows this implementation");
    }

    public NettyChannelBuilder builderForTarget(String target) {
        throw new AssertionError((Object)"NettyChannelProvider shadows this implementation");
    }

    @Override
    public ManagedChannelProvider.NewChannelBuilderResult newChannelBuilder(String target, ChannelCredentials creds) {
        Preconditions.checkState(this.isAvailable());
        ManagedChannelProvider.NewChannelBuilderResult result = new NettyChannelProvider().newChannelBuilder(target, creds);
        if (result.getChannelBuilder() != null) {
            ((NettyChannelBuilder)result.getChannelBuilder()).eventLoopGroupPool(SharedResourcePool.forResource(Utils.DEFAULT_WORKER_EVENT_LOOP_GROUP)).channelType(Utils.EPOLL_DOMAIN_CLIENT_CHANNEL_TYPE, DomainSocketAddress.class);
        }
        return result;
    }

    @Override
    protected Collection<Class<? extends SocketAddress>> getSupportedSocketAddressTypes() {
        return Collections.singleton(DomainSocketAddress.class);
    }
}

