/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.ClientStreamTracer;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.InsightBuilder;
import io.grpc.internal.NoopClientStream;

public final class FailingClientStream
extends NoopClientStream {
    private boolean started;
    private final Status error;
    private final ClientStreamListener.RpcProgress rpcProgress;
    private final ClientStreamTracer[] tracers;

    public FailingClientStream(Status error, ClientStreamTracer[] tracers) {
        this(error, ClientStreamListener.RpcProgress.PROCESSED, tracers);
    }

    public FailingClientStream(Status error, ClientStreamListener.RpcProgress rpcProgress, ClientStreamTracer[] tracers) {
        Preconditions.checkArgument(!error.isOk(), "error must not be OK");
        this.error = error;
        this.rpcProgress = rpcProgress;
        this.tracers = tracers;
    }

    @Override
    public void start(ClientStreamListener listener) {
        Preconditions.checkState(!this.started, "already started");
        this.started = true;
        for (ClientStreamTracer tracer : this.tracers) {
            tracer.streamClosed(this.error);
        }
        listener.closed(this.error, this.rpcProgress, new Metadata());
    }

    @VisibleForTesting
    Status getError() {
        return this.error;
    }

    @Override
    public void appendTimeoutInsight(InsightBuilder insight) {
        insight.appendKeyValue("error", this.error).appendKeyValue("progress", (Object)this.rpcProgress);
    }
}

