/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.validate;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.wss4j.common.NamePasswordCallbackHandler;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAASUsernameTokenValidator
implements Validator {
    private static final Logger LOG = LoggerFactory.getLogger(JAASUsernameTokenValidator.class);
    private String contextName;

    public void setContextName(String name) {
        this.contextName = name;
    }

    public String getContextName() {
        return this.contextName;
    }

    @Override
    public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
        if (credential == null || credential.getUsernametoken() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCredential");
        }
        String user = null;
        String password = null;
        UsernameToken usernameToken = credential.getUsernametoken();
        user = usernameToken.getName();
        String pwType = usernameToken.getPasswordType();
        LOG.debug("UsernameToken user {}", (Object)usernameToken.getName());
        LOG.debug("UsernameToken password type {}", (Object)pwType);
        if (usernameToken.isHashed()) {
            LOG.warn("Authentication failed as hashed username token not supported");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        password = usernameToken.getPassword();
        if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText".equals(pwType)) {
            LOG.warn("Password type " + pwType + " not supported");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        if (user == null || user.length() <= 0 || password == null || password.length() <= 0) {
            LOG.warn("User or password empty");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        try {
            CallbackHandler handler = this.getCallbackHandler(user, password);
            LoginContext ctx = new LoginContext(this.getContextName(), handler);
            ctx.login();
            Subject subject = ctx.getSubject();
            credential.setSubject(subject);
        }
        catch (LoginException ex) {
            LOG.info("Authentication failed", ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, (Exception)ex);
        }
        return credential;
    }

    protected CallbackHandler getCallbackHandler(String name, String password) {
        return new NamePasswordCallbackHandler(name, password);
    }
}

