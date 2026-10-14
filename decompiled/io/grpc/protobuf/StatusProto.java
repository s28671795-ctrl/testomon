/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.protobuf;

import com.google.common.base.Preconditions;
import com.google.rpc.Status;
import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.lite.ProtoLiteUtils;
import javax.annotation.Nullable;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4695")
public final class StatusProto {
    private static final Metadata.Key<com.google.rpc.Status> STATUS_DETAILS_KEY = Metadata.Key.of("grpc-status-details-bin", ProtoLiteUtils.metadataMarshaller(com.google.rpc.Status.getDefaultInstance()));

    private StatusProto() {
    }

    public static StatusRuntimeException toStatusRuntimeException(com.google.rpc.Status statusProto) {
        return StatusProto.toStatus(statusProto).asRuntimeException(StatusProto.toMetadata(statusProto));
    }

    public static StatusRuntimeException toStatusRuntimeException(com.google.rpc.Status statusProto, Metadata metadata) {
        return StatusProto.toStatus(statusProto).asRuntimeException(StatusProto.toMetadata(statusProto, metadata));
    }

    public static StatusException toStatusException(com.google.rpc.Status statusProto) {
        return StatusProto.toStatus(statusProto).asException(StatusProto.toMetadata(statusProto));
    }

    public static StatusException toStatusException(com.google.rpc.Status statusProto, Metadata metadata) {
        return StatusProto.toStatus(statusProto).asException(StatusProto.toMetadata(statusProto, metadata));
    }

    public static StatusException toStatusException(com.google.rpc.Status statusProto, Metadata metadata, Throwable cause) {
        return StatusProto.toStatus(statusProto).withCause(cause).asException(StatusProto.toMetadata(statusProto, metadata));
    }

    private static Status toStatus(com.google.rpc.Status statusProto) {
        Status status = Status.fromCodeValue(statusProto.getCode());
        Preconditions.checkArgument(status.getCode().value() == statusProto.getCode(), "invalid status code");
        return status.withDescription(statusProto.getMessage());
    }

    private static Metadata toMetadata(com.google.rpc.Status statusProto) {
        Metadata metadata = new Metadata();
        metadata.put(STATUS_DETAILS_KEY, statusProto);
        return metadata;
    }

    private static Metadata toMetadata(com.google.rpc.Status statusProto, Metadata metadata) {
        Preconditions.checkNotNull(metadata, "metadata must not be null");
        metadata.discardAll(STATUS_DETAILS_KEY);
        metadata.put(STATUS_DETAILS_KEY, statusProto);
        return metadata;
    }

    @Nullable
    public static com.google.rpc.Status fromThrowable(Throwable t2) {
        for (Throwable cause = Preconditions.checkNotNull(t2, "t"); cause != null; cause = cause.getCause()) {
            if (cause instanceof StatusException) {
                StatusException e = (StatusException)cause;
                return StatusProto.fromStatusAndTrailers(e.getStatus(), e.getTrailers());
            }
            if (!(cause instanceof StatusRuntimeException)) continue;
            StatusRuntimeException e = (StatusRuntimeException)cause;
            return StatusProto.fromStatusAndTrailers(e.getStatus(), e.getTrailers());
        }
        return null;
    }

    public static com.google.rpc.Status fromStatusAndTrailers(Status status, @Nullable Metadata trailers) {
        com.google.rpc.Status statusProto;
        Preconditions.checkNotNull(status, "status");
        if (trailers != null && (statusProto = trailers.get(STATUS_DETAILS_KEY)) != null) {
            Preconditions.checkArgument(status.getCode().value() == statusProto.getCode(), "com.google.rpc.Status code must match gRPC status code");
            return statusProto;
        }
        Status.Builder statusBuilder = com.google.rpc.Status.newBuilder().setCode(status.getCode().value());
        if (status.getDescription() != null) {
            statusBuilder.setMessage(status.getDescription());
        }
        return statusBuilder.build();
    }
}

