/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.ServiceAccountDelegationInfo;
import com.google.cloud.audit.ServiceAccountDelegationInfoOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import java.util.List;

public interface AuthenticationInfoOrBuilder
extends MessageOrBuilder {
    public String getPrincipalEmail();

    public ByteString getPrincipalEmailBytes();

    public String getAuthoritySelector();

    public ByteString getAuthoritySelectorBytes();

    public boolean hasThirdPartyPrincipal();

    public Struct getThirdPartyPrincipal();

    public StructOrBuilder getThirdPartyPrincipalOrBuilder();

    public String getServiceAccountKeyName();

    public ByteString getServiceAccountKeyNameBytes();

    public List<ServiceAccountDelegationInfo> getServiceAccountDelegationInfoList();

    public ServiceAccountDelegationInfo getServiceAccountDelegationInfo(int var1);

    public int getServiceAccountDelegationInfoCount();

    public List<? extends ServiceAccountDelegationInfoOrBuilder> getServiceAccountDelegationInfoOrBuilderList();

    public ServiceAccountDelegationInfoOrBuilder getServiceAccountDelegationInfoOrBuilder(int var1);

    public String getPrincipalSubject();

    public ByteString getPrincipalSubjectBytes();
}

