/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.resolver;

import io.grpc.netty.shaded.io.netty.resolver.AddressResolver;
import io.grpc.netty.shaded.io.netty.resolver.AddressResolverGroup;
import io.grpc.netty.shaded.io.netty.resolver.DefaultNameResolver;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;
import java.net.InetSocketAddress;

public final class DefaultAddressResolverGroup
extends AddressResolverGroup<InetSocketAddress> {
    public static final DefaultAddressResolverGroup INSTANCE = new DefaultAddressResolverGroup();

    private DefaultAddressResolverGroup() {
    }

    @Override
    protected AddressResolver<InetSocketAddress> newResolver(EventExecutor executor2) throws Exception {
        return new DefaultNameResolver(executor2).asAddressResolver();
    }
}

