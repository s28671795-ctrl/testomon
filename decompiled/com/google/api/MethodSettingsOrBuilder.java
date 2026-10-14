/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.MethodSettings;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface MethodSettingsOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public boolean hasLongRunning();

    public MethodSettings.LongRunning getLongRunning();

    public MethodSettings.LongRunningOrBuilder getLongRunningOrBuilder();
}

