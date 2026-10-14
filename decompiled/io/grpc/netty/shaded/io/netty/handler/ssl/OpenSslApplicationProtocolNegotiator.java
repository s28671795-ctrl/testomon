/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.ssl;

import io.grpc.netty.shaded.io.netty.handler.ssl.ApplicationProtocolConfig;
import io.grpc.netty.shaded.io.netty.handler.ssl.ApplicationProtocolNegotiator;

@Deprecated
public interface OpenSslApplicationProtocolNegotiator
extends ApplicationProtocolNegotiator {
    public ApplicationProtocolConfig.Protocol protocol();

    public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior();

    public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior();
}

