/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.metrics.impl;

import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.opensaml.core.metrics.impl.DisabledMetric;

public class DisabledTimer
extends Timer
implements DisabledMetric {
    public DisabledTimer() {
        super(null);
    }

    @Override
    public void update(long duration, TimeUnit unit) {
    }

    @Override
    public <T> T time(Callable<T> event) throws Exception {
        return null;
    }

    @Override
    public Timer.Context time() {
        return null;
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

    @Override
    public Snapshot getSnapshot() {
        return null;
    }
}

