/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.NameResolver;

abstract class ForwardingNameResolver
extends NameResolver {
    private final NameResolver delegate;

    ForwardingNameResolver(NameResolver delegate) {
        Preconditions.checkNotNull(delegate, "delegate can not be null");
        this.delegate = delegate;
    }

    @Override
    public String getServiceAuthority() {
        return this.delegate.getServiceAuthority();
    }

    @Override
    @Deprecated
    public void start(NameResolver.Listener listener) {
        this.delegate.start(listener);
    }

    @Override
    public void start(NameResolver.Listener2 listener) {
        this.delegate.start(listener);
    }

    @Override
    public void shutdown() {
        this.delegate.shutdown();
    }

    @Override
    public void refresh() {
        this.delegate.refresh();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate).toString();
    }
}

