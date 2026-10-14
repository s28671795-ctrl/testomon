/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class SystemParameterProto {
    static final Descriptors.Descriptor internal_static_google_api_SystemParameters_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_SystemParameters_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_SystemParameterRule_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_SystemParameterRule_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_SystemParameter_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_SystemParameter_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private SystemParameterProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        SystemParameterProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n!google/api/system_parameter.proto\u0012\ngoogle.api\"B\n\u0010SystemParameters\u0012.\n\u0005rules\u0018\u0001 \u0003(\u000b2\u001f.google.api.SystemParameterRule\"X\n\u0013SystemParameterRule\u0012\u0010\n\bselector\u0018\u0001 \u0001(\t\u0012/\n\nparameters\u0018\u0002 \u0003(\u000b2\u001b.google.api.SystemParameter\"Q\n\u000fSystemParameter\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u0013\n\u000bhttp_header\u0018\u0002 \u0001(\t\u0012\u001b\n\u0013url_query_parameter\u0018\u0003 \u0001(\tBv\n\u000ecom.google.apiB\u0014SystemParameterProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_api_SystemParameters_descriptor = SystemParameterProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_SystemParameters_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_SystemParameters_descriptor, new String[]{"Rules"});
        internal_static_google_api_SystemParameterRule_descriptor = SystemParameterProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_api_SystemParameterRule_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_SystemParameterRule_descriptor, new String[]{"Selector", "Parameters"});
        internal_static_google_api_SystemParameter_descriptor = SystemParameterProto.getDescriptor().getMessageTypes().get(2);
        internal_static_google_api_SystemParameter_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_SystemParameter_descriptor, new String[]{"Name", "HttpHeader", "UrlQueryParameter"});
    }
}

