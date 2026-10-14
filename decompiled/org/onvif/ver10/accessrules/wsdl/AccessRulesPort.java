/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.accessrules.wsdl.AccessProfile;
import org.onvif.ver10.accessrules.wsdl.AccessProfileInfo;
import org.onvif.ver10.accessrules.wsdl.ObjectFactory;
import org.onvif.ver10.accessrules.wsdl.ServiceCapabilities;

@WebService(targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", name="AccessRulesPort")
@XmlSeeAlso(value={ObjectFactory.class, org.onvif.ver10.pacs.ObjectFactory.class})
public interface AccessRulesPort {
    @WebMethod(operationName="ModifyAccessProfile", action="http://www.onvif.org/ver10/accessrules/wsdl/ModifyAccessProfile")
    @RequestWrapper(localName="ModifyAccessProfile", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.ModifyAccessProfile")
    @ResponseWrapper(localName="ModifyAccessProfileResponse", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.ModifyAccessProfileResponse")
    public void modifyAccessProfile(@WebParam(name="AccessProfile", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") AccessProfile var1);

    @WebMethod(operationName="GetAccessProfiles", action="http://www.onvif.org/ver10/accessrules/wsdl/GetAccessProfiles")
    @RequestWrapper(localName="GetAccessProfiles", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetAccessProfiles")
    @ResponseWrapper(localName="GetAccessProfilesResponse", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetAccessProfilesResponse")
    @WebResult(name="AccessProfile", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl")
    public List<AccessProfile> getAccessProfiles(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") List<String> var1);

    @WebMethod(operationName="GetAccessProfileInfo", action="http://www.onvif.org/ver10/accessrules/wsdl/GetAccessProfileInfo")
    @RequestWrapper(localName="GetAccessProfileInfo", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetAccessProfileInfo")
    @ResponseWrapper(localName="GetAccessProfileInfoResponse", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetAccessProfileInfoResponse")
    @WebResult(name="AccessProfileInfo", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl")
    public List<AccessProfileInfo> getAccessProfileInfo(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") List<String> var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/accessrules/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl")
    public ServiceCapabilities getServiceCapabilities();

    @WebMethod(operationName="GetAccessProfileList", action="http://www.onvif.org/ver10/accessrules/wsdl/GetAccessProfileList")
    @RequestWrapper(localName="GetAccessProfileList", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetAccessProfileList")
    @ResponseWrapper(localName="GetAccessProfileListResponse", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetAccessProfileListResponse")
    public void getAccessProfileList(@WebParam(name="Limit", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") Integer var1, @WebParam(name="StartReference", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") String var2, @WebParam(mode=WebParam.Mode.OUT, name="NextStartReference", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") Holder<String> var3, @WebParam(mode=WebParam.Mode.OUT, name="AccessProfile", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") Holder<List<AccessProfile>> var4);

    @WebMethod(operationName="DeleteAccessProfile", action="http://www.onvif.org/ver10/accessrules/wsdl/DeleteAccessProfile")
    @RequestWrapper(localName="DeleteAccessProfile", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.DeleteAccessProfile")
    @ResponseWrapper(localName="DeleteAccessProfileResponse", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.DeleteAccessProfileResponse")
    public void deleteAccessProfile(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") String var1);

    @WebMethod(operationName="CreateAccessProfile", action="http://www.onvif.org/ver10/accessrules/wsdl/CreateAccessProfile")
    @RequestWrapper(localName="CreateAccessProfile", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.CreateAccessProfile")
    @ResponseWrapper(localName="CreateAccessProfileResponse", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.CreateAccessProfileResponse")
    @WebResult(name="Token", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl")
    public String createAccessProfile(@WebParam(name="AccessProfile", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") AccessProfile var1);

    @WebMethod(operationName="GetAccessProfileInfoList", action="http://www.onvif.org/ver10/accessrules/wsdl/GetAccessProfileInfoList")
    @RequestWrapper(localName="GetAccessProfileInfoList", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetAccessProfileInfoList")
    @ResponseWrapper(localName="GetAccessProfileInfoListResponse", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl", className="org.onvif.ver10.accessrules.wsdl.GetAccessProfileInfoListResponse")
    public void getAccessProfileInfoList(@WebParam(name="Limit", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") Integer var1, @WebParam(name="StartReference", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") String var2, @WebParam(mode=WebParam.Mode.OUT, name="NextStartReference", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") Holder<String> var3, @WebParam(mode=WebParam.Mode.OUT, name="AccessProfileInfo", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl") Holder<List<AccessProfileInfo>> var4);
}

