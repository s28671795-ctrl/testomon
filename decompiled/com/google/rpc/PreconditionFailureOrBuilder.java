/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.MessageOrBuilder;
import com.google.rpc.PreconditionFailure;
import java.util.List;

public interface PreconditionFailureOrBuilder
extends MessageOrBuilder {
    public List<PreconditionFailure.Violation> getViolationsList();

    public PreconditionFailure.Violation getViolations(int var1);

    public int getViolationsCount();

    public List<? extends PreconditionFailure.ViolationOrBuilder> getViolationsOrBuilderList();

    public PreconditionFailure.ViolationOrBuilder getViolationsOrBuilder(int var1);
}

