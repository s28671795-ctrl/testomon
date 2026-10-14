/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ServerCredentials;

public final class InsecureServerCredentials
extends ServerCredentials {
    public static ServerCredentials create() {
        return new InsecureServerCredentials();
    }

    private InsecureServerCredentials() {
    }
}

