/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.DestroyFailedException;

public class CipherPair {
    private String transformation;
    private Cipher enccipher;
    private SecretKey key;
    private byte[] ivp;

    public CipherPair(String transformation) throws GeneralSecurityException {
        this.transformation = transformation;
        int d = transformation.indexOf(47);
        String a = d > 0 ? transformation.substring(0, d) : transformation;
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(a);
            keygen.init(new SecureRandom());
            this.key = keygen.generateKey();
            this.enccipher = Cipher.getInstance(transformation);
            this.enccipher.init(1, this.key);
            this.ivp = this.enccipher.getIV();
        }
        catch (GeneralSecurityException e) {
            this.enccipher = null;
            throw e;
        }
    }

    public String getTransformation() {
        return this.transformation;
    }

    public Cipher getEncryptor() {
        return this.enccipher;
    }

    public Cipher getDecryptor() {
        Cipher deccipher = null;
        try {
            deccipher = Cipher.getInstance(this.transformation);
            deccipher.init(2, (Key)this.key, this.ivp == null ? null : new IvParameterSpec(this.ivp));
        }
        catch (GeneralSecurityException generalSecurityException) {
            // empty catch block
        }
        return deccipher;
    }

    public void clean() {
        if (this.ivp != null) {
            Arrays.fill(this.ivp, (byte)0);
        }
        try {
            this.key.destroy();
        }
        catch (DestroyFailedException destroyFailedException) {
            // empty catch block
        }
    }
}

