/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.MetricRule;
import com.google.api.MetricRuleOrBuilder;
import com.google.api.QuotaLimit;
import com.google.api.QuotaLimitOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface QuotaOrBuilder
extends MessageOrBuilder {
    public List<QuotaLimit> getLimitsList();

    public QuotaLimit getLimits(int var1);

    public int getLimitsCount();

    public List<? extends QuotaLimitOrBuilder> getLimitsOrBuilderList();

    public QuotaLimitOrBuilder getLimitsOrBuilder(int var1);

    public List<MetricRule> getMetricRulesList();

    public MetricRule getMetricRules(int var1);

    public int getMetricRulesCount();

    public List<? extends MetricRuleOrBuilder> getMetricRulesOrBuilderList();

    public MetricRuleOrBuilder getMetricRulesOrBuilder(int var1);
}

