/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.aggregated;

import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerWithGpusAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.CoreAggregatedMetrics;
import java.util.List;

public record AggregatedMetrics(CoreAggregatedMetrics coreMetrics, List<AnalyticsServerWithGpusAggregatedMetrics> serverMetrics) {
}

