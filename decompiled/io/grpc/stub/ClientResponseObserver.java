/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.StreamObserver;

public interface ClientResponseObserver<ReqT, RespT>
extends StreamObserver<RespT> {
    public void beforeStart(ClientCallStreamObserver<ReqT> var1);
}

