/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.OrgPolicyViolationInfo;
import com.google.cloud.audit.OrgPolicyViolationInfoOrBuilder;
import com.google.protobuf.MessageOrBuilder;

public interface PolicyViolationInfoOrBuilder
extends MessageOrBuilder {
    public boolean hasOrgPolicyViolationInfo();

    public OrgPolicyViolationInfo getOrgPolicyViolationInfo();

    public OrgPolicyViolationInfoOrBuilder getOrgPolicyViolationInfoOrBuilder();
}

