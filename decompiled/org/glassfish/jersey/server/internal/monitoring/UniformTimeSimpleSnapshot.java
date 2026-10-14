/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.util.concurrent.TimeUnit;
import org.glassfish.jersey.server.internal.monitoring.core.AbstractTimeSnapshot;

class UniformTimeSimpleSnapshot
extends AbstractTimeSnapshot {
    private final long max;
    private final long min;
    private final double mean;
    private final long count;

    public UniformTimeSimpleSnapshot(long max, long min2, double mean, long count, long timeInterval, TimeUnit timeIntervalUnit) {
        super(timeInterval, timeIntervalUnit);
        this.max = max;
        this.min = min2;
        this.mean = mean;
        this.count = count;
    }

    @Override
    public long size() {
        return this.count;
    }

    @Override
    public long getMax() {
        return this.max;
    }

    @Override
    public long getMin() {
        return this.min;
    }

    @Override
    public double getMean() {
        return this.mean;
    }
}

