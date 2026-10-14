/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.Map;

public interface QuotaLimitOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getDescription();

    public ByteString getDescriptionBytes();

    public long getDefaultLimit();

    public long getMaxLimit();

    public long getFreeTier();

    public String getDuration();

    public ByteString getDurationBytes();

    public String getMetric();

    public ByteString getMetricBytes();

    public String getUnit();

    public ByteString getUnitBytes();

    public int getValuesCount();

    public boolean containsValues(String var1);

    @Deprecated
    public Map<String, Long> getValues();

    public Map<String, Long> getValuesMap();

    public long getValuesOrDefault(String var1, long var2);

    public long getValuesOrThrow(String var1);

    public String getDisplayName();

    public ByteString getDisplayNameBytes();
}

