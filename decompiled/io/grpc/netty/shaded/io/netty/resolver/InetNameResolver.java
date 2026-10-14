/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.resolver;

import io.grpc.netty.shaded.io.netty.resolver.AddressResolver;
import io.grpc.netty.shaded.io.netty.resolver.InetSocketAddressResolver;
import io.grpc.netty.shaded.io.netty.resolver.SimpleNameResolver;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public abstract class InetNameResolver
extends SimpleNameResolver<InetAddress> {
    private volatile AddressResolver<InetSocketAddress> addressResolver;

    protected InetNameResolver(EventExecutor executor2) {
        super(executor2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AddressResolver<InetSocketAddress> asAddressResolver() {
        InetSocketAddressResolver result = this.addressResolver;
        if (result == null) {
            InetNameResolver inetNameResolver = this;
            synchronized (inetNameResolver) {
                result = this.addressResolver;
                if (result == null) {
                    this.addressResolver = result = new InetSocketAddressResolver(this.executor(), this);
                }
            }
        }
        return result;
    }
}

