/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import com.google.rpc.context.AttributeContext;

public interface AuthorizationInfoOrBuilder
extends MessageOrBuilder {
    public String getResource();

    public ByteString getResourceBytes();

    public String getPermission();

    public ByteString getPermissionBytes();

    public boolean getGranted();

    public boolean hasResourceAttributes();

    public AttributeContext.Resource getResourceAttributes();

    public AttributeContext.ResourceOrBuilder getResourceAttributesOrBuilder();
}

