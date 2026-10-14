/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.str;

import java.security.Principal;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.CustomTokenPrincipal;
import org.apache.wss4j.common.principal.SAMLTokenPrincipalImpl;
import org.apache.wss4j.common.principal.WSDerivedKeyTokenPrincipal;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLKeyInfoProcessor;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.DerivedKeyToken;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.saml.WSSSAMLKeyInfoProcessor;
import org.apache.wss4j.dom.str.STRParser;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.apache.wss4j.dom.str.STRParserUtil;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;

public class SignatureSTRParser
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
            uri = org.apache.wss4j.common.util.XMLUtils.getIDFromReference(uri);
        } else if (secRef.containsKeyIdentifier()) {
            uri = secRef.getKeyIdentifierValue();
        }
        WSSecurityEngineResult result = parameters.getData().getWsDocInfo().getResult(uri);
        if (result != null) {
            return this.processPreviousResult(result, secRef, parameters);
        }
        return this.processSTR(secRef, uri, parameters);
    }

    private Principal createPrincipalFromSAML(SamlAssertionWrapper samlAssertion, STRParserResult parserResult) {
        SAMLTokenPrincipalImpl samlPrincipal = new SAMLTokenPrincipalImpl(samlAssertion);
        String confirmMethod = null;
        List<String> methods = samlAssertion.getConfirmationMethods();
        if (methods != null && !methods.isEmpty()) {
            confirmMethod = methods.get(0);
        }
        if (OpenSAMLUtil.isMethodHolderOfKey(confirmMethod) && samlAssertion.isSigned()) {
            parserResult.setTrustedCredential(true);
        }
        return samlPrincipal;
    }

    private void parseSAMLKeyIdentifier(SecurityTokenReference secRef, RequestData data, STRParserResult parserResult) throws WSSecurityException {
        String valueType = secRef.getKeyIdentifierValueType();
        byte[] secretKey = STRParserUtil.getSecretKeyFromToken(secRef.getKeyIdentifierValue(), valueType, 9, data);
        if (secretKey == null) {
            SamlAssertionWrapper samlAssertion = STRParserUtil.getAssertionFromKeyIdentifier(secRef, secRef.getElement(), data);
            STRParserUtil.checkSamlTokenBSPCompliance(secRef, samlAssertion, data.getBSPEnforcer());
            SAMLKeyInfo samlKi = SAMLUtil.getCredentialFromSubject(samlAssertion, (SAMLKeyInfoProcessor)new WSSSAMLKeyInfoProcessor(data), data.getSigVerCrypto(), data.getCallbackHandler());
            X509Certificate[] foundCerts = samlKi.getCerts();
            if (foundCerts != null && foundCerts.length > 0) {
                parserResult.setCerts(new X509Certificate[]{foundCerts[0]});
            }
            secretKey = samlKi.getSecret();
            parserResult.setPublicKey(samlKi.getPublicKey());
            parserResult.setPrincipal(this.createPrincipalFromSAML(samlAssertion, parserResult));
        }
        parserResult.setSecretKey(secretKey);
    }

    private void parseBSTKeyIdentifier(SecurityTokenReference secRef, Crypto crypto, RequestData data, STRParserResult parserResult) throws WSSecurityException {
        STRParserUtil.checkBinarySecurityBSPCompliance(secRef, null, data.getBSPEnforcer());
        String valueType = secRef.getKeyIdentifierValueType();
        if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1".equals(valueType)) {
            byte[] secretKey = STRParserUtil.getSecretKeyFromToken(secRef.getKeyIdentifierValue(), valueType, 9, data);
            if (secretKey == null) {
                byte[] keyBytes = secRef.getSKIBytes();
                List<WSSecurityEngineResult> resultsList = data.getWsDocInfo().getResultsByTag(4096);
                for (WSSecurityEngineResult bstResult : resultsList) {
                    BinarySecurity bstToken = (BinarySecurity)bstResult.get("binary-security-token");
                    byte[] tokenDigest = KeyUtils.generateDigest(bstToken.getToken());
                    if (!Arrays.equals(tokenDigest, keyBytes)) continue;
                    secretKey = (byte[])bstResult.get("secret");
                    parserResult.setPrincipal((Principal)bstResult.get("principal"));
                    break;
                }
            } else {
                parserResult.setPrincipal(new CustomTokenPrincipal(secRef.getKeyIdentifierValue()));
            }
            parserResult.setSecretKey(secretKey);
        } else {
            X509Certificate[] foundCerts = secRef.getKeyIdentifier(crypto);
            if (foundCerts == null) {
                if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(valueType)) {
                    byte[] skiBytes = secRef.getSKIBytes();
                    List<WSSecurityEngineResult> resultsList = data.getWsDocInfo().getResultsByTag(4096);
                    for (WSSecurityEngineResult bstResult : resultsList) {
                        X509Certificate[] certs = (X509Certificate[])bstResult.get("x509-certificates");
                        if (certs == null || !Arrays.equals(skiBytes, crypto.getSKIBytesFromCert(certs[0]))) continue;
                        parserResult.setPrincipal((Principal)bstResult.get("principal"));
                        foundCerts = certs;
                        break;
                    }
                } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(valueType)) {
                    String kiValue = secRef.getKeyIdentifierValue();
                    List<WSSecurityEngineResult> resultsList = data.getWsDocInfo().getResultsByTag(4096);
                    for (WSSecurityEngineResult bstResult : resultsList) {
                        X509Certificate[] certs = (X509Certificate[])bstResult.get("x509-certificates");
                        if (certs == null) continue;
                        try {
                            byte[] digest = KeyUtils.generateDigest(certs[0].getEncoded());
                            if (!Arrays.equals(XMLUtils.decode(kiValue), digest)) continue;
                            parserResult.setPrincipal((Principal)bstResult.get("principal"));
                            foundCerts = certs;
                            break;
                        }
                        catch (CertificateEncodingException ex) {
                            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, ex, "encodeError");
                        }
                    }
                }
            }
            if (foundCerts != null) {
                parserResult.setCerts(new X509Certificate[]{foundCerts[0]});
            }
        }
    }

    private STRParserResult processPreviousResult(WSSecurityEngineResult result, SecurityTokenReference secRef, STRParserParameters parameters) throws WSSecurityException {
        STRParserResult parserResult = new STRParserResult();
        RequestData data = parameters.getData();
        Integer action = (Integer)result.get("action");
        if (action != null && (8192 == action || 1 == action)) {
            STRParserUtil.checkUsernameTokenBSPCompliance(secRef, data.getBSPEnforcer());
            UsernameToken usernameToken = (UsernameToken)result.get("username-token");
            usernameToken.setRawPassword(data.getCallbackHandler());
            parserResult.setSecretKey((byte[])result.get("secret"));
            parserResult.setPrincipal(usernameToken.createPrincipal());
        } else if (action != null && 4096 == action) {
            BinarySecurity token = (BinarySecurity)result.get("binary-security-token");
            STRParserUtil.checkBinarySecurityBSPCompliance(secRef, token, data.getBSPEnforcer());
            parserResult.setCerts((X509Certificate[])result.get("x509-certificates"));
            parserResult.setSecretKey((byte[])result.get("secret"));
            Boolean validatedToken = (Boolean)result.get("validated-token");
            if (validatedToken.booleanValue()) {
                parserResult.setTrustedCredential(true);
            }
        } else if (action != null && 4 == action) {
            STRParserUtil.checkEncryptedKeyBSPCompliance(secRef, data.getBSPEnforcer());
            parserResult.setSecretKey((byte[])result.get("secret"));
            String id = (String)result.get("id");
            parserResult.setPrincipal(new CustomTokenPrincipal(id));
        } else if (action != null && 1024 == action) {
            parserResult.setSecretKey((byte[])result.get("secret"));
            SecurityContextToken sct = (SecurityContextToken)result.get("security-context-token");
            parserResult.setPrincipal(new CustomTokenPrincipal(sct.getIdentifier()));
        } else if (action != null && 2048 == action) {
            DerivedKeyToken dkt = (DerivedKeyToken)result.get("derived-key-token");
            int keyLength = dkt.getLength();
            if (keyLength <= 0 && parameters.getDerivationKeyLength() > 0) {
                keyLength = parameters.getDerivationKeyLength();
            }
            byte[] secret = (byte[])result.get("secret");
            Principal principal = dkt.createPrincipal();
            ((WSDerivedKeyTokenPrincipal)principal).setSecret(secret);
            parserResult.setPrincipal(principal);
            parserResult.setSecretKey(dkt.deriveKey(keyLength, secret));
        } else if (action != null && (8 == action || 16 == action)) {
            SamlAssertionWrapper samlAssertion = (SamlAssertionWrapper)result.get("saml-assertion");
            STRParserUtil.checkSamlTokenBSPCompliance(secRef, samlAssertion, data.getBSPEnforcer());
            SAMLKeyInfo keyInfo = samlAssertion.getSubjectKeyInfo();
            if (keyInfo == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
            }
            X509Certificate[] foundCerts = keyInfo.getCerts();
            if (foundCerts != null) {
                parserResult.setCerts(new X509Certificate[]{foundCerts[0]});
            }
            parserResult.setSecretKey(keyInfo.getSecret());
            parserResult.setPublicKey(keyInfo.getPublicKey());
            parserResult.setPrincipal(this.createPrincipalFromSAML(samlAssertion, parserResult));
        }
        STRParser.REFERENCE_TYPE referenceType = this.getReferenceType(secRef);
        if (referenceType != null) {
            parserResult.setReferenceType(referenceType);
        }
        return parserResult;
    }

    private STRParserResult processSTR(SecurityTokenReference secRef, String uri, STRParserParameters parameters) throws WSSecurityException {
        Crypto crypto;
        STRParserResult parserResult = new STRParserResult();
        RequestData data = parameters.getData();
        WSDocInfo wsDocInfo = data.getWsDocInfo();
        Element strElement = parameters.getStrElement();
        if (secRef.containsReference()) {
            Reference reference = secRef.getReference();
            byte[] secretKey = STRParserUtil.getSecretKeyFromToken(uri, reference.getValueType(), 9, data);
            Principal principal = new CustomTokenPrincipal(uri);
            if (secretKey == null) {
                Element token = STRParserUtil.getTokenElement(strElement.getOwnerDocument(), wsDocInfo, data.getCallbackHandler(), uri, reference.getValueType());
                QName el = new QName(token.getNamespaceURI(), token.getLocalName());
                if (el.equals(WSConstants.BINARY_TOKEN)) {
                    Processor proc = data.getWssConfig().getProcessor(WSConstants.BINARY_TOKEN);
                    List<WSSecurityEngineResult> bstResult = proc.handleToken(token, parameters.getData());
                    BinarySecurity bstToken = (BinarySecurity)bstResult.get(0).get("binary-security-token");
                    STRParserUtil.checkBinarySecurityBSPCompliance(secRef, bstToken, data.getBSPEnforcer());
                    parserResult.setCerts((X509Certificate[])bstResult.get(0).get("x509-certificates"));
                    secretKey = (byte[])bstResult.get(0).get("secret");
                    principal = (Principal)bstResult.get(0).get("principal");
                } else if (el.equals(WSConstants.SAML_TOKEN) || el.equals(WSConstants.SAML2_TOKEN)) {
                    Processor proc = data.getWssConfig().getProcessor(WSConstants.SAML_TOKEN);
                    Element processedToken = STRParserUtil.findProcessedTokenElement(strElement.getOwnerDocument(), wsDocInfo, data.getCallbackHandler(), uri, secRef.getReference().getValueType());
                    SamlAssertionWrapper samlAssertion = null;
                    if (processedToken == null) {
                        List<WSSecurityEngineResult> samlResult = proc.handleToken(token, data);
                        samlAssertion = (SamlAssertionWrapper)samlResult.get(0).get("saml-assertion");
                    } else {
                        samlAssertion = new SamlAssertionWrapper(processedToken);
                        samlAssertion.parseSubject(new WSSSAMLKeyInfoProcessor(data), data.getSigVerCrypto(), data.getCallbackHandler());
                    }
                    STRParserUtil.checkSamlTokenBSPCompliance(secRef, samlAssertion, data.getBSPEnforcer());
                    SAMLKeyInfo keyInfo = samlAssertion.getSubjectKeyInfo();
                    X509Certificate[] foundCerts = keyInfo.getCerts();
                    if (foundCerts != null && foundCerts.length > 0) {
                        parserResult.setCerts(new X509Certificate[]{foundCerts[0]});
                    }
                    secretKey = keyInfo.getSecret();
                    principal = this.createPrincipalFromSAML(samlAssertion, parserResult);
                } else if (el.equals(WSConstants.ENCRYPTED_KEY)) {
                    STRParserUtil.checkEncryptedKeyBSPCompliance(secRef, data.getBSPEnforcer());
                    Processor proc = data.getWssConfig().getProcessor(WSConstants.ENCRYPTED_KEY);
                    List<WSSecurityEngineResult> encrResult = proc.handleToken(token, data);
                    secretKey = (byte[])encrResult.get(0).get("secret");
                    principal = new CustomTokenPrincipal(token.getAttributeNS(null, "Id"));
                }
            }
            parserResult.setSecretKey(secretKey);
            parserResult.setPrincipal(principal);
        } else if (secRef.containsX509Data() || secRef.containsX509IssuerSerial()) {
            parserResult.setReferenceType(STRParser.REFERENCE_TYPE.ISSUER_SERIAL);
            crypto = data.getSigVerCrypto();
            X509Certificate[] foundCerts = secRef.getX509IssuerSerial(crypto);
            if (foundCerts != null && foundCerts.length > 0) {
                parserResult.setCerts(new X509Certificate[]{foundCerts[0]});
            }
        } else if (secRef.containsKeyIdentifier()) {
            if (secRef.getKeyIdentifierValueType().equals("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1")) {
                STRParserUtil.checkEncryptedKeyBSPCompliance(secRef, data.getBSPEnforcer());
                String id = secRef.getKeyIdentifierValue();
                parserResult.setSecretKey(STRParserUtil.getSecretKeyFromToken(id, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1", 9, data));
                parserResult.setPrincipal(new CustomTokenPrincipal(id));
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(secRef.getKeyIdentifierValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(secRef.getKeyIdentifierValueType())) {
                this.parseSAMLKeyIdentifier(secRef, data, parserResult);
            } else {
                crypto = data.getSigVerCrypto();
                this.parseBSTKeyIdentifier(secRef, crypto, data, parserResult);
            }
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "unsupportedKeyInfo", new Object[]{strElement.toString()});
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

