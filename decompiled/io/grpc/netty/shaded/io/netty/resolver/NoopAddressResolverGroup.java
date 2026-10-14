/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.resolver;

import io.grpc.netty.shaded.io.netty.resolver.AddressResolver;
import io.grpc.netty.shaded.io.netty.resolver.AddressResolverGroup;
import io.grpc.netty.shaded.io.netty.resolver.NoopAddressResolver;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;
import java.net.SocketAddress;

public final class NoopAddressResolverGroup
extends AddressResolverGroup<SocketAddress> {
    public static final NoopAddressResolverGroup INSTANCE = new NoopAddressResolverGroup();

    private NoopAddressResolverGroup() {
    }

    @Override
    protected AddressResolver<SocketAddress> newResolver(EventExecutor executor2) throws Exception {
        return new NoopAddressResolver(executor2);
    }
}

