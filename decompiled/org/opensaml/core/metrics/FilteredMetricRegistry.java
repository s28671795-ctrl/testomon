/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.Timer;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.metrics.impl.DisabledCounter;
import org.opensaml.core.metrics.impl.DisabledHistogram;
import org.opensaml.core.metrics.impl.DisabledMeter;
import org.opensaml.core.metrics.impl.DisabledTimer;

public class FilteredMetricRegistry
extends MetricRegistry {
    @Nullable
    private MetricFilter metricFilter;
    @Nonnull
    private final DisabledCounter disabledCounter = new DisabledCounter();
    @Nonnull
    private final DisabledHistogram disabledHistogram = new DisabledHistogram();
    @Nonnull
    private final DisabledMeter disabledMeter = new DisabledMeter();
    @Nonnull
    private final DisabledTimer disabledTimer = new DisabledTimer();

    public void setMetricFilter(@Nullable MetricFilter filter2) {
        this.metricFilter = filter2;
    }

    @Override
    public Counter counter(String name) {
        if (this.metricFilter != null && this.metricFilter.matches(name, null)) {
            return super.counter(name);
        }
        return this.disabledCounter;
    }

    @Override
    public Histogram histogram(String name) {
        if (this.metricFilter != null && this.metricFilter.matches(name, null)) {
            return super.histogram(name);
        }
        return this.disabledHistogram;
    }

    @Override
    public Meter meter(String name) {
        if (this.metricFilter != null && this.metricFilter.matches(name, null)) {
            return super.meter(name);
        }
        return this.disabledMeter;
    }

    @Override
    public Timer timer(String name) {
        if (this.metricFilter != null && this.metricFilter.matches(name, null)) {
            return super.timer(name);
        }
        return this.disabledTimer;
    }

    public void registerMultiple(@Nonnull @NonnullElements Collection<MetricSet> metricSets) throws IllegalArgumentException {
        Constraint.isNotNull(metricSets, "Collection cannot be null");
        for (MetricSet set : Collections2.filter(metricSets, Predicates.notNull())) {
            this.registerAll(set);
        }
    }
}

