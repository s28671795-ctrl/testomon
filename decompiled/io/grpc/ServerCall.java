/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.SecurityLevel;
import io.grpc.Status;
import javax.annotation.Nullable;

public abstract class ServerCall<ReqT, RespT> {
    public abstract void request(int var1);

    public abstract void sendHeaders(Metadata var1);

    public abstract void sendMessage(RespT var1);

    public boolean isReady() {
        return true;
    }

    public abstract void close(Status var1, Metadata var2);

    public abstract boolean isCancelled();

    public void setMessageCompression(boolean enabled) {
    }

    public void setCompression(String compressor) {
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/11021")
    public void setOnReadyThreshold(int numBytes) {
        Preconditions.checkArgument(numBytes > 0, "numBytes must be positive: %s", numBytes);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4692")
    public SecurityLevel getSecurityLevel() {
        return SecurityLevel.NONE;
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1779")
    public Attributes getAttributes() {
        return Attributes.EMPTY;
    }

    @Nullable
    public String getAuthority() {
        return null;
    }

    public abstract MethodDescriptor<ReqT, RespT> getMethodDescriptor();

    public static abstract class Listener<ReqT> {
        public void onMessage(ReqT message) {
        }

        public void onHalfClose() {
        }

        public void onCancel() {
        }

        public void onComplete() {
        }

        public void onReady() {
        }
    }
}

