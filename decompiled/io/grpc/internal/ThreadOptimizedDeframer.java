/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.Deframer;

public interface ThreadOptimizedDeframer
extends Deframer {
    @Override
    public void request(int var1);
}

