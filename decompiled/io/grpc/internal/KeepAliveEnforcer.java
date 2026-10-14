/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckReturnValue;

public final class KeepAliveEnforcer {
    @VisibleForTesting
    public static final int MAX_PING_STRIKES = 2;
    @VisibleForTesting
    public static final long IMPLICIT_PERMIT_TIME_NANOS = TimeUnit.HOURS.toNanos(2L);
    private final boolean permitWithoutCalls;
    private final long minTimeNanos;
    private final Ticker ticker;
    private final long epoch;
    private long lastValidPingTime;
    private boolean hasOutstandingCalls;
    private int pingStrikes;

    public KeepAliveEnforcer(boolean permitWithoutCalls, long minTime, TimeUnit unit) {
        this(permitWithoutCalls, minTime, unit, SystemTicker.INSTANCE);
    }

    @VisibleForTesting
    KeepAliveEnforcer(boolean permitWithoutCalls, long minTime, TimeUnit unit, Ticker ticker) {
        Preconditions.checkArgument(minTime >= 0L, "minTime must be non-negative: %s", minTime);
        this.permitWithoutCalls = permitWithoutCalls;
        this.minTimeNanos = Math.min(unit.toNanos(minTime), IMPLICIT_PERMIT_TIME_NANOS);
        this.ticker = ticker;
        this.lastValidPingTime = this.epoch = ticker.nanoTime();
    }

    @CheckReturnValue
    public boolean pingAcceptable() {
        boolean valid;
        long now = this.ticker.nanoTime();
        if (!this.hasOutstandingCalls && !this.permitWithoutCalls) {
            valid = KeepAliveEnforcer.compareNanos(this.lastValidPingTime + IMPLICIT_PERMIT_TIME_NANOS, now) <= 0L;
        } else {
            boolean bl = valid = KeepAliveEnforcer.compareNanos(this.lastValidPingTime + this.minTimeNanos, now) <= 0L;
        }
        if (!valid) {
            ++this.pingStrikes;
            return this.pingStrikes <= 2;
        }
        this.lastValidPingTime = now;
        return true;
    }

    public void resetCounters() {
        this.lastValidPingTime = this.epoch;
        this.pingStrikes = 0;
    }

    public void onTransportActive() {
        this.hasOutstandingCalls = true;
    }

    public void onTransportIdle() {
        this.hasOutstandingCalls = false;
    }

    private static long compareNanos(long time1, long time2) {
        return time1 - time2;
    }

    @VisibleForTesting
    static class SystemTicker
    implements Ticker {
        public static final SystemTicker INSTANCE = new SystemTicker();

        SystemTicker() {
        }

        @Override
        public long nanoTime() {
            return System.nanoTime();
        }
    }

    @VisibleForTesting
    static interface Ticker {
        public long nanoTime();
    }
}

