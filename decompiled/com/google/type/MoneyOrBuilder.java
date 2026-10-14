/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface MoneyOrBuilder
extends MessageOrBuilder {
    public String getCurrencyCode();

    public ByteString getCurrencyCodeBytes();

    public long getUnits();

    public int getNanos();
}

