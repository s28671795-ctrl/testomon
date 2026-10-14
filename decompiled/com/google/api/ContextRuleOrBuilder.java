/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ContextRuleOrBuilder
extends MessageOrBuilder {
    public String getSelector();

    public ByteString getSelectorBytes();

    public List<String> getRequestedList();

    public int getRequestedCount();

    public String getRequested(int var1);

    public ByteString getRequestedBytes(int var1);

    public List<String> getProvidedList();

    public int getProvidedCount();

    public String getProvided(int var1);

    public ByteString getProvidedBytes(int var1);

    public List<String> getAllowedRequestExtensionsList();

    public int getAllowedRequestExtensionsCount();

    public String getAllowedRequestExtensions(int var1);

    public ByteString getAllowedRequestExtensionsBytes(int var1);

    public List<String> getAllowedResponseExtensionsList();

    public int getAllowedResponseExtensionsCount();

    public String getAllowedResponseExtensions(int var1);

    public ByteString getAllowedResponseExtensionsBytes(int var1);
}

