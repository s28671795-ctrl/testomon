/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.protobuf.ByteString;
import com.google.protobuf.Duration;
import com.google.protobuf.DurationOrBuilder;
import com.google.protobuf.MessageOrBuilder;

public interface WaitOperationRequestOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public boolean hasTimeout();

    public Duration getTimeout();

    public DurationOrBuilder getTimeoutOrBuilder();
}

