/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc.context;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;

public interface AuditContextOrBuilder
extends MessageOrBuilder {
    public ByteString getAuditLog();

    public boolean hasScrubbedRequest();

    public Struct getScrubbedRequest();

    public StructOrBuilder getScrubbedRequestOrBuilder();

    public boolean hasScrubbedResponse();

    public Struct getScrubbedResponse();

    public StructOrBuilder getScrubbedResponseOrBuilder();

    public int getScrubbedResponseItemCount();

    public String getTargetResource();

    public ByteString getTargetResourceBytes();
}

