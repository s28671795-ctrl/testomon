/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.util.Collection;
import java.util.LinkedList;

class AggregatedValueObject {
    private final long max;
    private final long min;
    private final double mean;
    private final long count;

    private AggregatedValueObject(long max, long min2, double mean, long count) {
        this.max = max;
        this.min = min2;
        this.mean = mean;
        this.count = count;
    }

    public static AggregatedValueObject createFromValues(Collection<Long> values2) {
        if (values2.isEmpty()) {
            throw new IllegalArgumentException("The values collection must not be empty");
        }
        long max = Long.MIN_VALUE;
        long min2 = Long.MAX_VALUE;
        long sum = 0L;
        for (Long value : values2) {
            max = Math.max(max, value);
            min2 = Math.min(min2, value);
            sum += value.longValue();
        }
        return new AggregatedValueObject(max, min2, (double)sum / (double)values2.size(), values2.size());
    }

    public static AggregatedValueObject createFromMultiValues(Collection<? extends Collection<Long>> values2) {
        LinkedList<Long> mergedCollection = new LinkedList<Long>();
        for (Collection<Long> collection : values2) {
            mergedCollection.addAll(collection);
        }
        return AggregatedValueObject.createFromValues(mergedCollection);
    }

    public long getMax() {
        return this.max;
    }

    public long getMin() {
        return this.min;
    }

    public double getMean() {
        return this.mean;
    }

    public long getCount() {
        return this.count;
    }
}

