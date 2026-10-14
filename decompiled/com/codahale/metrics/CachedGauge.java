/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Clock;
import com.codahale.metrics.Gauge;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public abstract class CachedGauge<T>
implements Gauge<T> {
    private final Clock clock;
    private final AtomicLong reloadAt;
    private final long timeoutNS;
    private volatile T value;

    protected CachedGauge(long timeout2, TimeUnit timeoutUnit) {
        this(Clock.defaultClock(), timeout2, timeoutUnit);
    }

    protected CachedGauge(Clock clock, long timeout2, TimeUnit timeoutUnit) {
        this.clock = clock;
        this.reloadAt = new AtomicLong(0L);
        this.timeoutNS = timeoutUnit.toNanos(timeout2);
    }

    protected abstract T loadValue();

    @Override
    public T getValue() {
        if (this.shouldLoad()) {
            this.value = this.loadValue();
        }
        return this.value;
    }

    private boolean shouldLoad() {
        long time;
        long current;
        do {
            time = this.clock.getTick();
            current = this.reloadAt.get();
            if (current <= time) continue;
            return false;
        } while (!this.reloadAt.compareAndSet(current, time + this.timeoutNS));
        return true;
    }
}

