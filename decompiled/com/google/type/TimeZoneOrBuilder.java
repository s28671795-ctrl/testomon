/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface TimeZoneOrBuilder
extends MessageOrBuilder {
    public String getId();

    public ByteString getIdBytes();

    public String getVersion();

    public ByteString getVersionBytes();
}

