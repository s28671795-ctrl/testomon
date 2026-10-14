/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Objects;
import io.grpc.Attributes;
import io.grpc.MethodDescriptor;
import io.grpc.ServerStreamTracer;
import javax.annotation.Nullable;

final class ServerCallInfoImpl<ReqT, RespT>
extends ServerStreamTracer.ServerCallInfo<ReqT, RespT> {
    private final MethodDescriptor<ReqT, RespT> methodDescriptor;
    private final Attributes attributes;
    private final String authority;

    ServerCallInfoImpl(MethodDescriptor<ReqT, RespT> methodDescriptor, Attributes attributes, @Nullable String authority) {
        this.methodDescriptor = methodDescriptor;
        this.attributes = attributes;
        this.authority = authority;
    }

    @Override
    public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
        return this.methodDescriptor;
    }

    @Override
    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    @Nullable
    public String getAuthority() {
        return this.authority;
    }

    public boolean equals(Object other) {
        if (!(other instanceof ServerCallInfoImpl)) {
            return false;
        }
        ServerCallInfoImpl that = (ServerCallInfoImpl)other;
        return Objects.equal(this.methodDescriptor, that.methodDescriptor) && Objects.equal(this.attributes, that.attributes) && Objects.equal(this.authority, that.authority);
    }

    public int hashCode() {
        return Objects.hashCode(this.methodDescriptor, this.attributes, this.authority);
    }
}

