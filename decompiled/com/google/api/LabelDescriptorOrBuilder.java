/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.LabelDescriptor;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface LabelDescriptorOrBuilder
extends MessageOrBuilder {
    public String getKey();

    public ByteString getKeyBytes();

    public int getValueTypeValue();

    public LabelDescriptor.ValueType getValueType();

    public String getDescription();

    public ByteString getDescriptionBytes();
}

