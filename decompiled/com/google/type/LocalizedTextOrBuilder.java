/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface LocalizedTextOrBuilder
extends MessageOrBuilder {
    public String getText();

    public ByteString getTextBytes();

    public String getLanguageCode();

    public ByteString getLanguageCodeBytes();
}

