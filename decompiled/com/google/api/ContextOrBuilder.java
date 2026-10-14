/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ContextRule;
import com.google.api.ContextRuleOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ContextOrBuilder
extends MessageOrBuilder {
    public List<ContextRule> getRulesList();

    public ContextRule getRules(int var1);

    public int getRulesCount();

    public List<? extends ContextRuleOrBuilder> getRulesOrBuilderList();

    public ContextRuleOrBuilder getRulesOrBuilder(int var1);
}

