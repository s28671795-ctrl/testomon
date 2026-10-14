/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface UsageRuleOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public boolean getAllowUnregisteredCalls();

    public boolean getSkipServiceControl();
}

