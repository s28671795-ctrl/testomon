/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.LongCounter;
import java.util.concurrent.atomic.AtomicLong;

final class AtomicLongCounter
implements LongCounter {
    private final AtomicLong counter = new AtomicLong();

    AtomicLongCounter() {
    }

    @Override
    public void add(long delta) {
        this.counter.getAndAdd(delta);
    }

    @Override
    public long value() {
        return this.counter.get();
    }
}

