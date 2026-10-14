/*
 * Decompiled with CFR 0.152.
 */
package com.google.geo.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.type.LatLngProto;

public final class ViewportProto {
    static final Descriptors.Descriptor internal_static_google_geo_type_Viewport_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_geo_type_Viewport_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private ViewportProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        ViewportProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001egoogle/geo/type/viewport.proto\u0012\u000fgoogle.geo.type\u001a\u0018google/type/latlng.proto\"O\n\bViewport\u0012 \n\u0003low\u0018\u0001 \u0001(\u000b2\u0013.google.type.LatLng\u0012!\n\u0004high\u0018\u0002 \u0001(\u000b2\u0013.google.type.LatLngBo\n\u0013com.google.geo.typeB\rViewportProtoP\u0001Z@google.golang.org/genproto/googleapis/geo/type/viewport;viewport\u00a2\u0002\u0004GGTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{LatLngProto.getDescriptor()});
        internal_static_google_geo_type_Viewport_descriptor = ViewportProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_geo_type_Viewport_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_geo_type_Viewport_descriptor, new String[]{"Low", "High"});
        LatLngProto.getDescriptor();
    }
}

