/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.BackendRule;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.Map;

public interface BackendRuleOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public String getAddress();

    public ByteString getAddressBytes();

    public double getDeadline();

    @Deprecated
    public double getMinDeadline();

    public double getOperationDeadline();

    public int getPathTranslationValue();

    public BackendRule.PathTranslation getPathTranslation();

    public boolean hasJwtAudience();

    public String getJwtAudience();

    public ByteString getJwtAudienceBytes();

    public boolean hasDisableAuth();

    public boolean getDisableAuth();

    public String getProtocol();

    public ByteString getProtocolBytes();

    public int getOverridesByRequestProtocolCount();

    public boolean containsOverridesByRequestProtocol(String var1);

    @Deprecated
    public Map<String, BackendRule> getOverridesByRequestProtocol();

    public Map<String, BackendRule> getOverridesByRequestProtocolMap();

    public BackendRule getOverridesByRequestProtocolOrDefault(String var1, BackendRule var2);

    public BackendRule getOverridesByRequestProtocolOrThrow(String var1);

    public BackendRule.AuthenticationCase getAuthenticationCase();
}

