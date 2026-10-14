/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface RequestInfoOrBuilder
extends MessageOrBuilder {
    public String getRequestId();

    public ByteString getRequestIdBytes();

    public String getServingData();

    public ByteString getServingDataBytes();
}

