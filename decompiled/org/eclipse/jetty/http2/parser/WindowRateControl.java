/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.http2.parser.RateControl;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.NanoTime;

public class WindowRateControl
implements RateControl {
    private final Queue<Long> events = new ConcurrentLinkedQueue<Long>();
    private final AtomicInteger size = new AtomicInteger();
    private final int maxEvents;
    private final long window;

    public static WindowRateControl fromEventsPerSecond(int maxEvents) {
        return new WindowRateControl(maxEvents, Duration.ofSeconds(1L));
    }

    public WindowRateControl(int maxEvents, Duration window) {
        this.maxEvents = maxEvents;
        this.window = window.toNanos();
        if (this.window == 0L) {
            throw new IllegalArgumentException("Invalid duration " + String.valueOf(window));
        }
    }

    public int getEventsPerSecond() {
        try {
            long rate = (long)this.maxEvents * 1000000000L / this.window;
            return Math.toIntExact(rate);
        }
        catch (ArithmeticException x) {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean onEvent(Object event) {
        Long time;
        long now = NanoTime.now();
        while ((time = this.events.peek()) != null && !NanoTime.isBefore(now, time)) {
            if (!this.events.remove(time)) continue;
            this.size.decrementAndGet();
        }
        this.events.add(now + this.window);
        return this.size.incrementAndGet() <= this.maxEvents;
    }

    public static class Factory
    implements RateControl.Factory {
        private final int maxEventRate;

        public Factory(int maxEventRate) {
            this.maxEventRate = maxEventRate;
        }

        @Override
        public RateControl newRateControl(EndPoint endPoint) {
            return WindowRateControl.fromEventsPerSecond(this.maxEventRate);
        }
    }
}

