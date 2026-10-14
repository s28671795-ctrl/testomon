/*
 * Decompiled with CFR 0.152.
 */
package com.google.longrunning;

import com.google.longrunning.Operation;
import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import com.google.rpc.Status;
import com.google.rpc.StatusOrBuilder;

public interface OperationOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public boolean hasMetadata();

    public Any getMetadata();

    public AnyOrBuilder getMetadataOrBuilder();

    public boolean getDone();

    public boolean hasError();

    public Status getError();

    public StatusOrBuilder getErrorOrBuilder();

    public boolean hasResponse();

    public Any getResponse();

    public AnyOrBuilder getResponseOrBuilder();

    public Operation.ResultCase getResultCase();
}

