/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.text;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.TextEncryptor;

public final class StrongTextEncryptor
implements TextEncryptor {
    private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    public StrongTextEncryptor() {
        this.encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
    }

    public void setPassword(String password) {
        this.encryptor.setPassword(password);
    }

    public void setPasswordCharArray(char[] password) {
        this.encryptor.setPasswordCharArray(password);
    }

    @Override
    public String encrypt(String message) {
        return this.encryptor.encrypt(message);
    }

    @Override
    public String decrypt(String encryptedMessage) {
        return this.encryptor.decrypt(encryptedMessage);
    }
}

