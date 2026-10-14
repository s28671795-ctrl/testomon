/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.ClientTransport;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
interface TransportProvider {
    @Nullable
    public ClientTransport obtainActiveTransport();
}

