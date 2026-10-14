/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.MethodDescriptor;
import java.util.concurrent.TimeUnit;

abstract class ForwardingManagedChannel
extends ManagedChannel {
    private final ManagedChannel delegate;

    ForwardingManagedChannel(ManagedChannel delegate) {
        this.delegate = delegate;
    }

    @Override
    public ManagedChannel shutdown() {
        return this.delegate.shutdown();
    }

    @Override
    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    @Override
    public ManagedChannel shutdownNow() {
        return this.delegate.shutdownNow();
    }

    @Override
    public boolean awaitTermination(long timeout2, TimeUnit unit) throws InterruptedException {
        return this.delegate.awaitTermination(timeout2, unit);
    }

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        return this.delegate.newCall(methodDescriptor, callOptions);
    }

    @Override
    public String authority() {
        return this.delegate.authority();
    }

    @Override
    public ConnectivityState getState(boolean requestConnection) {
        return this.delegate.getState(requestConnection);
    }

    @Override
    public void notifyWhenStateChanged(ConnectivityState source2, Runnable callback) {
        this.delegate.notifyWhenStateChanged(source2, callback);
    }

    @Override
    public void resetConnectBackoff() {
        this.delegate.resetConnectBackoff();
    }

    @Override
    public void enterIdle() {
        this.delegate.enterIdle();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate).toString();
    }
}

