/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.CallbackMetricInstrument;
import io.grpc.DoubleCounterMetricInstrument;
import io.grpc.DoubleHistogramMetricInstrument;
import io.grpc.Internal;
import io.grpc.LongCounterMetricInstrument;
import io.grpc.LongGaugeMetricInstrument;
import io.grpc.LongHistogramMetricInstrument;
import io.grpc.MetricInstrument;
import io.grpc.MetricRecorder;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Internal
public interface MetricSink {
    public Map<String, Boolean> getEnabledMetrics();

    public Set<String> getOptionalLabels();

    public int getMeasuresSize();

    default public void addDoubleCounter(DoubleCounterMetricInstrument metricInstrument, double value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public void addLongCounter(LongCounterMetricInstrument metricInstrument, long value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public void recordDoubleHistogram(DoubleHistogramMetricInstrument metricInstrument, double value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public void recordLongHistogram(LongHistogramMetricInstrument metricInstrument, long value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public void recordLongGauge(LongGaugeMetricInstrument metricInstrument, long value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public Registration registerBatchCallback(Runnable callback, CallbackMetricInstrument ... metricInstruments) {
        return () -> {};
    }

    public void updateMeasures(List<MetricInstrument> var1);

    public static interface Registration
    extends MetricRecorder.Registration {
    }
}

