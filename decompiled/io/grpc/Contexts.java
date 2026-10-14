/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.Context;
import io.grpc.ExperimentalApi;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.Status;
import java.util.concurrent.TimeoutException;

public final class Contexts {
    private Contexts() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(Context context, ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        Context previous = context.attach();
        try {
            ContextualizedServerCallListener<ReqT> contextualizedServerCallListener = new ContextualizedServerCallListener<ReqT>(next.startCall(call, headers), context);
            return contextualizedServerCallListener;
        }
        finally {
            context.detach(previous);
        }
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1975")
    public static Status statusFromCancelled(Context context) {
        Preconditions.checkNotNull(context, "context must not be null");
        if (!context.isCancelled()) {
            return null;
        }
        Throwable cancellationCause = context.cancellationCause();
        if (cancellationCause == null) {
            return Status.CANCELLED.withDescription("io.grpc.Context was cancelled without error");
        }
        if (cancellationCause instanceof TimeoutException) {
            return Status.DEADLINE_EXCEEDED.withDescription(cancellationCause.getMessage()).withCause(cancellationCause);
        }
        Status status = Status.fromThrowable(cancellationCause);
        if (Status.Code.UNKNOWN.equals((Object)status.getCode()) && status.getCause() == cancellationCause) {
            return Status.CANCELLED.withDescription("Context cancelled").withCause(cancellationCause);
        }
        return status.withCause(cancellationCause);
    }

    private static class ContextualizedServerCallListener<ReqT>
    extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {
        private final Context context;

        public ContextualizedServerCallListener(ServerCall.Listener<ReqT> delegate, Context context) {
            super(delegate);
            this.context = context;
        }

        @Override
        public void onMessage(ReqT message) {
            Context previous = this.context.attach();
            try {
                super.onMessage(message);
            }
            finally {
                this.context.detach(previous);
            }
        }

        @Override
        public void onHalfClose() {
            Context previous = this.context.attach();
            try {
                super.onHalfClose();
            }
            finally {
                this.context.detach(previous);
            }
        }

        @Override
        public void onCancel() {
            Context previous = this.context.attach();
            try {
                super.onCancel();
            }
            finally {
                this.context.detach(previous);
            }
        }

        @Override
        public void onComplete() {
            Context previous = this.context.attach();
            try {
                super.onComplete();
            }
            finally {
                this.context.detach(previous);
            }
        }

        @Override
        public void onReady() {
            Context previous = this.context.attach();
            try {
                super.onReady();
            }
            finally {
                this.context.detach(previous);
            }
        }
    }
}

