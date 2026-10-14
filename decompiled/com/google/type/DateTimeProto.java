/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.DurationProto;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class DateTimeProto {
    static final Descriptors.Descriptor internal_static_google_type_DateTime_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_DateTime_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_type_TimeZone_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_TimeZone_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private DateTimeProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        DateTimeProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001agoogle/type/datetime.proto\u0012\u000bgoogle.type\u001a\u001egoogle/protobuf/duration.proto\"\u00e0\u0001\n\bDateTime\u0012\f\n\u0004year\u0018\u0001 \u0001(\u0005\u0012\r\n\u0005month\u0018\u0002 \u0001(\u0005\u0012\u000b\n\u0003day\u0018\u0003 \u0001(\u0005\u0012\r\n\u0005hours\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007minutes\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007seconds\u0018\u0006 \u0001(\u0005\u0012\r\n\u0005nanos\u0018\u0007 \u0001(\u0005\u0012/\n\nutc_offset\u0018\b \u0001(\u000b2\u0019.google.protobuf.DurationH\u0000\u0012*\n\ttime_zone\u0018\t \u0001(\u000b2\u0015.google.type.TimeZoneH\u0000B\r\n\u000btime_offset\"'\n\bTimeZone\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\u000f\n\u0007version\u0018\u0002 \u0001(\tBi\n\u000fcom.google.typeB\rDateTimeProtoP\u0001Z<google.golang.org/genproto/googleapis/type/datetime;datetime\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{DurationProto.getDescriptor()});
        internal_static_google_type_DateTime_descriptor = DateTimeProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_DateTime_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_DateTime_descriptor, new String[]{"Year", "Month", "Day", "Hours", "Minutes", "Seconds", "Nanos", "UtcOffset", "TimeZone", "TimeOffset"});
        internal_static_google_type_TimeZone_descriptor = DateTimeProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_type_TimeZone_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_TimeZone_descriptor, new String[]{"Id", "Version"});
        DurationProto.getDescriptor();
    }
}

