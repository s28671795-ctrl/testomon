/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class DecimalProto {
    static final Descriptors.Descriptor internal_static_google_type_Decimal_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_Decimal_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private DecimalProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        DecimalProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0019google/type/decimal.proto\u0012\u000bgoogle.type\"\u0018\n\u0007Decimal\u0012\r\n\u0005value\u0018\u0001 \u0001(\tBf\n\u000fcom.google.typeB\fDecimalProtoP\u0001Z:google.golang.org/genproto/googleapis/type/decimal;decimal\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_Decimal_descriptor = DecimalProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_Decimal_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_Decimal_descriptor, new String[]{"Value"});
    }
}

