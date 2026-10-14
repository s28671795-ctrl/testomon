/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.metrics.AggregatedMetricsDTO;
import com.incoresoft.vezha.dto.metrics.SnapshotMetricsDTO;
import com.incoresoft.vezha.metrics.aggregated.AggregatedMetrics;
import com.incoresoft.vezha.metrics.snapshot.SnapshotMetrics;

public record MetricsDTO(@JSONField(name="aggregated") AggregatedMetricsDTO aggregated, @JSONField(name="snapshot") SnapshotMetricsDTO snapshot) {
    public MetricsDTO(AggregatedMetrics aggregatedMetrics, SnapshotMetrics snapshotMetrics) {
        this(new AggregatedMetricsDTO(aggregatedMetrics), new SnapshotMetricsDTO(snapshotMetrics));
    }
}

