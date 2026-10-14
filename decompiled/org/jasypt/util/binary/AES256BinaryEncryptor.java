/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.binary;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.util.binary.BinaryEncryptor;

public final class AES256BinaryEncryptor
implements BinaryEncryptor {
    private final StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();

    public AES256BinaryEncryptor() {
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
    public byte[] encrypt(byte[] binary) {
        return this.encryptor.encrypt(binary);
    }

    @Override
    public byte[] decrypt(byte[] encryptedBinary) {
        return this.encryptor.decrypt(encryptedBinary);
    }
}

