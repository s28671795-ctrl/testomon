/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface DebugInfoOrBuilder
extends MessageOrBuilder {
    public List<String> getStackEntriesList();

    public int getStackEntriesCount();

    public String getStackEntries(int var1);

    public ByteString getStackEntriesBytes(int var1);

    public String getDetail();

    public ByteString getDetailBytes();
}

