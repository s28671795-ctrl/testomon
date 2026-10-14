/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class MonitoringProto {
    static final Descriptors.Descriptor internal_static_google_api_Monitoring_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Monitoring_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_Monitoring_MonitoringDestination_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Monitoring_MonitoringDestination_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private MonitoringProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        MonitoringProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001bgoogle/api/monitoring.proto\u0012\ngoogle.api\"\u00ec\u0001\n\nMonitoring\u0012K\n\u0015producer_destinations\u0018\u0001 \u0003(\u000b2,.google.api.Monitoring.MonitoringDestination\u0012K\n\u0015consumer_destinations\u0018\u0002 \u0003(\u000b2,.google.api.Monitoring.MonitoringDestination\u001aD\n\u0015MonitoringDestination\u0012\u001a\n\u0012monitored_resource\u0018\u0001 \u0001(\t\u0012\u000f\n\u0007metrics\u0018\u0002 \u0003(\tBq\n\u000ecom.google.apiB\u000fMonitoringProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_api_Monitoring_descriptor = MonitoringProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_Monitoring_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Monitoring_descriptor, new String[]{"ProducerDestinations", "ConsumerDestinations"});
        internal_static_google_api_Monitoring_MonitoringDestination_descriptor = internal_static_google_api_Monitoring_descriptor.getNestedTypes().get(0);
        internal_static_google_api_Monitoring_MonitoringDestination_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Monitoring_MonitoringDestination_descriptor, new String[]{"MonitoredResource", "Metrics"});
    }
}

