/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Page;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface PageOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getContent();

    public ByteString getContentBytes();

    public List<Page> getSubpagesList();

    public Page getSubpages(int var1);

    public int getSubpagesCount();

    public List<? extends PageOrBuilder> getSubpagesOrBuilderList();

    public PageOrBuilder getSubpagesOrBuilder(int var1);
}

