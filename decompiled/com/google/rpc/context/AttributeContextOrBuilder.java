/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc.context;

import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import com.google.rpc.context.AttributeContext;
import java.util.List;

public interface AttributeContextOrBuilder
extends MessageOrBuilder {
    public boolean hasOrigin();

    public AttributeContext.Peer getOrigin();

    public AttributeContext.PeerOrBuilder getOriginOrBuilder();

    public boolean hasSource();

    public AttributeContext.Peer getSource();

    public AttributeContext.PeerOrBuilder getSourceOrBuilder();

    public boolean hasDestination();

    public AttributeContext.Peer getDestination();

    public AttributeContext.PeerOrBuilder getDestinationOrBuilder();

    public boolean hasRequest();

    public AttributeContext.Request getRequest();

    public AttributeContext.RequestOrBuilder getRequestOrBuilder();

    public boolean hasResponse();

    public AttributeContext.Response getResponse();

    public AttributeContext.ResponseOrBuilder getResponseOrBuilder();

    public boolean hasResource();

    public AttributeContext.Resource getResource();

    public AttributeContext.ResourceOrBuilder getResourceOrBuilder();

    public boolean hasApi();

    public AttributeContext.Api getApi();

    public AttributeContext.ApiOrBuilder getApiOrBuilder();

    public List<Any> getExtensionsList();

    public Any getExtensions(int var1);

    public int getExtensionsCount();

    public List<? extends AnyOrBuilder> getExtensionsOrBuilderList();

    public AnyOrBuilder getExtensionsOrBuilder(int var1);
}

