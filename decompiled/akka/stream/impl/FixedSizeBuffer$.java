/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.stream.impl.FixedSizeBuffer;

@InternalApi
public final class FixedSizeBuffer$ {
    public static final FixedSizeBuffer$ MODULE$ = new FixedSizeBuffer$();

    @InternalStableApi
    public <T> FixedSizeBuffer.FixedSizeBuffer<T> apply(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size must be positive");
        }
        if ((size - 1 & size) == 0) {
            return new FixedSizeBuffer.PowerOfTwoFixedSizeBuffer(size);
        }
        return new FixedSizeBuffer.ModuloFixedSizeBuffer(size);
    }

    private FixedSizeBuffer$() {
    }
}

