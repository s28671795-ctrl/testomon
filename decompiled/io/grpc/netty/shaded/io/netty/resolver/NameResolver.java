/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.resolver;

import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.Promise;
import java.io.Closeable;
import java.util.List;

public interface NameResolver<T>
extends Closeable {
    public Future<T> resolve(String var1);

    public Future<T> resolve(String var1, Promise<T> var2);

    public Future<List<T>> resolveAll(String var1);

    public Future<List<T>> resolveAll(String var1, Promise<List<T>> var2);

    @Override
    public void close();
}

