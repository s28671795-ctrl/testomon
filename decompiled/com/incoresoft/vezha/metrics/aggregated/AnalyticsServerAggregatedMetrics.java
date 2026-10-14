/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.aggregated;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;
import java.time.Instant;

public record AnalyticsServerAggregatedMetrics(int id, Instant startTime, String serverId, String ipAddress, StatisticalMetric cpuLoad, int clientId) implements Model
{
    public AnalyticsServerAggregatedMetrics(Instant startTime, String serverId, String ipAddress, StatisticalMetric cpuLoad) {
        this(0, startTime, serverId, ipAddress, cpuLoad, 0);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }
}

