/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss11;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wss11.EncryptedHeaderType;
import org.apache.wss4j.binding.wss11.SignatureConfirmationType;

@XmlRegistry
public class ObjectFactory {
    private static final String WSSE11_NS = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd";
    private static final QName _EncryptedHeader_QNAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeader");
    private static final QName _SignatureConfirmation_QNAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmation");
    private static final QName _Salt_QNAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Salt");
    private static final QName _Iteration_QNAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Iteration");

    public SignatureConfirmationType createSignatureConfirmationType() {
        return new SignatureConfirmationType();
    }

    public EncryptedHeaderType createEncryptedHeaderType() {
        return new EncryptedHeaderType();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", name="EncryptedHeader")
    public JAXBElement<EncryptedHeaderType> createEncryptedHeader(EncryptedHeaderType value) {
        return new JAXBElement<EncryptedHeaderType>(_EncryptedHeader_QNAME, EncryptedHeaderType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", name="SignatureConfirmation")
    public JAXBElement<SignatureConfirmationType> createSignatureConfirmation(SignatureConfirmationType value) {
        return new JAXBElement<SignatureConfirmationType>(_SignatureConfirmation_QNAME, SignatureConfirmationType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", name="Salt")
    public JAXBElement<byte[]> createSalt(byte[] value) {
        return new JAXBElement<byte[]>(_Salt_QNAME, byte[].class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", name="Iteration")
    public JAXBElement<Long> createIteration(Long value) {
        return new JAXBElement<Long>(_Iteration_QNAME, Long.class, null, value);
    }
}

