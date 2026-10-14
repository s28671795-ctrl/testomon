/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.internal.LogExceptionRunnable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

public final class MaxConnectionIdleManager {
    private static final Ticker systemTicker = new Ticker(){

        @Override
        public long nanoTime() {
            return System.nanoTime();
        }
    };
    private final long maxConnectionIdleInNanos;
    private final Ticker ticker;
    @CheckForNull
    private ScheduledFuture<?> shutdownFuture;
    private Runnable shutdownTask;
    private ScheduledExecutorService scheduler;
    private long nextIdleMonitorTime;
    private boolean shutdownDelayed;
    private boolean isActive;

    public MaxConnectionIdleManager(long maxConnectionIdleInNanos) {
        this(maxConnectionIdleInNanos, systemTicker);
    }

    @VisibleForTesting
    public MaxConnectionIdleManager(long maxConnectionIdleInNanos, Ticker ticker) {
        this.maxConnectionIdleInNanos = maxConnectionIdleInNanos;
        this.ticker = ticker;
    }

    public void start(final Runnable closeJob, final ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
        this.nextIdleMonitorTime = this.ticker.nanoTime() + this.maxConnectionIdleInNanos;
        this.shutdownTask = new LogExceptionRunnable(new Runnable(){

            @Override
            public void run() {
                if (MaxConnectionIdleManager.this.shutdownDelayed) {
                    if (!MaxConnectionIdleManager.this.isActive) {
                        MaxConnectionIdleManager.this.shutdownFuture = scheduler.schedule(MaxConnectionIdleManager.this.shutdownTask, MaxConnectionIdleManager.this.nextIdleMonitorTime - MaxConnectionIdleManager.this.ticker.nanoTime(), TimeUnit.NANOSECONDS);
                        MaxConnectionIdleManager.this.shutdownDelayed = false;
                    }
                } else {
                    closeJob.run();
                    MaxConnectionIdleManager.this.shutdownFuture = null;
                }
            }
        });
        this.shutdownFuture = scheduler.schedule(this.shutdownTask, this.maxConnectionIdleInNanos, TimeUnit.NANOSECONDS);
    }

    public void onTransportActive() {
        this.isActive = true;
        this.shutdownDelayed = true;
    }

    public void onTransportIdle() {
        this.isActive = false;
        if (this.shutdownFuture == null) {
            return;
        }
        if (this.shutdownFuture.isDone()) {
            this.shutdownDelayed = false;
            this.shutdownFuture = this.scheduler.schedule(this.shutdownTask, this.maxConnectionIdleInNanos, TimeUnit.NANOSECONDS);
        } else {
            this.nextIdleMonitorTime = this.ticker.nanoTime() + this.maxConnectionIdleInNanos;
        }
    }

    public void onTransportTermination() {
        if (this.shutdownFuture != null) {
            this.shutdownFuture.cancel(false);
            this.shutdownFuture = null;
        }
    }

    @VisibleForTesting
    public static interface Ticker {
        public long nanoTime();
    }
}

