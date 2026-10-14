/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import io.grpc.Attributes;
import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import io.grpc.SecurityLevel;
import io.grpc.ServerCall;
import io.grpc.Status;

abstract class PartialForwardingServerCall<ReqT, RespT>
extends ServerCall<ReqT, RespT> {
    PartialForwardingServerCall() {
    }

    protected abstract ServerCall<?, ?> delegate();

    @Override
    public void request(int numMessages) {
        this.delegate().request(numMessages);
    }

    @Override
    public void sendHeaders(Metadata headers) {
        this.delegate().sendHeaders(headers);
    }

    @Override
    public boolean isReady() {
        return this.delegate().isReady();
    }

    @Override
    public void close(Status status, Metadata trailers) {
        this.delegate().close(status, trailers);
    }

    @Override
    public boolean isCancelled() {
        return this.delegate().isCancelled();
    }

    @Override
    public void setMessageCompression(boolean enabled) {
        this.delegate().setMessageCompression(enabled);
    }

    @Override
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/11021")
    public void setOnReadyThreshold(int numBytes) {
        this.delegate().setOnReadyThreshold(numBytes);
    }

    @Override
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
    public void setCompression(String compressor) {
        this.delegate().setCompression(compressor);
    }

    @Override
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1779")
    public Attributes getAttributes() {
        return this.delegate().getAttributes();
    }

    @Override
    public String getAuthority() {
        return this.delegate().getAuthority();
    }

    @Override
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4692")
    public SecurityLevel getSecurityLevel() {
        return this.delegate().getSecurityLevel();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

