/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wssc200502;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wssc200502.DerivedKeyTokenType;
import org.apache.wss4j.binding.wssc200502.PropertiesType;
import org.apache.wss4j.binding.wssc200502.SecurityContextTokenType;

@XmlRegistry
public class ObjectFactory {
    private static final String WSSC_NS = "http://schemas.xmlsoap.org/ws/2005/02/sc";
    private static final QName _Nonce_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Nonce");
    private static final QName _Label_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Label");
    private static final QName _DerivedKeyToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "DerivedKeyToken");
    private static final QName _Name_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Name");
    private static final QName _Identifier_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Identifier");
    private static final QName _SecurityContextToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "SecurityContextToken");
    private static final QName _Instance_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Instance");

    public DerivedKeyTokenType createDerivedKeyTokenType() {
        return new DerivedKeyTokenType();
    }

    public SecurityContextTokenType createSecurityContextTokenType() {
        return new SecurityContextTokenType();
    }

    public PropertiesType createPropertiesType() {
        return new PropertiesType();
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/02/sc", name="Nonce")
    public JAXBElement<byte[]> createNonce(byte[] value) {
        return new JAXBElement<byte[]>(_Nonce_QNAME, byte[].class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/02/sc", name="Label")
    public JAXBElement<String> createLabel(String value) {
        return new JAXBElement<String>(_Label_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/02/sc", name="DerivedKeyToken")
    public JAXBElement<DerivedKeyTokenType> createDerivedKeyToken(DerivedKeyTokenType value) {
        return new JAXBElement<DerivedKeyTokenType>(_DerivedKeyToken_QNAME, DerivedKeyTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/02/sc", name="Name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/02/sc", name="Identifier")
    public JAXBElement<String> createIdentifier(String value) {
        return new JAXBElement<String>(_Identifier_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/02/sc", name="SecurityContextToken")
    public JAXBElement<SecurityContextTokenType> createSecurityContextToken(SecurityContextTokenType value) {
        return new JAXBElement<SecurityContextTokenType>(_SecurityContextToken_QNAME, SecurityContextTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/02/sc", name="Instance")
    public JAXBElement<String> createInstance(String value) {
        return new JAXBElement<String>(_Instance_QNAME, String.class, null, value);
    }
}

