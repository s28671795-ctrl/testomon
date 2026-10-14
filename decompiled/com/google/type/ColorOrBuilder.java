/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.FloatValue;
import com.google.protobuf.FloatValueOrBuilder;
import com.google.protobuf.MessageOrBuilder;

public interface ColorOrBuilder
extends MessageOrBuilder {
    public float getRed();

    public float getGreen();

    public float getBlue();

    public boolean hasAlpha();

    public FloatValue getAlpha();

    public FloatValueOrBuilder getAlphaOrBuilder();
}

