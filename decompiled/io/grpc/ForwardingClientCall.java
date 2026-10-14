/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.PartialForwardingClientCall;

public abstract class ForwardingClientCall<ReqT, RespT>
extends PartialForwardingClientCall<ReqT, RespT> {
    @Override
    protected abstract ClientCall<ReqT, RespT> delegate();

    @Override
    public void start(ClientCall.Listener<RespT> responseListener, Metadata headers) {
        this.delegate().start(responseListener, headers);
    }

    @Override
    public void sendMessage(ReqT message) {
        this.delegate().sendMessage(message);
    }

    public static abstract class SimpleForwardingClientCall<ReqT, RespT>
    extends ForwardingClientCall<ReqT, RespT> {
        private final ClientCall<ReqT, RespT> delegate;

        protected SimpleForwardingClientCall(ClientCall<ReqT, RespT> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected ClientCall<ReqT, RespT> delegate() {
            return this.delegate;
        }
    }
}

