/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import java.util.Map;

public interface MonitoredResourceMetadataOrBuilder
extends MessageOrBuilder {
    public boolean hasSystemLabels();

    public Struct getSystemLabels();

    public StructOrBuilder getSystemLabelsOrBuilder();

    public int getUserLabelsCount();

    public boolean containsUserLabels(String var1);

    @Deprecated
    public Map<String, String> getUserLabels();

    public Map<String, String> getUserLabelsMap();

    public String getUserLabelsOrDefault(String var1, String var2);

    public String getUserLabelsOrThrow(String var1);
}

