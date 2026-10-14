/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.glassfish.jersey.server.internal.monitoring.AggregatedValueObject;
import org.glassfish.jersey.server.internal.monitoring.AggregatingTrimmer;
import org.glassfish.jersey.server.internal.monitoring.UniformTimeSimpleSnapshot;
import org.glassfish.jersey.server.internal.monitoring.core.AbstractSlidingWindowTimeReservoir;
import org.glassfish.jersey.server.internal.monitoring.core.UniformTimeSnapshot;

class AggregatedSlidingWindowTimeReservoir
extends AbstractSlidingWindowTimeReservoir<AggregatedValueObject> {
    private final AggregatingTrimmer notifier;

    public AggregatedSlidingWindowTimeReservoir(long window, TimeUnit windowUnit, long startTime, TimeUnit startTimeUnit, AggregatingTrimmer notifier) {
        super(window, windowUnit, startTime, startTimeUnit);
        this.notifier = notifier;
        notifier.register(this);
    }

    @Override
    protected UniformTimeSnapshot snapshot(Collection<AggregatedValueObject> values2, long timeInterval, TimeUnit timeIntervalUnit, long time, TimeUnit timeUnit) {
        UniformTimeSnapshot notTrimmedMeasurementsSnapshot = this.notifier.getTimeReservoirNotifier().getSnapshot(time, timeUnit);
        AggregatedValueObject[] arrayValues = new AggregatedValueObject[values2.size()];
        arrayValues = values2.toArray(arrayValues);
        long min2 = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        long count = 0L;
        double meanNumerator = 0.0;
        for (AggregatedValueObject value : arrayValues) {
            min2 = Math.min(min2, value.getMin());
            max = Math.max(max, value.getMax());
            count += value.getCount();
            meanNumerator += (double)value.getCount() * value.getMean();
        }
        if (notTrimmedMeasurementsSnapshot.size() > 0L) {
            min2 = Math.min(min2, notTrimmedMeasurementsSnapshot.getMin());
            max = Math.max(max, notTrimmedMeasurementsSnapshot.getMax());
            count += notTrimmedMeasurementsSnapshot.size();
            meanNumerator += (double)notTrimmedMeasurementsSnapshot.size() * notTrimmedMeasurementsSnapshot.getMean();
        }
        if (count == 0L) {
            return new UniformTimeSimpleSnapshot(0L, 0L, 0.0, 0L, timeInterval, timeIntervalUnit);
        }
        return new UniformTimeSimpleSnapshot(max, min2, meanNumerator / (double)count, count, timeInterval, timeIntervalUnit);
    }
}

