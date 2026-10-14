/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Status;
import io.grpc.internal.StreamListener;

public interface ServerStreamListener
extends StreamListener {
    public void halfClosed();

    public void closed(Status var1);
}

