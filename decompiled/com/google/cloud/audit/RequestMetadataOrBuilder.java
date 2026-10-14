/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import com.google.rpc.context.AttributeContext;

public interface RequestMetadataOrBuilder
extends MessageOrBuilder {
    public String getCallerIp();

    public ByteString getCallerIpBytes();

    public String getCallerSuppliedUserAgent();

    public ByteString getCallerSuppliedUserAgentBytes();

    public String getCallerNetwork();

    public ByteString getCallerNetworkBytes();

    public boolean hasRequestAttributes();

    public AttributeContext.Request getRequestAttributes();

    public AttributeContext.RequestOrBuilder getRequestAttributesOrBuilder();

    public boolean hasDestinationAttributes();

    public AttributeContext.Peer getDestinationAttributes();

    public AttributeContext.PeerOrBuilder getDestinationAttributesOrBuilder();
}

