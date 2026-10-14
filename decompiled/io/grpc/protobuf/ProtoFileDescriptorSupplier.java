/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.protobuf;

import com.google.protobuf.Descriptors;

public interface ProtoFileDescriptorSupplier {
    public Descriptors.FileDescriptor getFileDescriptor();
}

