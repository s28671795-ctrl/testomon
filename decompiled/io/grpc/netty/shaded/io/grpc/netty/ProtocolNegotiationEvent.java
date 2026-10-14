/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.Internal;
import io.grpc.InternalChannelz;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
@Internal
public final class ProtocolNegotiationEvent {
    static final ProtocolNegotiationEvent DEFAULT = new ProtocolNegotiationEvent(Attributes.EMPTY, null);
    private final Attributes attributes;
    @Nullable
    private final InternalChannelz.Security security;

    private ProtocolNegotiationEvent(Attributes attributes, @Nullable InternalChannelz.Security security) {
        this.attributes = Preconditions.checkNotNull(attributes, "attributes");
        this.security = security;
    }

    @Nullable
    InternalChannelz.Security getSecurity() {
        return this.security;
    }

    Attributes getAttributes() {
        return this.attributes;
    }

    ProtocolNegotiationEvent withAttributes(Attributes attributes) {
        return new ProtocolNegotiationEvent(attributes, this.security);
    }

    ProtocolNegotiationEvent withSecurity(@Nullable InternalChannelz.Security security) {
        return new ProtocolNegotiationEvent(this.attributes, security);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("attributes", this.attributes).add("security", this.security).toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.attributes, this.security);
    }

    public boolean equals(Object other) {
        if (!(other instanceof ProtocolNegotiationEvent)) {
            return false;
        }
        ProtocolNegotiationEvent that = (ProtocolNegotiationEvent)other;
        return Objects.equal(this.attributes, that.attributes) && Objects.equal(this.security, that.security);
    }
}

