/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud;

import com.google.cloud.OperationResponseMapping;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;

public final class ExtendedOperationsProto {
    public static final int OPERATION_FIELD_FIELD_NUMBER = 1149;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.FieldOptions, OperationResponseMapping> operationField = GeneratedMessage.newFileScopedGeneratedExtension(OperationResponseMapping.class, null);
    public static final int OPERATION_REQUEST_FIELD_FIELD_NUMBER = 1150;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.FieldOptions, String> operationRequestField = GeneratedMessage.newFileScopedGeneratedExtension(String.class, null);
    public static final int OPERATION_RESPONSE_FIELD_FIELD_NUMBER = 1151;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.FieldOptions, String> operationResponseField = GeneratedMessage.newFileScopedGeneratedExtension(String.class, null);
    public static final int OPERATION_SERVICE_FIELD_NUMBER = 1249;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MethodOptions, String> operationService = GeneratedMessage.newFileScopedGeneratedExtension(String.class, null);
    public static final int OPERATION_POLLING_METHOD_FIELD_NUMBER = 1250;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MethodOptions, Boolean> operationPollingMethod = GeneratedMessage.newFileScopedGeneratedExtension(Boolean.class, null);
    private static Descriptors.FileDescriptor descriptor;

    private ExtendedOperationsProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add(operationField);
        registry.add(operationRequestField);
        registry.add(operationResponseField);
        registry.add(operationService);
        registry.add(operationPollingMethod);
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        ExtendedOperationsProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n&google/cloud/extended_operations.proto\u0012\fgoogle.cloud\u001a google/protobuf/descriptor.proto*b\n\u0018OperationResponseMapping\u0012\r\n\tUNDEFINED\u0010\u0000\u0012\b\n\u0004NAME\u0010\u0001\u0012\n\n\u0006STATUS\u0010\u0002\u0012\u000e\n\nERROR_CODE\u0010\u0003\u0012\u0011\n\rERROR_MESSAGE\u0010\u0004:_\n\u000foperation_field\u0012\u001d.google.protobuf.FieldOptions\u0018\u00fd\b \u0001(\u000e2&.google.cloud.OperationResponseMapping:?\n\u0017operation_request_field\u0012\u001d.google.protobuf.FieldOptions\u0018\u00fe\b \u0001(\t:@\n\u0018operation_response_field\u0012\u001d.google.protobuf.FieldOptions\u0018\u00ff\b \u0001(\t::\n\u0011operation_service\u0012\u001e.google.protobuf.MethodOptions\u0018\u00e1\t \u0001(\t:A\n\u0018operation_polling_method\u0012\u001e.google.protobuf.MethodOptions\u0018\u00e2\t \u0001(\bBy\n\u0010com.google.cloudB\u0017ExtendedOperationsProtoP\u0001ZCgoogle.golang.org/genproto/googleapis/cloud/extendedops;extendedops\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{DescriptorProtos.getDescriptor()});
        operationField.internalInit(descriptor.getExtensions().get(0));
        operationRequestField.internalInit(descriptor.getExtensions().get(1));
        operationResponseField.internalInit(descriptor.getExtensions().get(2));
        operationService.internalInit(descriptor.getExtensions().get(3));
        operationPollingMethod.internalInit(descriptor.getExtensions().get(4));
        DescriptorProtos.getDescriptor();
    }
}

