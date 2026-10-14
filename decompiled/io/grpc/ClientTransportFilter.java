/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Attributes;
import io.grpc.ExperimentalApi;

@ExperimentalApi(value="https://gitub.com/grpc/grpc-java/issues/10652")
public abstract class ClientTransportFilter {
    public Attributes transportReady(Attributes transportAttrs) {
        return transportAttrs;
    }

    public void transportTerminated(Attributes transportAttrs) {
    }
}

