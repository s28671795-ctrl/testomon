/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Duration;
import com.google.protobuf.DurationOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import com.google.type.DateTime;
import com.google.type.TimeZone;
import com.google.type.TimeZoneOrBuilder;

public interface DateTimeOrBuilder
extends MessageOrBuilder {
    public int getYear();

    public int getMonth();

    public int getDay();

    public int getHours();

    public int getMinutes();

    public int getSeconds();

    public int getNanos();

    public boolean hasUtcOffset();

    public Duration getUtcOffset();

    public DurationOrBuilder getUtcOffsetOrBuilder();

    public boolean hasTimeZone();

    public TimeZone getTimeZone();

    public TimeZoneOrBuilder getTimeZoneOrBuilder();

    public DateTime.TimeOffsetCase getTimeOffsetCase();
}

