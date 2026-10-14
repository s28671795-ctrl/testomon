/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.WrappersProto;

public final class ColorProto {
    static final Descriptors.Descriptor internal_static_google_type_Color_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_Color_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private ColorProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        ColorProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0017google/type/color.proto\u0012\u000bgoogle.type\u001a\u001egoogle/protobuf/wrappers.proto\"]\n\u0005Color\u0012\u000b\n\u0003red\u0018\u0001 \u0001(\u0002\u0012\r\n\u0005green\u0018\u0002 \u0001(\u0002\u0012\f\n\u0004blue\u0018\u0003 \u0001(\u0002\u0012*\n\u0005alpha\u0018\u0004 \u0001(\u000b2\u001b.google.protobuf.FloatValueB`\n\u000fcom.google.typeB\nColorProtoP\u0001Z6google.golang.org/genproto/googleapis/type/color;color\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{WrappersProto.getDescriptor()});
        internal_static_google_type_Color_descriptor = ColorProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_Color_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_Color_descriptor, new String[]{"Red", "Green", "Blue", "Alpha"});
        WrappersProto.getDescriptor();
    }
}

