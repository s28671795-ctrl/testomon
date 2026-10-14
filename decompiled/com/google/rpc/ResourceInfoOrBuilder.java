/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ResourceInfoOrBuilder
extends MessageOrBuilder {
    public String getResourceType();

    public ByteString getResourceTypeBytes();

    public String getResourceName();

    public ByteString getResourceNameBytes();

    public String getOwner();

    public ByteString getOwnerBytes();

    public String getDescription();

    public ByteString getDescriptionBytes();
}

