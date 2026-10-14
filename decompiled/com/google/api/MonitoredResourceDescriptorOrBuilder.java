/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.LabelDescriptor;
import com.google.api.LabelDescriptorOrBuilder;
import com.google.api.LaunchStage;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface MonitoredResourceDescriptorOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getType();

    public ByteString getTypeBytes();

    public String getDisplayName();

    public ByteString getDisplayNameBytes();

    public String getDescription();

    public ByteString getDescriptionBytes();

    public List<LabelDescriptor> getLabelsList();

    public LabelDescriptor getLabels(int var1);

    public int getLabelsCount();

    public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList();

    public LabelDescriptorOrBuilder getLabelsOrBuilder(int var1);

    public int getLaunchStageValue();

    public LaunchStage getLaunchStage();
}

