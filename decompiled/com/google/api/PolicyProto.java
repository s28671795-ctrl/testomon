/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.FieldPolicy;
import com.google.api.MethodPolicy;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessageV3;

public final class PolicyProto {
    public static final int FIELD_POLICY_FIELD_NUMBER = 158361448;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.FieldOptions, FieldPolicy> fieldPolicy = GeneratedMessage.newFileScopedGeneratedExtension(FieldPolicy.class, FieldPolicy.getDefaultInstance());
    public static final int METHOD_POLICY_FIELD_NUMBER = 161893301;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MethodOptions, MethodPolicy> methodPolicy = GeneratedMessage.newFileScopedGeneratedExtension(MethodPolicy.class, MethodPolicy.getDefaultInstance());
    static final Descriptors.Descriptor internal_static_google_api_FieldPolicy_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_FieldPolicy_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_MethodPolicy_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_MethodPolicy_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private PolicyProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add(fieldPolicy);
        registry.add(methodPolicy);
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        PolicyProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0017google/api/policy.proto\u0012\ngoogle.api\u001a google/protobuf/descriptor.proto\"S\n\u000bFieldPolicy\u0012\u0010\n\bselector\u0018\u0001 \u0001(\t\u0012\u001b\n\u0013resource_permission\u0018\u0002 \u0001(\t\u0012\u0015\n\rresource_type\u0018\u0003 \u0001(\t\"S\n\fMethodPolicy\u0012\u0010\n\bselector\u0018\t \u0001(\t\u00121\n\u0010request_policies\u0018\u0002 \u0003(\u000b2\u0017.google.api.FieldPolicy:O\n\ffield_policy\u0012\u001d.google.protobuf.FieldOptions\u0018\u00e8\u00ce\u00c1K \u0001(\u000b2\u0017.google.api.FieldPolicy:R\n\rmethod_policy\u0012\u001e.google.protobuf.MethodOptions\u0018\u00b5\u0097\u0099M \u0001(\u000b2\u0018.google.api.MethodPolicyBp\n\u000ecom.google.apiB\u000bPolicyProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00f8\u0001\u0001\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{DescriptorProtos.getDescriptor()});
        internal_static_google_api_FieldPolicy_descriptor = PolicyProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_FieldPolicy_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_FieldPolicy_descriptor, new String[]{"Selector", "ResourcePermission", "ResourceType"});
        internal_static_google_api_MethodPolicy_descriptor = PolicyProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_api_MethodPolicy_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_MethodPolicy_descriptor, new String[]{"Selector", "RequestPolicies"});
        fieldPolicy.internalInit(descriptor.getExtensions().get(0));
        methodPolicy.internalInit(descriptor.getExtensions().get(1));
        DescriptorProtos.getDescriptor();
    }
}

