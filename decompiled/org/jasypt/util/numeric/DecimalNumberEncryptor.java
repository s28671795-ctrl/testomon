/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.numeric;

import java.math.BigDecimal;

public interface DecimalNumberEncryptor {
    public BigDecimal encrypt(BigDecimal var1);

    public BigDecimal decrypt(BigDecimal var1);
}

