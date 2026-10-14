/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ChannelCredentials;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ChoiceChannelCredentials
extends ChannelCredentials {
    private final List<ChannelCredentials> creds;

    public static ChannelCredentials create(ChannelCredentials ... creds) {
        if (creds.length == 0) {
            throw new IllegalArgumentException("At least one credential is required");
        }
        for (ChannelCredentials cred : creds) {
            if (cred != null) continue;
            throw new NullPointerException();
        }
        return new ChoiceChannelCredentials(Collections.unmodifiableList(new ArrayList<ChannelCredentials>(Arrays.asList(creds))));
    }

    private ChoiceChannelCredentials(List<ChannelCredentials> creds) {
        this.creds = creds;
    }

    public List<ChannelCredentials> getCredentialsList() {
        return this.creds;
    }

    @Override
    public ChannelCredentials withoutBearerTokens() {
        ArrayList<ChannelCredentials> credsWithoutTokens = new ArrayList<ChannelCredentials>();
        for (ChannelCredentials cred : this.creds) {
            credsWithoutTokens.add(cred.withoutBearerTokens());
        }
        return new ChoiceChannelCredentials(Collections.unmodifiableList(credsWithoutTokens));
    }
}

