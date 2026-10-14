/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.UsageRule;
import com.google.api.UsageRuleOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface UsageOrBuilder
extends MessageOrBuilder {
    public List<String> getRequirementsList();

    public int getRequirementsCount();

    public String getRequirements(int var1);

    public ByteString getRequirementsBytes(int var1);

    public List<UsageRule> getRulesList();

    public UsageRule getRules(int var1);

    public int getRulesCount();

    public List<? extends UsageRuleOrBuilder> getRulesOrBuilderList();

    public UsageRuleOrBuilder getRulesOrBuilder(int var1);

    public String getProducerNotificationChannel();

    public ByteString getProducerNotificationChannelBytes();
}

