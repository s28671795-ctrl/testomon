/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import io.grpc.ServerCall;

abstract class PartialForwardingServerCallListener<ReqT>
extends ServerCall.Listener<ReqT> {
    PartialForwardingServerCallListener() {
    }

    protected abstract ServerCall.Listener<?> delegate();

    @Override
    public void onHalfClose() {
        this.delegate().onHalfClose();
    }

    @Override
    public void onCancel() {
        this.delegate().onCancel();
    }

    @Override
    public void onComplete() {
        this.delegate().onComplete();
    }

    @Override
    public void onReady() {
        this.delegate().onReady();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

