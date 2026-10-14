/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.SecurityLevel;
import io.grpc.Status;
import java.util.concurrent.Executor;

public abstract class CallCredentials {
    public abstract void applyRequestMetadata(RequestInfo var1, Executor var2, MetadataApplier var3);

    @Deprecated
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1914")
    public void thisUsesUnstableApi() {
    }

    public static abstract class RequestInfo {
        public abstract MethodDescriptor<?, ?> getMethodDescriptor();

        public CallOptions getCallOptions() {
            throw new UnsupportedOperationException("Not implemented");
        }

        public abstract SecurityLevel getSecurityLevel();

        public abstract String getAuthority();

        public abstract Attributes getTransportAttrs();
    }

    public static abstract class MetadataApplier {
        public abstract void apply(Metadata var1);

        public abstract void fail(Status var1);
    }
}

