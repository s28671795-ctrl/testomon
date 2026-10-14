/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.MethodDescriptor;
import io.grpc.PartialForwardingServerCall;
import io.grpc.ServerCall;

public abstract class ForwardingServerCall<ReqT, RespT>
extends PartialForwardingServerCall<ReqT, RespT> {
    @Override
    protected abstract ServerCall<ReqT, RespT> delegate();

    @Override
    public void sendMessage(RespT message) {
        this.delegate().sendMessage(message);
    }

    @Override
    public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
        return this.delegate().getMethodDescriptor();
    }

    public static abstract class SimpleForwardingServerCall<ReqT, RespT>
    extends ForwardingServerCall<ReqT, RespT> {
        private final ServerCall<ReqT, RespT> delegate;

        protected SimpleForwardingServerCall(ServerCall<ReqT, RespT> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected ServerCall<ReqT, RespT> delegate() {
            return this.delegate;
        }
    }
}

