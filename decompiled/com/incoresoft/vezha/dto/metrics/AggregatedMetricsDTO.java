/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.metrics.AnalyticsServerAggregatedMetricsDTO;
import com.incoresoft.vezha.dto.metrics.CoreAggregatedMetricsDTO;
import com.incoresoft.vezha.metrics.aggregated.AggregatedMetrics;
import java.time.Instant;
import java.util.List;

public record AggregatedMetricsDTO(@JSONField(name="core") CoreAggregatedMetricsDTO coreMetrics, @JSONField(name="analytics_servers") List<AnalyticsServerAggregatedMetricsDTO> analyticsServerMetrics, @JSONField(name="start_time") Instant startTime) {
    public AggregatedMetricsDTO(AggregatedMetrics metrics) {
        this(new CoreAggregatedMetricsDTO(metrics.coreMetrics()), metrics.serverMetrics().stream().map(AnalyticsServerAggregatedMetricsDTO::new).toList(), metrics.coreMetrics().startTime());
    }
}

