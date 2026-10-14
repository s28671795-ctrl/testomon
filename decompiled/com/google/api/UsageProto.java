/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class UsageProto {
    static final Descriptors.Descriptor internal_static_google_api_Usage_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Usage_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_UsageRule_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_UsageRule_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private UsageProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        UsageProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0016google/api/usage.proto\u0012\ngoogle.api\"j\n\u0005Usage\u0012\u0014\n\frequirements\u0018\u0001 \u0003(\t\u0012$\n\u0005rules\u0018\u0006 \u0003(\u000b2\u0015.google.api.UsageRule\u0012%\n\u001dproducer_notification_channel\u0018\u0007 \u0001(\t\"]\n\tUsageRule\u0012\u0010\n\bselector\u0018\u0001 \u0001(\t\u0012 \n\u0018allow_unregistered_calls\u0018\u0002 \u0001(\b\u0012\u001c\n\u0014skip_service_control\u0018\u0003 \u0001(\bBl\n\u000ecom.google.apiB\nUsageProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_api_Usage_descriptor = UsageProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_Usage_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Usage_descriptor, new String[]{"Requirements", "Rules", "ProducerNotificationChannel"});
        internal_static_google_api_UsageRule_descriptor = UsageProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_api_UsageRule_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_UsageRule_descriptor, new String[]{"Selector", "AllowUnregisteredCalls", "SkipServiceControl"});
    }
}

