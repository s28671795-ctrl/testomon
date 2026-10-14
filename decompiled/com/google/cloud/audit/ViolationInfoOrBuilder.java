/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.ViolationInfo;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ViolationInfoOrBuilder
extends MessageOrBuilder {
    public String getConstraint();

    public ByteString getConstraintBytes();

    public String getErrorMessage();

    public ByteString getErrorMessageBytes();

    public String getCheckedValue();

    public ByteString getCheckedValueBytes();

    public int getPolicyTypeValue();

    public ViolationInfo.PolicyType getPolicyType();
}

