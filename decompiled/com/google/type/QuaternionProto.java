/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class QuaternionProto {
    static final Descriptors.Descriptor internal_static_google_type_Quaternion_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_Quaternion_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private QuaternionProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        QuaternionProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001cgoogle/type/quaternion.proto\u0012\u000bgoogle.type\"8\n\nQuaternion\u0012\t\n\u0001x\u0018\u0001 \u0001(\u0001\u0012\t\n\u0001y\u0018\u0002 \u0001(\u0001\u0012\t\n\u0001z\u0018\u0003 \u0001(\u0001\u0012\t\n\u0001w\u0018\u0004 \u0001(\u0001Bo\n\u000fcom.google.typeB\u000fQuaternionProtoP\u0001Z@google.golang.org/genproto/googleapis/type/quaternion;quaternion\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_Quaternion_descriptor = QuaternionProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_Quaternion_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_Quaternion_descriptor, new String[]{"X", "Y", "Z", "W"});
    }
}

