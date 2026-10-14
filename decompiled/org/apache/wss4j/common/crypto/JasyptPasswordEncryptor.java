/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JasyptPasswordEncryptor
implements PasswordEncryptor {
    public static final String DEFAULT_ALGORITHM = "PBEWithMD5AndTripleDES";
    private static final Logger LOG = LoggerFactory.getLogger(JasyptPasswordEncryptor.class);
    private final StandardPBEStringEncryptor passwordEncryptor = new StandardPBEStringEncryptor();
    private CallbackHandler callbackHandler;

    public JasyptPasswordEncryptor(String masterPassword) {
        this(masterPassword, DEFAULT_ALGORITHM);
    }

    public JasyptPasswordEncryptor(String masterPassword, String algorithm) {
        this.passwordEncryptor.setPassword(masterPassword);
        this.passwordEncryptor.setAlgorithm(algorithm);
    }

    public JasyptPasswordEncryptor(CallbackHandler callbackHandler) {
        this(callbackHandler, DEFAULT_ALGORITHM);
    }

    public JasyptPasswordEncryptor(CallbackHandler callbackHandler, String algorithm) {
        this.passwordEncryptor.setAlgorithm(algorithm);
        this.callbackHandler = callbackHandler;
    }

    @Override
    public String encrypt(String password) {
        if (this.callbackHandler != null) {
            WSPasswordCallback pwCb = new WSPasswordCallback("", 10);
            try {
                this.callbackHandler.handle(new Callback[]{pwCb});
            }
            catch (IOException | UnsupportedCallbackException e) {
                LOG.debug("Error in getting master password: ", e);
            }
            if (pwCb.getPassword() != null) {
                this.passwordEncryptor.setPassword(pwCb.getPassword());
            }
        }
        return this.passwordEncryptor.encrypt(password);
    }

    @Override
    public String decrypt(String encryptedPassword) {
        if (this.callbackHandler != null) {
            WSPasswordCallback pwCb = new WSPasswordCallback("", 10);
            try {
                this.callbackHandler.handle(new Callback[]{pwCb});
            }
            catch (IOException | UnsupportedCallbackException e) {
                LOG.debug("Error in getting master password: ", e);
            }
            if (pwCb.getPassword() != null) {
                this.passwordEncryptor.setPassword(pwCb.getPassword());
            }
        }
        return this.passwordEncryptor.decrypt(encryptedPassword);
    }
}

