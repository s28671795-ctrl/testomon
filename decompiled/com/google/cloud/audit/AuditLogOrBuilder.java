/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuthenticationInfo;
import com.google.cloud.audit.AuthenticationInfoOrBuilder;
import com.google.cloud.audit.AuthorizationInfo;
import com.google.cloud.audit.AuthorizationInfoOrBuilder;
import com.google.cloud.audit.PolicyViolationInfo;
import com.google.cloud.audit.PolicyViolationInfoOrBuilder;
import com.google.cloud.audit.RequestMetadata;
import com.google.cloud.audit.RequestMetadataOrBuilder;
import com.google.cloud.audit.ResourceLocation;
import com.google.cloud.audit.ResourceLocationOrBuilder;
import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import com.google.rpc.Status;
import com.google.rpc.StatusOrBuilder;
import java.util.List;

public interface AuditLogOrBuilder
extends MessageOrBuilder {
    public String getServiceName();

    public ByteString getServiceNameBytes();

    public String getMethodName();

    public ByteString getMethodNameBytes();

    public String getResourceName();

    public ByteString getResourceNameBytes();

    public boolean hasResourceLocation();

    public ResourceLocation getResourceLocation();

    public ResourceLocationOrBuilder getResourceLocationOrBuilder();

    public boolean hasResourceOriginalState();

    public Struct getResourceOriginalState();

    public StructOrBuilder getResourceOriginalStateOrBuilder();

    public long getNumResponseItems();

    public boolean hasStatus();

    public Status getStatus();

    public StatusOrBuilder getStatusOrBuilder();

    public boolean hasAuthenticationInfo();

    public AuthenticationInfo getAuthenticationInfo();

    public AuthenticationInfoOrBuilder getAuthenticationInfoOrBuilder();

    public List<AuthorizationInfo> getAuthorizationInfoList();

    public AuthorizationInfo getAuthorizationInfo(int var1);

    public int getAuthorizationInfoCount();

    public List<? extends AuthorizationInfoOrBuilder> getAuthorizationInfoOrBuilderList();

    public AuthorizationInfoOrBuilder getAuthorizationInfoOrBuilder(int var1);

    public boolean hasPolicyViolationInfo();

    public PolicyViolationInfo getPolicyViolationInfo();

    public PolicyViolationInfoOrBuilder getPolicyViolationInfoOrBuilder();

    public boolean hasRequestMetadata();

    public RequestMetadata getRequestMetadata();

    public RequestMetadataOrBuilder getRequestMetadataOrBuilder();

    public boolean hasRequest();

    public Struct getRequest();

    public StructOrBuilder getRequestOrBuilder();

    public boolean hasResponse();

    public Struct getResponse();

    public StructOrBuilder getResponseOrBuilder();

    public boolean hasMetadata();

    public Struct getMetadata();

    public StructOrBuilder getMetadataOrBuilder();

    @Deprecated
    public boolean hasServiceData();

    @Deprecated
    public Any getServiceData();

    @Deprecated
    public AnyOrBuilder getServiceDataOrBuilder();
}

