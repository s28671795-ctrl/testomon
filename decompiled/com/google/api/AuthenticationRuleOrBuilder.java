/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.AuthRequirement;
import com.google.api.AuthRequirementOrBuilder;
import com.google.api.OAuthRequirements;
import com.google.api.OAuthRequirementsOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface AuthenticationRuleOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public boolean hasOauth();

    public OAuthRequirements getOauth();

    public OAuthRequirementsOrBuilder getOauthOrBuilder();

    public boolean getAllowWithoutCredential();

    public List<AuthRequirement> getRequirementsList();

    public AuthRequirement getRequirements(int var1);

    public int getRequirementsCount();

    public List<? extends AuthRequirementOrBuilder> getRequirementsOrBuilderList();

    public AuthRequirementOrBuilder getRequirementsOrBuilder(int var1);
}

