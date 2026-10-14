/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Clock;
import com.codahale.metrics.Reservoir;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.UniformSnapshot;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SlidingTimeWindowReservoir
implements Reservoir {
    private static final int COLLISION_BUFFER = 256;
    private static final int TRIM_THRESHOLD = 256;
    private final Clock clock;
    private final ConcurrentSkipListMap<Long, Long> measurements;
    private final long window;
    private final AtomicLong lastTick;
    private final AtomicLong count;

    public SlidingTimeWindowReservoir(long window, TimeUnit windowUnit) {
        this(window, windowUnit, Clock.defaultClock());
    }

    public SlidingTimeWindowReservoir(long window, TimeUnit windowUnit, Clock clock) {
        this.clock = clock;
        this.measurements = new ConcurrentSkipListMap();
        this.window = windowUnit.toNanos(window) * 256L;
        this.lastTick = new AtomicLong(clock.getTick() * 256L);
        this.count = new AtomicLong();
    }

    @Override
    public int size() {
        this.trim();
        return this.measurements.size();
    }

    @Override
    public void update(long value) {
        if (this.count.incrementAndGet() % 256L == 0L) {
            this.trim();
        }
        this.measurements.put(this.getTick(), value);
    }

    @Override
    public Snapshot getSnapshot() {
        this.trim();
        return new UniformSnapshot(this.measurements.values());
    }

    private long getTick() {
        long tick;
        long newTick;
        long oldTick;
        while (!this.lastTick.compareAndSet(oldTick = this.lastTick.get(), newTick = (tick = this.clock.getTick() * 256L) - oldTick > 0L ? tick : oldTick + 1L)) {
        }
        return newTick;
    }

    private void trim() {
        this.measurements.headMap((Object)(this.getTick() - this.window)).clear();
    }
}

