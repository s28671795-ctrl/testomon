/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.Map;

public interface MonitoredResourceOrBuilder
extends MessageOrBuilder {
    public String getType();

    public ByteString getTypeBytes();

    public int getLabelsCount();

    public boolean containsLabels(String var1);

    @Deprecated
    public Map<String, String> getLabels();

    public Map<String, String> getLabelsMap();

    public String getLabelsOrDefault(String var1, String var2);

    public String getLabelsOrThrow(String var1);
}

