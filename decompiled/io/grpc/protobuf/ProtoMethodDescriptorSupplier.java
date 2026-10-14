/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.protobuf;

import com.google.protobuf.Descriptors;
import io.grpc.protobuf.ProtoServiceDescriptorSupplier;
import javax.annotation.CheckReturnValue;

public interface ProtoMethodDescriptorSupplier
extends ProtoServiceDescriptorSupplier {
    @CheckReturnValue
    public Descriptors.MethodDescriptor getMethodDescriptor();
}

