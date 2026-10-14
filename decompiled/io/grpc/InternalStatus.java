/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Internal;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import javax.annotation.Nullable;

@Internal
public final class InternalStatus {
    @Internal
    public static final Metadata.Key<String> MESSAGE_KEY = Status.MESSAGE_KEY;
    @Internal
    public static final Metadata.Key<Status> CODE_KEY = Status.CODE_KEY;

    private InternalStatus() {
    }

    @Internal
    public static final StatusRuntimeException asRuntimeException(Status status, @Nullable Metadata trailers, boolean fillInStackTrace) {
        return new StatusRuntimeException(status, trailers, fillInStackTrace);
    }
}

