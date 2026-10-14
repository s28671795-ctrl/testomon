/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.database.CRUDConverter;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsAnalyticsServersRecord;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;
import org.jetbrains.annotations.NotNull;

public class AnalyticsServersMetricsConverter
implements CRUDConverter<AggregatedMetricsAnalyticsServersRecord, AnalyticsServerAggregatedMetrics> {
    @Override
    public AnalyticsServerAggregatedMetrics from(AggregatedMetricsAnalyticsServersRecord record) {
        return new AnalyticsServerAggregatedMetrics(record.getId(), record.getStartTime(), record.getServerId(), record.getIpAddress(), Serialization.readValue(record.getCpuLoad(), StatisticalMetric.class), record.getClientId());
    }

    @Override
    public AggregatedMetricsAnalyticsServersRecord to(AnalyticsServerAggregatedMetrics userObject) {
        return new AggregatedMetricsAnalyticsServersRecord(userObject.getId(), userObject.startTime(), userObject.serverId(), userObject.ipAddress(), Serialization.writeValueAsString(userObject.cpuLoad()), userObject.clientId());
    }

    @Override
    @NotNull
    public Class<AggregatedMetricsAnalyticsServersRecord> fromType() {
        return AggregatedMetricsAnalyticsServersRecord.class;
    }

    @Override
    @NotNull
    public Class<AnalyticsServerAggregatedMetrics> toType() {
        return AnalyticsServerAggregatedMetrics.class;
    }
}

