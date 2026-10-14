/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.collect.ImmutableList;
import io.grpc.Internal;
import io.grpc.MetricInstrument;
import java.util.List;

@Internal
abstract class PartialMetricInstrument
implements MetricInstrument {
    protected final int index;
    protected final String name;
    protected final String description;
    protected final String unit;
    protected final List<String> requiredLabelKeys;
    protected final List<String> optionalLabelKeys;
    protected final boolean enableByDefault;

    protected PartialMetricInstrument(int index, String name, String description, String unit, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        this.index = index;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.requiredLabelKeys = ImmutableList.copyOf(requiredLabelKeys);
        this.optionalLabelKeys = ImmutableList.copyOf(optionalLabelKeys);
        this.enableByDefault = enableByDefault;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public List<String> getRequiredLabelKeys() {
        return this.requiredLabelKeys;
    }

    @Override
    public List<String> getOptionalLabelKeys() {
        return this.optionalLabelKeys;
    }

    @Override
    public boolean isEnableByDefault() {
        return this.enableByDefault;
    }
}

