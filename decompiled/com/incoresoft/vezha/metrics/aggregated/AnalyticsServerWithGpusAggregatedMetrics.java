/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.aggregated;

import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerGpuAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;
import java.time.Instant;
import java.util.List;

public record AnalyticsServerWithGpusAggregatedMetrics(int id, Instant startTime, String serverId, String ipAddress, StatisticalMetric cpuLoad, List<AnalyticsServerGpuAggregatedMetrics> gpus, int clientId) {
    public AnalyticsServerWithGpusAggregatedMetrics(AnalyticsServerAggregatedMetrics metrics, List<AnalyticsServerGpuAggregatedMetrics> gpus) {
        this(metrics.id(), metrics.startTime(), metrics.serverId(), metrics.ipAddress(), metrics.cpuLoad(), gpus, metrics.clientId());
    }
}

