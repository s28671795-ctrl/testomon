/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Timestamp;
import com.google.protobuf.TimestampOrBuilder;

public interface IntervalOrBuilder
extends MessageOrBuilder {
    public boolean hasStartTime();

    public Timestamp getStartTime();

    public TimestampOrBuilder getStartTimeOrBuilder();

    public boolean hasEndTime();

    public Timestamp getEndTime();

    public TimestampOrBuilder getEndTimeOrBuilder();
}

