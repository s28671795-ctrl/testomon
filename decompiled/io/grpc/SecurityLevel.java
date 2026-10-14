/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4692")
public enum SecurityLevel {
    NONE,
    INTEGRITY,
    PRIVACY_AND_INTEGRITY;

}

