/*
 * Decompiled with CFR 0.152.
 */
package com.google.logging.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;

public final class LogSeverityProto {
    private static Descriptors.FileDescriptor descriptor;

    private LogSeverityProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        LogSeverityProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n&google/logging/type/log_severity.proto\u0012\u0013google.logging.type*\u0082\u0001\n\u000bLogSeverity\u0012\u000b\n\u0007DEFAULT\u0010\u0000\u0012\t\n\u0005DEBUG\u0010d\u0012\t\n\u0004INFO\u0010\u00c8\u0001\u0012\u000b\n\u0006NOTICE\u0010\u00ac\u0002\u0012\f\n\u0007WARNING\u0010\u0090\u0003\u0012\n\n\u0005ERROR\u0010\u00f4\u0003\u0012\r\n\bCRITICAL\u0010\u00d8\u0004\u0012\n\n\u0005ALERT\u0010\u00bc\u0005\u0012\u000e\n\tEMERGENCY\u0010\u00a0\u0006B\u00c5\u0001\n\u0017com.google.logging.typeB\u0010LogSeverityProtoP\u0001Z8google.golang.org/genproto/googleapis/logging/type;ltype\u00a2\u0002\u0004GLOG\u00aa\u0002\u0019Google.Cloud.Logging.Type\u00ca\u0002\u0019Google\\Cloud\\Logging\\Type\u00ea\u0002\u001cGoogle::Cloud::Logging::Typeb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
    }
}

