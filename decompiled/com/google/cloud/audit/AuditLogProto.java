/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.api.FieldBehaviorProto;
import com.google.protobuf.AnyProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.StructProto;
import com.google.rpc.StatusProto;
import com.google.rpc.context.AttributeContextProto;

public final class AuditLogProto {
    static final Descriptors.Descriptor internal_static_google_cloud_audit_AuditLog_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_AuditLog_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_AuthenticationInfo_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_AuthenticationInfo_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_AuthorizationInfo_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_AuthorizationInfo_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_RequestMetadata_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_RequestMetadata_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_ResourceLocation_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_ResourceLocation_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_ServiceAccountDelegationInfo_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_ServiceAccountDelegationInfo_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_PolicyViolationInfo_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_PolicyViolationInfo_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_OrgPolicyViolationInfo_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_OrgPolicyViolationInfo_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_OrgPolicyViolationInfo_ResourceTagsEntry_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_OrgPolicyViolationInfo_ResourceTagsEntry_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_cloud_audit_ViolationInfo_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_cloud_audit_ViolationInfo_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private AuditLogProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        AuditLogProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\"google/cloud/audit/audit_log.proto\u0012\u0012google.cloud.audit\u001a\u001fgoogle/api/field_behavior.proto\u001a\u0019google/protobuf/any.proto\u001a\u001cgoogle/protobuf/struct.proto\u001a*google/rpc/context/attribute_context.proto\u001a\u0017google/rpc/status.proto\"\u00c6\u0005\n\bAuditLog\u0012\u0014\n\fservice_name\u0018\u0007 \u0001(\t\u0012\u0013\n\u000bmethod_name\u0018\b \u0001(\t\u0012\u0015\n\rresource_name\u0018\u000b \u0001(\t\u0012?\n\u0011resource_location\u0018\u0014 \u0001(\u000b2$.google.cloud.audit.ResourceLocation\u00128\n\u0017resource_original_state\u0018\u0013 \u0001(\u000b2\u0017.google.protobuf.Struct\u0012\u001a\n\u0012num_response_items\u0018\f \u0001(\u0003\u0012\"\n\u0006status\u0018\u0002 \u0001(\u000b2\u0012.google.rpc.Status\u0012C\n\u0013authentication_info\u0018\u0003 \u0001(\u000b2&.google.cloud.audit.AuthenticationInfo\u0012A\n\u0012authorization_info\u0018\t \u0003(\u000b2%.google.cloud.audit.AuthorizationInfo\u0012F\n\u0015policy_violation_info\u0018\u0019 \u0001(\u000b2'.google.cloud.audit.PolicyViolationInfo\u0012=\n\u0010request_metadata\u0018\u0004 \u0001(\u000b2#.google.cloud.audit.RequestMetadata\u0012(\n\u0007request\u0018\u0010 \u0001(\u000b2\u0017.google.protobuf.Struct\u0012)\n\bresponse\u0018\u0011 \u0001(\u000b2\u0017.google.protobuf.Struct\u0012)\n\bmetadata\u0018\u0012 \u0001(\u000b2\u0017.google.protobuf.Struct\u0012.\n\fservice_data\u0018\u000f \u0001(\u000b2\u0014.google.protobuf.AnyB\u0002\u0018\u0001\"\u0099\u0002\n\u0012AuthenticationInfo\u0012\u0017\n\u000fprincipal_email\u0018\u0001 \u0001(\t\u0012\u001a\n\u0012authority_selector\u0018\u0002 \u0001(\t\u00126\n\u0015third_party_principal\u0018\u0004 \u0001(\u000b2\u0017.google.protobuf.Struct\u0012 \n\u0018service_account_key_name\u0018\u0005 \u0001(\t\u0012Y\n\u001fservice_account_delegation_info\u0018\u0006 \u0003(\u000b20.google.cloud.audit.ServiceAccountDelegationInfo\u0012\u0019\n\u0011principal_subject\u0018\b \u0001(\t\"\u0096\u0001\n\u0011AuthorizationInfo\u0012\u0010\n\bresource\u0018\u0001 \u0001(\t\u0012\u0012\n\npermission\u0018\u0002 \u0001(\t\u0012\u000f\n\u0007granted\u0018\u0003 \u0001(\b\u0012J\n\u0013resource_attributes\u0018\u0005 \u0001(\u000b2-.google.rpc.context.AttributeContext.Resource\"\u00f5\u0001\n\u000fRequestMetadata\u0012\u0011\n\tcaller_ip\u0018\u0001 \u0001(\t\u0012\"\n\u001acaller_supplied_user_agent\u0018\u0002 \u0001(\t\u0012\u0016\n\u000ecaller_network\u0018\u0003 \u0001(\t\u0012H\n\u0012request_attributes\u0018\u0007 \u0001(\u000b2,.google.rpc.context.AttributeContext.Request\u0012I\n\u0016destination_attributes\u0018\b \u0001(\u000b2).google.rpc.context.AttributeContext.Peer\"I\n\u0010ResourceLocation\u0012\u0019\n\u0011current_locations\u0018\u0001 \u0003(\t\u0012\u001a\n\u0012original_locations\u0018\u0002 \u0003(\t\"\u00c3\u0003\n\u001cServiceAccountDelegationInfo\u0012\u0019\n\u0011principal_subject\u0018\u0003 \u0001(\t\u0012e\n\u0015first_party_principal\u0018\u0001 \u0001(\u000b2D.google.cloud.audit.ServiceAccountDelegationInfo.FirstPartyPrincipalH\u0000\u0012e\n\u0015third_party_principal\u0018\u0002 \u0001(\u000b2D.google.cloud.audit.ServiceAccountDelegationInfo.ThirdPartyPrincipalH\u0000\u001aa\n\u0013FirstPartyPrincipal\u0012\u0017\n\u000fprincipal_email\u0018\u0001 \u0001(\t\u00121\n\u0010service_metadata\u0018\u0002 \u0001(\u000b2\u0017.google.protobuf.Struct\u001aJ\n\u0013ThirdPartyPrincipal\u00123\n\u0012third_party_claims\u0018\u0001 \u0001(\u000b2\u0017.google.protobuf.StructB\u000b\n\tAuthority\"d\n\u0013PolicyViolationInfo\u0012M\n\u0019org_policy_violation_info\u0018\u0001 \u0001(\u000b2*.google.cloud.audit.OrgPolicyViolationInfo\"\u00b6\u0002\n\u0016OrgPolicyViolationInfo\u0012.\n\u0007payload\u0018\u0001 \u0001(\u000b2\u0017.google.protobuf.StructB\u0004\u00e2A\u0001\u0001\u0012\u001b\n\rresource_type\u0018\u0002 \u0001(\tB\u0004\u00e2A\u0001\u0001\u0012Y\n\rresource_tags\u0018\u0003 \u0003(\u000b2<.google.cloud.audit.OrgPolicyViolationInfo.ResourceTagsEntryB\u0004\u00e2A\u0001\u0001\u0012?\n\u000eviolation_info\u0018\u0004 \u0003(\u000b2!.google.cloud.audit.ViolationInfoB\u0004\u00e2A\u0001\u0001\u001a3\n\u0011ResourceTagsEntry\u0012\u000b\n\u0003key\u0018\u0001 \u0001(\t\u0012\r\n\u0005value\u0018\u0002 \u0001(\t:\u00028\u0001\"\u009b\u0002\n\rViolationInfo\u0012\u0018\n\nconstraint\u0018\u0001 \u0001(\tB\u0004\u00e2A\u0001\u0001\u0012\u001b\n\rerror_message\u0018\u0002 \u0001(\tB\u0004\u00e2A\u0001\u0001\u0012\u001b\n\rchecked_value\u0018\u0003 \u0001(\tB\u0004\u00e2A\u0001\u0001\u0012G\n\u000bpolicy_type\u0018\u0004 \u0001(\u000e2,.google.cloud.audit.ViolationInfo.PolicyTypeB\u0004\u00e2A\u0001\u0001\"m\n\nPolicyType\u0012\u001b\n\u0017POLICY_TYPE_UNSPECIFIED\u0010\u0000\u0012\u0016\n\u0012BOOLEAN_CONSTRAINT\u0010\u0001\u0012\u0013\n\u000fLIST_CONSTRAINT\u0010\u0002\u0012\u0015\n\u0011CUSTOM_CONSTRAINT\u0010\u0003Be\n\u0016com.google.cloud.auditB\rAuditLogProtoP\u0001Z7google.golang.org/genproto/googleapis/cloud/audit;audit\u00f8\u0001\u0001b\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{FieldBehaviorProto.getDescriptor(), AnyProto.getDescriptor(), StructProto.getDescriptor(), AttributeContextProto.getDescriptor(), StatusProto.getDescriptor()});
        internal_static_google_cloud_audit_AuditLog_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_cloud_audit_AuditLog_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_AuditLog_descriptor, new String[]{"ServiceName", "MethodName", "ResourceName", "ResourceLocation", "ResourceOriginalState", "NumResponseItems", "Status", "AuthenticationInfo", "AuthorizationInfo", "PolicyViolationInfo", "RequestMetadata", "Request", "Response", "Metadata", "ServiceData"});
        internal_static_google_cloud_audit_AuthenticationInfo_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(1);
        internal_static_google_cloud_audit_AuthenticationInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_AuthenticationInfo_descriptor, new String[]{"PrincipalEmail", "AuthoritySelector", "ThirdPartyPrincipal", "ServiceAccountKeyName", "ServiceAccountDelegationInfo", "PrincipalSubject"});
        internal_static_google_cloud_audit_AuthorizationInfo_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(2);
        internal_static_google_cloud_audit_AuthorizationInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_AuthorizationInfo_descriptor, new String[]{"Resource", "Permission", "Granted", "ResourceAttributes"});
        internal_static_google_cloud_audit_RequestMetadata_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(3);
        internal_static_google_cloud_audit_RequestMetadata_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_RequestMetadata_descriptor, new String[]{"CallerIp", "CallerSuppliedUserAgent", "CallerNetwork", "RequestAttributes", "DestinationAttributes"});
        internal_static_google_cloud_audit_ResourceLocation_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(4);
        internal_static_google_cloud_audit_ResourceLocation_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_ResourceLocation_descriptor, new String[]{"CurrentLocations", "OriginalLocations"});
        internal_static_google_cloud_audit_ServiceAccountDelegationInfo_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(5);
        internal_static_google_cloud_audit_ServiceAccountDelegationInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_ServiceAccountDelegationInfo_descriptor, new String[]{"PrincipalSubject", "FirstPartyPrincipal", "ThirdPartyPrincipal", "Authority"});
        internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_descriptor = internal_static_google_cloud_audit_ServiceAccountDelegationInfo_descriptor.getNestedTypes().get(0);
        internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_ServiceAccountDelegationInfo_FirstPartyPrincipal_descriptor, new String[]{"PrincipalEmail", "ServiceMetadata"});
        internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_descriptor = internal_static_google_cloud_audit_ServiceAccountDelegationInfo_descriptor.getNestedTypes().get(1);
        internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_ServiceAccountDelegationInfo_ThirdPartyPrincipal_descriptor, new String[]{"ThirdPartyClaims"});
        internal_static_google_cloud_audit_PolicyViolationInfo_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(6);
        internal_static_google_cloud_audit_PolicyViolationInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_PolicyViolationInfo_descriptor, new String[]{"OrgPolicyViolationInfo"});
        internal_static_google_cloud_audit_OrgPolicyViolationInfo_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(7);
        internal_static_google_cloud_audit_OrgPolicyViolationInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_OrgPolicyViolationInfo_descriptor, new String[]{"Payload", "ResourceType", "ResourceTags", "ViolationInfo"});
        internal_static_google_cloud_audit_OrgPolicyViolationInfo_ResourceTagsEntry_descriptor = internal_static_google_cloud_audit_OrgPolicyViolationInfo_descriptor.getNestedTypes().get(0);
        internal_static_google_cloud_audit_OrgPolicyViolationInfo_ResourceTagsEntry_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_OrgPolicyViolationInfo_ResourceTagsEntry_descriptor, new String[]{"Key", "Value"});
        internal_static_google_cloud_audit_ViolationInfo_descriptor = AuditLogProto.getDescriptor().getMessageTypes().get(8);
        internal_static_google_cloud_audit_ViolationInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_cloud_audit_ViolationInfo_descriptor, new String[]{"Constraint", "ErrorMessage", "CheckedValue", "PolicyType"});
        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(FieldBehaviorProto.fieldBehavior);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(descriptor, registry);
        FieldBehaviorProto.getDescriptor();
        AnyProto.getDescriptor();
        StructProto.getDescriptor();
        AttributeContextProto.getDescriptor();
        StatusProto.getDescriptor();
    }
}

