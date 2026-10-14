/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ChannelCredentials;
import io.grpc.Internal;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ManagedChannelProvider;

@Internal
public final class InternalManagedChannelProvider {
    private InternalManagedChannelProvider() {
    }

    public static boolean isAvailable(ManagedChannelProvider provider) {
        return provider.isAvailable();
    }

    public static ManagedChannelBuilder<?> builderForAddress(ManagedChannelProvider provider, String name, int port) {
        return provider.builderForAddress(name, port);
    }

    public static ManagedChannelBuilder<?> builderForTarget(ManagedChannelProvider provider, String target) {
        return provider.builderForTarget(target);
    }

    public static ManagedChannelProvider.NewChannelBuilderResult newChannelBuilder(ManagedChannelProvider provider, String target, ChannelCredentials creds) {
        return provider.newChannelBuilder(target, creds);
    }
}

