/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Internal;
import io.grpc.PartialMetricInstrument;
import java.util.List;

@Internal
public final class LongHistogramMetricInstrument
extends PartialMetricInstrument {
    private final List<Long> bucketBoundaries;

    public LongHistogramMetricInstrument(int index, String name, String description, String unit, List<Long> bucketBoundaries, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        super(index, name, description, unit, requiredLabelKeys, optionalLabelKeys, enableByDefault);
        this.bucketBoundaries = bucketBoundaries;
    }

    public List<Long> getBucketBoundaries() {
        return this.bucketBoundaries;
    }
}

