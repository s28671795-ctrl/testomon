/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.str;

import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.callback.DOMCallbackLookup;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.dom.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class STRParserUtil {
    private static final Logger LOG = LoggerFactory.getLogger(STRParserUtil.class);

    private STRParserUtil() {
    }

    public static SamlAssertionWrapper getAssertionFromKeyIdentifier(SecurityTokenReference secRef, Element strElement, RequestData request) throws WSSecurityException {
        String keyIdentifierValue = secRef.getKeyIdentifierValue();
        String type = secRef.getKeyIdentifierValueType();
        WSSecurityEngineResult result = request.getWsDocInfo().getResult(keyIdentifierValue);
        SamlAssertionWrapper samlAssertion = null;
        Element token = null;
        if (result != null) {
            samlAssertion = (SamlAssertionWrapper)result.get("saml-assertion");
            return samlAssertion;
        }
        token = STRParserUtil.findProcessedTokenElement(strElement.getOwnerDocument(), request.getWsDocInfo(), request.getCallbackHandler(), keyIdentifierValue, type);
        if (token != null) {
            if (!"Assertion".equals(token.getLocalName())) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
            }
            return new SamlAssertionWrapper(token);
        }
        token = STRParserUtil.findUnprocessedTokenElement(strElement.getOwnerDocument(), request.getWsDocInfo(), request.getCallbackHandler(), keyIdentifierValue, type);
        if (token == null || !"Assertion".equals(token.getLocalName())) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
        }
        Processor proc = request.getWssConfig().getProcessor(WSConstants.SAML_TOKEN);
        List<WSSecurityEngineResult> samlResult = proc.handleToken(token, request);
        return (SamlAssertionWrapper)samlResult.get(0).get("saml-assertion");
    }

    public static void checkBinarySecurityBSPCompliance(SecurityTokenReference secRef, BinarySecurity token, BSPEnforcer bspEnforcer) throws WSSecurityException {
        String tokenType;
        String valueType;
        if (secRef.containsReference()) {
            valueType = secRef.getReference().getValueType();
            if (token instanceof X509Security && !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType) || token instanceof PKIPathSecurity && !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1".equals(valueType) || token instanceof KerberosSecurity && valueType != null && !"".equals(valueType) && !"http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(valueType)) {
                bspEnforcer.handleBSPRule(BSPRule.R3058);
            }
        } else if (!(!secRef.containsKeyIdentifier() || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(valueType = secRef.getKeyIdentifierValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType))) {
            bspEnforcer.handleBSPRule(BSPRule.R3063);
        }
        if (token instanceof PKIPathSecurity && !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1".equals(tokenType = secRef.getTokenType())) {
            bspEnforcer.handleBSPRule(BSPRule.R5215);
        }
    }

    public static void checkEncryptedKeyBSPCompliance(SecurityTokenReference secRef, BSPEnforcer bspEnforcer) throws WSSecurityException {
        String tokenType;
        String valueType;
        if (secRef.containsKeyIdentifier() && !"http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(valueType = secRef.getKeyIdentifierValueType())) {
            bspEnforcer.handleBSPRule(BSPRule.R3063);
        }
        if (!"http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(tokenType = secRef.getTokenType())) {
            bspEnforcer.handleBSPRule(BSPRule.R5215);
        }
    }

    public static void checkSamlTokenBSPCompliance(SecurityTokenReference secRef, SamlAssertionWrapper samlAssertion, BSPEnforcer bspEnforcer) throws WSSecurityException {
        String valueType;
        if (secRef.containsKeyIdentifier()) {
            String encoding;
            String valueType2 = secRef.getKeyIdentifierValueType();
            if (samlAssertion.getSaml1() != null && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType2)) {
                bspEnforcer.handleBSPRule(BSPRule.R6603);
            }
            if (samlAssertion.getSaml2() != null && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType2)) {
                bspEnforcer.handleBSPRule(BSPRule.R6616);
            }
            if ((encoding = secRef.getKeyIdentifierEncodingType()) != null && !"".equals(encoding)) {
                bspEnforcer.handleBSPRule(BSPRule.R6604);
            }
        }
        String tokenType = secRef.getTokenType();
        if (samlAssertion.getSaml1() != null && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType)) {
            bspEnforcer.handleBSPRule(BSPRule.R6611);
        }
        if (samlAssertion.getSaml2() != null && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType)) {
            bspEnforcer.handleBSPRule(BSPRule.R6617);
        }
        if (samlAssertion.getSaml2() != null && secRef.containsReference() && (valueType = secRef.getReference().getValueType()) != null && !"".equals(valueType)) {
            bspEnforcer.handleBSPRule(BSPRule.R6614);
        }
    }

    public static void checkUsernameTokenBSPCompliance(SecurityTokenReference secRef, BSPEnforcer bspEnforcer) throws WSSecurityException {
        String valueType;
        if (!secRef.containsReference()) {
            bspEnforcer.handleBSPRule(BSPRule.R4215);
        }
        if (secRef.getReference() != null && !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken".equals(valueType = secRef.getReference().getValueType())) {
            bspEnforcer.handleBSPRule(BSPRule.R4214);
        }
    }

    public static byte[] getSecretKeyFromToken(String id, String type, int identifier, RequestData data) throws WSSecurityException {
        String uri = XMLUtils.getIDFromReference(id);
        WSPasswordCallback pwcb = new WSPasswordCallback(uri, null, type, identifier);
        try {
            Callback[] callbacks = new Callback[]{pwcb};
            if (data.getCallbackHandler() != null) {
                data.getCallbackHandler().handle(callbacks);
                return pwcb.getKey();
            }
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "noPassword", new Object[]{uri});
        }
        return null;
    }

    public static Element getTokenElement(Document doc, WSDocInfo docInfo, CallbackHandler cb, String uri, String valueType) throws WSSecurityException {
        LOG.debug("Token reference uri: {}", (Object)uri);
        LOG.debug("Token reference ValueType: {}", (Object)valueType);
        if (uri == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "badReferenceURI");
        }
        Element tokElement = STRParserUtil.findProcessedTokenElement(doc, docInfo, cb, uri, valueType);
        if (tokElement == null) {
            tokElement = STRParserUtil.findUnprocessedTokenElement(doc, docInfo, cb, uri, valueType);
        }
        if (tokElement == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, "noToken", new Object[]{uri});
        }
        return tokElement;
    }

    public static Element findUnprocessedTokenElement(Document doc, WSDocInfo docInfo, CallbackHandler cb, String uri, String type) throws WSSecurityException {
        String id = XMLUtils.getIDFromReference(uri);
        CallbackLookup callbackLookup = null;
        if (docInfo != null) {
            callbackLookup = docInfo.getCallbackLookup();
        }
        if (callbackLookup == null) {
            callbackLookup = new DOMCallbackLookup(doc);
        }
        return callbackLookup.getElement(id, type, true);
    }

    public static Element findProcessedTokenElement(Document doc, WSDocInfo docInfo, CallbackHandler cb, String uri, String type) throws WSSecurityException {
        Element token;
        String id = XMLUtils.getIDFromReference(uri);
        if (docInfo != null && (token = docInfo.getTokenElement(id)) != null) {
            return token;
        }
        if (cb != null && ("http://schemas.xmlsoap.org/ws/2005/02/sc/sct".equals(type) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(type) || KerberosSecurity.isKerberosToken(type))) {
            WSPasswordCallback pwcb = new WSPasswordCallback(id, 7);
            try {
                cb.handle(new Callback[]{pwcb});
                Element assertionElem = pwcb.getCustomToken();
                if (assertionElem != null) {
                    return (Element)doc.importNode(assertionElem, true);
                }
            }
            catch (Exception e) {
                LOG.debug(e.getMessage(), e);
            }
        }
        return null;
    }
}

