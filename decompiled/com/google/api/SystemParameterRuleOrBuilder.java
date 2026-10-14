/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.SystemParameter;
import com.google.api.SystemParameterOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface SystemParameterRuleOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public List<SystemParameter> getParametersList();

    public SystemParameter getParameters(int var1);

    public int getParametersCount();

    public List<? extends SystemParameterOrBuilder> getParametersOrBuilderList();

    public SystemParameterOrBuilder getParametersOrBuilder(int var1);
}

