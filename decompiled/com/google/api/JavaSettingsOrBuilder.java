/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.CommonLanguageSettings;
import com.google.api.CommonLanguageSettingsOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.Map;

public interface JavaSettingsOrBuilder
extends MessageOrBuilder {
    public String getLibraryPackage();

    public ByteString getLibraryPackageBytes();

    public int getServiceClassNamesCount();

    public boolean containsServiceClassNames(String var1);

    @Deprecated
    public Map<String, String> getServiceClassNames();

    public Map<String, String> getServiceClassNamesMap();

    public String getServiceClassNamesOrDefault(String var1, String var2);

    public String getServiceClassNamesOrThrow(String var1);

    public boolean hasCommon();

    public CommonLanguageSettings getCommon();

    public CommonLanguageSettingsOrBuilder getCommonOrBuilder();
}

