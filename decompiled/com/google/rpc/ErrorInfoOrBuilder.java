/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.Map;

public interface ErrorInfoOrBuilder
extends MessageOrBuilder {
    public String getReason();

    public ByteString getReasonBytes();

    public String getDomain();

    public ByteString getDomainBytes();

    public int getMetadataCount();

    public boolean containsMetadata(String var1);

    @Deprecated
    public Map<String, String> getMetadata();

    public Map<String, String> getMetadataMap();

    public String getMetadataOrDefault(String var1, String var2);

    public String getMetadataOrThrow(String var1);
}

