/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wssc13;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wssc13.DerivedKeyTokenType;
import org.apache.wss4j.binding.wssc13.PropertiesType;
import org.apache.wss4j.binding.wssc13.SecurityContextTokenType;

@XmlRegistry
public class ObjectFactory {
    private static final String WSSC13_NS = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
    private static final QName _Identifier_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Identifier");
    private static final QName _SecurityContextToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "SecurityContextToken");
    private static final QName _Instance_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Instance");
    private static final QName _DerivedKeyToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "DerivedKeyToken");
    private static final QName _Name_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Name");
    private static final QName _Label_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Label");
    private static final QName _Nonce_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Nonce");

    public DerivedKeyTokenType createDerivedKeyTokenType() {
        return new DerivedKeyTokenType();
    }

    public SecurityContextTokenType createSecurityContextTokenType() {
        return new SecurityContextTokenType();
    }

    public PropertiesType createPropertiesType() {
        return new PropertiesType();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name="Identifier")
    public JAXBElement<String> createIdentifier(String value) {
        return new JAXBElement<String>(_Identifier_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name="SecurityContextToken")
    public JAXBElement<SecurityContextTokenType> createSecurityContextToken(SecurityContextTokenType value) {
        return new JAXBElement<SecurityContextTokenType>(_SecurityContextToken_QNAME, SecurityContextTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name="Instance")
    public JAXBElement<String> createInstance(String value) {
        return new JAXBElement<String>(_Instance_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name="DerivedKeyToken")
    public JAXBElement<DerivedKeyTokenType> createDerivedKeyToken(DerivedKeyTokenType value) {
        return new JAXBElement<DerivedKeyTokenType>(_DerivedKeyToken_QNAME, DerivedKeyTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name="Name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name="Label")
    public JAXBElement<String> createLabel(String value) {
        return new JAXBElement<String>(_Label_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", name="Nonce")
    public JAXBElement<byte[]> createNonce(byte[] value) {
        return new JAXBElement<byte[]>(_Nonce_QNAME, byte[].class, null, value);
    }
}

