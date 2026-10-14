/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface RoutingParameterOrBuilder
extends MessageOrBuilder {
    public String getField();

    public ByteString getFieldBytes();

    public String getPathTemplate();

    public ByteString getPathTemplateBytes();
}

