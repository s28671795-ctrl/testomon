/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface EndpointOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    @Deprecated
    public List<String> getAliasesList();

    @Deprecated
    public int getAliasesCount();

    @Deprecated
    public String getAliases(int var1);

    @Deprecated
    public ByteString getAliasesBytes(int var1);

    public String getTarget();

    public ByteString getTargetBytes();

    public boolean getAllowCors();
}

