/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.glassfish.jersey.server.internal.monitoring.core.AbstractTimeSnapshot;

public class UniformTimeValuesSnapshot
extends AbstractTimeSnapshot {
    private final long[] values;

    public UniformTimeValuesSnapshot(Collection<Long> values2, long timeInterval, TimeUnit timeIntervalUnit) {
        super(timeInterval, timeIntervalUnit);
        Object[] copy = values2.toArray();
        this.values = new long[copy.length];
        for (int i = 0; i < copy.length; ++i) {
            this.values[i] = (Long)copy[i];
        }
        Arrays.sort(this.values);
    }

    public double getValue(double quantile) {
        if (quantile < 0.0 || quantile > 1.0 || Double.isNaN(quantile)) {
            throw new IllegalArgumentException(quantile + " is not in [0..1] range");
        }
        if (this.values.length == 0) {
            return 0.0;
        }
        double pos = quantile * (double)(this.values.length + 1);
        int index = (int)pos;
        if (index < 1) {
            return this.values[0];
        }
        if (index >= this.values.length) {
            return this.values[this.values.length - 1];
        }
        double lower = this.values[index - 1];
        double upper = this.values[index];
        return lower + (pos - Math.floor(pos)) * (upper - lower);
    }

    @Override
    public long size() {
        return this.values.length;
    }

    public long[] getValues() {
        return Arrays.copyOf(this.values, this.values.length);
    }

    @Override
    public long getMax() {
        if (this.values.length == 0) {
            return 0L;
        }
        return this.values[this.values.length - 1];
    }

    @Override
    public long getMin() {
        if (this.values.length == 0) {
            return 0L;
        }
        return this.values[0];
    }

    @Override
    public double getMean() {
        if (this.values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (long value : this.values) {
            sum += (double)value;
        }
        return sum / (double)this.values.length;
    }
}

