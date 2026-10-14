/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.metrics.StatisticalMetricDTO;
import com.incoresoft.vezha.metrics.aggregated.CoreAggregatedMetrics;

public record CoreAggregatedMetricsDTO(@JSONField(name="cpu_load") StatisticalMetricDTO cpuLoad, @JSONField(name="startups_number") int startupsNumber) {
    public CoreAggregatedMetricsDTO(CoreAggregatedMetrics metrics) {
        this(new StatisticalMetricDTO(metrics.cpuLoad()), metrics.startupsNumber());
    }
}

