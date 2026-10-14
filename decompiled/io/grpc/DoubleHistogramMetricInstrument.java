/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Internal;
import io.grpc.PartialMetricInstrument;
import java.util.List;

@Internal
public final class DoubleHistogramMetricInstrument
extends PartialMetricInstrument {
    private final List<Double> bucketBoundaries;

    public DoubleHistogramMetricInstrument(int index, String name, String description, String unit, List<Double> bucketBoundaries, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        super(index, name, description, unit, requiredLabelKeys, optionalLabelKeys, enableByDefault);
        this.bucketBoundaries = bucketBoundaries;
    }

    public List<Double> getBucketBoundaries() {
        return this.bucketBoundaries;
    }
}

