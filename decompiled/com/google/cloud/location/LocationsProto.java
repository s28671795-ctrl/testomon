/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.location;

import com.google.api.AnnotationsProto;
import com.google.api.ClientProto;
import com.google.protobuf.AnyProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class LocationsProto {
    static final Descriptors.Descriptor internal_static_google_cloud_location_ListLocationsRequest_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_location_ListLocationsRequest_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_location_ListLocationsResponse_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_location_ListLocationsResponse_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_location_GetLocationRequest_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_location_GetLocationRequest_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_location_Location_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_location_Location_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_location_Location_LabelsEntry_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_location_Location_LabelsEntry_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private LocationsProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        LocationsProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n%google/cloud/location/locations.proto\u0012\u0015google.cloud.location\u001a\u001cgoogle/api/annotations.proto\u001a\u0019google/protobuf/any.proto\u001a\u0017google/api/client.proto\"[\n\u0014ListLocationsRequest\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u000e\n\u0006filter\u0018\u0002 \u0001(\t\u0012\u0011\n\tpage_size\u0018\u0003 \u0001(\u0005\u0012\u0012\n\npage_token\u0018\u0004 \u0001(\t\"d\n\u0015ListLocationsResponse\u00122\n\tlocations\u0018\u0001 \u0003(\u000b2\u001f.google.cloud.location.Location\u0012\u0017\n\u000fnext_page_token\u0018\u0002 \u0001(\t\"\"\n\u0012GetLocationRequest\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\"\u00d7\u0001\n\bLocation\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u0013\n\u000blocation_id\u0018\u0004 \u0001(\t\u0012\u0014\n\fdisplay_name\u0018\u0005 \u0001(\t\u0012;\n\u0006labels\u0018\u0002 \u0003(\u000b2+.google.cloud.location.Location.LabelsEntry\u0012&\n\bmetadata\u0018\u0003 \u0001(\u000b2\u0014.google.protobuf.Any\u001a-\n\u000bLabelsEntry\u0012\u000b\n\u0003key\u0018\u0001 \u0001(\t\u0012\r\n\u0005value\u0018\u0002 \u0001(\t:\u00028\u00012\u00a4\u0003\n\tLocations\u0012\u00ab\u0001\n\rListLocations\u0012+.google.cloud.location.ListLocationsRequest\u001a,.google.cloud.location.ListLocationsResponse\"?\u0082\u00d3\u00e4\u0093\u00029\u0012\u0014/v1/{name=locations}Z!\u0012\u001f/v1/{name=projects/*}/locations\u0012\u009e\u0001\n\u000bGetLocation\u0012).google.cloud.location.GetLocationRequest\u001a\u001f.google.cloud.location.Location\"C\u0082\u00d3\u00e4\u0093\u0002=\u0012\u0016/v1/{name=locations/*}Z#\u0012!/v1/{name=projects/*/locations/*}\u001aH\u00caA\u0014cloud.googleapis.com\u00d2A.https://www.googleapis.com/auth/cloud-platformBo\n\u0019com.google.cloud.locationB\u000eLocationsProtoP\u0001Z=google.golang.org/genproto/googleapis/cloud/location;location\u00f8\u0001\u0001b\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{AnnotationsProto.getDescriptor(), AnyProto.getDescriptor(), ClientProto.getDescriptor()});
        internal_static_google_cloud_location_ListLocationsRequest_descriptor = LocationsProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_cloud_location_ListLocationsRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_location_ListLocationsRequest_descriptor, new String[]{"Name", "Filter", "PageSize", "PageToken"});
        internal_static_google_cloud_location_ListLocationsResponse_descriptor = LocationsProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_cloud_location_ListLocationsResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_location_ListLocationsResponse_descriptor, new String[]{"Locations", "NextPageToken"});
        internal_static_google_cloud_location_GetLocationRequest_descriptor = LocationsProto.getDescriptor().getMessageTypes().get(2);
        internal_static_google_cloud_location_GetLocationRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_location_GetLocationRequest_descriptor, new String[]{"Name"});
        internal_static_google_cloud_location_Location_descriptor = LocationsProto.getDescriptor().getMessageTypes().get(3);
        internal_static_google_cloud_location_Location_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_location_Location_descriptor, new String[]{"Name", "LocationId", "DisplayName", "Labels", "Metadata"});
        internal_static_google_cloud_location_Location_LabelsEntry_descriptor = internal_static_google_cloud_location_Location_descriptor.getNestedTypes().get(0);
        internal_static_google_cloud_location_Location_LabelsEntry_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_location_Location_LabelsEntry_descriptor, new String[]{"Key", "Value"});
        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(ClientProto.defaultHost);
        registry.add(AnnotationsProto.http);
        registry.add(ClientProto.oauthScopes);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(descriptor, registry);
        AnnotationsProto.getDescriptor();
        AnyProto.getDescriptor();
        ClientProto.getDescriptor();
    }
}

