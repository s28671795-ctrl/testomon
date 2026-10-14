/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class DateProto {
    static final Descriptors.Descriptor internal_static_google_type_Date_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_Date_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private DateProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        DateProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0016google/type/date.proto\u0012\u000bgoogle.type\"0\n\u0004Date\u0012\f\n\u0004year\u0018\u0001 \u0001(\u0005\u0012\r\n\u0005month\u0018\u0002 \u0001(\u0005\u0012\u000b\n\u0003day\u0018\u0003 \u0001(\u0005B]\n\u000fcom.google.typeB\tDateProtoP\u0001Z4google.golang.org/genproto/googleapis/type/date;date\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_Date_descriptor = DateProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_Date_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_Date_descriptor, new String[]{"Year", "Month", "Day"});
    }
}

