/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ServerInterceptor {
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> var1, Metadata var2, ServerCallHandler<ReqT, RespT> var3);
}

