/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.binary;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.util.binary.BinaryEncryptor;

public final class StrongBinaryEncryptor
implements BinaryEncryptor {
    private final StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();

    public StrongBinaryEncryptor() {
        this.encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
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

