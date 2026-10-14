/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface HttpBodyOrBuilder
extends MessageOrBuilder {
    public String getContentType();

    public ByteString getContentTypeBytes();

    public ByteString getData();

    public List<Any> getExtensionsList();

    public Any getExtensions(int var1);

    public int getExtensionsCount();

    public List<? extends AnyOrBuilder> getExtensionsOrBuilderList();

    public AnyOrBuilder getExtensionsOrBuilder(int var1);
}

