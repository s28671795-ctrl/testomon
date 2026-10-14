/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.str;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLKeyInfoProcessor;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.saml.WSSSAMLKeyInfoProcessor;
import org.apache.wss4j.dom.str.STRParser;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.apache.wss4j.dom.str.STRParserUtil;

public class DerivedKeyTokenSTRParser
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
        return this.processSTR(secRef, uri, parameters);
    }

    private STRParserResult processPreviousResult(WSSecurityEngineResult result, SecurityTokenReference secRef, STRParserParameters parameters) throws WSSecurityException {
        STRParserResult parserResult = new STRParserResult();
        RequestData data = parameters.getData();
        Integer action = (Integer)result.get("action");
        if (action != null && (8192 == action || 1 == action)) {
            STRParserUtil.checkUsernameTokenBSPCompliance(secRef, data.getBSPEnforcer());
            byte[] secretKey = (byte[])result.get("secret");
            parserResult.setSecretKey(secretKey);
        } else if (action != null && 4 == action) {
            STRParserUtil.checkEncryptedKeyBSPCompliance(secRef, data.getBSPEnforcer());
            byte[] secretKey = (byte[])result.get("secret");
            parserResult.setSecretKey(secretKey);
        } else if (action != null && (1024 == action || 4096 == action)) {
            byte[] secretKey = (byte[])result.get("secret");
            parserResult.setSecretKey(secretKey);
        } else if (action != null && (8 == action || 16 == action)) {
            SamlAssertionWrapper samlAssertion = (SamlAssertionWrapper)result.get("saml-assertion");
            STRParserUtil.checkSamlTokenBSPCompliance(secRef, samlAssertion, data.getBSPEnforcer());
            SAMLKeyInfo keyInfo = SAMLUtil.getCredentialFromSubject(samlAssertion, (SAMLKeyInfoProcessor)new WSSSAMLKeyInfoProcessor(data), data.getSigVerCrypto(), data.getCallbackHandler());
            byte[] secretKey = keyInfo.getSecret();
            parserResult.setSecretKey(secretKey);
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "unsupportedKeyId");
        }
        return parserResult;
    }

    private STRParserResult processSTR(SecurityTokenReference secRef, String uri, STRParserParameters parameters) throws WSSecurityException {
        STRParserResult parserResult = new STRParserResult();
        RequestData data = parameters.getData();
        if (secRef.containsReference()) {
            byte[] secretKey = STRParserUtil.getSecretKeyFromToken(uri, null, 6, data);
            if (secretKey == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "unsupportedKeyId", new Object[]{uri});
            }
            parserResult.setSecretKey(secretKey);
        } else if (secRef.containsKeyIdentifier()) {
            String keyIdentifierValueType = secRef.getKeyIdentifierValueType();
            if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1".equals(keyIdentifierValueType)) {
                byte[] secretKey = STRParserUtil.getSecretKeyFromToken(secRef.getKeyIdentifierValue(), keyIdentifierValueType, 9, data);
                if (secretKey == null) {
                    byte[] keyBytes = secRef.getSKIBytes();
                    List<WSSecurityEngineResult> resultsList = data.getWsDocInfo().getResultsByTag(4096);
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
                Crypto crypto;
                X509Certificate[] certs;
                if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(keyIdentifierValueType)) {
                    STRParserUtil.checkEncryptedKeyBSPCompliance(secRef, data.getBSPEnforcer());
                }
                if ((certs = secRef.getKeyIdentifier(crypto = data.getDecCrypto())) == null || certs.length < 1 || certs[0] == null) {
                    byte[] secretKey = STRParserUtil.getSecretKeyFromToken(secRef.getKeyIdentifierValue(), keyIdentifierValueType, 9, data);
                    if (secretKey == null) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "unsupportedKeyId", new Object[]{uri});
                    }
                    parserResult.setSecretKey(secretKey);
                } else {
                    byte[] secretKey = crypto.getPrivateKey(certs[0], data.getCallbackHandler()).getEncoded();
                    parserResult.setSecretKey(secretKey);
                }
            }
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "unsupportedKeyId");
        }
        return parserResult;
    }
}

