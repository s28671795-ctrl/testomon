/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ResourceReferenceOrBuilder
extends MessageOrBuilder {
    public String getType();

    public ByteString getTypeBytes();

    public String getChildType();

    public ByteString getChildTypeBytes();
}

