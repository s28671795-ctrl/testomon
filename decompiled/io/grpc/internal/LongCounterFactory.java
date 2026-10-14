/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.AtomicLongCounter;
import io.grpc.internal.LongCounter;
import io.grpc.internal.ReflectionLongAdderCounter;

final class LongCounterFactory {
    LongCounterFactory() {
    }

    public static LongCounter create() {
        if (ReflectionLongAdderCounter.isAvailable()) {
            return new ReflectionLongAdderCounter();
        }
        return new AtomicLongCounter();
    }
}

