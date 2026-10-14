/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface OperationInfoOrBuilder
extends MessageOrBuilder {
    public String getResponseType();

    public ByteString getResponseTypeBytes();

    public String getMetadataType();

    public ByteString getMetadataTypeBytes();
}

