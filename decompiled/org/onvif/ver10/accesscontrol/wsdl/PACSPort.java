/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointInfo;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointState;
import org.onvif.ver10.accesscontrol.wsdl.AreaInfo;
import org.onvif.ver10.accesscontrol.wsdl.Decision;
import org.onvif.ver10.accesscontrol.wsdl.ObjectFactory;
import org.onvif.ver10.accesscontrol.wsdl.ServiceCapabilities;

@WebService(targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", name="PACSPort")
@XmlSeeAlso(value={ObjectFactory.class, org.onvif.ver10.pacs.ObjectFactory.class})
public interface PACSPort {
    @WebMethod(operationName="GetAccessPointState", action="http://www.onvif.org/ver10/accesscontrol/wsdl/GetAccessPointState")
    @RequestWrapper(localName="GetAccessPointState", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAccessPointState")
    @ResponseWrapper(localName="GetAccessPointStateResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAccessPointStateResponse")
    @WebResult(name="AccessPointState", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl")
    public AccessPointState getAccessPointState(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") String var1);

    @WebMethod(operationName="ExternalAuthorization", action="http://www.onvif.org/ver10/accesscontrol/wsdl/ExternalAuthorization")
    @RequestWrapper(localName="ExternalAuthorization", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.ExternalAuthorization")
    @ResponseWrapper(localName="ExternalAuthorizationResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.ExternalAuthorizationResponse")
    public void externalAuthorization(@WebParam(name="AccessPointToken", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") String var1, @WebParam(name="CredentialToken", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") String var2, @WebParam(name="Reason", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") String var3, @WebParam(name="Decision", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") Decision var4);

    @WebMethod(operationName="EnableAccessPoint", action="http://www.onvif.org/ver10/accesscontrol/wsdl/EnableAccessPoint")
    @RequestWrapper(localName="EnableAccessPoint", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.EnableAccessPoint")
    @ResponseWrapper(localName="EnableAccessPointResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.EnableAccessPointResponse")
    public void enableAccessPoint(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") String var1);

    @WebMethod(operationName="DisableAccessPoint", action="http://www.onvif.org/ver10/accesscontrol/wsdl/DisableAccessPoint")
    @RequestWrapper(localName="DisableAccessPoint", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.DisableAccessPoint")
    @ResponseWrapper(localName="DisableAccessPointResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.DisableAccessPointResponse")
    public void disableAccessPoint(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") String var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/accesscontrol/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl")
    public ServiceCapabilities getServiceCapabilities();

    @WebMethod(operationName="GetAccessPointInfo", action="http://www.onvif.org/ver10/accesscontrol/wsdl/GetAccessPointInfo")
    @RequestWrapper(localName="GetAccessPointInfo", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAccessPointInfo")
    @ResponseWrapper(localName="GetAccessPointInfoResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAccessPointInfoResponse")
    @WebResult(name="AccessPointInfo", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl")
    public List<AccessPointInfo> getAccessPointInfo(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") List<String> var1);

    @WebMethod(operationName="GetAreaInfo", action="http://www.onvif.org/ver10/accesscontrol/wsdl/GetAreaInfo")
    @RequestWrapper(localName="GetAreaInfo", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAreaInfo")
    @ResponseWrapper(localName="GetAreaInfoResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAreaInfoResponse")
    @WebResult(name="AreaInfo", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl")
    public List<AreaInfo> getAreaInfo(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") List<String> var1);

    @WebMethod(operationName="GetAccessPointInfoList", action="http://www.onvif.org/ver10/accesscontrol/wsdl/GetAccessPointInfoList")
    @RequestWrapper(localName="GetAccessPointInfoList", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAccessPointInfoList")
    @ResponseWrapper(localName="GetAccessPointInfoListResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAccessPointInfoListResponse")
    public void getAccessPointInfoList(@WebParam(name="Limit", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") Integer var1, @WebParam(name="StartReference", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") String var2, @WebParam(mode=WebParam.Mode.OUT, name="NextStartReference", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") Holder<String> var3, @WebParam(mode=WebParam.Mode.OUT, name="AccessPointInfo", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") Holder<List<AccessPointInfo>> var4);

    @WebMethod(operationName="GetAreaInfoList", action="http://www.onvif.org/ver10/accesscontrol/wsdl/GetAreaInfoList")
    @RequestWrapper(localName="GetAreaInfoList", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAreaInfoList")
    @ResponseWrapper(localName="GetAreaInfoListResponse", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl", className="org.onvif.ver10.accesscontrol.wsdl.GetAreaInfoListResponse")
    public void getAreaInfoList(@WebParam(name="Limit", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") Integer var1, @WebParam(name="StartReference", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") String var2, @WebParam(mode=WebParam.Mode.OUT, name="NextStartReference", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") Holder<String> var3, @WebParam(mode=WebParam.Mode.OUT, name="AreaInfo", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl") Holder<List<AreaInfo>> var4);
}

