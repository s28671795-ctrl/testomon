/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.RoutingParameter;
import com.google.api.RoutingParameterOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface RoutingRuleOrBuilder
extends MessageOrBuilder {
    public List<RoutingParameter> getRoutingParametersList();

    public RoutingParameter getRoutingParameters(int var1);

    public int getRoutingParametersCount();

    public List<? extends RoutingParameterOrBuilder> getRoutingParametersOrBuilderList();

    public RoutingParameterOrBuilder getRoutingParametersOrBuilder(int var1);
}

