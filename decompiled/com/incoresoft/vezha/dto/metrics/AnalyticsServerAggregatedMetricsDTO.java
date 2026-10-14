/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.metrics.AnalyticsServerGpuAggregatedMetricsDTO;
import com.incoresoft.vezha.dto.metrics.StatisticalMetricDTO;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerWithGpusAggregatedMetrics;
import java.util.List;

public record AnalyticsServerAggregatedMetricsDTO(@JSONField(name="server_id") String serverId, @JSONField(name="ip_address") String ipAddress, @JSONField(name="cpu_load") StatisticalMetricDTO cpuLoad, @JSONField(name="gpus") List<AnalyticsServerGpuAggregatedMetricsDTO> gpus) {
    public AnalyticsServerAggregatedMetricsDTO(AnalyticsServerWithGpusAggregatedMetrics metrics) {
        this(metrics.serverId(), metrics.ipAddress(), new StatisticalMetricDTO(metrics.cpuLoad()), metrics.gpus().stream().map(AnalyticsServerGpuAggregatedMetricsDTO::new).toList());
    }
}

