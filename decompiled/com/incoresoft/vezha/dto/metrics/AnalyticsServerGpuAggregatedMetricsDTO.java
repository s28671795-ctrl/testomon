/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.metrics.StatisticalMetricDTO;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerGpuAggregatedMetrics;

public record AnalyticsServerGpuAggregatedMetricsDTO(@JSONField(name="name") String gpuName, @JSONField(name="load") StatisticalMetricDTO load) {
    public AnalyticsServerGpuAggregatedMetricsDTO(AnalyticsServerGpuAggregatedMetrics metrics) {
        this(metrics.gpuName(), new StatisticalMetricDTO(metrics.load()));
    }
}

