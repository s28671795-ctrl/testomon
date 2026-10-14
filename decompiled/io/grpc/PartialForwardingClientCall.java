/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import io.grpc.Attributes;
import io.grpc.ClientCall;
import javax.annotation.Nullable;

abstract class PartialForwardingClientCall<ReqT, RespT>
extends ClientCall<ReqT, RespT> {
    PartialForwardingClientCall() {
    }

    protected abstract ClientCall<?, ?> delegate();

    @Override
    public void request(int numMessages) {
        this.delegate().request(numMessages);
    }

    @Override
    public void cancel(@Nullable String message, @Nullable Throwable cause) {
        this.delegate().cancel(message, cause);
    }

    @Override
    public void halfClose() {
        this.delegate().halfClose();
    }

    @Override
    public void setMessageCompression(boolean enabled) {
        this.delegate().setMessageCompression(enabled);
    }

    @Override
    public boolean isReady() {
        return this.delegate().isReady();
    }

    @Override
    public Attributes getAttributes() {
        return this.delegate().getAttributes();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

