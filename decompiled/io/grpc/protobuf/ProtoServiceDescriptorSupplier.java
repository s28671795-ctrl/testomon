/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.protobuf;

import com.google.protobuf.Descriptors;
import io.grpc.protobuf.ProtoFileDescriptorSupplier;

public interface ProtoServiceDescriptorSupplier
extends ProtoFileDescriptorSupplier {
    public Descriptors.ServiceDescriptor getServiceDescriptor();
}

