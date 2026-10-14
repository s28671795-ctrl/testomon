/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface StatusOrBuilder
extends MessageOrBuilder {
    public int getCode();

    public String getMessage();

    public ByteString getMessageBytes();

    public List<Any> getDetailsList();

    public Any getDetails(int var1);

    public int getDetailsCount();

    public List<? extends AnyOrBuilder> getDetailsOrBuilderList();

    public AnyOrBuilder getDetailsOrBuilder(int var1);
}

