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
import org.xmlsoap.schemas.ws._2005._04.discovery.ByeType;
import org.xmlsoap.schemas.ws._2005._04.discovery.HelloType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ResolveType;

@WebService(targetNamespace="http://www.onvif.org/ver10/network/wsdl", name="RemoteDiscoveryPort")
@XmlSeeAlso(value={org.xmlsoap.schemas.ws._2005._04.discovery.ObjectFactory.class, ObjectFactory.class, org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface RemoteDiscoveryPort {
    @WebMethod(operationName="Bye", action="http://www.onvif.org/ver10/network/wsdl/Bye")
    @WebResult(name="ByeResponse", targetNamespace="http://www.onvif.org/ver10/network/wsdl", partName="parameters")
    public ResolveType bye(@WebParam(partName="parameters", name="Bye", targetNamespace="http://www.onvif.org/ver10/network/wsdl") ByeType var1);

    @WebMethod(operationName="Hello", action="http://www.onvif.org/ver10/network/wsdl/Hello")
    @WebResult(name="HelloResponse", targetNamespace="http://www.onvif.org/ver10/network/wsdl", partName="parameters")
    public ResolveType hello(@WebParam(partName="parameters", name="Hello", targetNamespace="http://www.onvif.org/ver10/network/wsdl") HelloType var1);
}

