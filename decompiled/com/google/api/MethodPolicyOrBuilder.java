/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.FieldPolicy;
import com.google.api.FieldPolicyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface MethodPolicyOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public List<FieldPolicy> getRequestPoliciesList();

    public FieldPolicy getRequestPolicies(int var1);

    public int getRequestPoliciesCount();

    public List<? extends FieldPolicyOrBuilder> getRequestPoliciesOrBuilderList();

    public FieldPolicyOrBuilder getRequestPoliciesOrBuilder(int var1);
}

