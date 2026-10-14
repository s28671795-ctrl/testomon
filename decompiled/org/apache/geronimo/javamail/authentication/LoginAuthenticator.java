/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.authentication;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;

public class LoginAuthenticator
implements ClientAuthenticator {
    protected static final int USERNAME = 0;
    protected static final int PASSWORD = 1;
    protected static final int COMPLETE = 2;
    protected String username;
    protected String password;
    protected int stage = 0;

    public LoginAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean hasInitialResponse() {
        return false;
    }

    public boolean isComplete() {
        return this.stage == 2;
    }

    public String getMechanismName() {
        return "LOGIN";
    }

    public byte[] evaluateChallenge(byte[] challenge) throws MessagingException {
        switch (this.stage) {
            case 2: {
                throw new MessagingException("Invalid LOGIN challenge");
            }
            case 0: {
                byte[] userBytes;
                try {
                    userBytes = this.username.getBytes("UTF-8");
                }
                catch (UnsupportedEncodingException e) {
                    throw new MessagingException("Invalid encoding");
                }
                this.stage = 1;
                return userBytes;
            }
            case 1: {
                byte[] passBytes;
                try {
                    passBytes = this.password.getBytes("UTF-8");
                }
                catch (UnsupportedEncodingException e) {
                    throw new MessagingException("Invalid encoding");
                }
                this.stage = 2;
                return passBytes;
            }
        }
        throw new MessagingException("Invalid LOGIN challenge");
    }
}

