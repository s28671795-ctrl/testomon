/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.aggregated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record StatisticalMetric(@JsonProperty(value="sum") double sum, @JsonProperty(value="max") double max, @JsonProperty(value="measurements_count") int measurementsCount) {
    public StatisticalMetric(double firstMeasurement) {
        this(firstMeasurement, firstMeasurement, 1);
    }

    @JsonIgnore
    public StatisticalMetric withNewMeasurement(double newMeasurement) {
        return new StatisticalMetric(this.sum + newMeasurement, Math.max(this.max, newMeasurement), this.measurementsCount + 1);
    }

    @JsonIgnore
    public double getAverage() {
        return this.sum / (double)this.measurementsCount;
    }
}

