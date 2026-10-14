/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Internal;
import io.grpc.ServerBuilder;
import io.grpc.ServerCredentials;
import io.grpc.ServerProvider;

@Internal
public final class InternalServerProvider {
    private InternalServerProvider() {
    }

    public static ServerBuilder<?> builderForPort(ServerProvider provider, int port) {
        return provider.builderForPort(port);
    }

    public static ServerProvider.NewServerBuilderResult newServerBuilderForPort(ServerProvider provider, int port, ServerCredentials creds) {
        return provider.newServerBuilderForPort(port, creds);
    }
}

