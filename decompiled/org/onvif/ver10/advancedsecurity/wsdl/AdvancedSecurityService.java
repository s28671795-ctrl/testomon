/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.advancedsecurity.wsdl.Capabilities;
import org.onvif.ver10.advancedsecurity.wsdl.ObjectFactory;

@WebService(targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", name="AdvancedSecurityService")
@XmlSeeAlso(value={ObjectFactory.class})
public interface AdvancedSecurityService {
    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public Capabilities getServiceCapabilities();
}

