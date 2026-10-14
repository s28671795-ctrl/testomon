/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class MoneyProto {
    static final Descriptors.Descriptor internal_static_google_type_Money_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_Money_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private MoneyProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        MoneyProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0017google/type/money.proto\u0012\u000bgoogle.type\"<\n\u0005Money\u0012\u0015\n\rcurrency_code\u0018\u0001 \u0001(\t\u0012\r\n\u0005units\u0018\u0002 \u0001(\u0003\u0012\r\n\u0005nanos\u0018\u0003 \u0001(\u0005B`\n\u000fcom.google.typeB\nMoneyProtoP\u0001Z6google.golang.org/genproto/googleapis/type/money;money\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_Money_descriptor = MoneyProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_Money_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_Money_descriptor, new String[]{"CurrencyCode", "Units", "Nanos"});
    }
}

