/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.InternalChannelz;
import io.grpc.InternalInstrumented;
import io.grpc.Status;
import java.util.concurrent.ScheduledExecutorService;

public interface ServerTransport
extends InternalInstrumented<InternalChannelz.SocketStats> {
    public void shutdown();

    public void shutdownNow(Status var1);

    public ScheduledExecutorService getScheduledExecutorService();
}

