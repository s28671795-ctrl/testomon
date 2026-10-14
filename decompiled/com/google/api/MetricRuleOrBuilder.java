/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.Map;

public interface MetricRuleOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public int getMetricCostsCount();

    public boolean containsMetricCosts(String var1);

    @Deprecated
    public Map<String, Long> getMetricCosts();

    public Map<String, Long> getMetricCostsMap();

    public long getMetricCostsOrDefault(String var1, long var2);

    public long getMetricCostsOrThrow(String var1);
}

