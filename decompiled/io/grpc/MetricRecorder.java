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
import java.util.List;

@Internal
public interface MetricRecorder {
    default public void addDoubleCounter(DoubleCounterMetricInstrument metricInstrument, double value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public void addLongCounter(LongCounterMetricInstrument metricInstrument, long value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public void recordDoubleHistogram(DoubleHistogramMetricInstrument metricInstrument, double value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public void recordLongHistogram(LongHistogramMetricInstrument metricInstrument, long value, List<String> requiredLabelValues, List<String> optionalLabelValues) {
    }

    default public Registration registerBatchCallback(BatchCallback callback, CallbackMetricInstrument ... metricInstruments) {
        return () -> {};
    }

    public static interface Registration
    extends AutoCloseable {
        @Override
        public void close();
    }

    public static interface BatchRecorder {
        public void recordLongGauge(LongGaugeMetricInstrument var1, long var2, List<String> var4, List<String> var5);
    }

    public static interface BatchCallback {
        public void accept(BatchRecorder var1);
    }
}

