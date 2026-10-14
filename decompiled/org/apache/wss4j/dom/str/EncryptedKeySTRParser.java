/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.str;

import java.security.cert.X509Certificate;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLKeyInfoProcessor;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.saml.WSSSAMLKeyInfoProcessor;
import org.apache.wss4j.dom.str.STRParser;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.apache.wss4j.dom.str.STRParserUtil;
import org.w3c.dom.Element;

public class EncryptedKeySTRParser
implements STRParser {
    @Override
    public STRParserResult parseSecurityTokenReference(STRParserParameters parameters) throws WSSecurityException {
        if (parameters == null || parameters.getData() == null || parameters.getData().getWsDocInfo() == null || parameters.getStrElement() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSTRParserParameter");
        }
        SecurityTokenReference secRef = new SecurityTokenReference(parameters.getStrElement(), parameters.getData().getBSPEnforcer());
        String uri = null;
        if (secRef.getReference() != null) {
            uri = secRef.getReference().getURI();
            uri = XMLUtils.getIDFromReference(uri);
        } else if (secRef.containsKeyIdentifier()) {
            uri = secRef.getKeyIdentifierValue();
        }
        WSSecurityEngineResult result = parameters.getData().getWsDocInfo().getResult(uri);
        if (result != null) {
            return this.processPreviousResult(result, secRef, parameters);
        }
        return this.processSTR(secRef, parameters);
    }

    private STRParserResult processPreviousResult(WSSecurityEngineResult result, SecurityTokenReference secRef, STRParserParameters parameters) throws WSSecurityException {
        STRParserResult parserResult = new STRParserResult();
        RequestData data = parameters.getData();
        Integer action = (Integer)result.get("action");
        if (action != null && 4096 == action) {
            BinarySecurity token = (BinarySecurity)result.get("binary-security-token");
            STRParserUtil.checkBinarySecurityBSPCompliance(secRef, token, data.getBSPEnforcer());
            X509Certificate[] certs = (X509Certificate[])result.get("x509-certificates");
            parserResult.setCerts(certs);
        } else if (action != null && (8 == action || 16 == action)) {
            SamlAssertionWrapper samlAssertion = (SamlAssertionWrapper)result.get("saml-assertion");
            STRParserUtil.checkSamlTokenBSPCompliance(secRef, samlAssertion, data.getBSPEnforcer());
            SAMLKeyInfo keyInfo = SAMLUtil.getCredentialFromSubject(samlAssertion, (SAMLKeyInfoProcessor)new WSSSAMLKeyInfoProcessor(data), data.getSigVerCrypto(), data.getCallbackHandler());
            parserResult.setCerts(keyInfo.getCerts());
            parserResult.setPublicKey(keyInfo.getPublicKey());
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "unsupportedBinaryTokenType");
        }
        STRParser.REFERENCE_TYPE referenceType = this.getReferenceType(secRef);
        if (referenceType != null) {
            parserResult.setReferenceType(referenceType);
        }
        return parserResult;
    }

    private STRParserResult processSTR(SecurityTokenReference secRef, STRParserParameters parameters) throws WSSecurityException {
        STRParserResult parserResult = new STRParserResult();
        RequestData data = parameters.getData();
        Element strElement = parameters.getStrElement();
        WSDocInfo wsDocInfo = data.getWsDocInfo();
        Crypto crypto = data.getDecCrypto();
        if (secRef.containsKeyIdentifier()) {
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(secRef.getKeyIdentifierValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(secRef.getKeyIdentifierValueType())) {
                SamlAssertionWrapper samlAssertion = STRParserUtil.getAssertionFromKeyIdentifier(secRef, strElement, data);
                STRParserUtil.checkSamlTokenBSPCompliance(secRef, samlAssertion, data.getBSPEnforcer());
                SAMLKeyInfo samlKi = SAMLUtil.getCredentialFromSubject(samlAssertion, (SAMLKeyInfoProcessor)new WSSSAMLKeyInfoProcessor(data), data.getSigVerCrypto(), data.getCallbackHandler());
                parserResult.setCerts(samlKi.getCerts());
                parserResult.setPublicKey(samlKi.getPublicKey());
            } else {
                STRParserUtil.checkBinarySecurityBSPCompliance(secRef, null, data.getBSPEnforcer());
                parserResult.setCerts(secRef.getKeyIdentifier(crypto));
            }
        } else if (secRef.containsX509Data() || secRef.containsX509IssuerSerial()) {
            parserResult.setReferenceType(STRParser.REFERENCE_TYPE.ISSUER_SERIAL);
            parserResult.setCerts(secRef.getX509IssuerSerial(crypto));
        } else if (secRef.containsReference()) {
            Reference reference = secRef.getReference();
            Element bstElement = STRParserUtil.getTokenElement(strElement.getOwnerDocument(), wsDocInfo, data.getCallbackHandler(), reference.getURI(), reference.getValueType());
            QName el = new QName(bstElement.getNamespaceURI(), bstElement.getLocalName());
            if (el.equals(WSConstants.BINARY_TOKEN)) {
                X509Security token = new X509Security(bstElement, data.getBSPEnforcer());
                STRParserUtil.checkBinarySecurityBSPCompliance(secRef, token, data.getBSPEnforcer());
                parserResult.setCerts(new X509Certificate[]{token.getX509Certificate(crypto)});
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "unsupportedBinaryTokenType");
            }
        }
        STRParser.REFERENCE_TYPE referenceType = this.getReferenceType(secRef);
        if (referenceType != null) {
            parserResult.setReferenceType(referenceType);
        }
        return parserResult;
    }

    private STRParser.REFERENCE_TYPE getReferenceType(SecurityTokenReference secRef) {
        if (secRef.containsReference()) {
            return STRParser.REFERENCE_TYPE.DIRECT_REF;
        }
        if (secRef.containsKeyIdentifier()) {
            if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(secRef.getKeyIdentifierValueType())) {
                return STRParser.REFERENCE_TYPE.THUMBPRINT_SHA1;
            }
            return STRParser.REFERENCE_TYPE.KEY_IDENTIFIER;
        }
        return null;
    }
}

