/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util.internal;

import io.grpc.netty.shaded.io.netty.util.internal.TypeParameterMatcher;

public final class NoOpTypeParameterMatcher
extends TypeParameterMatcher {
    @Override
    public boolean match(Object msg) {
        return true;
    }
}

