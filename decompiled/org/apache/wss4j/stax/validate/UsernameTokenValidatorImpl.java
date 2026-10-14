/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import org.apache.wss4j.binding.wss10.AttributedString;
import org.apache.wss4j.binding.wss10.EncodedString;
import org.apache.wss4j.binding.wss10.PasswordString;
import org.apache.wss4j.binding.wss10.UsernameTokenType;
import org.apache.wss4j.binding.wsu10.AttributedDateTime;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.impl.securityToken.UsernameSecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.wss4j.stax.validate.UsernameTokenValidator;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsernameTokenValidatorImpl
implements UsernameTokenValidator {
    private static final transient Logger LOG = LoggerFactory.getLogger(UsernameTokenValidatorImpl.class);

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
                LOG.debug("Authentication failed as the received password type does not match the required password type of: {}", (Object)requiredPasswordType);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
            }
            usernameTokenPasswordType = WSSConstants.UsernameTokenPasswordType.getUsernameTokenPasswordType(passwordType.getType());
            if (requiredPasswordType != usernameTokenPasswordType) {
                LOG.debug("Authentication failed as the received password type does not match the required password type of: {}", (Object)requiredPasswordType);
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
            nonceVal = XMLUtils.decode(encodedNonce.getValue());
        }
        AttributedDateTime attributedDateTimeCreated = (AttributedDateTime)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSU_CREATED);
        String created = null;
        if (attributedDateTimeCreated != null) {
            created = attributedDateTimeCreated.getValue();
        }
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
            this.verifyCustomPassword(username.getValue(), passwordType, tokenContext);
        } else if (!allowUsernameTokenNoPassword) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
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
        UsernameSecurityTokenImpl token = usernameSecurityToken;
        return (T)token;
    }

    protected void verifyDigestPassword(String username, PasswordString passwordType, byte[] nonceVal, String created, TokenContext tokenContext) throws WSSecurityException {
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

    protected void verifyPlaintextPassword(String username, PasswordString passwordType, TokenContext tokenContext) throws WSSecurityException {
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

    protected void verifyCustomPassword(String username, PasswordString passwordType, TokenContext tokenContext) throws WSSecurityException {
        this.verifyPlaintextPassword(username, passwordType, tokenContext);
    }
}

