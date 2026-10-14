/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class BackendProto {
    static final Descriptors.Descriptor internal_static_google_api_Backend_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Backend_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_BackendRule_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_BackendRule_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private BackendProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        BackendProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0018google/api/backend.proto\u0012\ngoogle.api\"1\n\u0007Backend\u0012&\n\u0005rules\u0018\u0001 \u0003(\u000b2\u0017.google.api.BackendRule\"\u00b2\u0004\n\u000bBackendRule\u0012\u0010\n\bselector\u0018\u0001 \u0001(\t\u0012\u000f\n\u0007address\u0018\u0002 \u0001(\t\u0012\u0010\n\bdeadline\u0018\u0003 \u0001(\u0001\u0012\u0018\n\fmin_deadline\u0018\u0004 \u0001(\u0001B\u0002\u0018\u0001\u0012\u001a\n\u0012operation_deadline\u0018\u0005 \u0001(\u0001\u0012A\n\u0010path_translation\u0018\u0006 \u0001(\u000e2'.google.api.BackendRule.PathTranslation\u0012\u0016\n\fjwt_audience\u0018\u0007 \u0001(\tH\u0000\u0012\u0016\n\fdisable_auth\u0018\b \u0001(\bH\u0000\u0012\u0010\n\bprotocol\u0018\t \u0001(\t\u0012^\n\u001doverrides_by_request_protocol\u0018\n \u0003(\u000b27.google.api.BackendRule.OverridesByRequestProtocolEntry\u001aZ\n\u001fOverridesByRequestProtocolEntry\u0012\u000b\n\u0003key\u0018\u0001 \u0001(\t\u0012&\n\u0005value\u0018\u0002 \u0001(\u000b2\u0017.google.api.BackendRule:\u00028\u0001\"e\n\u000fPathTranslation\u0012 \n\u001cPATH_TRANSLATION_UNSPECIFIED\u0010\u0000\u0012\u0014\n\u0010CONSTANT_ADDRESS\u0010\u0001\u0012\u001a\n\u0016APPEND_PATH_TO_ADDRESS\u0010\u0002B\u0010\n\u000eauthenticationBn\n\u000ecom.google.apiB\fBackendProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_api_Backend_descriptor = BackendProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_Backend_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Backend_descriptor, new String[]{"Rules"});
        internal_static_google_api_BackendRule_descriptor = BackendProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_api_BackendRule_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_BackendRule_descriptor, new String[]{"Selector", "Address", "Deadline", "MinDeadline", "OperationDeadline", "PathTranslation", "JwtAudience", "DisableAuth", "Protocol", "OverridesByRequestProtocol", "Authentication"});
        internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_descriptor = internal_static_google_api_BackendRule_descriptor.getNestedTypes().get(0);
        internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_descriptor, new String[]{"Key", "Value"});
    }
}

