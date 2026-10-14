/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.CallOptions;
import io.grpc.LoadBalancer;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

public final class PickSubchannelArgsImpl
extends LoadBalancer.PickSubchannelArgs {
    private final CallOptions callOptions;
    private final Metadata headers;
    private final MethodDescriptor<?, ?> method;
    private final LoadBalancer.PickDetailsConsumer pickDetailsConsumer;

    public PickSubchannelArgsImpl(MethodDescriptor<?, ?> method2, Metadata headers, CallOptions callOptions, LoadBalancer.PickDetailsConsumer pickDetailsConsumer) {
        this.method = Preconditions.checkNotNull(method2, "method");
        this.headers = Preconditions.checkNotNull(headers, "headers");
        this.callOptions = Preconditions.checkNotNull(callOptions, "callOptions");
        this.pickDetailsConsumer = Preconditions.checkNotNull(pickDetailsConsumer, "pickDetailsConsumer");
    }

    @Override
    public Metadata getHeaders() {
        return this.headers;
    }

    @Override
    public CallOptions getCallOptions() {
        return this.callOptions;
    }

    @Override
    public MethodDescriptor<?, ?> getMethodDescriptor() {
        return this.method;
    }

    @Override
    public LoadBalancer.PickDetailsConsumer getPickDetailsConsumer() {
        return this.pickDetailsConsumer;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PickSubchannelArgsImpl that = (PickSubchannelArgsImpl)o;
        return Objects.equal(this.callOptions, that.callOptions) && Objects.equal(this.headers, that.headers) && Objects.equal(this.method, that.method) && Objects.equal(this.pickDetailsConsumer, that.pickDetailsConsumer);
    }

    public int hashCode() {
        return Objects.hashCode(this.callOptions, this.headers, this.method, this.pickDetailsConsumer);
    }

    public final String toString() {
        return "[method=" + this.method + " headers=" + this.headers + " callOptions=" + this.callOptions + "]";
    }
}

