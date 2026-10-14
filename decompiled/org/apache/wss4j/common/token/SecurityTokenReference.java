/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.token;

import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.DOMX509IssuerSerial;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class SecurityTokenReference {
    public static final String SECURITY_TOKEN_REFERENCE = "SecurityTokenReference";
    public static final QName STR_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenReference");
    public static final String SKI_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier";
    public static final String THUMB_URI = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1";
    public static final String ENC_KEY_SHA1_URI = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1";
    public static final String X509_V3_TYPE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
    private static final Logger LOG = LoggerFactory.getLogger(SecurityTokenReference.class);
    private Element element;
    private DOMX509IssuerSerial issuerSerial;
    private byte[] skiBytes;
    private Reference reference;

    public SecurityTokenReference(Element elem, BSPEnforcer bspEnforcer) throws WSSecurityException {
        this.element = elem;
        QName el = new QName(this.element.getNamespaceURI(), this.element.getLocalName());
        if (!STR_QNAME.equals(el)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "badElement", new Object[]{STR_QNAME, el});
        }
        this.checkBSPCompliance(bspEnforcer);
        if (this.containsReference()) {
            for (Node node = this.element.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (1 != node.getNodeType() || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(node.getNamespaceURI()) || !"Reference".equals(node.getLocalName())) continue;
                this.reference = new Reference((Element)node);
                break;
            }
        }
    }

    public SecurityTokenReference(Document doc) {
        this.element = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:SecurityTokenReference");
    }

    public void addWSSENamespace() {
        org.apache.wss4j.common.util.XMLUtils.setNamespace(this.element, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse");
    }

    public void addWSUNamespace() {
        this.element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    }

    public void addTokenType(String tokenType) {
        if (tokenType != null) {
            org.apache.wss4j.common.util.XMLUtils.setNamespace(this.element, "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11");
            this.element.setAttributeNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11:TokenType", tokenType);
        }
    }

    public String getTokenType() {
        return this.element.getAttributeNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "TokenType");
    }

    public void setReference(Reference ref) {
        Element elem = this.getFirstElement();
        if (elem != null) {
            this.element.replaceChild(ref.getElement(), elem);
        } else {
            this.element.appendChild(ref.getElement());
        }
        this.reference = ref;
    }

    public Reference getReference() throws WSSecurityException {
        return this.reference;
    }

    public void setKeyIdentifier(X509Certificate cert) throws WSSecurityException {
        Document doc = this.element.getOwnerDocument();
        byte[] data = null;
        try {
            data = cert.getEncoded();
        }
        catch (CertificateEncodingException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "encodeError");
        }
        Text text = doc.createTextNode(XMLUtils.encodeToString(data));
        this.createKeyIdentifier(doc, X509_V3_TYPE, text, true);
    }

    public void setKeyIdentifierSKI(X509Certificate cert, Crypto crypto) throws WSSecurityException {
        if (cert.getVersion() != 3) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "invalidCertForSKI", new Object[]{cert.getVersion()});
        }
        Document doc = this.element.getOwnerDocument();
        Crypto skiCrypto = crypto;
        if (skiCrypto == null) {
            skiCrypto = new Merlin();
        }
        byte[] data = skiCrypto.getSKIBytesFromCert(cert);
        Text text = doc.createTextNode(XMLUtils.encodeToString(data));
        this.createKeyIdentifier(doc, SKI_URI, text, true);
    }

    public void setKeyIdentifierThumb(X509Certificate cert) throws WSSecurityException {
        Document doc = this.element.getOwnerDocument();
        byte[] encodedCert = null;
        try {
            encodedCert = cert.getEncoded();
        }
        catch (CertificateEncodingException e1) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e1, "encodeError");
        }
        try {
            byte[] encodedBytes = KeyUtils.generateDigest(encodedCert);
            Text text = doc.createTextNode(XMLUtils.encodeToString(encodedBytes));
            this.createKeyIdentifier(doc, THUMB_URI, text, true);
        }
        catch (WSSecurityException e1) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e1, "decoding.general");
        }
    }

    public void setKeyIdentifierEncKeySHA1(String value) throws WSSecurityException {
        Document doc = this.element.getOwnerDocument();
        Text text = doc.createTextNode(value);
        this.createKeyIdentifier(doc, ENC_KEY_SHA1_URI, text, true);
    }

    public void setKeyIdentifier(String valueType, String keyIdVal) throws WSSecurityException {
        this.setKeyIdentifier(valueType, keyIdVal, false);
    }

    public void setKeyIdentifier(String valueType, String keyIdVal, boolean base64) throws WSSecurityException {
        Document doc = this.element.getOwnerDocument();
        this.createKeyIdentifier(doc, valueType, doc.createTextNode(keyIdVal), base64);
    }

    private void createKeyIdentifier(Document doc, String uri, Node node, boolean base64) {
        Element keyId = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:KeyIdentifier");
        keyId.setAttributeNS(null, "ValueType", uri);
        if (base64) {
            keyId.setAttributeNS(null, "EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
        }
        keyId.appendChild(node);
        Element elem = this.getFirstElement();
        if (elem != null) {
            this.element.replaceChild(keyId, elem);
        } else {
            this.element.appendChild(keyId);
        }
    }

    public Element getFirstElement() {
        for (Node currentChild = this.element.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
            if (1 != currentChild.getNodeType()) continue;
            return (Element)currentChild;
        }
        return null;
    }

    public X509Certificate[] getKeyIdentifier(Crypto crypto) throws WSSecurityException {
        String text;
        if (crypto == null) {
            return null;
        }
        Element elem = this.getFirstElement();
        String value = elem.getAttributeNS(null, "ValueType");
        if (X509_V3_TYPE.equals(value)) {
            X509Security token = new X509Security(elem, new BSPEnforcer(true));
            X509Certificate cert = token.getX509Certificate(crypto);
            return new X509Certificate[]{cert};
        }
        if (SKI_URI.equals(value)) {
            X509Certificate cert = this.getX509SKIAlias(crypto);
            if (cert != null) {
                return new X509Certificate[]{cert};
            }
        } else if (THUMB_URI.equals(value) && (text = org.apache.wss4j.common.util.XMLUtils.getElementText(this.getFirstElement())) != null) {
            byte[] thumb = XMLUtils.decode(text);
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.THUMBPRINT_SHA1);
            cryptoType.setBytes(thumb);
            X509Certificate[] certs = crypto.getX509Certificates(cryptoType);
            if (certs != null) {
                return new X509Certificate[]{certs[0]};
            }
        }
        return null;
    }

    public String getKeyIdentifierValue() {
        if (this.containsKeyIdentifier()) {
            return org.apache.wss4j.common.util.XMLUtils.getElementText(this.getFirstElement());
        }
        return null;
    }

    public String getKeyIdentifierValueType() {
        if (this.containsKeyIdentifier()) {
            Element elem = this.getFirstElement();
            return elem.getAttributeNS(null, "ValueType");
        }
        return null;
    }

    public String getKeyIdentifierEncodingType() {
        if (this.containsKeyIdentifier()) {
            Element elem = this.getFirstElement();
            return elem.getAttributeNS(null, "EncodingType");
        }
        return null;
    }

    public X509Certificate getX509SKIAlias(Crypto crypto) throws WSSecurityException {
        if (crypto == null) {
            return null;
        }
        if (this.skiBytes == null) {
            this.skiBytes = this.getSKIBytes();
            if (this.skiBytes == null) {
                return null;
            }
        }
        CryptoType cryptoType = new CryptoType(CryptoType.TYPE.SKI_BYTES);
        cryptoType.setBytes(this.skiBytes);
        X509Certificate[] certs = crypto.getX509Certificates(cryptoType);
        if (certs != null) {
            return certs[0];
        }
        return null;
    }

    public byte[] getSKIBytes() {
        if (this.skiBytes != null) {
            return this.skiBytes;
        }
        String text = org.apache.wss4j.common.util.XMLUtils.getElementText(this.getFirstElement());
        if (text != null) {
            this.skiBytes = XMLUtils.decode(text);
        }
        return this.skiBytes;
    }

    public void setUnknownElement(Element unknownElement) {
        Element elem = this.getFirstElement();
        if (elem != null) {
            this.element.replaceChild(unknownElement, elem);
        } else {
            this.element.appendChild(unknownElement);
        }
    }

    public X509Certificate[] getX509IssuerSerial(Crypto crypto) throws WSSecurityException {
        if (crypto == null) {
            return null;
        }
        if (this.issuerSerial == null) {
            this.issuerSerial = this.getIssuerSerial();
            if (this.issuerSerial == null) {
                return null;
            }
        }
        CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ISSUER_SERIAL);
        cryptoType.setIssuerSerial(this.issuerSerial.getIssuer(), this.issuerSerial.getSerialNumber());
        return crypto.getX509Certificates(cryptoType);
    }

    private DOMX509IssuerSerial getIssuerSerial() throws WSSecurityException {
        if (this.issuerSerial != null) {
            return this.issuerSerial;
        }
        Element elem = this.getFirstElement();
        if (elem == null) {
            return null;
        }
        if ("X509Data".equals(elem.getLocalName())) {
            elem = org.apache.wss4j.common.util.XMLUtils.findElement(elem, "X509IssuerSerial", "http://www.w3.org/2000/09/xmldsig#");
        }
        this.issuerSerial = new DOMX509IssuerSerial(elem);
        return this.issuerSerial;
    }

    public boolean containsReference() {
        return this.containsElement("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Reference");
    }

    public boolean containsX509IssuerSerial() {
        return this.containsElement("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial");
    }

    public boolean containsX509Data() {
        return this.containsElement("http://www.w3.org/2000/09/xmldsig#", "X509Data");
    }

    public boolean containsKeyIdentifier() {
        return this.containsElement("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifier");
    }

    private boolean containsElement(String namespace, String localname) {
        for (Node node = this.element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (1 != node.getNodeType()) continue;
            String ns = node.getNamespaceURI();
            String name = node.getLocalName();
            if ((namespace == null || !namespace.equals(ns)) && (namespace != null || ns != null) || !localname.equals(name)) continue;
            return true;
        }
        return false;
    }

    public Element getElement() {
        return this.element;
    }

    public void setID(String id) {
        this.element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", id);
    }

    public String getID() {
        return this.element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }

    private void checkBSPCompliance(BSPEnforcer bspEnforcer) throws WSSecurityException {
        Node node;
        int result = 0;
        Node child = null;
        for (node = this.element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (1 != node.getNodeType()) continue;
            ++result;
            child = (Element)node;
        }
        if (result != 1) {
            bspEnforcer.handleBSPRule(BSPRule.R3061);
        }
        if ("KeyIdentifier".equals(child.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(child.getNamespaceURI())) {
            String encodingType;
            String valueType = this.getKeyIdentifierValueType();
            if (valueType == null || "".equals(valueType)) {
                bspEnforcer.handleBSPRule(BSPRule.R3054);
            }
            if (!"".equals(encodingType = this.getFirstElement().getAttributeNS(null, "EncodingType")) && !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encodingType)) {
                bspEnforcer.handleBSPRule(BSPRule.R3071);
            }
            if (!"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType) && "".equals(encodingType)) {
                bspEnforcer.handleBSPRule(BSPRule.R3070);
            }
        } else if ("Embedded".equals(child.getLocalName())) {
            result = 0;
            for (node = child.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (1 != node.getNodeType()) continue;
                ++result;
                if (!SECURITY_TOKEN_REFERENCE.equals(node.getLocalName()) || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(node.getNamespaceURI())) continue;
                bspEnforcer.handleBSPRule(BSPRule.R3056);
            }
            if (result != 1) {
                bspEnforcer.handleBSPRule(BSPRule.R3060);
            }
        }
    }

    public int hashCode() {
        byte[] skiBytes;
        String tokenType;
        String keyIdentifierValue;
        String keyIdentifierValueType;
        int result = 17;
        try {
            Reference reference = this.getReference();
            if (reference != null) {
                result = 31 * result + reference.hashCode();
            }
        }
        catch (WSSecurityException e) {
            LOG.error(e.getMessage(), e);
        }
        String keyIdentifierEncodingType = this.getKeyIdentifierEncodingType();
        if (keyIdentifierEncodingType != null) {
            result = 31 * result + keyIdentifierEncodingType.hashCode();
        }
        if ((keyIdentifierValueType = this.getKeyIdentifierValueType()) != null) {
            result = 31 * result + keyIdentifierValueType.hashCode();
        }
        if ((keyIdentifierValue = this.getKeyIdentifierValue()) != null) {
            result = 31 * result + keyIdentifierValue.hashCode();
        }
        if ((tokenType = this.getTokenType()) != null) {
            result = 31 * result + tokenType.hashCode();
        }
        if ((skiBytes = this.getSKIBytes()) != null) {
            result = 31 * result + Arrays.hashCode(skiBytes);
        }
        String issuer = null;
        BigInteger serialNumber = null;
        try {
            issuer = this.getIssuerSerial().getIssuer();
            serialNumber = this.getIssuerSerial().getSerialNumber();
        }
        catch (WSSecurityException e) {
            LOG.error(e.getMessage(), e);
        }
        if (issuer != null) {
            result = 31 * result + issuer.hashCode();
        }
        if (serialNumber != null) {
            result = 31 * result + serialNumber.hashCode();
        }
        return result;
    }

    public boolean equals(Object object) {
        if (!(object instanceof SecurityTokenReference)) {
            return false;
        }
        SecurityTokenReference tokenReference = (SecurityTokenReference)object;
        try {
            if (!this.getReference().equals(tokenReference.getReference())) {
                return false;
            }
        }
        catch (WSSecurityException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        if (!this.compare(this.getKeyIdentifierEncodingType(), tokenReference.getKeyIdentifierEncodingType())) {
            return false;
        }
        if (!this.compare(this.getKeyIdentifierValueType(), tokenReference.getKeyIdentifierValueType())) {
            return false;
        }
        if (!this.compare(this.getKeyIdentifierValue(), tokenReference.getKeyIdentifierValue())) {
            return false;
        }
        if (!this.compare(this.getTokenType(), tokenReference.getTokenType())) {
            return false;
        }
        if (!Arrays.equals(this.getSKIBytes(), tokenReference.getSKIBytes())) {
            return false;
        }
        try {
            if (this.getIssuerSerial() != null && tokenReference.getIssuerSerial() != null) {
                if (!this.compare(this.getIssuerSerial().getIssuer(), tokenReference.getIssuerSerial().getIssuer())) {
                    return false;
                }
                if (!this.compare(this.getIssuerSerial().getSerialNumber(), tokenReference.getIssuerSerial().getSerialNumber())) {
                    return false;
                }
            }
        }
        catch (WSSecurityException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private boolean compare(String item1, String item2) {
        if (item1 == null && item2 != null) {
            return false;
        }
        return item1 == null || item1.equals(item2);
    }

    private boolean compare(BigInteger item1, BigInteger item2) {
        if (item1 == null && item2 != null) {
            return false;
        }
        return item1 == null || item1.equals(item2);
    }
}

