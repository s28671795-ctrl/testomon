/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ClientStreamTracer;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.ManagedClientTransport;
import java.util.concurrent.Executor;

abstract class ForwardingConnectionClientTransport
implements ConnectionClientTransport {
    ForwardingConnectionClientTransport() {
    }

    @Override
    public Runnable start(ManagedClientTransport.Listener listener) {
        return this.delegate().start(listener);
    }

    @Override
    public void shutdown(Status status) {
        this.delegate().shutdown(status);
    }

    @Override
    public void shutdownNow(Status status) {
        this.delegate().shutdownNow(status);
    }

    @Override
    public ClientStream newStream(MethodDescriptor<?, ?> method2, Metadata headers, CallOptions callOptions, ClientStreamTracer[] tracers) {
        return this.delegate().newStream(method2, headers, callOptions, tracers);
    }

    @Override
    public void ping(ClientTransport.PingCallback callback, Executor executor2) {
        this.delegate().ping(callback, executor2);
    }

    @Override
    public InternalLogId getLogId() {
        return this.delegate().getLogId();
    }

    @Override
    public Attributes getAttributes() {
        return this.delegate().getAttributes();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }

    @Override
    public ListenableFuture<InternalChannelz.SocketStats> getStats() {
        return this.delegate().getStats();
    }

    protected abstract ConnectionClientTransport delegate();
}

