/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import java.math.BigInteger;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigIntPolynomial;

public class Resultant {
    public BigIntPolynomial rho;
    public BigInteger res;

    Resultant(BigIntPolynomial bigIntPolynomial, BigInteger bigInteger) {
        this.rho = bigIntPolynomial;
        this.res = bigInteger;
    }
}

