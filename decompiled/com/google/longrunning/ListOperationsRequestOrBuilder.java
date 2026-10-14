/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ListOperationsRequestOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getFilter();

    public ByteString getFilterBytes();

    public int getPageSize();

    public String getPageToken();

    public ByteString getPageTokenBytes();
}

