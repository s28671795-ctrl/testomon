/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.plugin.commons.database.CRUDRepository;
import com.incoresoft.vezha.database.converters.CoreAggregatedMetricsConverter;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsCoreRecord;
import com.incoresoft.vezha.metrics.aggregated.CoreAggregatedMetrics;
import java.time.Instant;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class CoreAggregatedMetricsRepository
extends CRUDRepository<AggregatedMetricsCoreRecord, CoreAggregatedMetrics> {
    @Inject
    public CoreAggregatedMetricsRepository(@NotNull DSLContext dslContext, @NotNull CoreAggregatedMetricsConverter converter) {
        super(dslContext, converter, Tables.AGGREGATED_METRICS_CORE, Tables.AGGREGATED_METRICS_CORE.ID, Tables.AGGREGATED_METRICS_CORE.CPU_LOAD, Tables.AGGREGATED_METRICS_CORE.CLIENT_ID);
    }

    public Optional<CoreAggregatedMetrics> getByStartTime(Instant startTime) {
        return this.context.selectFrom(Tables.AGGREGATED_METRICS_CORE).where(Tables.AGGREGATED_METRICS_CORE.START_TIME.eq(startTime)).fetchOptional().map(this.converter::from);
    }

    public int deleteByStartTimeLessOrEqual(Instant startTime) {
        return this.context.deleteFrom(Tables.AGGREGATED_METRICS_CORE).where(Tables.AGGREGATED_METRICS_CORE.START_TIME.le(startTime)).execute();
    }
}

