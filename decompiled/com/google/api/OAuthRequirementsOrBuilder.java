/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface OAuthRequirementsOrBuilder
extends MessageOrBuilder {
    public String getCanonicalScopes();

    public ByteString getCanonicalScopesBytes();
}

