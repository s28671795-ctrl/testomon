/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.PartialForwardingServerCallListener;
import io.grpc.ServerCall;

public abstract class ForwardingServerCallListener<ReqT>
extends PartialForwardingServerCallListener<ReqT> {
    @Override
    protected abstract ServerCall.Listener<ReqT> delegate();

    @Override
    public void onMessage(ReqT message) {
        this.delegate().onMessage(message);
    }

    public static abstract class SimpleForwardingServerCallListener<ReqT>
    extends ForwardingServerCallListener<ReqT> {
        private final ServerCall.Listener<ReqT> delegate;

        protected SimpleForwardingServerCallListener(ServerCall.Listener<ReqT> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected ServerCall.Listener<ReqT> delegate() {
            return this.delegate;
        }
    }
}

