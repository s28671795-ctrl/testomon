/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.ServiceAccountDelegationInfo;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ServiceAccountDelegationInfoOrBuilder
extends MessageOrBuilder {
    public String getPrincipalSubject();

    public ByteString getPrincipalSubjectBytes();

    public boolean hasFirstPartyPrincipal();

    public ServiceAccountDelegationInfo.FirstPartyPrincipal getFirstPartyPrincipal();

    public ServiceAccountDelegationInfo.FirstPartyPrincipalOrBuilder getFirstPartyPrincipalOrBuilder();

    public boolean hasThirdPartyPrincipal();

    public ServiceAccountDelegationInfo.ThirdPartyPrincipal getThirdPartyPrincipal();

    public ServiceAccountDelegationInfo.ThirdPartyPrincipalOrBuilder getThirdPartyPrincipalOrBuilder();

    public ServiceAccountDelegationInfo.AuthorityCase getAuthorityCase();
}

