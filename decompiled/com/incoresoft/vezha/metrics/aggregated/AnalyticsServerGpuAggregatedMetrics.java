/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.aggregated;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;

public record AnalyticsServerGpuAggregatedMetrics(int id, int serverMetricsId, int gpuId, String gpuName, StatisticalMetric load, int clientId) implements Model
{
    public AnalyticsServerGpuAggregatedMetrics(int serverMetricsId, int gpuId, String gpuName, StatisticalMetric load) {
        this(0, serverMetricsId, gpuId, gpuName, load, 0);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }
}

