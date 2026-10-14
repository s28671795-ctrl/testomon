/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import java.security.Provider;

final class UnhelpfulSecurityProvider
extends Provider {
    private static final long serialVersionUID = 0L;

    public UnhelpfulSecurityProvider() {
        super("UnhelpfulSecurityProvider", 0.0, "A Provider that provides nothing");
    }
}

