/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface SourceInfoOrBuilder
extends MessageOrBuilder {
    public List<Any> getSourceFilesList();

    public Any getSourceFiles(int var1);

    public int getSourceFilesCount();

    public List<? extends AnyOrBuilder> getSourceFilesOrBuilderList();

    public AnyOrBuilder getSourceFilesOrBuilder(int var1);
}

