/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.ClientStreamTracer;
import io.grpc.LoadBalancer;

final class PickDetailsConsumerImpl
implements LoadBalancer.PickDetailsConsumer {
    private final ClientStreamTracer[] tracers;

    public PickDetailsConsumerImpl(ClientStreamTracer[] tracers) {
        this.tracers = Preconditions.checkNotNull(tracers, "tracers");
    }

    @Override
    public void addOptionalLabel(String key, String value) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(value, "value");
        for (ClientStreamTracer tracer : this.tracers) {
            tracer.addOptionalLabel(key, value);
        }
    }
}

