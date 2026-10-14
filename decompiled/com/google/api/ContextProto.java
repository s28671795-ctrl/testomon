/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class ContextProto {
    static final Descriptors.Descriptor internal_static_google_api_Context_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Context_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_ContextRule_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_ContextRule_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private ContextProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        ContextProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0018google/api/context.proto\u0012\ngoogle.api\"1\n\u0007Context\u0012&\n\u0005rules\u0018\u0001 \u0003(\u000b2\u0017.google.api.ContextRule\"\u008d\u0001\n\u000bContextRule\u0012\u0010\n\bselector\u0018\u0001 \u0001(\t\u0012\u0011\n\trequested\u0018\u0002 \u0003(\t\u0012\u0010\n\bprovided\u0018\u0003 \u0003(\t\u0012\"\n\u001aallowed_request_extensions\u0018\u0004 \u0003(\t\u0012#\n\u001ballowed_response_extensions\u0018\u0005 \u0003(\tBn\n\u000ecom.google.apiB\fContextProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_api_Context_descriptor = ContextProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_Context_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Context_descriptor, new String[]{"Rules"});
        internal_static_google_api_ContextRule_descriptor = ContextProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_api_ContextRule_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_ContextRule_descriptor, new String[]{"Selector", "Requested", "Provided", "AllowedRequestExtensions", "AllowedResponseExtensions"});
    }
}

