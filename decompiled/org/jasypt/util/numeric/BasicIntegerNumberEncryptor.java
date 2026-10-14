/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.numeric;

import java.math.BigInteger;
import org.jasypt.encryption.pbe.StandardPBEBigIntegerEncryptor;
import org.jasypt.util.numeric.IntegerNumberEncryptor;

public final class BasicIntegerNumberEncryptor
implements IntegerNumberEncryptor {
    private final StandardPBEBigIntegerEncryptor encryptor = new StandardPBEBigIntegerEncryptor();

    public BasicIntegerNumberEncryptor() {
        this.encryptor.setAlgorithm("PBEWithMD5AndDES");
    }

    public void setPassword(String password) {
        this.encryptor.setPassword(password);
    }

    public void setPasswordCharArray(char[] password) {
        this.encryptor.setPasswordCharArray(password);
    }

    @Override
    public BigInteger encrypt(BigInteger number) {
        return this.encryptor.encrypt(number);
    }

    @Override
    public BigInteger decrypt(BigInteger encryptedNumber) {
        return this.encryptor.decrypt(encryptedNumber);
    }
}

