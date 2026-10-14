/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.CommonLanguageSettings;
import com.google.api.CommonLanguageSettingsOrBuilder;
import com.google.protobuf.MessageOrBuilder;

public interface NodeSettingsOrBuilder
extends MessageOrBuilder {
    public boolean hasCommon();

    public CommonLanguageSettings getCommon();

    public CommonLanguageSettingsOrBuilder getCommonOrBuilder();
}

