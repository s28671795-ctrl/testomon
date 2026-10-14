/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;

public record StatisticalMetricDTO(@JSONField(name="average") double average, @JSONField(name="max") double max, @JSONField(name="measurements_count") int measurementsCount) {
    public StatisticalMetricDTO(StatisticalMetric metric) {
        this(metric.getAverage(), metric.max(), metric.measurementsCount());
    }
}

