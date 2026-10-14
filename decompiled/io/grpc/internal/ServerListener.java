/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.ServerTransport;
import io.grpc.internal.ServerTransportListener;

public interface ServerListener {
    public ServerTransportListener transportCreated(ServerTransport var1);

    public void serverShutdown();
}

