/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.numeric;

import java.math.BigDecimal;
import org.jasypt.encryption.pbe.StandardPBEBigDecimalEncryptor;
import org.jasypt.util.numeric.DecimalNumberEncryptor;

public final class BasicDecimalNumberEncryptor
implements DecimalNumberEncryptor {
    private final StandardPBEBigDecimalEncryptor encryptor = new StandardPBEBigDecimalEncryptor();

    public BasicDecimalNumberEncryptor() {
        this.encryptor.setAlgorithm("PBEWithMD5AndDES");
    }

    public void setPassword(String password) {
        this.encryptor.setPassword(password);
    }

    public void setPasswordCharArray(char[] password) {
        this.encryptor.setPasswordCharArray(password);
    }

    @Override
    public BigDecimal encrypt(BigDecimal number) {
        return this.encryptor.encrypt(number);
    }

    @Override
    public BigDecimal decrypt(BigDecimal encryptedNumber) {
        return this.encryptor.decrypt(encryptedNumber);
    }
}

