/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.StreamListener;

public interface ClientStreamListener
extends StreamListener {
    public void headersRead(Metadata var1);

    public void closed(Status var1, RpcProgress var2, Metadata var3);

    public static enum RpcProgress {
        PROCESSED,
        REFUSED,
        DROPPED,
        MISCARRIED;

    }
}

