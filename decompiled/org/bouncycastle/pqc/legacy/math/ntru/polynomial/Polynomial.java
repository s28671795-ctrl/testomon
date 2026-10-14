/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigIntPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;

public interface Polynomial {
    public IntegerPolynomial mult(IntegerPolynomial var1);

    public IntegerPolynomial mult(IntegerPolynomial var1, int var2);

    public IntegerPolynomial toIntegerPolynomial();

    public BigIntPolynomial mult(BigIntPolynomial var1);
}

