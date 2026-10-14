/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface DocumentationRuleOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public String getDescription();

    public ByteString getDescriptionBytes();

    public String getDeprecationDescription();

    public ByteString getDeprecationDescriptionBytes();
}

