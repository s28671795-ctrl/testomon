/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface AuthRequirementOrBuilder
extends MessageOrBuilder {
    public String getProviderId();

    public ByteString getProviderIdBytes();

    public String getAudiences();

    public ByteString getAudiencesBytes();
}

