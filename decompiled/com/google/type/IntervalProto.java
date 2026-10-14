/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.TimestampProto;

public final class IntervalProto {
    static final Descriptors.Descriptor internal_static_google_type_Interval_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_Interval_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private IntervalProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        IntervalProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001agoogle/type/interval.proto\u0012\u000bgoogle.type\u001a\u001fgoogle/protobuf/timestamp.proto\"h\n\bInterval\u0012.\n\nstart_time\u0018\u0001 \u0001(\u000b2\u001a.google.protobuf.Timestamp\u0012,\n\bend_time\u0018\u0002 \u0001(\u000b2\u001a.google.protobuf.TimestampBi\n\u000fcom.google.typeB\rIntervalProtoP\u0001Z<google.golang.org/genproto/googleapis/type/interval;interval\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{TimestampProto.getDescriptor()});
        internal_static_google_type_Interval_descriptor = IntervalProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_Interval_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_Interval_descriptor, new String[]{"StartTime", "EndTime"});
        TimestampProto.getDescriptor();
    }
}

