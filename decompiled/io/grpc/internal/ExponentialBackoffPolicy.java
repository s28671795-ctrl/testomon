/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.internal.BackoffPolicy;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public final class ExponentialBackoffPolicy
implements BackoffPolicy {
    private Random random = new Random();
    private long initialBackoffNanos = TimeUnit.SECONDS.toNanos(1L);
    private long maxBackoffNanos = TimeUnit.MINUTES.toNanos(2L);
    private double multiplier = 1.6;
    private double jitter = 0.2;
    private long nextBackoffNanos = this.initialBackoffNanos;

    @Override
    public long nextBackoffNanos() {
        long currentBackoffNanos = this.nextBackoffNanos;
        this.nextBackoffNanos = Math.min((long)((double)currentBackoffNanos * this.multiplier), this.maxBackoffNanos);
        return currentBackoffNanos + this.uniformRandom(-this.jitter * (double)currentBackoffNanos, this.jitter * (double)currentBackoffNanos);
    }

    private long uniformRandom(double low, double high) {
        Preconditions.checkArgument(high >= low);
        double mag = high - low;
        return (long)(this.random.nextDouble() * mag + low);
    }

    @VisibleForTesting
    ExponentialBackoffPolicy setRandom(Random random) {
        this.random = random;
        return this;
    }

    @VisibleForTesting
    ExponentialBackoffPolicy setInitialBackoffNanos(long initialBackoffNanos) {
        this.initialBackoffNanos = initialBackoffNanos;
        return this;
    }

    @VisibleForTesting
    ExponentialBackoffPolicy setMaxBackoffNanos(long maxBackoffNanos) {
        this.maxBackoffNanos = maxBackoffNanos;
        return this;
    }

    @VisibleForTesting
    ExponentialBackoffPolicy setMultiplier(double multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    @VisibleForTesting
    ExponentialBackoffPolicy setJitter(double jitter) {
        this.jitter = jitter;
        return this;
    }

    public static final class Provider
    implements BackoffPolicy.Provider {
        @Override
        public BackoffPolicy get() {
            return new ExponentialBackoffPolicy();
        }
    }
}

