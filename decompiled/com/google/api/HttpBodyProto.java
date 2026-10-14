/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.AnyProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class HttpBodyProto {
    static final Descriptors.Descriptor internal_static_google_api_HttpBody_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_HttpBody_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private HttpBodyProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        HttpBodyProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0019google/api/httpbody.proto\u0012\ngoogle.api\u001a\u0019google/protobuf/any.proto\"X\n\bHttpBody\u0012\u0014\n\fcontent_type\u0018\u0001 \u0001(\t\u0012\f\n\u0004data\u0018\u0002 \u0001(\f\u0012(\n\nextensions\u0018\u0003 \u0003(\u000b2\u0014.google.protobuf.AnyBh\n\u000ecom.google.apiB\rHttpBodyProtoP\u0001Z;google.golang.org/genproto/googleapis/api/httpbody;httpbody\u00f8\u0001\u0001\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{AnyProto.getDescriptor()});
        internal_static_google_api_HttpBody_descriptor = HttpBodyProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_HttpBody_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_HttpBody_descriptor, new String[]{"ContentType", "Data", "Extensions"});
        AnyProto.getDescriptor();
    }
}

