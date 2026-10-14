/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ExprOrBuilder
extends MessageOrBuilder {
    public String getExpression();

    public ByteString getExpressionBytes();

    public String getTitle();

    public ByteString getTitleBytes();

    public String getDescription();

    public ByteString getDescriptionBytes();

    public String getLocation();

    public ByteString getLocationBytes();
}

