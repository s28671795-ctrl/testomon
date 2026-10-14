/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ServerCallHandler<RequestT, ResponseT> {
    public ServerCall.Listener<RequestT> startCall(ServerCall<RequestT, ResponseT> var1, Metadata var2);
}

