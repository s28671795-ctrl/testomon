/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.concurrent.Promise;

public interface AsyncMapping<IN, OUT> {
    public Future<OUT> map(IN var1, Promise<OUT> var2);
}

