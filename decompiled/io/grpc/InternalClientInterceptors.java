/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.Internal;
import io.grpc.MethodDescriptor;

@Internal
public final class InternalClientInterceptors {
    public static <ReqT, RespT> ClientInterceptor wrapClientInterceptor(ClientInterceptor interceptor, MethodDescriptor.Marshaller<ReqT> reqMarshaller, MethodDescriptor.Marshaller<RespT> respMarshaller) {
        return ClientInterceptors.wrapClientInterceptor(interceptor, reqMarshaller, respMarshaller);
    }
}

