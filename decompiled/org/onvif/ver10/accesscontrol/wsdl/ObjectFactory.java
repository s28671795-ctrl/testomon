/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import javax.xml.bind.annotation.XmlRegistry;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointCapabilities;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointInfo;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointInfoBase;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointState;
import org.onvif.ver10.accesscontrol.wsdl.AreaInfo;
import org.onvif.ver10.accesscontrol.wsdl.AreaInfoBase;
import org.onvif.ver10.accesscontrol.wsdl.DisableAccessPoint;
import org.onvif.ver10.accesscontrol.wsdl.DisableAccessPointResponse;
import org.onvif.ver10.accesscontrol.wsdl.EnableAccessPoint;
import org.onvif.ver10.accesscontrol.wsdl.EnableAccessPointResponse;
import org.onvif.ver10.accesscontrol.wsdl.ExternalAuthorization;
import org.onvif.ver10.accesscontrol.wsdl.ExternalAuthorizationResponse;
import org.onvif.ver10.accesscontrol.wsdl.GetAccessPointInfo;
import org.onvif.ver10.accesscontrol.wsdl.GetAccessPointInfoList;
import org.onvif.ver10.accesscontrol.wsdl.GetAccessPointInfoListResponse;
import org.onvif.ver10.accesscontrol.wsdl.GetAccessPointInfoResponse;
import org.onvif.ver10.accesscontrol.wsdl.GetAccessPointState;
import org.onvif.ver10.accesscontrol.wsdl.GetAccessPointStateResponse;
import org.onvif.ver10.accesscontrol.wsdl.GetAreaInfo;
import org.onvif.ver10.accesscontrol.wsdl.GetAreaInfoList;
import org.onvif.ver10.accesscontrol.wsdl.GetAreaInfoListResponse;
import org.onvif.ver10.accesscontrol.wsdl.GetAreaInfoResponse;
import org.onvif.ver10.accesscontrol.wsdl.GetServiceCapabilities;
import org.onvif.ver10.accesscontrol.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.accesscontrol.wsdl.ServiceCapabilities;

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

    public GetAccessPointInfoList createGetAccessPointInfoList() {
        return new GetAccessPointInfoList();
    }

    public GetAccessPointInfoListResponse createGetAccessPointInfoListResponse() {
        return new GetAccessPointInfoListResponse();
    }

    public AccessPointInfo createAccessPointInfo() {
        return new AccessPointInfo();
    }

    public GetAccessPointInfo createGetAccessPointInfo() {
        return new GetAccessPointInfo();
    }

    public GetAccessPointInfoResponse createGetAccessPointInfoResponse() {
        return new GetAccessPointInfoResponse();
    }

    public GetAreaInfoList createGetAreaInfoList() {
        return new GetAreaInfoList();
    }

    public GetAreaInfoListResponse createGetAreaInfoListResponse() {
        return new GetAreaInfoListResponse();
    }

    public AreaInfo createAreaInfo() {
        return new AreaInfo();
    }

    public GetAreaInfo createGetAreaInfo() {
        return new GetAreaInfo();
    }

    public GetAreaInfoResponse createGetAreaInfoResponse() {
        return new GetAreaInfoResponse();
    }

    public GetAccessPointState createGetAccessPointState() {
        return new GetAccessPointState();
    }

    public GetAccessPointStateResponse createGetAccessPointStateResponse() {
        return new GetAccessPointStateResponse();
    }

    public AccessPointState createAccessPointState() {
        return new AccessPointState();
    }

    public EnableAccessPoint createEnableAccessPoint() {
        return new EnableAccessPoint();
    }

    public EnableAccessPointResponse createEnableAccessPointResponse() {
        return new EnableAccessPointResponse();
    }

    public DisableAccessPoint createDisableAccessPoint() {
        return new DisableAccessPoint();
    }

    public DisableAccessPointResponse createDisableAccessPointResponse() {
        return new DisableAccessPointResponse();
    }

    public ExternalAuthorization createExternalAuthorization() {
        return new ExternalAuthorization();
    }

    public ExternalAuthorizationResponse createExternalAuthorizationResponse() {
        return new ExternalAuthorizationResponse();
    }

    public AccessPointInfoBase createAccessPointInfoBase() {
        return new AccessPointInfoBase();
    }

    public AccessPointCapabilities createAccessPointCapabilities() {
        return new AccessPointCapabilities();
    }

    public AreaInfoBase createAreaInfoBase() {
        return new AreaInfoBase();
    }
}

