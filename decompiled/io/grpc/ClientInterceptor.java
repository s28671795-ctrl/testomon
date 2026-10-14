/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.MethodDescriptor;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ClientInterceptor {
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> var1, CallOptions var2, Channel var3);
}

