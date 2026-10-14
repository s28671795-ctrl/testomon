/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring.core;

import java.util.Collection;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.glassfish.jersey.server.internal.monitoring.core.SlidingWindowTrimmer;
import org.glassfish.jersey.server.internal.monitoring.core.TimeReservoir;
import org.glassfish.jersey.server.internal.monitoring.core.UniformTimeSnapshot;

public abstract class AbstractSlidingWindowTimeReservoir<V>
implements TimeReservoir<V> {
    private final ConcurrentNavigableMap<Long, V> measurements;
    private final long window;
    private final AtomicLong greatestTick;
    private final AtomicLong updateCount;
    private final AtomicLong startTick;
    private final AtomicInteger trimOff;
    private final SlidingWindowTrimmer<V> trimmer;
    private final long interval;
    private final TimeUnit intervalUnit;

    public AbstractSlidingWindowTimeReservoir(long window, TimeUnit windowUnit, long startTime, TimeUnit startTimeUnit) {
        this(window, windowUnit, startTime, startTimeUnit, null);
    }

    public AbstractSlidingWindowTimeReservoir(long window, TimeUnit windowUnit, long startTime, TimeUnit startTimeUnit, SlidingWindowTrimmer<V> trimmer) {
        this.trimmer = trimmer != null ? trimmer : DefaultSlidingWindowTrimmerHolder.INSTANCE;
        this.measurements = new ConcurrentSkipListMap<Long, V>();
        this.interval = window;
        this.intervalUnit = windowUnit;
        this.window = windowUnit.toNanos(window) << 8;
        this.startTick = new AtomicLong(this.tick(startTime, startTimeUnit));
        this.greatestTick = new AtomicLong(this.startTick.get());
        this.updateCount = new AtomicLong(0L);
        this.trimOff = new AtomicInteger(0);
        this.trimmer.setTimeReservoir(this);
    }

    @Override
    public int size(long time, TimeUnit timeUnit) {
        this.conditionallyUpdateGreatestTick(this.tick(time, timeUnit));
        this.trim();
        return this.measurements.size();
    }

    @Override
    public void update(V value, long time, TimeUnit timeUnit) {
        if (this.updateCount.incrementAndGet() % 256L == 0L) {
            this.trim();
        }
        long tick = this.tick(time, timeUnit);
        for (int i = 0; i < 256; ++i) {
            if (this.measurements.putIfAbsent(tick, value) == null) {
                this.conditionallyUpdateGreatestTick(tick);
                return;
            }
            ++tick;
        }
    }

    @Override
    public long interval(TimeUnit timeUnit) {
        return timeUnit.convert(this.interval, this.intervalUnit);
    }

    private long conditionallyUpdateGreatestTick(long tick) {
        long currentGreatestTick;
        do {
            if (tick > (currentGreatestTick = this.greatestTick.get())) continue;
            return currentGreatestTick;
        } while (!this.greatestTick.compareAndSet(currentGreatestTick, tick));
        return tick;
    }

    private void conditionallyUpdateStartTick(Map.Entry<Long, V> firstEntry) {
        Long firstEntryKey;
        Long l = firstEntryKey = firstEntry != null ? firstEntry.getKey() : null;
        if (firstEntryKey != null && firstEntryKey < this.startTick.get()) {
            long expectedStartTick;
            while (!this.startTick.compareAndSet(expectedStartTick = this.startTick.get(), firstEntryKey)) {
            }
            return;
        }
    }

    protected abstract UniformTimeSnapshot snapshot(Collection<V> var1, long var2, TimeUnit var4, long var5, TimeUnit var7);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public UniformTimeSnapshot getSnapshot(long time, TimeUnit timeUnit) {
        this.trimOff.incrementAndGet();
        long baselineTick = this.conditionallyUpdateGreatestTick(this.tick(time, timeUnit));
        try {
            NavigableMap windowMap = this.measurements.subMap((Object)(this.roundTick(baselineTick) - this.window), true, (Object)baselineTick, true);
            this.conditionallyUpdateStartTick(windowMap.firstEntry());
            long measuredTickInterval = Math.min(baselineTick - this.startTick.get(), this.window);
            UniformTimeSnapshot uniformTimeSnapshot = this.snapshot(windowMap.values(), measuredTickInterval >> 8, TimeUnit.NANOSECONDS, time, timeUnit);
            return uniformTimeSnapshot;
        }
        finally {
            this.trimOff.decrementAndGet();
            this.trim(baselineTick);
        }
    }

    private long tick(long time, TimeUnit timeUnit) {
        return timeUnit.toNanos(time) << 8;
    }

    private void trim() {
        this.trim(this.greatestTick.get());
    }

    private void trim(long baselineTick) {
        if (this.trimEnabled()) {
            long key = this.roundTick(baselineTick) - this.window;
            this.trimmer.trim(this.measurements, key);
        }
    }

    private boolean trimEnabled() {
        return this.trimOff.get() == 0;
    }

    private long roundTick(long tick) {
        return tick >> 8 << 8;
    }

    private static final class DefaultSlidingWindowTrimmerHolder {
        static final SlidingWindowTrimmer<Object> INSTANCE = new SlidingWindowTrimmer<Object>(){

            @Override
            public void trim(ConcurrentNavigableMap<Long, Object> map, long key) {
                map.headMap((Object)key).clear();
            }

            @Override
            public void setTimeReservoir(TimeReservoir<Object> reservoir) {
            }
        };

        private DefaultSlidingWindowTrimmerHolder() {
        }
    }
}

