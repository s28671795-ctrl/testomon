/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ProxiedSocketAddress;
import java.io.IOException;
import java.net.SocketAddress;
import javax.annotation.Nullable;

public interface ProxyDetector {
    @Nullable
    public ProxiedSocketAddress proxyFor(SocketAddress var1) throws IOException;
}

