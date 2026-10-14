/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.MessageOrBuilder;
import com.google.rpc.QuotaFailure;
import java.util.List;

public interface QuotaFailureOrBuilder
extends MessageOrBuilder {
    public List<QuotaFailure.Violation> getViolationsList();

    public QuotaFailure.Violation getViolations(int var1);

    public int getViolationsCount();

    public List<? extends QuotaFailure.ViolationOrBuilder> getViolationsOrBuilderList();

    public QuotaFailure.ViolationOrBuilder getViolationsOrBuilder(int var1);
}

