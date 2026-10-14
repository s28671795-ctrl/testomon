/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.network.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.onvif.ver10.network.wsdl.ObjectFactory;
import org.xmlsoap.schemas.ws._2005._04.discovery.ProbeMatchesType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ProbeType;

@WebService(targetNamespace="http://www.onvif.org/ver10/network/wsdl", name="DiscoveryLookupPort")
@XmlSeeAlso(value={org.xmlsoap.schemas.ws._2005._04.discovery.ObjectFactory.class, ObjectFactory.class, org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface DiscoveryLookupPort {
    @WebMethod(operationName="Probe", action="http://www.onvif.org/ver10/network/wsdl/Probe")
    @WebResult(name="ProbeResponse", targetNamespace="http://www.onvif.org/ver10/network/wsdl", partName="parameters")
    public ProbeMatchesType probe(@WebParam(partName="parameters", name="Probe", targetNamespace="http://www.onvif.org/ver10/network/wsdl") ProbeType var1);
}

