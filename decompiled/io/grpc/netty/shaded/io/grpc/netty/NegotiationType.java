/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.ExperimentalApi;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1784")
public enum NegotiationType {
    TLS,
    PLAINTEXT_UPGRADE,
    PLAINTEXT;

}

