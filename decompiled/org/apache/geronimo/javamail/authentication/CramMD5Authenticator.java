/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.authentication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.apache.geronimo.mail.util.Hex;

public class CramMD5Authenticator
implements ClientAuthenticator {
    protected String username;
    protected String password;
    protected boolean complete = false;

    public CramMD5Authenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean hasInitialResponse() {
        return false;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public String getMechanismName() {
        return "CRAM-MD5";
    }

    public byte[] evaluateChallenge(byte[] challenge) throws MessagingException {
        try {
            byte[] passBytes = this.password.getBytes("UTF-8");
            byte[] digest = this.computeCramDigest(passBytes, challenge);
            String responseString = this.username + " " + new String(Hex.encode(digest), "ISO8859-1");
            this.complete = true;
            return responseString.getBytes("ISO8859-1");
        }
        catch (UnsupportedEncodingException e) {
            throw new MessagingException("Invalid character encodings");
        }
    }

    protected byte[] computeCramDigest(byte[] key, byte[] input) throws MessagingException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new MessagingException("Unable to access MD5 message digest", e);
        }
        if (key.length > 64) {
            digest.update(key);
            key = digest.digest();
        }
        byte[] ipad = new byte[64];
        byte[] opad = new byte[64];
        System.arraycopy(key, 0, ipad, 0, key.length);
        System.arraycopy(key, 0, opad, 0, key.length);
        int i = 0;
        while (i < 64) {
            int n = i;
            ipad[n] = (byte)(ipad[n] ^ 0x36);
            int n2 = i++;
            opad[n2] = (byte)(opad[n2] ^ 0x5C);
        }
        digest.reset();
        digest.update(ipad);
        digest.update(input);
        byte[] md5digest = digest.digest();
        digest.reset();
        digest.update(opad);
        digest.update(md5digest);
        return digest.digest();
    }
}

