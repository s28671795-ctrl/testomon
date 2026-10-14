/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.network.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.xmlsoap.schemas.ws._2005._04.discovery.ByeType;
import org.xmlsoap.schemas.ws._2005._04.discovery.HelloType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ProbeMatchesType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ProbeType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ResolveType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Hello_QNAME = new QName("http://www.onvif.org/ver10/network/wsdl", "Hello");
    private static final QName _HelloResponse_QNAME = new QName("http://www.onvif.org/ver10/network/wsdl", "HelloResponse");
    private static final QName _Probe_QNAME = new QName("http://www.onvif.org/ver10/network/wsdl", "Probe");
    private static final QName _ProbeResponse_QNAME = new QName("http://www.onvif.org/ver10/network/wsdl", "ProbeResponse");
    private static final QName _Bye_QNAME = new QName("http://www.onvif.org/ver10/network/wsdl", "Bye");
    private static final QName _ByeResponse_QNAME = new QName("http://www.onvif.org/ver10/network/wsdl", "ByeResponse");

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/network/wsdl", name="Hello")
    public JAXBElement<HelloType> createHello(HelloType value) {
        return new JAXBElement<HelloType>(_Hello_QNAME, HelloType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/network/wsdl", name="HelloResponse")
    public JAXBElement<ResolveType> createHelloResponse(ResolveType value) {
        return new JAXBElement<ResolveType>(_HelloResponse_QNAME, ResolveType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/network/wsdl", name="Probe")
    public JAXBElement<ProbeType> createProbe(ProbeType value) {
        return new JAXBElement<ProbeType>(_Probe_QNAME, ProbeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/network/wsdl", name="ProbeResponse")
    public JAXBElement<ProbeMatchesType> createProbeResponse(ProbeMatchesType value) {
        return new JAXBElement<ProbeMatchesType>(_ProbeResponse_QNAME, ProbeMatchesType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/network/wsdl", name="Bye")
    public JAXBElement<ByeType> createBye(ByeType value) {
        return new JAXBElement<ByeType>(_Bye_QNAME, ByeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/network/wsdl", name="ByeResponse")
    public JAXBElement<ResolveType> createByeResponse(ResolveType value) {
        return new JAXBElement<ResolveType>(_ByeResponse_QNAME, ResolveType.class, null, value);
    }
}

