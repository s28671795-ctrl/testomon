/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Attributes;
import io.grpc.Decompressor;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.ServerStreamListener;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.Stream;
import javax.annotation.Nullable;

public interface ServerStream
extends Stream {
    public void writeHeaders(Metadata var1, boolean var2);

    public void close(Status var1, Metadata var2);

    public void cancel(Status var1);

    public void setDecompressor(Decompressor var1);

    public Attributes getAttributes();

    @Nullable
    public String getAuthority();

    public void setListener(ServerStreamListener var1);

    public StatsTraceContext statsTraceContext();

    public int streamId();

    public void setOnReadyThreshold(int var1);
}

