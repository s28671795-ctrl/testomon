/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.wss4j.binding.wss10.EncodedString;
import org.apache.wss4j.binding.wss10.PasswordString;
import org.apache.wss4j.binding.wss10.UsernameTokenType;
import org.apache.wss4j.binding.wsu10.AttributedDateTime;
import org.apache.wss4j.common.NamePasswordCallbackHandler;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.impl.securityToken.UsernameSecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.wss4j.stax.validate.UsernameTokenValidator;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAASUsernameTokenValidatorImpl
implements UsernameTokenValidator {
    private static final transient Logger LOG = LoggerFactory.getLogger(JAASUsernameTokenValidatorImpl.class);
    private String contextName;

    public void setContextName(String name) {
        this.contextName = name;
    }

    public String getContextName() {
        return this.contextName;
    }

    @Override
    public <T extends UsernameSecurityToken & InboundSecurityToken> T validate(UsernameTokenType usernameTokenType, TokenContext tokenContext) throws WSSecurityException {
        AttributedDateTime attributedDateTimeCreated;
        Subject subject;
        PasswordString passwordType = (PasswordString)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE_PASSWORD);
        WSSConstants.UsernameTokenPasswordType usernameTokenPasswordType = WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE;
        if (passwordType != null && passwordType.getType() != null) {
            usernameTokenPasswordType = WSSConstants.UsernameTokenPasswordType.getUsernameTokenPasswordType(passwordType.getType());
        }
        if (usernameTokenPasswordType != WSSConstants.UsernameTokenPasswordType.PASSWORD_TEXT) {
            LOG.warn("Password type is not supported");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        String username = null;
        if (usernameTokenType.getUsername() != null) {
            username = usernameTokenType.getUsername().getValue();
        }
        String password = null;
        if (passwordType != null) {
            password = passwordType.getValue();
        }
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            LOG.warn("User or password empty");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        try {
            CallbackHandler handler = this.getCallbackHandler(username, password);
            LoginContext ctx = new LoginContext(this.getContextName(), handler);
            ctx.login();
            subject = ctx.getSubject();
        }
        catch (LoginException ex) {
            LOG.info("Authentication failed", ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, (Exception)ex);
        }
        EncodedString encodedNonce = (EncodedString)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE_NONCE);
        byte[] nonceVal = null;
        if (encodedNonce != null) {
            if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encodedNonce.getEncodingType())) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "badTokenType01");
            }
            nonceVal = XMLUtils.decode(encodedNonce.getValue());
        }
        UsernameSecurityTokenImpl usernameSecurityToken = new UsernameSecurityTokenImpl(usernameTokenPasswordType, username, password, (attributedDateTimeCreated = (AttributedDateTime)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSU_CREATED)) != null ? attributedDateTimeCreated.getValue() : null, nonceVal, null, null, tokenContext.getWsSecurityContext(), usernameTokenType.getId(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
        usernameSecurityToken.setElementPath(tokenContext.getElementPath());
        usernameSecurityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
        usernameSecurityToken.setSubject(subject);
        UsernameSecurityTokenImpl token = usernameSecurityToken;
        return (T)token;
    }

    protected CallbackHandler getCallbackHandler(String name, String password) {
        return new NamePasswordCallbackHandler(name, password);
    }
}

