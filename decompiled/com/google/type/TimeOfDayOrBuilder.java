/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.MessageOrBuilder;

public interface TimeOfDayOrBuilder
extends MessageOrBuilder {
    public int getHours();

    public int getMinutes();

    public int getSeconds();

    public int getNanos();
}

