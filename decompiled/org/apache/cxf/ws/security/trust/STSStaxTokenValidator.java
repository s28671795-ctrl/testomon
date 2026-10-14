/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.io.Serializable;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.bind.JAXBElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.wss4j.binding.wss10.AttributedString;
import org.apache.wss4j.binding.wss10.BinarySecurityTokenType;
import org.apache.wss4j.binding.wss10.EncodedString;
import org.apache.wss4j.binding.wss10.PasswordString;
import org.apache.wss4j.binding.wss10.UsernameTokenType;
import org.apache.wss4j.binding.wsu10.AttributedDateTime;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.KerberosServiceSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.SamlSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.UsernameSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509PKIPathv1SecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509V3SecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.wss4j.stax.validate.BinarySecurityTokenValidator;
import org.apache.wss4j.stax.validate.BinarySecurityTokenValidatorImpl;
import org.apache.wss4j.stax.validate.SamlTokenValidatorImpl;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.wss4j.stax.validate.UsernameTokenValidator;
import org.apache.xml.security.binding.xop.Include;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class STSStaxTokenValidator
extends SamlTokenValidatorImpl
implements BinarySecurityTokenValidator,
UsernameTokenValidator {
    private boolean alwaysValidateToSts;

    public STSStaxTokenValidator() {
    }

    public STSStaxTokenValidator(boolean alwaysValidateToSts) {
        this.alwaysValidateToSts = alwaysValidateToSts;
    }

    @Override
    public <T extends SamlSecurityToken & InboundSecurityToken> T validate(SamlAssertionWrapper samlAssertionWrapper, InboundSecurityToken subjectSecurityToken, TokenContext tokenContext) throws WSSecurityException {
        this.checkConditions(samlAssertionWrapper);
        this.checkOneTimeUse(samlAssertionWrapper, tokenContext.getWssSecurityProperties().getSamlOneTimeUseReplayCache());
        this.validateAssertion(samlAssertionWrapper);
        Crypto sigVerCrypto = null;
        if (samlAssertionWrapper.isSigned()) {
            sigVerCrypto = tokenContext.getWssSecurityProperties().getSignatureVerificationCrypto();
        }
        final SoapMessage message = (SoapMessage)tokenContext.getWssSecurityProperties().getMsgContext();
        boolean valid = false;
        if (this.alwaysValidateToSts) {
            Element tokenElement = samlAssertionWrapper.getElement();
            STSStaxTokenValidator.validateTokenToSTS(tokenElement, message);
            valid = true;
        }
        final boolean stsValidated = valid;
        SamlSecurityTokenImpl securityToken = new SamlSecurityTokenImpl(samlAssertionWrapper, subjectSecurityToken, tokenContext.getWsSecurityContext(), sigVerCrypto, WSSecurityTokenConstants.KeyIdentifier_NoKeyInfo, tokenContext.getWssSecurityProperties()){

            @Override
            public void verify() throws XMLSecurityException {
                if (stsValidated) {
                    return;
                }
                try {
                    super.verify();
                }
                catch (XMLSecurityException ex) {
                    SamlAssertionWrapper assertion = super.getSamlAssertionWrapper();
                    Element tokenElement = assertion.getElement();
                    STSStaxTokenValidator.validateTokenToSTS(tokenElement, message);
                }
            }
        };
        securityToken.setElementPath(tokenContext.getElementPath());
        securityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
        return (T)securityToken;
    }

    @Override
    public InboundSecurityToken validate(BinarySecurityTokenType binarySecurityTokenType, TokenContext tokenContext) throws WSSecurityException {
        STSStaxBSTValidator validator = new STSStaxBSTValidator(this.alwaysValidateToSts);
        return validator.validate(binarySecurityTokenType, tokenContext);
    }

    @Override
    public <T extends UsernameSecurityToken & InboundSecurityToken> T validate(UsernameTokenType usernameTokenType, TokenContext tokenContext) throws WSSecurityException {
        String password;
        AttributedString username;
        WSSConstants.UsernameTokenPasswordType usernameTokenPasswordType;
        byte[] salt = (byte[])XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE11_SALT);
        PasswordString passwordType = (PasswordString)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE_PASSWORD);
        Long iteration = (Long)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE11_ITERATION);
        if (salt != null && (passwordType != null || iteration == null)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badTokenType01");
        }
        boolean handleCustomPasswordTypes = tokenContext.getWssSecurityProperties().getHandleCustomPasswordTypes();
        boolean allowUsernameTokenNoPassword = tokenContext.getWssSecurityProperties().isAllowUsernameTokenNoPassword() || Boolean.parseBoolean((String)tokenContext.getWsSecurityContext().get("secureProcessing.AllowUsernameTokenNoPassword"));
        WSSConstants.UsernameTokenPasswordType requiredPasswordType = tokenContext.getWssSecurityProperties().getUsernameTokenPasswordType();
        if (requiredPasswordType != null) {
            if (passwordType == null || passwordType.getType() == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
            }
            usernameTokenPasswordType = WSSConstants.UsernameTokenPasswordType.getUsernameTokenPasswordType(passwordType.getType());
            if (requiredPasswordType != usernameTokenPasswordType) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
            }
        }
        usernameTokenPasswordType = WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE;
        if (passwordType != null && passwordType.getType() != null) {
            usernameTokenPasswordType = WSSConstants.UsernameTokenPasswordType.getUsernameTokenPasswordType(passwordType.getType());
        }
        if ((username = usernameTokenType.getUsername()) == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badTokenType01");
        }
        EncodedString encodedNonce = (EncodedString)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE_NONCE);
        byte[] nonceVal = null;
        if (encodedNonce != null && encodedNonce.getValue() != null) {
            nonceVal = Base64.decodeBase64(encodedNonce.getValue());
        }
        AttributedDateTime attributedDateTimeCreated = (AttributedDateTime)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSU_CREATED);
        String created = null;
        if (attributedDateTimeCreated != null) {
            created = attributedDateTimeCreated.getValue();
        }
        boolean valid = false;
        SoapMessage message = (SoapMessage)tokenContext.getWssSecurityProperties().getMsgContext();
        if (this.alwaysValidateToSts) {
            Element tokenElement = this.convertToDOM(username.getValue(), passwordType.getValue(), passwordType.getType(), usernameTokenType.getId());
            STSStaxTokenValidator.validateTokenToSTS(tokenElement, message);
            valid = true;
        }
        if (!valid) {
            try {
                if (usernameTokenPasswordType == WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST) {
                    if (encodedNonce == null || attributedDateTimeCreated == null) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badTokenType01");
                    }
                    if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encodedNonce.getEncodingType())) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "badTokenType01");
                    }
                    this.verifyDigestPassword(username.getValue(), passwordType, nonceVal, created, tokenContext);
                } else if (usernameTokenPasswordType == WSSConstants.UsernameTokenPasswordType.PASSWORD_TEXT || passwordType != null && passwordType.getValue() != null && usernameTokenPasswordType == WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE) {
                    this.verifyPlaintextPassword(username.getValue(), passwordType, tokenContext);
                } else if (passwordType != null && passwordType.getValue() != null) {
                    if (!handleCustomPasswordTypes) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
                    }
                    this.verifyPlaintextPassword(username.getValue(), passwordType, tokenContext);
                } else if (!allowUsernameTokenNoPassword) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
                }
            }
            catch (WSSecurityException ex) {
                Element tokenElement = this.convertToDOM(username.getValue(), passwordType.getValue(), passwordType.getType(), usernameTokenType.getId());
                STSStaxTokenValidator.validateTokenToSTS(tokenElement, message);
            }
        }
        if (passwordType != null) {
            password = passwordType.getValue();
        } else if (salt != null) {
            WSPasswordCallback pwCb = new WSPasswordCallback(username.getValue(), 2);
            try {
                WSSUtils.doPasswordCallback(tokenContext.getWssSecurityProperties().getCallbackHandler(), pwCb);
            }
            catch (WSSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, (Exception)e);
            }
            password = pwCb.getPassword();
        } else {
            password = null;
        }
        UsernameSecurityTokenImpl usernameSecurityToken = new UsernameSecurityTokenImpl(usernameTokenPasswordType, username.getValue(), password, created, nonceVal, salt, iteration, tokenContext.getWsSecurityContext(), usernameTokenType.getId(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
        usernameSecurityToken.setElementPath(tokenContext.getElementPath());
        usernameSecurityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
        return (T)usernameSecurityToken;
    }

    private void verifyDigestPassword(String username, PasswordString passwordType, byte[] nonceVal, String created, TokenContext tokenContext) throws WSSecurityException {
        WSPasswordCallback pwCb = new WSPasswordCallback(username, null, passwordType.getType(), 2);
        try {
            WSSUtils.doPasswordCallback(tokenContext.getWssSecurityProperties().getCallbackHandler(), pwCb);
        }
        catch (WSSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, (Exception)e);
        }
        if (pwCb.getPassword() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        String passDigest = WSSUtils.doPasswordDigest(nonceVal, created, pwCb.getPassword());
        if (!passwordType.getValue().equals(passDigest)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        passwordType.setValue(pwCb.getPassword());
    }

    private void verifyPlaintextPassword(String username, PasswordString passwordType, TokenContext tokenContext) throws WSSecurityException {
        WSPasswordCallback pwCb = new WSPasswordCallback(username, null, passwordType.getType(), 2);
        try {
            WSSUtils.doPasswordCallback(tokenContext.getWssSecurityProperties().getCallbackHandler(), pwCb);
        }
        catch (WSSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, (Exception)e);
        }
        if (pwCb.getPassword() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        if (!passwordType.getValue().equals(pwCb.getPassword())) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        passwordType.setValue(pwCb.getPassword());
    }

    private Element convertToDOM(String username, String password, String passwordType, String id) {
        Document doc = DOMUtils.getEmptyDocument();
        UsernameToken usernameToken = new UsernameToken(true, doc, passwordType);
        usernameToken.setName(username);
        usernameToken.setPassword(password);
        usernameToken.setID(id);
        usernameToken.addWSSENamespace();
        usernameToken.addWSUNamespace();
        return usernameToken.getElement();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void validateTokenToSTS(Element tokenElement, SoapMessage message) throws WSSecurityException {
        STSClient c;
        SecurityToken token = new SecurityToken();
        token.setToken(tokenElement);
        STSClient sTSClient = c = STSUtils.getClient(message, "sts");
        synchronized (sTSClient) {
            System.setProperty("noprint", "true");
            try {
                c.validateSecurityToken(token);
            }
            catch (Exception e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, e);
            }
        }
    }

    private static class STSStaxBSTValidator
    extends BinarySecurityTokenValidatorImpl {
        private boolean alwaysValidateToSts;

        STSStaxBSTValidator(boolean alwaysValidateToSts) {
            this.alwaysValidateToSts = alwaysValidateToSts;
        }

        @Override
        public InboundSecurityToken validate(final BinarySecurityTokenType binarySecurityTokenType, TokenContext tokenContext) throws WSSecurityException {
            if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(binarySecurityTokenType.getEncodingType())) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badEncoding", new Object[]{binarySecurityTokenType.getEncodingType()});
            }
            byte[] securityTokenData = null;
            try {
                securityTokenData = this.getBinarySecurityTokenBytes(binarySecurityTokenType, tokenContext.getWssSecurityProperties());
            }
            catch (XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, (Exception)e);
            }
            final SoapMessage message = (SoapMessage)tokenContext.getWssSecurityProperties().getMsgContext();
            boolean valid = false;
            if (this.alwaysValidateToSts) {
                Element tokenElement = this.convertToDOM(binarySecurityTokenType, securityTokenData);
                STSStaxTokenValidator.validateTokenToSTS(tokenElement, message);
                valid = true;
            }
            final boolean stsValidated = valid;
            try {
                final byte[] bytes = securityTokenData;
                if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(binarySecurityTokenType.getValueType())) {
                    Crypto crypto = this.getCrypto(tokenContext.getWssSecurityProperties());
                    X509V3SecurityTokenImpl x509V3SecurityToken = new X509V3SecurityTokenImpl(tokenContext.getWsSecurityContext(), crypto, tokenContext.getWssSecurityProperties().getCallbackHandler(), securityTokenData, binarySecurityTokenType.getId(), tokenContext.getWssSecurityProperties()){

                        @Override
                        public void verify() throws XMLSecurityException {
                            if (stsValidated) {
                                return;
                            }
                            try {
                                super.verify();
                            }
                            catch (XMLSecurityException ex) {
                                Element tokenElement = this.convertToDOM(binarySecurityTokenType, bytes);
                                STSStaxTokenValidator.validateTokenToSTS(tokenElement, message);
                            }
                        }
                    };
                    x509V3SecurityToken.setElementPath(tokenContext.getElementPath());
                    x509V3SecurityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
                    return x509V3SecurityToken;
                }
                if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1".equals(binarySecurityTokenType.getValueType())) {
                    Crypto crypto = this.getCrypto(tokenContext.getWssSecurityProperties());
                    X509PKIPathv1SecurityTokenImpl x509PKIPathv1SecurityToken = new X509PKIPathv1SecurityTokenImpl(tokenContext.getWsSecurityContext(), crypto, tokenContext.getWssSecurityProperties().getCallbackHandler(), securityTokenData, binarySecurityTokenType.getId(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE, tokenContext.getWssSecurityProperties()){

                        @Override
                        public void verify() throws XMLSecurityException {
                            if (stsValidated) {
                                return;
                            }
                            try {
                                super.verify();
                            }
                            catch (XMLSecurityException ex) {
                                Element tokenElement = this.convertToDOM(binarySecurityTokenType, bytes);
                                STSStaxTokenValidator.validateTokenToSTS(tokenElement, message);
                            }
                        }
                    };
                    x509PKIPathv1SecurityToken.setElementPath(tokenContext.getElementPath());
                    x509PKIPathv1SecurityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
                    return x509PKIPathv1SecurityToken;
                }
                if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(binarySecurityTokenType.getValueType())) {
                    KerberosServiceSecurityTokenImpl kerberosServiceSecurityToken = new KerberosServiceSecurityTokenImpl(tokenContext.getWsSecurityContext(), tokenContext.getWssSecurityProperties().getCallbackHandler(), securityTokenData, binarySecurityTokenType.getValueType(), binarySecurityTokenType.getId(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE){

                        @Override
                        public void verify() throws XMLSecurityException {
                            if (stsValidated) {
                                return;
                            }
                            try {
                                super.verify();
                            }
                            catch (XMLSecurityException ex) {
                                Element tokenElement = this.convertToDOM(binarySecurityTokenType, bytes);
                                STSStaxTokenValidator.validateTokenToSTS(tokenElement, message);
                            }
                        }
                    };
                    kerberosServiceSecurityToken.setElementPath(tokenContext.getElementPath());
                    kerberosServiceSecurityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
                    return kerberosServiceSecurityToken;
                }
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "invalidValueType", new Object[]{binarySecurityTokenType.getValueType()});
            }
            catch (XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, (Exception)e);
            }
        }

        private byte[] getBinarySecurityTokenBytes(BinarySecurityTokenType binarySecurityTokenType, WSSSecurityProperties wssSecurityProperties) throws XMLSecurityException {
            StringBuilder sb = new StringBuilder();
            for (Serializable obj : binarySecurityTokenType.getContent()) {
                Include include;
                JAXBElement element;
                if (obj instanceof String) {
                    sb.append((String)((Object)obj));
                    continue;
                }
                if (!(obj instanceof JAXBElement) || !XMLSecurityConstants.TAG_XOP_INCLUDE.equals((element = (JAXBElement)obj).getName()) || (include = (Include)element.getValue()) == null || include.getHref() == null || !include.getHref().startsWith("cid:")) continue;
                CallbackHandler callbackHandler = wssSecurityProperties.getAttachmentCallbackHandler();
                return AttachmentUtils.getBytesFromAttachment(include.getHref(), callbackHandler, true);
            }
            return Base64.decodeBase64(sb.toString());
        }

        private Element convertToDOM(BinarySecurityTokenType binarySecurityTokenType, byte[] securityTokenData) throws WSSecurityException {
            Document doc = DOMUtils.getEmptyDocument();
            BinarySecurity binarySecurity = null;
            if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(binarySecurityTokenType.getValueType())) {
                binarySecurity = new X509Security(doc);
            } else if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1".equals(binarySecurityTokenType.getValueType())) {
                binarySecurity = new PKIPathSecurity(doc);
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(binarySecurityTokenType.getValueType())) {
                binarySecurity = new KerberosSecurity(doc);
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN);
            }
            binarySecurity.addWSSENamespace();
            binarySecurity.addWSUNamespace();
            binarySecurity.setEncodingType(binarySecurityTokenType.getEncodingType());
            binarySecurity.setValueType(binarySecurityTokenType.getValueType());
            binarySecurity.setID(binarySecurityTokenType.getId());
            binarySecurity.setToken(securityTokenData);
            return binarySecurity.getElement();
        }
    }
}

