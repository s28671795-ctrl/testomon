/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.Duration;
import com.google.protobuf.DurationOrBuilder;
import com.google.protobuf.MessageOrBuilder;

public interface RetryInfoOrBuilder
extends MessageOrBuilder {
    public boolean hasRetryDelay();

    public Duration getRetryDelay();

    public DurationOrBuilder getRetryDelayOrBuilder();
}

