/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.numeric;

import java.math.BigDecimal;
import org.jasypt.encryption.pbe.StandardPBEBigDecimalEncryptor;
import org.jasypt.util.numeric.DecimalNumberEncryptor;

public final class StrongDecimalNumberEncryptor
implements DecimalNumberEncryptor {
    private final StandardPBEBigDecimalEncryptor encryptor = new StandardPBEBigDecimalEncryptor();

    public StrongDecimalNumberEncryptor() {
        this.encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
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

