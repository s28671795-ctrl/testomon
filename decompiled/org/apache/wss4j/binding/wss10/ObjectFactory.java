/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss10;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wss10.AttributedString;
import org.apache.wss4j.binding.wss10.BinarySecurityTokenType;
import org.apache.wss4j.binding.wss10.EmbeddedType;
import org.apache.wss4j.binding.wss10.EncodedString;
import org.apache.wss4j.binding.wss10.KeyIdentifierType;
import org.apache.wss4j.binding.wss10.PasswordString;
import org.apache.wss4j.binding.wss10.ReferenceType;
import org.apache.wss4j.binding.wss10.SecurityHeaderType;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.binding.wss10.TransformationParametersType;
import org.apache.wss4j.binding.wss10.UsernameTokenType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _UsernameToken_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
    private static final QName _BinarySecurityToken_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken");
    private static final QName _Reference_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Reference");
    private static final QName _Embedded_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Embedded");
    private static final QName _KeyIdentifier_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifier");
    private static final QName _SecurityTokenReference_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenReference");
    private static final QName _Security_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
    private static final QName _TransformationParameters_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "TransformationParameters");
    private static final QName _Password_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password");
    private static final QName _Nonce_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Nonce");

    public UsernameTokenType createUsernameTokenType() {
        return new UsernameTokenType();
    }

    public BinarySecurityTokenType createBinarySecurityTokenType() {
        return new BinarySecurityTokenType();
    }

    public ReferenceType createReferenceType() {
        return new ReferenceType();
    }

    public EmbeddedType createEmbeddedType() {
        return new EmbeddedType();
    }

    public KeyIdentifierType createKeyIdentifierType() {
        return new KeyIdentifierType();
    }

    public SecurityTokenReferenceType createSecurityTokenReferenceType() {
        return new SecurityTokenReferenceType();
    }

    public SecurityHeaderType createSecurityHeaderType() {
        return new SecurityHeaderType();
    }

    public TransformationParametersType createTransformationParametersType() {
        return new TransformationParametersType();
    }

    public PasswordString createPasswordString() {
        return new PasswordString();
    }

    public EncodedString createEncodedString() {
        return new EncodedString();
    }

    public AttributedString createAttributedString() {
        return new AttributedString();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="UsernameToken")
    public JAXBElement<UsernameTokenType> createUsernameToken(UsernameTokenType value) {
        return new JAXBElement<UsernameTokenType>(_UsernameToken_QNAME, UsernameTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="BinarySecurityToken")
    public JAXBElement<BinarySecurityTokenType> createBinarySecurityToken(BinarySecurityTokenType value) {
        return new JAXBElement<BinarySecurityTokenType>(_BinarySecurityToken_QNAME, BinarySecurityTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="Reference")
    public JAXBElement<ReferenceType> createReference(ReferenceType value) {
        return new JAXBElement<ReferenceType>(_Reference_QNAME, ReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="Embedded")
    public JAXBElement<EmbeddedType> createEmbedded(EmbeddedType value) {
        return new JAXBElement<EmbeddedType>(_Embedded_QNAME, EmbeddedType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="KeyIdentifier")
    public JAXBElement<KeyIdentifierType> createKeyIdentifier(KeyIdentifierType value) {
        return new JAXBElement<KeyIdentifierType>(_KeyIdentifier_QNAME, KeyIdentifierType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="SecurityTokenReference")
    public JAXBElement<SecurityTokenReferenceType> createSecurityTokenReference(SecurityTokenReferenceType value) {
        return new JAXBElement<SecurityTokenReferenceType>(_SecurityTokenReference_QNAME, SecurityTokenReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="Security")
    public JAXBElement<SecurityHeaderType> createSecurity(SecurityHeaderType value) {
        return new JAXBElement<SecurityHeaderType>(_Security_QNAME, SecurityHeaderType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="TransformationParameters")
    public JAXBElement<TransformationParametersType> createTransformationParameters(TransformationParametersType value) {
        return new JAXBElement<TransformationParametersType>(_TransformationParameters_QNAME, TransformationParametersType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="Password")
    public JAXBElement<PasswordString> createPassword(PasswordString value) {
        return new JAXBElement<PasswordString>(_Password_QNAME, PasswordString.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name="Nonce")
    public JAXBElement<EncodedString> createNonce(EncodedString value) {
        return new JAXBElement<EncodedString>(_Nonce_QNAME, EncodedString.class, null, value);
    }
}

