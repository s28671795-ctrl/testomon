/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.MethodPolicy;
import com.google.api.MethodPolicyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ControlOrBuilder
extends MessageOrBuilder {
    public String getEnvironment();

    public ByteString getEnvironmentBytes();

    public List<MethodPolicy> getMethodPoliciesList();

    public MethodPolicy getMethodPolicies(int var1);

    public int getMethodPoliciesCount();

    public List<? extends MethodPolicyOrBuilder> getMethodPoliciesOrBuilderList();

    public MethodPolicyOrBuilder getMethodPoliciesOrBuilder(int var1);
}

