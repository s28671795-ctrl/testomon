/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.AuthProvider;
import com.google.api.AuthProviderOrBuilder;
import com.google.api.AuthenticationRule;
import com.google.api.AuthenticationRuleOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface AuthenticationOrBuilder
extends MessageOrBuilder {
    public List<AuthenticationRule> getRulesList();

    public AuthenticationRule getRules(int var1);

    public int getRulesCount();

    public List<? extends AuthenticationRuleOrBuilder> getRulesOrBuilderList();

    public AuthenticationRuleOrBuilder getRulesOrBuilder(int var1);

    public List<AuthProvider> getProvidersList();

    public AuthProvider getProviders(int var1);

    public int getProvidersCount();

    public List<? extends AuthProviderOrBuilder> getProvidersOrBuilderList();

    public AuthProviderOrBuilder getProvidersOrBuilder(int var1);
}

