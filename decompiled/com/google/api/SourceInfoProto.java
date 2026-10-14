/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.AnyProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class SourceInfoProto {
    static final Descriptors.Descriptor internal_static_google_api_SourceInfo_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_SourceInfo_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private SourceInfoProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        SourceInfoProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001cgoogle/api/source_info.proto\u0012\ngoogle.api\u001a\u0019google/protobuf/any.proto\"8\n\nSourceInfo\u0012*\n\fsource_files\u0018\u0001 \u0003(\u000b2\u0014.google.protobuf.AnyBq\n\u000ecom.google.apiB\u000fSourceInfoProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{AnyProto.getDescriptor()});
        internal_static_google_api_SourceInfo_descriptor = SourceInfoProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_SourceInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_SourceInfo_descriptor, new String[]{"SourceFiles"});
        AnyProto.getDescriptor();
    }
}

