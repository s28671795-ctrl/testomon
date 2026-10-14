/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

public final class AuthProto {
    static final Descriptors.Descriptor internal_static_google_api_Authentication_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Authentication_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_AuthenticationRule_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_AuthenticationRule_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_JwtLocation_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_JwtLocation_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_AuthProvider_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_AuthProvider_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_OAuthRequirements_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_OAuthRequirements_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_AuthRequirement_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_AuthRequirement_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private AuthProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        AuthProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0015google/api/auth.proto\u0012\ngoogle.api\"l\n\u000eAuthentication\u0012-\n\u0005rules\u0018\u0003 \u0003(\u000b2\u001e.google.api.AuthenticationRule\u0012+\n\tproviders\u0018\u0004 \u0003(\u000b2\u0018.google.api.AuthProvider\"\u00a9\u0001\n\u0012AuthenticationRule\u0012\u0010\n\bselector\u0018\u0001 \u0001(\t\u0012,\n\u0005oauth\u0018\u0002 \u0001(\u000b2\u001d.google.api.OAuthRequirements\u0012 \n\u0018allow_without_credential\u0018\u0005 \u0001(\b\u00121\n\frequirements\u0018\u0007 \u0003(\u000b2\u001b.google.api.AuthRequirement\"^\n\u000bJwtLocation\u0012\u0010\n\u0006header\u0018\u0001 \u0001(\tH\u0000\u0012\u000f\n\u0005query\u0018\u0002 \u0001(\tH\u0000\u0012\u0010\n\u0006cookie\u0018\u0004 \u0001(\tH\u0000\u0012\u0014\n\fvalue_prefix\u0018\u0003 \u0001(\tB\u0004\n\u0002in\"\u009a\u0001\n\fAuthProvider\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\u000e\n\u0006issuer\u0018\u0002 \u0001(\t\u0012\u0010\n\bjwks_uri\u0018\u0003 \u0001(\t\u0012\u0011\n\taudiences\u0018\u0004 \u0001(\t\u0012\u0019\n\u0011authorization_url\u0018\u0005 \u0001(\t\u0012.\n\rjwt_locations\u0018\u0006 \u0003(\u000b2\u0017.google.api.JwtLocation\"-\n\u0011OAuthRequirements\u0012\u0018\n\u0010canonical_scopes\u0018\u0001 \u0001(\t\"9\n\u000fAuthRequirement\u0012\u0013\n\u000bprovider_id\u0018\u0001 \u0001(\t\u0012\u0011\n\taudiences\u0018\u0002 \u0001(\tBk\n\u000ecom.google.apiB\tAuthProtoP\u0001ZEgoogle.golang.org/genproto/googleapis/api/serviceconfig;serviceconfig\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_api_Authentication_descriptor = AuthProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_Authentication_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Authentication_descriptor, new String[]{"Rules", "Providers"});
        internal_static_google_api_AuthenticationRule_descriptor = AuthProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_api_AuthenticationRule_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_AuthenticationRule_descriptor, new String[]{"Selector", "Oauth", "AllowWithoutCredential", "Requirements"});
        internal_static_google_api_JwtLocation_descriptor = AuthProto.getDescriptor().getMessageTypes().get(2);
        internal_static_google_api_JwtLocation_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_JwtLocation_descriptor, new String[]{"Header", "Query", "Cookie", "ValuePrefix", "In"});
        internal_static_google_api_AuthProvider_descriptor = AuthProto.getDescriptor().getMessageTypes().get(3);
        internal_static_google_api_AuthProvider_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_AuthProvider_descriptor, new String[]{"Id", "Issuer", "JwksUri", "Audiences", "AuthorizationUrl", "JwtLocations"});
        internal_static_google_api_OAuthRequirements_descriptor = AuthProto.getDescriptor().getMessageTypes().get(4);
        internal_static_google_api_OAuthRequirements_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_OAuthRequirements_descriptor, new String[]{"CanonicalScopes"});
        internal_static_google_api_AuthRequirement_descriptor = AuthProto.getDescriptor().getMessageTypes().get(5);
        internal_static_google_api_AuthRequirement_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_AuthRequirement_descriptor, new String[]{"ProviderId", "Audiences"});
    }
}

