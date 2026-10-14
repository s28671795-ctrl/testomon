/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

public interface StreamObserver<V> {
    public void onNext(V var1);

    public void onError(Throwable var1);

    public void onCompleted();
}

