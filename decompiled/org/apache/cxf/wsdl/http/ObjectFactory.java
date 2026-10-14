/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.http;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.wsdl.http.AddressType;
import org.apache.cxf.wsdl.http.BindingType;
import org.apache.cxf.wsdl.http.OperationType;
import org.apache.cxf.wsdl.http.UrlEncoded;
import org.apache.cxf.wsdl.http.UrlReplacement;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Address_QNAME = new QName("http://schemas.xmlsoap.org/wsdl/http/", "address");
    private static final QName _Binding_QNAME = new QName("http://schemas.xmlsoap.org/wsdl/http/", "binding");
    private static final QName _Operation_QNAME = new QName("http://schemas.xmlsoap.org/wsdl/http/", "operation");

    public AddressType createAddressType() {
        return new AddressType();
    }

    public BindingType createBindingType() {
        return new BindingType();
    }

    public OperationType createOperationType() {
        return new OperationType();
    }

    public UrlEncoded createUrlEncoded() {
        return new UrlEncoded();
    }

    public UrlReplacement createUrlReplacement() {
        return new UrlReplacement();
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/wsdl/http/", name="address")
    public JAXBElement<AddressType> createAddress(AddressType value) {
        return new JAXBElement<AddressType>(_Address_QNAME, AddressType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/wsdl/http/", name="binding")
    public JAXBElement<BindingType> createBinding(BindingType value) {
        return new JAXBElement<BindingType>(_Binding_QNAME, BindingType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/wsdl/http/", name="operation")
    public JAXBElement<OperationType> createOperation(OperationType value) {
        return new JAXBElement<OperationType>(_Operation_QNAME, OperationType.class, null, value);
    }
}

