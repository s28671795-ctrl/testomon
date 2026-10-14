/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface SystemParameterOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getHttpHeader();

    public ByteString getHttpHeaderBytes();

    public String getUrlQueryParameter();

    public ByteString getUrlQueryParameterBytes();
}

