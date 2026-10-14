/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.CommonLanguageSettings;
import com.google.api.CommonLanguageSettingsOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;
import java.util.Map;

public interface DotnetSettingsOrBuilder
extends MessageOrBuilder {
    public boolean hasCommon();

    public CommonLanguageSettings getCommon();

    public CommonLanguageSettingsOrBuilder getCommonOrBuilder();

    public int getRenamedServicesCount();

    public boolean containsRenamedServices(String var1);

    @Deprecated
    public Map<String, String> getRenamedServices();

    public Map<String, String> getRenamedServicesMap();

    public String getRenamedServicesOrDefault(String var1, String var2);

    public String getRenamedServicesOrThrow(String var1);

    public int getRenamedResourcesCount();

    public boolean containsRenamedResources(String var1);

    @Deprecated
    public Map<String, String> getRenamedResources();

    public Map<String, String> getRenamedResourcesMap();

    public String getRenamedResourcesOrDefault(String var1, String var2);

    public String getRenamedResourcesOrThrow(String var1);

    public List<String> getIgnoredResourcesList();

    public int getIgnoredResourcesCount();

    public String getIgnoredResources(int var1);

    public ByteString getIgnoredResourcesBytes(int var1);

    public List<String> getForcedNamespaceAliasesList();

    public int getForcedNamespaceAliasesCount();

    public String getForcedNamespaceAliases(int var1);

    public ByteString getForcedNamespaceAliasesBytes(int var1);

    public List<String> getHandwrittenSignaturesList();

    public int getHandwrittenSignaturesCount();

    public String getHandwrittenSignatures(int var1);

    public ByteString getHandwrittenSignaturesBytes(int var1);
}

