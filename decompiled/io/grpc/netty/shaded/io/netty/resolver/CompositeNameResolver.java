/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.resolver;

import io.grpc.netty.shaded.io.netty.resolver.NameResolver;
import io.grpc.netty.shaded.io.netty.resolver.SimpleNameResolver;
import io.grpc.netty.shaded.io.netty.util.concurrent.EventExecutor;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.FutureListener;
import io.grpc.netty.shaded.io.netty.util.concurrent.Promise;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import java.util.Arrays;
import java.util.List;

public final class CompositeNameResolver<T>
extends SimpleNameResolver<T> {
    private final NameResolver<T>[] resolvers;

    public CompositeNameResolver(EventExecutor executor2, NameResolver<T> ... resolvers) {
        super(executor2);
        ObjectUtil.checkNotNull(resolvers, "resolvers");
        for (int i = 0; i < resolvers.length; ++i) {
            ObjectUtil.checkNotNull(resolvers[i], "resolvers[" + i + ']');
        }
        if (resolvers.length < 2) {
            throw new IllegalArgumentException("resolvers: " + Arrays.asList(resolvers) + " (expected: at least 2 resolvers)");
        }
        this.resolvers = (NameResolver[])resolvers.clone();
    }

    @Override
    protected void doResolve(String inetHost, Promise<T> promise) throws Exception {
        this.doResolveRec(inetHost, promise, 0, null);
    }

    private void doResolveRec(final String inetHost, final Promise<T> promise, final int resolverIndex, Throwable lastFailure) throws Exception {
        if (resolverIndex >= this.resolvers.length) {
            promise.setFailure(lastFailure);
        } else {
            NameResolver<T> resolver2 = this.resolvers[resolverIndex];
            resolver2.resolve(inetHost).addListener(new FutureListener<T>(){

                @Override
                public void operationComplete(Future<T> future) throws Exception {
                    if (future.isSuccess()) {
                        promise.setSuccess(future.getNow());
                    } else {
                        CompositeNameResolver.this.doResolveRec(inetHost, promise, resolverIndex + 1, future.cause());
                    }
                }
            });
        }
    }

    @Override
    protected void doResolveAll(String inetHost, Promise<List<T>> promise) throws Exception {
        this.doResolveAllRec(inetHost, promise, 0, null);
    }

    private void doResolveAllRec(final String inetHost, final Promise<List<T>> promise, final int resolverIndex, Throwable lastFailure) throws Exception {
        if (resolverIndex >= this.resolvers.length) {
            promise.setFailure(lastFailure);
        } else {
            NameResolver<T> resolver2 = this.resolvers[resolverIndex];
            resolver2.resolveAll(inetHost).addListener(new FutureListener<List<T>>(){

                @Override
                public void operationComplete(Future<List<T>> future) throws Exception {
                    if (future.isSuccess()) {
                        promise.setSuccess(future.getNow());
                    } else {
                        CompositeNameResolver.this.doResolveAllRec(inetHost, promise, resolverIndex + 1, future.cause());
                    }
                }
            });
        }
    }
}

