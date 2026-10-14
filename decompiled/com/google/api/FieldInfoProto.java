/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.FieldInfo;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessageV3;

public final class FieldInfoProto {
    public static final int FIELD_INFO_FIELD_NUMBER = 291403980;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.FieldOptions, FieldInfo> fieldInfo = GeneratedMessage.newFileScopedGeneratedExtension(FieldInfo.class, FieldInfo.getDefaultInstance());
    static final Descriptors.Descriptor internal_static_google_api_FieldInfo_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_FieldInfo_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private FieldInfoProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add(fieldInfo);
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        FieldInfoProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001bgoogle/api/field_info.proto\u0012\ngoogle.api\u001a google/protobuf/descriptor.proto\"\u008c\u0001\n\tFieldInfo\u0012,\n\u0006format\u0018\u0001 \u0001(\u000e2\u001c.google.api.FieldInfo.Format\"Q\n\u0006Format\u0012\u0016\n\u0012FORMAT_UNSPECIFIED\u0010\u0000\u0012\t\n\u0005UUID4\u0010\u0001\u0012\b\n\u0004IPV4\u0010\u0002\u0012\b\n\u0004IPV6\u0010\u0003\u0012\u0010\n\fIPV4_OR_IPV6\u0010\u0004:L\n\nfield_info\u0012\u001d.google.protobuf.FieldOptions\u0018\u00cc\u00f1\u00f9\u008a\u0001 \u0001(\u000b2\u0015.google.api.FieldInfoBl\n\u000ecom.google.apiB\u000eFieldInfoProtoP\u0001ZAgoogle.golang.org/genproto/googleapis/api/annotations;annotations\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{DescriptorProtos.getDescriptor()});
        internal_static_google_api_FieldInfo_descriptor = FieldInfoProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_FieldInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_FieldInfo_descriptor, new String[]{"Format"});
        fieldInfo.internalInit(descriptor.getExtensions().get(0));
        DescriptorProtos.getDescriptor();
    }
}

