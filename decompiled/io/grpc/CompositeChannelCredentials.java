/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.CallCredentials;
import io.grpc.ChannelCredentials;

public final class CompositeChannelCredentials
extends ChannelCredentials {
    private final ChannelCredentials channelCredentials;
    private final CallCredentials callCredentials;

    public static ChannelCredentials create(ChannelCredentials channelCreds, CallCredentials callCreds) {
        return new CompositeChannelCredentials(channelCreds, callCreds);
    }

    private CompositeChannelCredentials(ChannelCredentials channelCreds, CallCredentials callCreds) {
        this.channelCredentials = Preconditions.checkNotNull(channelCreds, "channelCreds");
        this.callCredentials = Preconditions.checkNotNull(callCreds, "callCreds");
    }

    public ChannelCredentials getChannelCredentials() {
        return this.channelCredentials;
    }

    public CallCredentials getCallCredentials() {
        return this.callCredentials;
    }

    @Override
    public ChannelCredentials withoutBearerTokens() {
        return this.channelCredentials.withoutBearerTokens();
    }
}

