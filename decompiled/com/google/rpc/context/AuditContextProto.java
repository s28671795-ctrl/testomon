/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc.context;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.StructProto;

public final class AuditContextProto {
    static final Descriptors.Descriptor internal_static_google_rpc_context_AuditContext_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_rpc_context_AuditContext_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private AuditContextProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        AuditContextProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n&google/rpc/context/audit_context.proto\u0012\u0012google.rpc.context\u001a\u001cgoogle/protobuf/struct.proto\"\u00c7\u0001\n\fAuditContext\u0012\u0011\n\taudit_log\u0018\u0001 \u0001(\f\u00121\n\u0010scrubbed_request\u0018\u0002 \u0001(\u000b2\u0017.google.protobuf.Struct\u00122\n\u0011scrubbed_response\u0018\u0003 \u0001(\u000b2\u0017.google.protobuf.Struct\u0012$\n\u001cscrubbed_response_item_count\u0018\u0004 \u0001(\u0005\u0012\u0017\n\u000ftarget_resource\u0018\u0005 \u0001(\tBk\n\u0016com.google.rpc.contextB\u0011AuditContextProtoP\u0001Z9google.golang.org/genproto/googleapis/rpc/context;context\u00f8\u0001\u0001b\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{StructProto.getDescriptor()});
        internal_static_google_rpc_context_AuditContext_descriptor = AuditContextProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_rpc_context_AuditContext_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_rpc_context_AuditContext_descriptor, new String[]{"AuditLog", "ScrubbedRequest", "ScrubbedResponse", "ScrubbedResponseItemCount", "TargetResource"});
        StructProto.getDescriptor();
    }
}

