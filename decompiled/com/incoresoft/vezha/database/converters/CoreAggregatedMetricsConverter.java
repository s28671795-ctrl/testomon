/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.database.CRUDConverter;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsCoreRecord;
import com.incoresoft.vezha.metrics.aggregated.CoreAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;
import org.jetbrains.annotations.NotNull;

public class CoreAggregatedMetricsConverter
implements CRUDConverter<AggregatedMetricsCoreRecord, CoreAggregatedMetrics> {
    @Override
    public CoreAggregatedMetrics from(AggregatedMetricsCoreRecord record) {
        return new CoreAggregatedMetrics(record.getId(), record.getStartTime(), Serialization.readValue(record.getCpuLoad(), StatisticalMetric.class), record.getStartupsNumber(), record.getClientId());
    }

    @Override
    public AggregatedMetricsCoreRecord to(CoreAggregatedMetrics userObject) {
        return new AggregatedMetricsCoreRecord(userObject.getId(), userObject.startTime(), Serialization.writeValueAsString(userObject.cpuLoad()), userObject.startupsNumber(), userObject.clientId());
    }

    @Override
    @NotNull
    public Class<AggregatedMetricsCoreRecord> fromType() {
        return AggregatedMetricsCoreRecord.class;
    }

    @Override
    @NotNull
    public Class<CoreAggregatedMetrics> toType() {
        return CoreAggregatedMetrics.class;
    }
}

