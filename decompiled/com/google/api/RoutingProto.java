/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.RoutingRule;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessageV3;

public final class RoutingProto {
    public static final int ROUTING_FIELD_NUMBER = 72295729;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MethodOptions, RoutingRule> routing = GeneratedMessage.newFileScopedGeneratedExtension(RoutingRule.class, RoutingRule.getDefaultInstance());
    static final Descriptors.Descriptor internal_static_google_api_RoutingRule_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_RoutingRule_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_RoutingParameter_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_RoutingParameter_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private RoutingProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add(routing);
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        RoutingProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0018google/api/routing.proto\u0012\ngoogle.api\u001a google/protobuf/descriptor.proto\"G\n\u000bRoutingRule\u00128\n\u0012routing_parameters\u0018\u0002 \u0003(\u000b2\u001c.google.api.RoutingParameter\"8\n\u0010RoutingParameter\u0012\r\n\u0005field\u0018\u0001 \u0001(\t\u0012\u0015\n\rpath_template\u0018\u0002 \u0001(\t:K\n\u0007routing\u0012\u001e.google.protobuf.MethodOptions\u0018\u00b1\u00ca\u00bc\" \u0001(\u000b2\u0017.google.api.RoutingRuleBj\n\u000ecom.google.apiB\fRoutingProtoP\u0001ZAgoogle.golang.org/genproto/googleapis/api/annotations;annotations\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{DescriptorProtos.getDescriptor()});
        internal_static_google_api_RoutingRule_descriptor = RoutingProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_RoutingRule_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_RoutingRule_descriptor, new String[]{"RoutingParameters"});
        internal_static_google_api_RoutingParameter_descriptor = RoutingProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_api_RoutingParameter_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_RoutingParameter_descriptor, new String[]{"Field", "PathTemplate"});
        routing.internalInit(descriptor.getExtensions().get(0));
        DescriptorProtos.getDescriptor();
    }
}

