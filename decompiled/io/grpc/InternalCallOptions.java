/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.CallOptions;
import io.grpc.Internal;

@Internal
public final class InternalCallOptions {
    private InternalCallOptions() {
    }

    public static Boolean getWaitForReady(CallOptions callOptions) {
        return callOptions.getWaitForReady();
    }
}

