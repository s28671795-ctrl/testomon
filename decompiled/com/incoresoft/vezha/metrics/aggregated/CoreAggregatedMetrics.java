/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.aggregated;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;
import java.time.Instant;

public record CoreAggregatedMetrics(int id, Instant startTime, StatisticalMetric cpuLoad, int startupsNumber, int clientId) implements Model
{
    public CoreAggregatedMetrics(Instant startTime, StatisticalMetric cpuLoad) {
        this(0, startTime, cpuLoad, 0, 0);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public CoreAggregatedMetrics withId(int id) {
        return this.id == id ? this : new CoreAggregatedMetrics(id, this.startTime, this.cpuLoad, this.startupsNumber, this.clientId);
    }

    public CoreAggregatedMetrics withStartTime(Instant startTime) {
        return this.startTime == startTime ? this : new CoreAggregatedMetrics(this.id, startTime, this.cpuLoad, this.startupsNumber, this.clientId);
    }

    public CoreAggregatedMetrics withCpuLoad(StatisticalMetric cpuLoad) {
        return this.cpuLoad == cpuLoad ? this : new CoreAggregatedMetrics(this.id, this.startTime, cpuLoad, this.startupsNumber, this.clientId);
    }

    public CoreAggregatedMetrics withStartupsNumber(int startupsNumber) {
        return this.startupsNumber == startupsNumber ? this : new CoreAggregatedMetrics(this.id, this.startTime, this.cpuLoad, startupsNumber, this.clientId);
    }

    public CoreAggregatedMetrics withClientId(int clientId) {
        return this.clientId == clientId ? this : new CoreAggregatedMetrics(this.id, this.startTime, this.cpuLoad, this.startupsNumber, clientId);
    }
}

