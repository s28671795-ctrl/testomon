/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.numeric;

import java.math.BigInteger;

public interface IntegerNumberEncryptor {
    public BigInteger encrypt(BigInteger var1);

    public BigInteger decrypt(BigInteger var1);
}

