/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.ViolationInfo;
import com.google.cloud.audit.ViolationInfoOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import java.util.List;
import java.util.Map;

public interface OrgPolicyViolationInfoOrBuilder
extends MessageOrBuilder {
    public boolean hasPayload();

    public Struct getPayload();

    public StructOrBuilder getPayloadOrBuilder();

    public String getResourceType();

    public ByteString getResourceTypeBytes();

    public int getResourceTagsCount();

    public boolean containsResourceTags(String var1);

    @Deprecated
    public Map<String, String> getResourceTags();

    public Map<String, String> getResourceTagsMap();

    public String getResourceTagsOrDefault(String var1, String var2);

    public String getResourceTagsOrThrow(String var1);

    public List<ViolationInfo> getViolationInfoList();

    public ViolationInfo getViolationInfo(int var1);

    public int getViolationInfoCount();

    public List<? extends ViolationInfoOrBuilder> getViolationInfoOrBuilderList();

    public ViolationInfoOrBuilder getViolationInfoOrBuilder(int var1);
}

