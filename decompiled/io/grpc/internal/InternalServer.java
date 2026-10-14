/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.InternalChannelz;
import io.grpc.InternalInstrumented;
import io.grpc.internal.ServerListener;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface InternalServer {
    public void start(ServerListener var1) throws IOException;

    public void shutdown();

    public SocketAddress getListenSocketAddress();

    @Nullable
    public InternalInstrumented<InternalChannelz.SocketStats> getListenSocketStats();

    public List<? extends SocketAddress> getListenSocketAddresses();

    @Nullable
    public List<InternalInstrumented<InternalChannelz.SocketStats>> getListenSocketStatsList();
}

