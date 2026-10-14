/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.str.STRParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public final class STRTransformUtil {
    private static final Logger LOG = LoggerFactory.getLogger(STRTransformUtil.class);

    public static Element dereferenceSTR(Document doc, SecurityTokenReference secRef, WSDocInfo wsDocInfo) throws WSSecurityException {
        if (secRef.containsReference()) {
            LOG.debug("STR: Reference");
            Reference reference = secRef.getReference();
            return STRParserUtil.getTokenElement(doc, wsDocInfo, null, reference.getURI(), reference.getValueType());
        }
        if (secRef.containsX509Data() || secRef.containsX509IssuerSerial()) {
            LOG.debug("STR: IssuerSerial");
            X509Certificate[] certs = secRef.getX509IssuerSerial(wsDocInfo.getCrypto());
            if (certs == null || certs.length == 0 || certs[0] == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            return STRTransformUtil.createBSTX509(doc, certs[0], secRef.getElement(), secRef.getKeyIdentifierEncodingType());
        }
        if (secRef.containsKeyIdentifier()) {
            LOG.debug("STR: KeyIdentifier");
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(secRef.getKeyIdentifierValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(secRef.getKeyIdentifierValueType())) {
                return STRParserUtil.getTokenElement(doc, wsDocInfo, null, secRef.getKeyIdentifierValue(), secRef.getKeyIdentifierValueType());
            }
            X509Certificate[] certs = secRef.getKeyIdentifier(wsDocInfo.getCrypto());
            if (certs == null || certs.length == 0 || certs[0] == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            return STRTransformUtil.createBSTX509(doc, certs[0], secRef.getElement());
        }
        return null;
    }

    public static Element createBSTX509(Document doc, X509Certificate cert, Element secRefE) throws WSSecurityException {
        return STRTransformUtil.createBSTX509(doc, cert, secRefE, null);
    }

    public static Element createBSTX509(Document doc, X509Certificate cert, Element secRefE, String secRefEncType) throws WSSecurityException {
        byte[] data;
        try {
            data = cert.getEncoded();
        }
        catch (CertificateEncodingException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "encodeError");
        }
        String prefix = XMLUtils.getPrefixNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", secRefE);
        if (prefix == null) {
            prefix = "wsse";
        }
        Element elem = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", prefix + ":BinarySecurityToken");
        XMLUtils.setNamespace(elem, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", prefix);
        elem.setAttributeNS(null, "ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
        if (secRefEncType != null) {
            elem.setAttributeNS(null, "EncodingType", secRefEncType);
        }
        Text certText = doc.createTextNode(org.apache.xml.security.utils.XMLUtils.encodeToString(data));
        elem.appendChild(certText);
        return elem;
    }

    private STRTransformUtil() {
    }
}

