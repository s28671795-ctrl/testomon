/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Advice;
import com.google.api.AdviceOrBuilder;
import com.google.api.ChangeType;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ConfigChangeOrBuilder
extends MessageOrBuilder {
    public String getElement();

    public ByteString getElementBytes();

    public String getOldValue();

    public ByteString getOldValueBytes();

    public String getNewValue();

    public ByteString getNewValueBytes();

    public int getChangeTypeValue();

    public ChangeType getChangeType();

    public List<Advice> getAdvicesList();

    public Advice getAdvices(int var1);

    public int getAdvicesCount();

    public List<? extends AdviceOrBuilder> getAdvicesOrBuilderList();

    public AdviceOrBuilder getAdvicesOrBuilder(int var1);
}

