/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Clock;
import com.codahale.metrics.EWMA;
import com.codahale.metrics.LongAdder;
import com.codahale.metrics.Metered;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Meter
implements Metered {
    private static final long TICK_INTERVAL = TimeUnit.SECONDS.toNanos(5L);
    private final EWMA m1Rate = EWMA.oneMinuteEWMA();
    private final EWMA m5Rate = EWMA.fiveMinuteEWMA();
    private final EWMA m15Rate = EWMA.fifteenMinuteEWMA();
    private final LongAdder count = new LongAdder();
    private final long startTime;
    private final AtomicLong lastTick;
    private final Clock clock;

    public Meter() {
        this(Clock.defaultClock());
    }

    public Meter(Clock clock) {
        this.clock = clock;
        this.startTime = this.clock.getTick();
        this.lastTick = new AtomicLong(this.startTime);
    }

    public void mark() {
        this.mark(1L);
    }

    public void mark(long n) {
        this.tickIfNecessary();
        this.count.add(n);
        this.m1Rate.update(n);
        this.m5Rate.update(n);
        this.m15Rate.update(n);
    }

    private void tickIfNecessary() {
        long newIntervalStartTick;
        long oldTick = this.lastTick.get();
        long newTick = this.clock.getTick();
        long age = newTick - oldTick;
        if (age > TICK_INTERVAL && this.lastTick.compareAndSet(oldTick, newIntervalStartTick = newTick - age % TICK_INTERVAL)) {
            long requiredTicks = age / TICK_INTERVAL;
            for (long i = 0L; i < requiredTicks; ++i) {
                this.m1Rate.tick();
                this.m5Rate.tick();
                this.m15Rate.tick();
            }
        }
    }

    @Override
    public long getCount() {
        return this.count.sum();
    }

    @Override
    public double getFifteenMinuteRate() {
        this.tickIfNecessary();
        return this.m15Rate.getRate(TimeUnit.SECONDS);
    }

    @Override
    public double getFiveMinuteRate() {
        this.tickIfNecessary();
        return this.m5Rate.getRate(TimeUnit.SECONDS);
    }

    @Override
    public double getMeanRate() {
        if (this.getCount() == 0L) {
            return 0.0;
        }
        double elapsed = this.clock.getTick() - this.startTime;
        return (double)this.getCount() / elapsed * (double)TimeUnit.SECONDS.toNanos(1L);
    }

    @Override
    public double getOneMinuteRate() {
        this.tickIfNecessary();
        return this.m1Rate.getRate(TimeUnit.SECONDS);
    }
}

