/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.VisibilityRule;
import com.google.api.VisibilityRuleOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface VisibilityOrBuilder
extends MessageOrBuilder {
    public List<VisibilityRule> getRulesList();

    public VisibilityRule getRules(int var1);

    public int getRulesCount();

    public List<? extends VisibilityRuleOrBuilder> getRulesOrBuilderList();

    public VisibilityRuleOrBuilder getRulesOrBuilder(int var1);
}

