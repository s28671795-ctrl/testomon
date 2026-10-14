/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.text;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.util.text.TextEncryptor;

public final class AES256TextEncryptor
implements TextEncryptor {
    private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    public AES256TextEncryptor() {
        this.encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        this.encryptor.setIvGenerator(new RandomIvGenerator());
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

