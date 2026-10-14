/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.location;

import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.Map;

public interface LocationOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getLocationId();

    public ByteString getLocationIdBytes();

    public String getDisplayName();

    public ByteString getDisplayNameBytes();

    public int getLabelsCount();

    public boolean containsLabels(String var1);

    @Deprecated
    public Map<String, String> getLabels();

    public Map<String, String> getLabelsMap();

    public String getLabelsOrDefault(String var1, String var2);

    public String getLabelsOrThrow(String var1);

    public boolean hasMetadata();

    public Any getMetadata();

    public AnyOrBuilder getMetadataOrBuilder();
}

