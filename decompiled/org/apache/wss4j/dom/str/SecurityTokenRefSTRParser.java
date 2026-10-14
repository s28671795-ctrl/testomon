/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.str;

import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLKeyInfoProcessor;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.DerivedKeyToken;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.saml.WSSSAMLKeyInfoProcessor;
import org.apache.wss4j.dom.str.STRParser;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.apache.wss4j.dom.str.STRParserUtil;
import org.w3c.dom.Element;

public class SecurityTokenRefSTRParser
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
            return this.processPreviousResult(result, secRef, uri, parameters);
        }
        return this.processSTR(secRef, uri, parameters);
    }

    private byte[] getSecretKeyFromAssertion(SamlAssertionWrapper samlAssertion, SecurityTokenReference secRef, RequestData data) throws WSSecurityException {
        STRParserUtil.checkSamlTokenBSPCompliance(secRef, samlAssertion, data.getBSPEnforcer());
        SAMLKeyInfo samlKi = SAMLUtil.getCredentialFromSubject(samlAssertion, (SAMLKeyInfoProcessor)new WSSSAMLKeyInfoProcessor(data), data.getSigVerCrypto(), data.getCallbackHandler());
        if (samlKi == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "invalidSAMLToken", new Object[]{"No Secret Key"});
        }
        return samlKi.getSecret();
    }

    private STRParserResult processPreviousResult(WSSecurityEngineResult result, SecurityTokenReference secRef, String uri, STRParserParameters parameters) throws WSSecurityException {
        STRParserResult parserResult = new STRParserResult();
        RequestData data = parameters.getData();
        Integer action = (Integer)result.get("action");
        if (action != null && 4 == action) {
            STRParserUtil.checkEncryptedKeyBSPCompliance(secRef, data.getBSPEnforcer());
            byte[] secretKey = (byte[])result.get("secret");
            parserResult.setSecretKey(secretKey);
        } else if (action != null && 2048 == action) {
            DerivedKeyToken dkt = (DerivedKeyToken)result.get("derived-key-token");
            int keyLength = dkt.getLength();
            if (keyLength <= 0 && parameters.getDerivationKeyLength() > 0) {
                keyLength = parameters.getDerivationKeyLength();
            }
            byte[] secret = (byte[])result.get("secret");
            parserResult.setSecretKey(dkt.deriveKey(keyLength, secret));
            parserResult.setPrincipal(dkt.createPrincipal());
        } else if (action != null && (8 == action || 16 == action)) {
            SamlAssertionWrapper samlAssertion = (SamlAssertionWrapper)result.get("saml-assertion");
            byte[] secretKey = this.getSecretKeyFromAssertion(samlAssertion, secRef, data);
            parserResult.setSecretKey(secretKey);
        } else if (action != null && (1024 == action || 4096 == action)) {
            byte[] secretKey = (byte[])result.get("secret");
            parserResult.setSecretKey(secretKey);
        } else if (action != null && (8192 == action || 1 == action)) {
            STRParserUtil.checkUsernameTokenBSPCompliance(secRef, data.getBSPEnforcer());
            UsernameToken usernameToken = (UsernameToken)result.get("username-token");
            usernameToken.setRawPassword(data.getCallbackHandler());
            byte[] secretKey = usernameToken.getDerivedKey(data.getBSPEnforcer());
            parserResult.setSecretKey(secretKey);
        }
        if (parserResult.getSecretKey() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "unsupportedKeyId", new Object[]{uri});
        }
        return parserResult;
    }

    private STRParserResult processSTR(SecurityTokenReference secRef, String uri, STRParserParameters parameters) throws WSSecurityException {
        STRParserResult parserResult = new STRParserResult();
        RequestData data = parameters.getData();
        Element strElement = parameters.getStrElement();
        WSDocInfo wsDocInfo = data.getWsDocInfo();
        if (secRef.containsReference()) {
            Element token;
            QName el;
            Reference reference = secRef.getReference();
            byte[] secretKey = STRParserUtil.getSecretKeyFromToken(uri, reference.getValueType(), 9, data);
            if (secretKey == null && (el = new QName((token = STRParserUtil.getTokenElement(strElement.getOwnerDocument(), wsDocInfo, data.getCallbackHandler(), uri, reference.getValueType())).getNamespaceURI(), token.getLocalName())).equals(WSConstants.BINARY_TOKEN)) {
                Processor proc = data.getWssConfig().getProcessor(WSConstants.BINARY_TOKEN);
                List<WSSecurityEngineResult> bstResult = proc.handleToken(token, data);
                BinarySecurity bstToken = (BinarySecurity)bstResult.get(0).get("binary-security-token");
                STRParserUtil.checkBinarySecurityBSPCompliance(secRef, bstToken, data.getBSPEnforcer());
                secretKey = (byte[])bstResult.get(0).get("secret");
            }
            if (secretKey == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "unsupportedKeyId", new Object[]{uri});
            }
            parserResult.setSecretKey(secretKey);
        } else if (secRef.containsKeyIdentifier()) {
            String valueType = secRef.getKeyIdentifierValueType();
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType)) {
                byte[] secretKey = STRParserUtil.getSecretKeyFromToken(secRef.getKeyIdentifierValue(), valueType, 9, data);
                if (secretKey == null) {
                    SamlAssertionWrapper samlAssertion = STRParserUtil.getAssertionFromKeyIdentifier(secRef, strElement, data);
                    secretKey = this.getSecretKeyFromAssertion(samlAssertion, secRef, data);
                }
                parserResult.setSecretKey(secretKey);
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1".equals(valueType)) {
                byte[] secretKey = STRParserUtil.getSecretKeyFromToken(secRef.getKeyIdentifierValue(), valueType, 9, data);
                if (secretKey == null) {
                    byte[] keyBytes = secRef.getSKIBytes();
                    List<WSSecurityEngineResult> resultsList = wsDocInfo.getResultsByTag(4096);
                    for (WSSecurityEngineResult bstResult : resultsList) {
                        BinarySecurity bstToken = (BinarySecurity)bstResult.get("binary-security-token");
                        byte[] tokenDigest = KeyUtils.generateDigest(bstToken.getToken());
                        if (!Arrays.equals(tokenDigest, keyBytes)) continue;
                        secretKey = (byte[])bstResult.get("secret");
                        break;
                    }
                }
                if (secretKey == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "unsupportedKeyId", new Object[]{uri});
                }
                parserResult.setSecretKey(secretKey);
            } else {
                byte[] secretKey;
                if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(valueType)) {
                    STRParserUtil.checkEncryptedKeyBSPCompliance(secRef, data.getBSPEnforcer());
                }
                if ((secretKey = STRParserUtil.getSecretKeyFromToken(secRef.getKeyIdentifierValue(), secRef.getKeyIdentifierValueType(), 9, data)) == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "unsupportedKeyId", new Object[]{uri});
                }
                parserResult.setSecretKey(secretKey);
            }
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "noReference");
        }
        return parserResult;
    }
}

