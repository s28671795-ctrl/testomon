/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Attributes;
import io.grpc.Metadata;
import io.grpc.internal.ServerStream;

public interface ServerTransportListener {
    public void streamCreated(ServerStream var1, String var2, Metadata var3);

    public Attributes transportReady(Attributes var1);

    public void transportTerminated();
}

