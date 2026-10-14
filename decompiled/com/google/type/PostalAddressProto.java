/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class PostalAddressProto {
    static final Descriptors.Descriptor internal_static_google_type_PostalAddress_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_type_PostalAddress_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private PostalAddressProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        PostalAddressProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n google/type/postal_address.proto\u0012\u000bgoogle.type\"\u00fd\u0001\n\rPostalAddress\u0012\u0010\n\brevision\u0018\u0001 \u0001(\u0005\u0012\u0013\n\u000bregion_code\u0018\u0002 \u0001(\t\u0012\u0015\n\rlanguage_code\u0018\u0003 \u0001(\t\u0012\u0013\n\u000bpostal_code\u0018\u0004 \u0001(\t\u0012\u0014\n\fsorting_code\u0018\u0005 \u0001(\t\u0012\u001b\n\u0013administrative_area\u0018\u0006 \u0001(\t\u0012\u0010\n\blocality\u0018\u0007 \u0001(\t\u0012\u0013\n\u000bsublocality\u0018\b \u0001(\t\u0012\u0015\n\raddress_lines\u0018\t \u0003(\t\u0012\u0012\n\nrecipients\u0018\n \u0003(\t\u0012\u0014\n\forganization\u0018\u000b \u0001(\tBx\n\u000fcom.google.typeB\u0012PostalAddressProtoP\u0001ZFgoogle.golang.org/genproto/googleapis/type/postaladdress;postaladdress\u00f8\u0001\u0001\u00a2\u0002\u0003GTPb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_type_PostalAddress_descriptor = PostalAddressProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_type_PostalAddress_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_type_PostalAddress_descriptor, new String[]{"Revision", "RegionCode", "LanguageCode", "PostalCode", "SortingCode", "AdministrativeArea", "Locality", "Sublocality", "AddressLines", "Recipients", "Organization"});
    }
}

