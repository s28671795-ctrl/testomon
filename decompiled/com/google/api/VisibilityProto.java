/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.VisibilityRule;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessageV3;

public final class VisibilityProto {
    public static final int ENUM_VISIBILITY_FIELD_NUMBER = 72295727;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.EnumOptions, VisibilityRule> enumVisibility = GeneratedMessage.newFileScopedGeneratedExtension(VisibilityRule.class, VisibilityRule.getDefaultInstance());
    public static final int VALUE_VISIBILITY_FIELD_NUMBER = 72295727;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.EnumValueOptions, VisibilityRule> valueVisibility = GeneratedMessage.newFileScopedGeneratedExtension(VisibilityRule.class, VisibilityRule.getDefaultInstance());
    public static final int FIELD_VISIBILITY_FIELD_NUMBER = 72295727;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.FieldOptions, VisibilityRule> fieldVisibility = GeneratedMessage.newFileScopedGeneratedExtension(VisibilityRule.class, VisibilityRule.getDefaultInstance());
    public static final int MESSAGE_VISIBILITY_FIELD_NUMBER = 72295727;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MessageOptions, VisibilityRule> messageVisibility = GeneratedMessage.newFileScopedGeneratedExtension(VisibilityRule.class, VisibilityRule.getDefaultInstance());
    public static final int METHOD_VISIBILITY_FIELD_NUMBER = 72295727;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MethodOptions, VisibilityRule> methodVisibility = GeneratedMessage.newFileScopedGeneratedExtension(VisibilityRule.class, VisibilityRule.getDefaultInstance());
    public static final int API_VISIBILITY_FIELD_NUMBER = 72295727;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.ServiceOptions, VisibilityRule> apiVisibility = GeneratedMessage.newFileScopedGeneratedExtension(VisibilityRule.class, VisibilityRule.getDefaultInstance());
    static final Descriptors.Descriptor internal_static_google_api_Visibility_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Visibility_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_VisibilityRule_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_VisibilityRule_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private VisibilityProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add(enumVisibility);
        registry.add(valueVisibility);
        registry.add(fieldVisibility);
        registry.add(messageVisibility);
        registry.add(methodVisibility);
        registry.add(apiVisibility);
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        VisibilityProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001bgoogle/api/visibility.proto\u0012\ngoogle.api\u001a google/protobuf/descriptor.proto\"7\n\nVisibility\u0012)\n\u0005rules\u0018\u0001 \u0003(\u000b2\u001a.google.api.VisibilityRule\"7\n\u000eVisibilityRule\u0012\u0010\n\bselector\u0018\u0001 \u0001(\t\u0012\u0013\n\u000brestriction\u0018\u0002 \u0001(\t:T\n\u000fenum_visibility\u0012\u001c.google.protobuf.EnumOptions\u0018\u00af\u00ca\u00bc\" \u0001(\u000b2\u001a.google.api.VisibilityRule:Z\n\u0010value_visibility\u0012!.google.protobuf.EnumValueOptions\u0018\u00af\u00ca\u00bc\" \u0001(\u000b2\u001a.google.api.VisibilityRule:V\n\u0010field_visibility\u0012\u001d.google.protobuf.FieldOptions\u0018\u00af\u00ca\u00bc\" \u0001(\u000b2\u001a.google.api.VisibilityRule:Z\n\u0012message_visibility\u0012\u001f.google.protobuf.MessageOptions\u0018\u00af\u00ca\u00bc\" \u0001(\u000b2\u001a.google.api.VisibilityRule:X\n\u0011method_visibility\u0012\u001e.google.protobuf.MethodOptions\u0018\u00af\u00ca\u00bc\" \u0001(\u000b2\u001a.google.api.VisibilityRule:V\n\u000eapi_visibility\u0012\u001f.google.protobuf.ServiceOptions\u0018\u00af\u00ca\u00bc\" \u0001(\u000b2\u001a.google.api.VisibilityRuleBn\n\u000ecom.google.apiB\u000fVisibilityProtoP\u0001Z?google.golang.org/genproto/googleapis/api/visibility;visibility\u00f8\u0001\u0001\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{DescriptorProtos.getDescriptor()});
        internal_static_google_api_Visibility_descriptor = VisibilityProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_Visibility_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Visibility_descriptor, new String[]{"Rules"});
        internal_static_google_api_VisibilityRule_descriptor = VisibilityProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_api_VisibilityRule_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_VisibilityRule_descriptor, new String[]{"Selector", "Restriction"});
        enumVisibility.internalInit(descriptor.getExtensions().get(0));
        valueVisibility.internalInit(descriptor.getExtensions().get(1));
        fieldVisibility.internalInit(descriptor.getExtensions().get(2));
        messageVisibility.internalInit(descriptor.getExtensions().get(3));
        methodVisibility.internalInit(descriptor.getExtensions().get(4));
        apiVisibility.internalInit(descriptor.getExtensions().get(5));
        DescriptorProtos.getDescriptor();
    }
}

