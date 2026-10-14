/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.MessageOrBuilder;
import com.google.rpc.BadRequest;
import java.util.List;

public interface BadRequestOrBuilder
extends MessageOrBuilder {
    public List<BadRequest.FieldViolation> getFieldViolationsList();

    public BadRequest.FieldViolation getFieldViolations(int var1);

    public int getFieldViolationsCount();

    public List<? extends BadRequest.FieldViolationOrBuilder> getFieldViolationsOrBuilderList();

    public BadRequest.FieldViolationOrBuilder getFieldViolationsOrBuilder(int var1);
}

