/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.glassfish.jersey.server.internal.monitoring.core.AbstractSlidingWindowTimeReservoir;
import org.glassfish.jersey.server.internal.monitoring.core.SlidingWindowTrimmer;
import org.glassfish.jersey.server.internal.monitoring.core.UniformTimeSnapshot;
import org.glassfish.jersey.server.internal.monitoring.core.UniformTimeValuesSnapshot;

class SlidingWindowTimeReservoir
extends AbstractSlidingWindowTimeReservoir<Long> {
    public SlidingWindowTimeReservoir(long window, TimeUnit windowUnit, long startTime, TimeUnit startTimeUnit, SlidingWindowTrimmer<Long> trimmer) {
        super(window, windowUnit, startTime, startTimeUnit, trimmer);
    }

    public SlidingWindowTimeReservoir(long window, TimeUnit windowUnit, long startTime, TimeUnit startTimeUnit) {
        this(window, windowUnit, startTime, startTimeUnit, (SlidingWindowTrimmer<Long>)null);
    }

    @Override
    protected UniformTimeSnapshot snapshot(Collection<Long> values2, long timeInterval, TimeUnit timeIntervalUnit, long time, TimeUnit timeUnit) {
        return new UniformTimeValuesSnapshot(values2, timeInterval, timeIntervalUnit);
    }
}

