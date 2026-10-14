/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.Attributes;
import io.grpc.Internal;
import io.grpc.InternalChannelz;
import io.grpc.netty.shaded.io.grpc.netty.ProtocolNegotiationEvent;
import javax.annotation.Nullable;

@Internal
public final class InternalProtocolNegotiationEvent {
    private InternalProtocolNegotiationEvent() {
    }

    public static ProtocolNegotiationEvent getDefault() {
        return ProtocolNegotiationEvent.DEFAULT;
    }

    public static ProtocolNegotiationEvent withAttributes(ProtocolNegotiationEvent event, Attributes attributes) {
        return event.withAttributes(attributes);
    }

    public static ProtocolNegotiationEvent withSecurity(ProtocolNegotiationEvent event, @Nullable InternalChannelz.Security security) {
        return event.withSecurity(security);
    }

    public static Attributes getAttributes(ProtocolNegotiationEvent event) {
        return event.getAttributes();
    }

    @Nullable
    public static InternalChannelz.Security getSecurity(ProtocolNegotiationEvent event) {
        return event.getSecurity();
    }
}

