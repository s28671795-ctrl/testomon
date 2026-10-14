/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.protobuf;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.lite.ProtoLiteUtils;

public final class ProtoUtils {
    public static void setExtensionRegistry(ExtensionRegistry registry) {
        ProtoLiteUtils.setExtensionRegistry(registry);
    }

    public static <T extends Message> MethodDescriptor.Marshaller<T> marshaller(T defaultInstance) {
        return ProtoLiteUtils.marshaller(defaultInstance);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/10108")
    public static <T extends Message> MethodDescriptor.Marshaller<T> marshallerWithRecursionLimit(T defaultInstance, int recursionLimit) {
        return ProtoLiteUtils.marshallerWithRecursionLimit(defaultInstance, recursionLimit);
    }

    public static <T extends Message> Metadata.Key<T> keyForProto(T instance) {
        return Metadata.Key.of(instance.getDescriptorForType().getFullName() + "-bin", ProtoUtils.metadataMarshaller(instance));
    }

    public static <T extends Message> Metadata.BinaryMarshaller<T> metadataMarshaller(T instance) {
        return ProtoLiteUtils.metadataMarshaller(instance);
    }

    private ProtoUtils() {
    }
}

