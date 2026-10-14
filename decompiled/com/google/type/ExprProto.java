/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class ExprProto {
    static final Descriptors.Descriptor internal_static_google_type_Expr_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_Expr_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private ExprProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        ExprProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0016google/type/expr.proto\u0012\u000bgoogle.type\"P\n\u0004Expr\u0012\u0012\n\nexpression\u0018\u0001 \u0001(\t\u0012\r\n\u0005title\u0018\u0002 \u0001(\t\u0012\u0013\n\u000bdescription\u0018\u0003 \u0001(\t\u0012\u0010\n\blocation\u0018\u0004 \u0001(\tBZ\n\u000fcom.google.typeB\tExprProtoP\u0001Z4google.golang.org/genproto/googleapis/type/expr;expr\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_Expr_descriptor = ExprProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_Expr_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_Expr_descriptor, new String[]{"Expression", "Title", "Description", "Location"});
    }
}

