/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.longrunning.Operation;
import com.google.longrunning.OperationOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ListOperationsResponseOrBuilder
extends MessageOrBuilder {
    public List<Operation> getOperationsList();

    public Operation getOperations(int var1);

    public int getOperationsCount();

    public List<? extends OperationOrBuilder> getOperationsOrBuilderList();

    public OperationOrBuilder getOperationsOrBuilder(int var1);

    public String getNextPageToken();

    public ByteString getNextPageTokenBytes();
}

