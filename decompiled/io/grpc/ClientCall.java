/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Attributes;
import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import io.grpc.Status;
import javax.annotation.Nullable;

public abstract class ClientCall<ReqT, RespT> {
    public abstract void start(Listener<RespT> var1, Metadata var2);

    public abstract void request(int var1);

    public abstract void cancel(@Nullable String var1, @Nullable Throwable var2);

    public abstract void halfClose();

    public abstract void sendMessage(ReqT var1);

    public boolean isReady() {
        return true;
    }

    public void setMessageCompression(boolean enabled) {
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2607")
    public Attributes getAttributes() {
        return Attributes.EMPTY;
    }

    public static abstract class Listener<T> {
        public void onHeaders(Metadata headers) {
        }

        public void onMessage(T message) {
        }

        public void onClose(Status status, Metadata trailers) {
        }

        public void onReady() {
        }
    }
}

