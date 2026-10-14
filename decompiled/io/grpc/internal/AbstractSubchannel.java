/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.InternalChannelz;
import io.grpc.InternalInstrumented;
import io.grpc.LoadBalancer;

abstract class AbstractSubchannel
extends LoadBalancer.Subchannel {
    AbstractSubchannel() {
    }

    @VisibleForTesting
    abstract InternalInstrumented<InternalChannelz.ChannelStats> getInstrumentedInternalSubchannel();
}

