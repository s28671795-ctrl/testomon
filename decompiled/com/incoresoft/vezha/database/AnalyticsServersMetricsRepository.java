/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.plugin.commons.database.CRUDRepository;
import com.incoresoft.vezha.database.converters.AnalyticsServersMetricsConverter;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsAnalyticsServersRecord;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerAggregatedMetrics;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class AnalyticsServersMetricsRepository
extends CRUDRepository<AggregatedMetricsAnalyticsServersRecord, AnalyticsServerAggregatedMetrics> {
    @Inject
    protected AnalyticsServersMetricsRepository(@NotNull DSLContext context, @NotNull AnalyticsServersMetricsConverter converter) {
        super(context, converter, Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS, Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS.ID, Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS.SERVER_ID, Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS.CLIENT_ID);
    }

    public List<AnalyticsServerAggregatedMetrics> getByStartTime(Instant startTime) {
        return this.context.selectFrom(Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS).where(Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS.START_TIME.eq(startTime)).fetch(this.converter);
    }

    public Optional<AnalyticsServerAggregatedMetrics> getByServerIdAndStartTime(String serverId, Instant startTime) {
        return this.context.selectFrom(Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS).where(Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS.START_TIME.eq(startTime).and(Tables.AGGREGATED_METRICS_ANALYTICS_SERVERS.SERVER_ID.eq(serverId))).fetchOptional(this.converter);
    }
}

