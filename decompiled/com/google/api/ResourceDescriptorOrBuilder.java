/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ResourceDescriptor;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ResourceDescriptorOrBuilder
extends MessageOrBuilder {
    public String getType();

    public ByteString getTypeBytes();

    public List<String> getPatternList();

    public int getPatternCount();

    public String getPattern(int var1);

    public ByteString getPatternBytes(int var1);

    public String getNameField();

    public ByteString getNameFieldBytes();

    public int getHistoryValue();

    public ResourceDescriptor.History getHistory();

    public String getPlural();

    public ByteString getPluralBytes();

    public String getSingular();

    public ByteString getSingularBytes();

    public List<ResourceDescriptor.Style> getStyleList();

    public int getStyleCount();

    public ResourceDescriptor.Style getStyle(int var1);

    public List<Integer> getStyleValueList();

    public int getStyleValue(int var1);
}

