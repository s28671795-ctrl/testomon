/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ClientCall;
import io.grpc.PartialForwardingClientCallListener;

public abstract class ForwardingClientCallListener<RespT>
extends PartialForwardingClientCallListener<RespT> {
    @Override
    protected abstract ClientCall.Listener<RespT> delegate();

    @Override
    public void onMessage(RespT message) {
        this.delegate().onMessage(message);
    }

    public static abstract class SimpleForwardingClientCallListener<RespT>
    extends ForwardingClientCallListener<RespT> {
        private final ClientCall.Listener<RespT> delegate;

        protected SimpleForwardingClientCallListener(ClientCall.Listener<RespT> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected ClientCall.Listener<RespT> delegate() {
            return this.delegate;
        }
    }
}

