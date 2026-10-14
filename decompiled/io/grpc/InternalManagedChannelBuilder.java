/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Internal;
import io.grpc.ManagedChannelBuilder;
import io.grpc.MetricSink;

@Internal
public final class InternalManagedChannelBuilder {
    private InternalManagedChannelBuilder() {
    }

    public static <T extends ManagedChannelBuilder<T>> T interceptWithTarget(ManagedChannelBuilder<T> builder, InternalInterceptorFactory factory2) {
        return builder.interceptWithTarget(factory2);
    }

    public static <T extends ManagedChannelBuilder<T>> T addMetricSink(ManagedChannelBuilder<T> builder, MetricSink metricSink) {
        return builder.addMetricSink(metricSink);
    }

    public static interface InternalInterceptorFactory
    extends ManagedChannelBuilder.InterceptorFactory {
    }
}

