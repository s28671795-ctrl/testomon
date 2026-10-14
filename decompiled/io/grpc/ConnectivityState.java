/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4359")
public enum ConnectivityState {
    CONNECTING,
    READY,
    TRANSIENT_FAILURE,
    IDLE,
    SHUTDOWN;

}

