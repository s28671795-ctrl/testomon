/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface LocalizedMessageOrBuilder
extends MessageOrBuilder {
    public String getLocale();

    public ByteString getLocaleBytes();

    public String getMessage();

    public ByteString getMessageBytes();
}

