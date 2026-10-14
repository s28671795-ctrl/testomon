/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.metrics.impl;

import com.codahale.metrics.Meter;
import org.opensaml.core.metrics.impl.DisabledMetric;

public class DisabledMeter
extends Meter
implements DisabledMetric {
    @Override
    public void mark() {
    }

    @Override
    public void mark(long n) {
    }

    @Override
    public long getCount() {
        return 0L;
    }

    @Override
    public double getFifteenMinuteRate() {
        return 0.0;
    }

    @Override
    public double getFiveMinuteRate() {
        return 0.0;
    }

    @Override
    public double getMeanRate() {
        return 0.0;
    }

    @Override
    public double getOneMinuteRate() {
        return 0.0;
    }
}

