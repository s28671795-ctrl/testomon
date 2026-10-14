/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.CallOptions;
import io.grpc.ClientStreamTracer;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.FailingClientStream;
import java.util.concurrent.Executor;

class FailingClientTransport
implements ClientTransport {
    @VisibleForTesting
    final Status error;
    private final ClientStreamListener.RpcProgress rpcProgress;

    FailingClientTransport(Status error, ClientStreamListener.RpcProgress rpcProgress) {
        Preconditions.checkArgument(!error.isOk(), "error must not be OK");
        this.error = error;
        this.rpcProgress = rpcProgress;
    }

    @Override
    public ClientStream newStream(MethodDescriptor<?, ?> method2, Metadata headers, CallOptions callOptions, ClientStreamTracer[] tracers) {
        return new FailingClientStream(this.error, this.rpcProgress, tracers);
    }

    @Override
    public void ping(final ClientTransport.PingCallback callback, Executor executor2) {
        executor2.execute(new Runnable(){

            @Override
            public void run() {
                callback.onFailure(FailingClientTransport.this.error.asException());
            }
        });
    }

    @Override
    public ListenableFuture<InternalChannelz.SocketStats> getStats() {
        SettableFuture<InternalChannelz.SocketStats> ret = SettableFuture.create();
        ret.set(null);
        return ret;
    }

    @Override
    public InternalLogId getLogId() {
        throw new UnsupportedOperationException("Not a real transport");
    }
}

