/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.Status;

abstract class PartialForwardingClientCallListener<RespT>
extends ClientCall.Listener<RespT> {
    PartialForwardingClientCallListener() {
    }

    protected abstract ClientCall.Listener<?> delegate();

    @Override
    public void onHeaders(Metadata headers) {
        this.delegate().onHeaders(headers);
    }

    @Override
    public void onClose(Status status, Metadata trailers) {
        this.delegate().onClose(status, trailers);
    }

    @Override
    public void onReady() {
        this.delegate().onReady();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

