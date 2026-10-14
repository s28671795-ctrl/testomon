/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.metrics.impl;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Snapshot;
import org.opensaml.core.metrics.impl.DisabledMetric;

public class DisabledHistogram
extends Histogram
implements DisabledMetric {
    public DisabledHistogram() {
        super(null);
    }

    @Override
    public void update(int value) {
    }

    @Override
    public void update(long value) {
    }

    @Override
    public long getCount() {
        return 0L;
    }

    @Override
    public Snapshot getSnapshot() {
        return null;
    }
}

