/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

public interface ResultCallback<T> {
    public void onSuccess(long var1, T var3);

    public void onError(long var1, Throwable var3);
}

