/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;
import io.grpc.NameResolver;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4159")
public abstract class NameResolverProvider
extends NameResolver.Factory {
    protected abstract boolean isAvailable();

    protected abstract int priority();

    protected String getScheme() {
        return this.getDefaultScheme();
    }

    public Collection<Class<? extends SocketAddress>> getProducedSocketAddressTypes() {
        return Collections.singleton(InetSocketAddress.class);
    }
}

