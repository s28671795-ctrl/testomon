/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.addressing.wsdl.Anonymous;
import org.apache.cxf.ws.addressing.wsdl.AttributedQNameType;
import org.apache.cxf.ws.addressing.wsdl.ServiceNameType;
import org.apache.cxf.ws.addressing.wsdl.UsingAddressing;

@XmlRegistry
public class ObjectFactory {
    private static final QName _ServiceName_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "ServiceName");
    private static final QName _InterfaceName_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "InterfaceName");
    private static final QName _UsingAddressing_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "UsingAddressing");

    public ServiceNameType createServiceNameType() {
        return new ServiceNameType();
    }

    public AttributedQNameType createAttributedQNameType() {
        return new AttributedQNameType();
    }

    public UsingAddressing createUsingAddressing() {
        return new UsingAddressing();
    }

    public Anonymous createAnonymous() {
        return new Anonymous();
    }

    @XmlElementDecl(namespace="http://www.w3.org/2006/05/addressing/wsdl", name="ServiceName")
    public JAXBElement<ServiceNameType> createServiceName(ServiceNameType value) {
        return new JAXBElement<ServiceNameType>(_ServiceName_QNAME, ServiceNameType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.w3.org/2006/05/addressing/wsdl", name="InterfaceName")
    public JAXBElement<AttributedQNameType> createInterfaceName(AttributedQNameType value) {
        return new JAXBElement<AttributedQNameType>(_InterfaceName_QNAME, AttributedQNameType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.w3.org/2006/05/addressing/wsdl", name="UsingAddressing")
    public JAXBElement<UsingAddressing> createUsingAddressing(UsingAddressing value) {
        return new JAXBElement<UsingAddressing>(_UsingAddressing_QNAME, UsingAddressing.class, null, value);
    }
}

