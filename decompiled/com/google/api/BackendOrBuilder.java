/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.BackendRule;
import com.google.api.BackendRuleOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface BackendOrBuilder
extends MessageOrBuilder {
    public List<BackendRule> getRulesList();

    public BackendRule getRules(int var1);

    public int getRulesCount();

    public List<? extends BackendRuleOrBuilder> getRulesOrBuilderList();

    public BackendRuleOrBuilder getRulesOrBuilder(int var1);
}

