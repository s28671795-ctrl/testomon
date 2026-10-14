/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import javax.xml.bind.annotation.XmlRegistry;
import org.onvif.ver10.accessrules.wsdl.AccessPolicy;
import org.onvif.ver10.accessrules.wsdl.AccessPolicyExtension;
import org.onvif.ver10.accessrules.wsdl.AccessProfile;
import org.onvif.ver10.accessrules.wsdl.AccessProfileExtension;
import org.onvif.ver10.accessrules.wsdl.AccessProfileInfo;
import org.onvif.ver10.accessrules.wsdl.CreateAccessProfile;
import org.onvif.ver10.accessrules.wsdl.CreateAccessProfileResponse;
import org.onvif.ver10.accessrules.wsdl.DeleteAccessProfile;
import org.onvif.ver10.accessrules.wsdl.DeleteAccessProfileResponse;
import org.onvif.ver10.accessrules.wsdl.GetAccessProfileInfo;
import org.onvif.ver10.accessrules.wsdl.GetAccessProfileInfoList;
import org.onvif.ver10.accessrules.wsdl.GetAccessProfileInfoListResponse;
import org.onvif.ver10.accessrules.wsdl.GetAccessProfileInfoResponse;
import org.onvif.ver10.accessrules.wsdl.GetAccessProfileList;
import org.onvif.ver10.accessrules.wsdl.GetAccessProfileListResponse;
import org.onvif.ver10.accessrules.wsdl.GetAccessProfiles;
import org.onvif.ver10.accessrules.wsdl.GetAccessProfilesResponse;
import org.onvif.ver10.accessrules.wsdl.GetServiceCapabilities;
import org.onvif.ver10.accessrules.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.accessrules.wsdl.ModifyAccessProfile;
import org.onvif.ver10.accessrules.wsdl.ModifyAccessProfileResponse;
import org.onvif.ver10.accessrules.wsdl.ServiceCapabilities;

@XmlRegistry
public class ObjectFactory {
    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public ServiceCapabilities createServiceCapabilities() {
        return new ServiceCapabilities();
    }

    public GetAccessProfileInfo createGetAccessProfileInfo() {
        return new GetAccessProfileInfo();
    }

    public GetAccessProfileInfoResponse createGetAccessProfileInfoResponse() {
        return new GetAccessProfileInfoResponse();
    }

    public AccessProfileInfo createAccessProfileInfo() {
        return new AccessProfileInfo();
    }

    public GetAccessProfileInfoList createGetAccessProfileInfoList() {
        return new GetAccessProfileInfoList();
    }

    public GetAccessProfileInfoListResponse createGetAccessProfileInfoListResponse() {
        return new GetAccessProfileInfoListResponse();
    }

    public GetAccessProfiles createGetAccessProfiles() {
        return new GetAccessProfiles();
    }

    public GetAccessProfilesResponse createGetAccessProfilesResponse() {
        return new GetAccessProfilesResponse();
    }

    public AccessProfile createAccessProfile() {
        return new AccessProfile();
    }

    public GetAccessProfileList createGetAccessProfileList() {
        return new GetAccessProfileList();
    }

    public GetAccessProfileListResponse createGetAccessProfileListResponse() {
        return new GetAccessProfileListResponse();
    }

    public CreateAccessProfile createCreateAccessProfile() {
        return new CreateAccessProfile();
    }

    public CreateAccessProfileResponse createCreateAccessProfileResponse() {
        return new CreateAccessProfileResponse();
    }

    public ModifyAccessProfile createModifyAccessProfile() {
        return new ModifyAccessProfile();
    }

    public ModifyAccessProfileResponse createModifyAccessProfileResponse() {
        return new ModifyAccessProfileResponse();
    }

    public DeleteAccessProfile createDeleteAccessProfile() {
        return new DeleteAccessProfile();
    }

    public DeleteAccessProfileResponse createDeleteAccessProfileResponse() {
        return new DeleteAccessProfileResponse();
    }

    public AccessPolicy createAccessPolicy() {
        return new AccessPolicy();
    }

    public AccessPolicyExtension createAccessPolicyExtension() {
        return new AccessPolicyExtension();
    }

    public AccessProfileExtension createAccessProfileExtension() {
        return new AccessProfileExtension();
    }
}

