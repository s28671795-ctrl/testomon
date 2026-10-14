/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Attributes;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.Status;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.InsightBuilder;
import io.grpc.internal.Stream;
import javax.annotation.Nonnull;

public interface ClientStream
extends Stream {
    public void cancel(Status var1);

    public void halfClose();

    public void setAuthority(String var1);

    public void setFullStreamDecompression(boolean var1);

    public void setDecompressorRegistry(DecompressorRegistry var1);

    public void start(ClientStreamListener var1);

    public void setMaxInboundMessageSize(int var1);

    public void setMaxOutboundMessageSize(int var1);

    public void setDeadline(@Nonnull Deadline var1);

    public Attributes getAttributes();

    public void appendTimeoutInsight(InsightBuilder var1);
}

