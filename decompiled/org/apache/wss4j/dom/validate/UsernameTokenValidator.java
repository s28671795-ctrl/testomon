/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.validate;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsernameTokenValidator
implements Validator {
    private static final Logger LOG = LoggerFactory.getLogger(UsernameTokenValidator.class);

    @Override
    public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
        if (credential == null || credential.getUsernametoken() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCredential");
        }
        boolean handleCustomPasswordTypes = data.isHandleCustomPasswordTypes();
        boolean passwordsAreEncoded = data.isEncodePasswords();
        String requiredPasswordType = data.getRequiredPasswordType();
        UsernameToken usernameToken = credential.getUsernametoken();
        usernameToken.setPasswordsAreEncoded(passwordsAreEncoded);
        String pwType = usernameToken.getPasswordType();
        LOG.debug("UsernameToken user {}", (Object)usernameToken.getName());
        LOG.debug("UsernameToken password type {}", (Object)pwType);
        if (requiredPasswordType != null && !requiredPasswordType.equals(pwType)) {
            LOG.debug("Authentication failed as the received password type does not match the required password type of: {}", (Object)requiredPasswordType);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        String password = usernameToken.getPassword();
        if (usernameToken.isHashed()) {
            this.verifyDigestPassword(usernameToken, data);
        } else if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText".equals(pwType) || password != null && (pwType == null || "".equals(pwType.trim()))) {
            this.verifyPlaintextPassword(usernameToken, data);
        } else if (password != null) {
            if (!handleCustomPasswordTypes) {
                LOG.debug("Authentication failed as handleCustomUsernameTokenTypes is false");
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
            }
            this.verifyCustomPassword(usernameToken, data);
        } else {
            this.verifyUnknownPassword(usernameToken, data);
        }
        return credential;
    }

    protected void verifyCustomPassword(UsernameToken usernameToken, RequestData data) throws WSSecurityException {
        this.verifyPlaintextPassword(usernameToken, data);
    }

    protected void verifyPlaintextPassword(UsernameToken usernameToken, RequestData data) throws WSSecurityException {
        this.verifyDigestPassword(usernameToken, data);
    }

    protected void verifyDigestPassword(UsernameToken usernameToken, RequestData data) throws WSSecurityException {
        String passDigest;
        if (data.getCallbackHandler() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCallback");
        }
        String user = usernameToken.getName();
        String password = usernameToken.getPassword();
        String nonce = usernameToken.getNonce();
        String createdTime = usernameToken.getCreated();
        String pwType = usernameToken.getPasswordType();
        boolean passwordsAreEncoded = usernameToken.getPasswordsAreEncoded();
        WSPasswordCallback pwCb = new WSPasswordCallback(user, null, pwType, 2);
        try {
            data.getCallbackHandler().handle(new Callback[]{pwCb});
        }
        catch (IOException | UnsupportedCallbackException e) {
            LOG.debug(e.getMessage(), e);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, e);
        }
        String origPassword = pwCb.getPassword();
        if (origPassword == null) {
            LOG.debug("Callback supplied no password for: {}", (Object)user);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        if (usernameToken.isHashed() ? !(passDigest = passwordsAreEncoded ? UsernameToken.doPasswordDigest(nonce, createdTime, XMLUtils.decode(origPassword)) : UsernameToken.doPasswordDigest(nonce, createdTime, origPassword)).equals(password) : !origPassword.equals(password)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
    }

    protected void verifyUnknownPassword(UsernameToken usernameToken, RequestData data) throws WSSecurityException {
        boolean allowUsernameTokenDerivedKeys = data.isAllowUsernameTokenNoPassword();
        if (!allowUsernameTokenDerivedKeys) {
            LOG.debug("Authentication failed as the received UsernameToken does not contain any password element");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
    }
}

