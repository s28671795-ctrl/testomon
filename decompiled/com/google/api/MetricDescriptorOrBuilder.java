/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.LabelDescriptor;
import com.google.api.LabelDescriptorOrBuilder;
import com.google.api.LaunchStage;
import com.google.api.MetricDescriptor;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface MetricDescriptorOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getType();

    public ByteString getTypeBytes();

    public List<LabelDescriptor> getLabelsList();

    public LabelDescriptor getLabels(int var1);

    public int getLabelsCount();

    public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList();

    public LabelDescriptorOrBuilder getLabelsOrBuilder(int var1);

    public int getMetricKindValue();

    public MetricDescriptor.MetricKind getMetricKind();

    public int getValueTypeValue();

    public MetricDescriptor.ValueType getValueType();

    public String getUnit();

    public ByteString getUnitBytes();

    public String getDescription();

    public ByteString getDescriptionBytes();

    public String getDisplayName();

    public ByteString getDisplayNameBytes();

    public boolean hasMetadata();

    public MetricDescriptor.MetricDescriptorMetadata getMetadata();

    public MetricDescriptor.MetricDescriptorMetadataOrBuilder getMetadataOrBuilder();

    public int getLaunchStageValue();

    public LaunchStage getLaunchStage();

    public List<String> getMonitoredResourceTypesList();

    public int getMonitoredResourceTypesCount();

    public String getMonitoredResourceTypes(int var1);

    public ByteString getMonitoredResourceTypesBytes(int var1);
}

