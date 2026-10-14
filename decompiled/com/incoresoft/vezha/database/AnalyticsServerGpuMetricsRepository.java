/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.plugin.commons.database.CRUDRepository;
import com.incoresoft.vezha.database.converters.AnalyticsServerGpuMetricsConverter;
import com.incoresoft.vezha.generated.tables.AggregatedMetricsAnalyticsServersGpu;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsAnalyticsServersGpuRecord;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerGpuAggregatedMetrics;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class AnalyticsServerGpuMetricsRepository
extends CRUDRepository<AggregatedMetricsAnalyticsServersGpuRecord, AnalyticsServerGpuAggregatedMetrics> {
    @Inject
    protected AnalyticsServerGpuMetricsRepository(@NotNull DSLContext dslContext, @NotNull AnalyticsServerGpuMetricsConverter converter) {
        super(dslContext, converter, AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU, AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.ID, AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.GPU_NAME, AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.CLIENT_ID);
    }

    public Optional<AnalyticsServerGpuAggregatedMetrics> getByServerMetricsIdAnsGpuId(int serverMetricsId, int gpuId) {
        return this.context.selectFrom(this.table).where(AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.SERVER_METRICS_ID.eq(serverMetricsId).and(AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.GPU_ID.eq(gpuId))).fetchOptional().map(this.converter);
    }

    public List<AnalyticsServerGpuAggregatedMetrics> getByServerMetricsId(int id) {
        return this.context.selectFrom(this.table).where(AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.SERVER_METRICS_ID.eq(id)).fetch().map(this.converter);
    }
}

