/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.database.CRUDConverter;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsAnalyticsServersGpuRecord;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerGpuAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;
import org.jetbrains.annotations.NotNull;

public class AnalyticsServerGpuMetricsConverter
implements CRUDConverter<AggregatedMetricsAnalyticsServersGpuRecord, AnalyticsServerGpuAggregatedMetrics> {
    @Override
    public AnalyticsServerGpuAggregatedMetrics from(AggregatedMetricsAnalyticsServersGpuRecord record) {
        return new AnalyticsServerGpuAggregatedMetrics(record.getId(), record.getServerMetricsId(), record.getGpuId(), record.getGpuName(), Serialization.readValue(record.getLoad(), StatisticalMetric.class), record.getClientId());
    }

    @Override
    public AggregatedMetricsAnalyticsServersGpuRecord to(AnalyticsServerGpuAggregatedMetrics userObject) {
        return new AggregatedMetricsAnalyticsServersGpuRecord(userObject.getId(), userObject.serverMetricsId(), userObject.gpuId(), userObject.gpuName(), Serialization.writeValueAsString(userObject.load()), userObject.getClientId());
    }

    @Override
    @NotNull
    public Class<AggregatedMetricsAnalyticsServersGpuRecord> fromType() {
        return AggregatedMetricsAnalyticsServersGpuRecord.class;
    }

    @Override
    @NotNull
    public Class<AnalyticsServerGpuAggregatedMetrics> toType() {
        return AnalyticsServerGpuAggregatedMetrics.class;
    }
}

