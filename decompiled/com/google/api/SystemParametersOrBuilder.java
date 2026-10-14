/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.SystemParameterRule;
import com.google.api.SystemParameterRuleOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface SystemParametersOrBuilder
extends MessageOrBuilder {
    public List<SystemParameterRule> getRulesList();

    public SystemParameterRule getRules(int var1);

    public int getRulesCount();

    public List<? extends SystemParameterRuleOrBuilder> getRulesOrBuilderList();

    public SystemParameterRuleOrBuilder getRulesOrBuilder(int var1);
}

