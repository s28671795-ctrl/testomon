/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.Internal;
import io.grpc.InternalKnownTransport;
import io.grpc.MethodDescriptor;

@Internal
public final class InternalMethodDescriptor {
    private final InternalKnownTransport transport;

    public InternalMethodDescriptor(InternalKnownTransport transport) {
        this.transport = Preconditions.checkNotNull(transport, "transport");
    }

    public Object geRawMethodName(MethodDescriptor<?, ?> descriptor) {
        return descriptor.getRawMethodName(this.transport.ordinal());
    }

    public void setRawMethodName(MethodDescriptor<?, ?> descriptor, Object o) {
        descriptor.setRawMethodName(this.transport.ordinal(), o);
    }
}

