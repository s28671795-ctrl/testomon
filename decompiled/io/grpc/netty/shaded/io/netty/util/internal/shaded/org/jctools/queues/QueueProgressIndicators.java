/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues;

public interface QueueProgressIndicators {
    public long currentProducerIndex();

    public long currentConsumerIndex();
}

