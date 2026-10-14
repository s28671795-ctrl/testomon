/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.HttpProto;
import com.google.api.HttpRule;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;

public final class AnnotationsProto {
    public static final int HTTP_FIELD_NUMBER = 72295728;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MethodOptions, HttpRule> http = GeneratedMessage.newFileScopedGeneratedExtension(HttpRule.class, HttpRule.getDefaultInstance());
    private static Descriptors.FileDescriptor descriptor;

    private AnnotationsProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add(http);
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        AnnotationsProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001cgoogle/api/annotations.proto\u0012\ngoogle.api\u001a\u0015google/api/http.proto\u001a google/protobuf/descriptor.proto:E\n\u0004http\u0012\u001e.google.protobuf.MethodOptions\u0018\u00b0\u00ca\u00bc\" \u0001(\u000b2\u0014.google.api.HttpRuleBn\n\u000ecom.google.apiB\u0010AnnotationsProtoP\u0001ZAgoogle.golang.org/genproto/googleapis/api/annotations;annotations\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{HttpProto.getDescriptor(), DescriptorProtos.getDescriptor()});
        http.internalInit(descriptor.getExtensions().get(0));
        HttpProto.getDescriptor();
        DescriptorProtos.getDescriptor();
    }
}

