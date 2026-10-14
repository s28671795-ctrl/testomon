/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class TimeOfDayProto {
    static final Descriptors.Descriptor internal_static_google_type_TimeOfDay_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_TimeOfDay_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private TimeOfDayProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        TimeOfDayProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001bgoogle/type/timeofday.proto\u0012\u000bgoogle.type\"K\n\tTimeOfDay\u0012\r\n\u0005hours\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007minutes\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007seconds\u0018\u0003 \u0001(\u0005\u0012\r\n\u0005nanos\u0018\u0004 \u0001(\u0005Bl\n\u000fcom.google.typeB\u000eTimeOfDayProtoP\u0001Z>google.golang.org/genproto/googleapis/type/timeofday;timeofday\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_TimeOfDay_descriptor = TimeOfDayProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_TimeOfDay_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_TimeOfDay_descriptor, new String[]{"Hours", "Minutes", "Seconds", "Nanos"});
    }
}

