/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.CallbackMetricInstrument;
import io.grpc.DoubleCounterMetricInstrument;
import io.grpc.DoubleHistogramMetricInstrument;
import io.grpc.LongCounterMetricInstrument;
import io.grpc.LongGaugeMetricInstrument;
import io.grpc.LongHistogramMetricInstrument;
import io.grpc.MetricInstrumentRegistry;
import io.grpc.MetricRecorder;
import io.grpc.MetricSink;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

final class MetricRecorderImpl
implements MetricRecorder {
    private final List<MetricSink> metricSinks;
    private final MetricInstrumentRegistry registry;

    @VisibleForTesting
    MetricRecorderImpl(List<MetricSink> metricSinks, MetricInstrumentRegistry registry) {
        this.metricSinks = metricSinks;
        this.registry = registry;
    }

    @Override
    public void addDoubleCounter(DoubleCounterMetricInstrument metricInstrument, double value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
        Preconditions.checkArgument(requiredLabelValues != null && requiredLabelValues.size() == metricInstrument.getRequiredLabelKeys().size(), "Incorrect number of required labels provided. Expected: " + metricInstrument.getRequiredLabelKeys().size());
        Preconditions.checkArgument(optionalLabelValues != null && optionalLabelValues.size() == metricInstrument.getOptionalLabelKeys().size(), "Incorrect number of optional labels provided. Expected: " + metricInstrument.getOptionalLabelKeys().size());
        for (MetricSink sink2 : this.metricSinks) {
            int measuresSize = sink2.getMeasuresSize();
            if (measuresSize <= metricInstrument.getIndex()) {
                sink2.updateMeasures(this.registry.getMetricInstruments());
            }
            sink2.addDoubleCounter(metricInstrument, value, requiredLabelValues, optionalLabelValues);
        }
    }

    @Override
    public void addLongCounter(LongCounterMetricInstrument metricInstrument, long value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
        Preconditions.checkArgument(requiredLabelValues != null && requiredLabelValues.size() == metricInstrument.getRequiredLabelKeys().size(), "Incorrect number of required labels provided. Expected: " + metricInstrument.getRequiredLabelKeys().size());
        Preconditions.checkArgument(optionalLabelValues != null && optionalLabelValues.size() == metricInstrument.getOptionalLabelKeys().size(), "Incorrect number of optional labels provided. Expected: " + metricInstrument.getOptionalLabelKeys().size());
        for (MetricSink sink2 : this.metricSinks) {
            int measuresSize = sink2.getMeasuresSize();
            if (measuresSize <= metricInstrument.getIndex()) {
                sink2.updateMeasures(this.registry.getMetricInstruments());
            }
            sink2.addLongCounter(metricInstrument, value, requiredLabelValues, optionalLabelValues);
        }
    }

    @Override
    public void recordDoubleHistogram(DoubleHistogramMetricInstrument metricInstrument, double value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
        Preconditions.checkArgument(requiredLabelValues != null && requiredLabelValues.size() == metricInstrument.getRequiredLabelKeys().size(), "Incorrect number of required labels provided. Expected: " + metricInstrument.getRequiredLabelKeys().size());
        Preconditions.checkArgument(optionalLabelValues != null && optionalLabelValues.size() == metricInstrument.getOptionalLabelKeys().size(), "Incorrect number of optional labels provided. Expected: " + metricInstrument.getOptionalLabelKeys().size());
        for (MetricSink sink2 : this.metricSinks) {
            int measuresSize = sink2.getMeasuresSize();
            if (measuresSize <= metricInstrument.getIndex()) {
                sink2.updateMeasures(this.registry.getMetricInstruments());
            }
            sink2.recordDoubleHistogram(metricInstrument, value, requiredLabelValues, optionalLabelValues);
        }
    }

    @Override
    public void recordLongHistogram(LongHistogramMetricInstrument metricInstrument, long value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
        Preconditions.checkArgument(requiredLabelValues != null && requiredLabelValues.size() == metricInstrument.getRequiredLabelKeys().size(), "Incorrect number of required labels provided. Expected: " + metricInstrument.getRequiredLabelKeys().size());
        Preconditions.checkArgument(optionalLabelValues != null && optionalLabelValues.size() == metricInstrument.getOptionalLabelKeys().size(), "Incorrect number of optional labels provided. Expected: " + metricInstrument.getOptionalLabelKeys().size());
        for (MetricSink sink2 : this.metricSinks) {
            int measuresSize = sink2.getMeasuresSize();
            if (measuresSize <= metricInstrument.getIndex()) {
                sink2.updateMeasures(this.registry.getMetricInstruments());
            }
            sink2.recordLongHistogram(metricInstrument, value, requiredLabelValues, optionalLabelValues);
        }
    }

    @Override
    public MetricRecorder.Registration registerBatchCallback(MetricRecorder.BatchCallback callback, CallbackMetricInstrument ... metricInstruments) {
        long largestMetricInstrumentIndex = -1L;
        BitSet allowedInstruments = new BitSet();
        for (CallbackMetricInstrument metricInstrument : metricInstruments) {
            largestMetricInstrumentIndex = Math.max(largestMetricInstrumentIndex, (long)metricInstrument.getIndex());
            allowedInstruments.set(metricInstrument.getIndex());
        }
        ArrayList<MetricSink.Registration> registrations = new ArrayList<MetricSink.Registration>();
        for (MetricSink sink2 : this.metricSinks) {
            int measuresSize = sink2.getMeasuresSize();
            if ((long)measuresSize <= largestMetricInstrumentIndex) {
                sink2.updateMeasures(this.registry.getMetricInstruments());
            }
            BatchRecorderImpl singleSinkRecorder = new BatchRecorderImpl(sink2, allowedInstruments);
            registrations.add(sink2.registerBatchCallback(() -> callback.accept(singleSinkRecorder), metricInstruments));
        }
        return () -> {
            for (MetricSink.Registration registration : registrations) {
                registration.close();
            }
        };
    }

    static class BatchRecorderImpl
    implements MetricRecorder.BatchRecorder {
        private final MetricSink sink;
        private final BitSet allowedInstruments;

        BatchRecorderImpl(MetricSink sink2, BitSet allowedInstruments) {
            this.sink = Preconditions.checkNotNull(sink2, "sink");
            this.allowedInstruments = Preconditions.checkNotNull(allowedInstruments, "allowedInstruments");
        }

        @Override
        public void recordLongGauge(LongGaugeMetricInstrument metricInstrument, long value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
            Preconditions.checkArgument(this.allowedInstruments.get(metricInstrument.getIndex()), "Instrument was not listed when registering callback: %s", (Object)metricInstrument);
            Preconditions.checkArgument(requiredLabelValues != null && requiredLabelValues.size() == metricInstrument.getRequiredLabelKeys().size(), "Incorrect number of required labels provided. Expected: %s", metricInstrument.getRequiredLabelKeys().size());
            Preconditions.checkArgument(optionalLabelValues != null && optionalLabelValues.size() == metricInstrument.getOptionalLabelKeys().size(), "Incorrect number of optional labels provided. Expected: %s", metricInstrument.getOptionalLabelKeys().size());
            this.sink.recordLongGauge(metricInstrument, value, requiredLabelValues, optionalLabelValues);
        }
    }
}

