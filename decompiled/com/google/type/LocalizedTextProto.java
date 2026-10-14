/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class LocalizedTextProto {
    static final Descriptors.Descriptor internal_static_google_type_LocalizedText_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_LocalizedText_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private LocalizedTextProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        LocalizedTextProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n google/type/localized_text.proto\u0012\u000bgoogle.type\"4\n\rLocalizedText\u0012\f\n\u0004text\u0018\u0001 \u0001(\t\u0012\u0015\n\rlanguage_code\u0018\u0002 \u0001(\tBz\n\u000fcom.google.typeB\u0012LocalizedTextProtoP\u0001ZHgoogle.golang.org/genproto/googleapis/type/localized_text;localized_text\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_LocalizedText_descriptor = LocalizedTextProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_LocalizedText_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_LocalizedText_descriptor, new String[]{"Text", "LanguageCode"});
    }
}

