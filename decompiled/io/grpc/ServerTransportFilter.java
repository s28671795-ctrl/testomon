/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Attributes;
import io.grpc.ExperimentalApi;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2132")
public abstract class ServerTransportFilter {
    public Attributes transportReady(Attributes transportAttrs) {
        return transportAttrs;
    }

    public void transportTerminated(Attributes transportAttrs) {
    }
}

