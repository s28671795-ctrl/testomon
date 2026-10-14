/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.grpc.DoubleCounterMetricInstrument;
import io.grpc.DoubleHistogramMetricInstrument;
import io.grpc.Internal;
import io.grpc.LongCounterMetricInstrument;
import io.grpc.LongGaugeMetricInstrument;
import io.grpc.LongHistogramMetricInstrument;
import io.grpc.MetricInstrument;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;

@Internal
public final class MetricInstrumentRegistry {
    static final int INITIAL_INSTRUMENT_CAPACITY = 5;
    private static MetricInstrumentRegistry instance;
    private final Object lock = new Object();
    @GuardedBy(value="lock")
    private final Set<String> registeredMetricNames = new HashSet<String>();
    @GuardedBy(value="lock")
    private MetricInstrument[] metricInstruments = new MetricInstrument[5];
    @GuardedBy(value="lock")
    private int nextAvailableMetricIndex;

    @VisibleForTesting
    MetricInstrumentRegistry() {
    }

    public static synchronized MetricInstrumentRegistry getDefaultRegistry() {
        if (instance == null) {
            instance = new MetricInstrumentRegistry();
        }
        return instance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<MetricInstrument> getMetricInstruments() {
        Object object = this.lock;
        synchronized (object) {
            return Collections.unmodifiableList(Arrays.asList(Arrays.copyOfRange(this.metricInstruments, 0, this.nextAvailableMetricIndex)));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DoubleCounterMetricInstrument registerDoubleCounter(String name, String description, String unit, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "missing metric name");
        Preconditions.checkNotNull(description, "description");
        Preconditions.checkNotNull(unit, "unit");
        Preconditions.checkNotNull(requiredLabelKeys, "requiredLabelKeys");
        Preconditions.checkNotNull(optionalLabelKeys, "optionalLabelKeys");
        Object object = this.lock;
        synchronized (object) {
            if (this.registeredMetricNames.contains(name)) {
                throw new IllegalStateException("Metric with name " + name + " already exists");
            }
            int index = this.nextAvailableMetricIndex;
            if (index + 1 == this.metricInstruments.length) {
                this.resizeMetricInstruments();
            }
            DoubleCounterMetricInstrument instrument = new DoubleCounterMetricInstrument(index, name, description, unit, requiredLabelKeys, optionalLabelKeys, enableByDefault);
            this.metricInstruments[index] = instrument;
            this.registeredMetricNames.add(name);
            ++this.nextAvailableMetricIndex;
            return instrument;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LongCounterMetricInstrument registerLongCounter(String name, String description, String unit, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "missing metric name");
        Preconditions.checkNotNull(description, "description");
        Preconditions.checkNotNull(unit, "unit");
        Preconditions.checkNotNull(requiredLabelKeys, "requiredLabelKeys");
        Preconditions.checkNotNull(optionalLabelKeys, "optionalLabelKeys");
        Object object = this.lock;
        synchronized (object) {
            if (this.registeredMetricNames.contains(name)) {
                throw new IllegalStateException("Metric with name " + name + " already exists");
            }
            int index = this.nextAvailableMetricIndex;
            if (index + 1 == this.metricInstruments.length) {
                this.resizeMetricInstruments();
            }
            LongCounterMetricInstrument instrument = new LongCounterMetricInstrument(index, name, description, unit, requiredLabelKeys, optionalLabelKeys, enableByDefault);
            this.metricInstruments[index] = instrument;
            this.registeredMetricNames.add(name);
            ++this.nextAvailableMetricIndex;
            return instrument;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DoubleHistogramMetricInstrument registerDoubleHistogram(String name, String description, String unit, List<Double> bucketBoundaries, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "missing metric name");
        Preconditions.checkNotNull(description, "description");
        Preconditions.checkNotNull(unit, "unit");
        Preconditions.checkNotNull(bucketBoundaries, "bucketBoundaries");
        Preconditions.checkNotNull(requiredLabelKeys, "requiredLabelKeys");
        Preconditions.checkNotNull(optionalLabelKeys, "optionalLabelKeys");
        Object object = this.lock;
        synchronized (object) {
            if (this.registeredMetricNames.contains(name)) {
                throw new IllegalStateException("Metric with name " + name + " already exists");
            }
            int index = this.nextAvailableMetricIndex;
            if (index + 1 == this.metricInstruments.length) {
                this.resizeMetricInstruments();
            }
            DoubleHistogramMetricInstrument instrument = new DoubleHistogramMetricInstrument(index, name, description, unit, bucketBoundaries, requiredLabelKeys, optionalLabelKeys, enableByDefault);
            this.metricInstruments[index] = instrument;
            this.registeredMetricNames.add(name);
            ++this.nextAvailableMetricIndex;
            return instrument;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LongHistogramMetricInstrument registerLongHistogram(String name, String description, String unit, List<Long> bucketBoundaries, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "missing metric name");
        Preconditions.checkNotNull(description, "description");
        Preconditions.checkNotNull(unit, "unit");
        Preconditions.checkNotNull(bucketBoundaries, "bucketBoundaries");
        Preconditions.checkNotNull(requiredLabelKeys, "requiredLabelKeys");
        Preconditions.checkNotNull(optionalLabelKeys, "optionalLabelKeys");
        Object object = this.lock;
        synchronized (object) {
            if (this.registeredMetricNames.contains(name)) {
                throw new IllegalStateException("Metric with name " + name + " already exists");
            }
            int index = this.nextAvailableMetricIndex;
            if (index + 1 == this.metricInstruments.length) {
                this.resizeMetricInstruments();
            }
            LongHistogramMetricInstrument instrument = new LongHistogramMetricInstrument(index, name, description, unit, bucketBoundaries, requiredLabelKeys, optionalLabelKeys, enableByDefault);
            this.metricInstruments[index] = instrument;
            this.registeredMetricNames.add(name);
            ++this.nextAvailableMetricIndex;
            return instrument;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LongGaugeMetricInstrument registerLongGauge(String name, String description, String unit, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "missing metric name");
        Preconditions.checkNotNull(description, "description");
        Preconditions.checkNotNull(unit, "unit");
        Preconditions.checkNotNull(requiredLabelKeys, "requiredLabelKeys");
        Preconditions.checkNotNull(optionalLabelKeys, "optionalLabelKeys");
        Object object = this.lock;
        synchronized (object) {
            if (this.registeredMetricNames.contains(name)) {
                throw new IllegalStateException("Metric with name " + name + " already exists");
            }
            int index = this.nextAvailableMetricIndex;
            if (index + 1 == this.metricInstruments.length) {
                this.resizeMetricInstruments();
            }
            LongGaugeMetricInstrument instrument = new LongGaugeMetricInstrument(index, name, description, unit, requiredLabelKeys, optionalLabelKeys, enableByDefault);
            this.metricInstruments[index] = instrument;
            this.registeredMetricNames.add(name);
            ++this.nextAvailableMetricIndex;
            return instrument;
        }
    }

    @GuardedBy(value="lock")
    private void resizeMetricInstruments() {
        int newInstrumentsCapacity = this.metricInstruments.length + 5;
        MetricInstrument[] resizedMetricInstruments = Arrays.copyOf(this.metricInstruments, newInstrumentsCapacity);
        this.metricInstruments = resizedMetricInstruments;
    }
}

