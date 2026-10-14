/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ChannelCredentials;

public final class InsecureChannelCredentials
extends ChannelCredentials {
    public static ChannelCredentials create() {
        return new InsecureChannelCredentials();
    }

    private InsecureChannelCredentials() {
    }

    @Override
    public ChannelCredentials withoutBearerTokens() {
        return this;
    }
}

