/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class Rescheduler {
    private final ScheduledExecutorService scheduler;
    private final Executor serializingExecutor;
    private final Runnable runnable;
    private final Stopwatch stopwatch;
    private long runAtNanos;
    private boolean enabled;
    private ScheduledFuture<?> wakeUp;

    Rescheduler(Runnable r, Executor serializingExecutor, ScheduledExecutorService scheduler, Stopwatch stopwatch) {
        this.runnable = r;
        this.serializingExecutor = serializingExecutor;
        this.scheduler = scheduler;
        this.stopwatch = stopwatch;
        stopwatch.start();
    }

    void reschedule(long delay, TimeUnit timeUnit) {
        long delayNanos = timeUnit.toNanos(delay);
        long newRunAtNanos = this.nanoTime() + delayNanos;
        this.enabled = true;
        if (newRunAtNanos - this.runAtNanos < 0L || this.wakeUp == null) {
            if (this.wakeUp != null) {
                this.wakeUp.cancel(false);
            }
            this.wakeUp = this.scheduler.schedule(new FutureRunnable(), delayNanos, TimeUnit.NANOSECONDS);
        }
        this.runAtNanos = newRunAtNanos;
    }

    void cancel(boolean permanent) {
        this.enabled = false;
        if (permanent && this.wakeUp != null) {
            this.wakeUp.cancel(false);
            this.wakeUp = null;
        }
    }

    @VisibleForTesting
    static boolean isEnabled(Runnable r) {
        return ((FutureRunnable)r).isEnabled();
    }

    private long nanoTime() {
        return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
    }

    private final class ChannelFutureRunnable
    implements Runnable {
        private ChannelFutureRunnable() {
        }

        @Override
        public void run() {
            if (!Rescheduler.this.enabled) {
                Rescheduler.this.wakeUp = null;
                return;
            }
            long now = Rescheduler.this.nanoTime();
            if (Rescheduler.this.runAtNanos - now > 0L) {
                Rescheduler.this.wakeUp = Rescheduler.this.scheduler.schedule(new FutureRunnable(), Rescheduler.this.runAtNanos - now, TimeUnit.NANOSECONDS);
            } else {
                Rescheduler.this.enabled = false;
                Rescheduler.this.wakeUp = null;
                Rescheduler.this.runnable.run();
            }
        }
    }

    private final class FutureRunnable
    implements Runnable {
        private FutureRunnable() {
        }

        @Override
        public void run() {
            Rescheduler.this.serializingExecutor.execute(new ChannelFutureRunnable());
        }

        private boolean isEnabled() {
            return Rescheduler.this.enabled;
        }
    }
}

