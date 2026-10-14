/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.authentication;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;

public class PlainAuthenticator
implements ClientAuthenticator {
    protected String username;
    protected String password;
    protected boolean complete = false;

    public PlainAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean hasInitialResponse() {
        return true;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public String getMechanismName() {
        return "PLAIN";
    }

    public byte[] evaluateChallenge(byte[] challenge) throws MessagingException {
        try {
            byte[] userBytes = this.username.getBytes("UTF-8");
            byte[] passBytes = this.password.getBytes("UTF-8");
            byte[] tokenBytes = new byte[userBytes.length * 2 + passBytes.length + 2];
            System.arraycopy(userBytes, 0, tokenBytes, 0, userBytes.length);
            System.arraycopy(userBytes, 0, tokenBytes, userBytes.length + 1, userBytes.length);
            System.arraycopy(passBytes, 0, tokenBytes, userBytes.length * 2 + 2, passBytes.length);
            this.complete = true;
            return tokenBytes;
        }
        catch (UnsupportedEncodingException e) {
            throw new MessagingException("Invalid encoding");
        }
    }
}

