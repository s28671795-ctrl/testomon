/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.session;

import com.hierynomus.mssmb2.messages.SMB2SessionSetup;
import java.util.Arrays;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SessionContext {
    private Set<SMB2SessionSetup.SMB2SessionFlags> sessionFlags;
    private boolean signingRequired;
    private boolean encryptData;
    private SecretKey sessionKey;
    private SecretKey signingKey;
    private SecretKey decryptionKey;
    private SecretKey encryptionKey;
    private SecretKey applicationKey;
    byte[] preauthIntegrityHashValue;

    public void established(SMB2SessionSetup response) {
        this.sessionFlags = response.getSessionFlags();
    }

    public boolean isSigningRequired() {
        return this.signingRequired;
    }

    public void setSigningRequired(boolean signingRequired) {
        this.signingRequired = signingRequired;
    }

    public boolean isEncryptData() {
        return this.encryptData;
    }

    public void setEncryptData(boolean encryptData) {
        this.encryptData = encryptData;
    }

    public boolean isAnonymous() {
        return this.sessionFlags.contains(SMB2SessionSetup.SMB2SessionFlags.SMB2_SESSION_FLAG_IS_NULL);
    }

    public boolean isGuest() {
        return this.sessionFlags.contains(SMB2SessionSetup.SMB2SessionFlags.SMB2_SESSION_FLAG_IS_GUEST);
    }

    public void setSessionKey(SecretKeySpec sessionKey) {
        this.sessionKey = sessionKey;
    }

    public void setPreauthIntegrityHashValue(byte[] preauthIntegrityHashValue) {
        this.preauthIntegrityHashValue = Arrays.copyOf(preauthIntegrityHashValue, preauthIntegrityHashValue.length);
    }

    public byte[] getPreauthIntegrityHashValue() {
        return this.preauthIntegrityHashValue;
    }

    public SecretKey getSessionKey() {
        return this.sessionKey;
    }

    public SecretKey getSigningKey() {
        return this.signingKey;
    }

    public SecretKey getDecryptionKey() {
        return this.decryptionKey;
    }

    public SecretKey getEncryptionKey() {
        return this.encryptionKey;
    }

    public void setEncryptionKey(SecretKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public void setDecryptionKey(SecretKey decryptionKey) {
        this.decryptionKey = decryptionKey;
    }

    public void setSigningKey(SecretKey signingKey) {
        this.signingKey = signingKey;
    }

    public void setApplicationKey(SecretKey applicationKey) {
        this.applicationKey = applicationKey;
    }
}

