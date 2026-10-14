/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Property;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface PropertyOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public int getTypeValue();

    public Property.PropertyType getType();

    public String getDescription();

    public ByteString getDescriptionBytes();
}

