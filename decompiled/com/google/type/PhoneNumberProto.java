/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class PhoneNumberProto {
    static final Descriptors.Descriptor internal_static_google_type_PhoneNumber_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_PhoneNumber_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_type_PhoneNumber_ShortCode_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_PhoneNumber_ShortCode_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private PhoneNumberProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        PhoneNumberProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001egoogle/type/phone_number.proto\u0012\u000bgoogle.type\"\u00ab\u0001\n\u000bPhoneNumber\u0012\u0015\n\u000be164_number\u0018\u0001 \u0001(\tH\u0000\u00128\n\nshort_code\u0018\u0002 \u0001(\u000b2\".google.type.PhoneNumber.ShortCodeH\u0000\u0012\u0011\n\textension\u0018\u0003 \u0001(\t\u001a0\n\tShortCode\u0012\u0013\n\u000bregion_code\u0018\u0001 \u0001(\t\u0012\u000e\n\u0006number\u0018\u0002 \u0001(\tB\u0006\n\u0004kindBt\n\u000fcom.google.typeB\u0010PhoneNumberProtoP\u0001ZDgoogle.golang.org/genproto/googleapis/type/phone_number;phone_number\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_PhoneNumber_descriptor = PhoneNumberProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_PhoneNumber_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_PhoneNumber_descriptor, new String[]{"E164Number", "ShortCode", "Extension", "Kind"});
        internal_static_google_type_PhoneNumber_ShortCode_descriptor = internal_static_google_type_PhoneNumber_descriptor.getNestedTypes().get(0);
        internal_static_google_type_PhoneNumber_ShortCode_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_PhoneNumber_ShortCode_descriptor, new String[]{"RegionCode", "Number"});
    }
}

