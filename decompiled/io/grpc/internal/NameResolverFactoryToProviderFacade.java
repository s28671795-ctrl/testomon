/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import java.net.URI;

public class NameResolverFactoryToProviderFacade
extends NameResolverProvider {
    private NameResolver.Factory factory;

    NameResolverFactoryToProviderFacade(NameResolver.Factory factory2) {
        this.factory = factory2;
    }

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args2) {
        return this.factory.newNameResolver(targetUri, args2);
    }

    @Override
    public String getDefaultScheme() {
        return this.factory.getDefaultScheme();
    }

    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 5;
    }
}

