/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.AnyProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class StatusProto {
    static final Descriptors.Descriptor internal_static_google_rpc_Status_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_rpc_Status_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private StatusProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        StatusProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0017google/rpc/status.proto\u0012\ngoogle.rpc\u001a\u0019google/protobuf/any.proto\"N\n\u0006Status\u0012\f\n\u0004code\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007message\u0018\u0002 \u0001(\t\u0012%\n\u0007details\u0018\u0003 \u0003(\u000b2\u0014.google.protobuf.AnyBa\n\u000ecom.google.rpcB\u000bStatusProtoP\u0001Z7google.golang.org/genproto/googleapis/rpc/status;status\u00f8\u0001\u0001\u00a2\u0002\u0003RPCb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{AnyProto.getDescriptor()});
        internal_static_google_rpc_Status_descriptor = StatusProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_rpc_Status_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_rpc_Status_descriptor, new String[]{"Code", "Message", "Details"});
        AnyProto.getDescriptor();
    }
}

