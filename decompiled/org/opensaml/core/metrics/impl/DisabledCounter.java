/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.metrics.impl;

import com.codahale.metrics.Counter;
import org.opensaml.core.metrics.impl.DisabledMetric;

public class DisabledCounter
extends Counter
implements DisabledMetric {
    @Override
    public void inc() {
    }

    @Override
    public void inc(long n) {
    }

    @Override
    public void dec() {
    }

    @Override
    public void dec(long n) {
    }

    @Override
    public long getCount() {
        return 0L;
    }
}

