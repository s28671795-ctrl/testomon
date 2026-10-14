/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.HttpRule;
import com.google.api.HttpRuleOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface HttpOrBuilder
extends MessageOrBuilder {
    public List<HttpRule> getRulesList();

    public HttpRule getRules(int var1);

    public int getRulesCount();

    public List<? extends HttpRuleOrBuilder> getRulesOrBuilderList();

    public HttpRuleOrBuilder getRulesOrBuilder(int var1);

    public boolean getFullyDecodeReservedExpansion();
}

