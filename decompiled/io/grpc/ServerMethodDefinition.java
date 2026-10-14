/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.MethodDescriptor;
import io.grpc.ServerCallHandler;

public final class ServerMethodDefinition<ReqT, RespT> {
    private final MethodDescriptor<ReqT, RespT> method;
    private final ServerCallHandler<ReqT, RespT> handler;

    private ServerMethodDefinition(MethodDescriptor<ReqT, RespT> method2, ServerCallHandler<ReqT, RespT> handler) {
        this.method = method2;
        this.handler = handler;
    }

    public static <ReqT, RespT> ServerMethodDefinition<ReqT, RespT> create(MethodDescriptor<ReqT, RespT> method2, ServerCallHandler<ReqT, RespT> handler) {
        return new ServerMethodDefinition<ReqT, RespT>(method2, handler);
    }

    public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
        return this.method;
    }

    public ServerCallHandler<ReqT, RespT> getServerCallHandler() {
        return this.handler;
    }

    public ServerMethodDefinition<ReqT, RespT> withServerCallHandler(ServerCallHandler<ReqT, RespT> handler) {
        return new ServerMethodDefinition<ReqT, RespT>(this.method, handler);
    }
}

