/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class EndpointProto {
    static final Descriptors.Descriptor internal_static_google_api_Endpoint_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Endpoint_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private EndpointProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        EndpointProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0019google/api/endpoint.proto\u0012\ngoogle.api\"Q\n\bEndpoint\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u0013\n\u0007aliases\u0018\u0002 \u0003(\tB\u0002\u0018\u0001\u0012\u000e\n\u0006target\u0018e \u0001(\t\u0012\u0012\n\nallow_cors\u0018\u0005 \u0001(\bBo\n\u000ecom.google.apiB\rEndpointProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_api_Endpoint_descriptor = EndpointProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_Endpoint_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Endpoint_descriptor, new String[]{"Name", "Aliases", "Target", "AllowCors"});
    }
}

