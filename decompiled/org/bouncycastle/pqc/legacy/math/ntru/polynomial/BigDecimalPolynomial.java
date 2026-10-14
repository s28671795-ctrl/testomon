/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import java.math.BigDecimal;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigIntPolynomial;

public class BigDecimalPolynomial {
    private static final BigDecimal ZERO = new BigDecimal("0");
    private static final BigDecimal ONE_HALF = new BigDecimal("0.5");
    BigDecimal[] coeffs;

    BigDecimalPolynomial(int n) {
        this.coeffs = new BigDecimal[n];
        for (int i = 0; i < n; ++i) {
            this.coeffs[i] = ZERO;
        }
    }

    BigDecimalPolynomial(BigDecimal[] bigDecimalArray) {
        this.coeffs = bigDecimalArray;
    }

    public BigDecimalPolynomial(BigIntPolynomial bigIntPolynomial) {
        int n = bigIntPolynomial.coeffs.length;
        this.coeffs = new BigDecimal[n];
        for (int i = 0; i < n; ++i) {
            this.coeffs[i] = new BigDecimal(bigIntPolynomial.coeffs[i]);
        }
    }

    public void halve() {
        for (int i = 0; i < this.coeffs.length; ++i) {
            this.coeffs[i] = this.coeffs[i].multiply(ONE_HALF);
        }
    }

    public BigDecimalPolynomial mult(BigIntPolynomial bigIntPolynomial) {
        return this.mult(new BigDecimalPolynomial(bigIntPolynomial));
    }

    public BigDecimalPolynomial mult(BigDecimalPolynomial bigDecimalPolynomial) {
        int n = this.coeffs.length;
        if (bigDecimalPolynomial.coeffs.length != n) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        BigDecimalPolynomial bigDecimalPolynomial2 = this.multRecursive(bigDecimalPolynomial);
        if (bigDecimalPolynomial2.coeffs.length > n) {
            for (int i = n; i < bigDecimalPolynomial2.coeffs.length; ++i) {
                bigDecimalPolynomial2.coeffs[i - n] = bigDecimalPolynomial2.coeffs[i - n].add(bigDecimalPolynomial2.coeffs[i]);
            }
            bigDecimalPolynomial2.coeffs = this.copyOf(bigDecimalPolynomial2.coeffs, n);
        }
        return bigDecimalPolynomial2;
    }

    private BigDecimalPolynomial multRecursive(BigDecimalPolynomial bigDecimalPolynomial) {
        int n;
        BigDecimal[] bigDecimalArray = this.coeffs;
        BigDecimal[] bigDecimalArray2 = bigDecimalPolynomial.coeffs;
        int n2 = bigDecimalPolynomial.coeffs.length;
        if (n2 <= 1) {
            BigDecimal[] bigDecimalArray3 = (BigDecimal[])this.coeffs.clone();
            for (int i = 0; i < this.coeffs.length; ++i) {
                bigDecimalArray3[i] = bigDecimalArray3[i].multiply(bigDecimalPolynomial.coeffs[0]);
            }
            return new BigDecimalPolynomial(bigDecimalArray3);
        }
        int n3 = n2 / 2;
        BigDecimalPolynomial bigDecimalPolynomial2 = new BigDecimalPolynomial(this.copyOf(bigDecimalArray, n3));
        BigDecimalPolynomial bigDecimalPolynomial3 = new BigDecimalPolynomial(this.copyOfRange(bigDecimalArray, n3, n2));
        BigDecimalPolynomial bigDecimalPolynomial4 = new BigDecimalPolynomial(this.copyOf(bigDecimalArray2, n3));
        BigDecimalPolynomial bigDecimalPolynomial5 = new BigDecimalPolynomial(this.copyOfRange(bigDecimalArray2, n3, n2));
        BigDecimalPolynomial bigDecimalPolynomial6 = (BigDecimalPolynomial)bigDecimalPolynomial2.clone();
        bigDecimalPolynomial6.add(bigDecimalPolynomial3);
        BigDecimalPolynomial bigDecimalPolynomial7 = (BigDecimalPolynomial)bigDecimalPolynomial4.clone();
        bigDecimalPolynomial7.add(bigDecimalPolynomial5);
        BigDecimalPolynomial bigDecimalPolynomial8 = bigDecimalPolynomial2.multRecursive(bigDecimalPolynomial4);
        BigDecimalPolynomial bigDecimalPolynomial9 = bigDecimalPolynomial3.multRecursive(bigDecimalPolynomial5);
        BigDecimalPolynomial bigDecimalPolynomial10 = bigDecimalPolynomial6.multRecursive(bigDecimalPolynomial7);
        bigDecimalPolynomial10.sub(bigDecimalPolynomial8);
        bigDecimalPolynomial10.sub(bigDecimalPolynomial9);
        BigDecimalPolynomial bigDecimalPolynomial11 = new BigDecimalPolynomial(2 * n2 - 1);
        for (n = 0; n < bigDecimalPolynomial8.coeffs.length; ++n) {
            bigDecimalPolynomial11.coeffs[n] = bigDecimalPolynomial8.coeffs[n];
        }
        for (n = 0; n < bigDecimalPolynomial10.coeffs.length; ++n) {
            bigDecimalPolynomial11.coeffs[n3 + n] = bigDecimalPolynomial11.coeffs[n3 + n].add(bigDecimalPolynomial10.coeffs[n]);
        }
        for (n = 0; n < bigDecimalPolynomial9.coeffs.length; ++n) {
            bigDecimalPolynomial11.coeffs[2 * n3 + n] = bigDecimalPolynomial11.coeffs[2 * n3 + n].add(bigDecimalPolynomial9.coeffs[n]);
        }
        return bigDecimalPolynomial11;
    }

    public void add(BigDecimalPolynomial bigDecimalPolynomial) {
        int n;
        if (bigDecimalPolynomial.coeffs.length > this.coeffs.length) {
            n = this.coeffs.length;
            this.coeffs = this.copyOf(this.coeffs, bigDecimalPolynomial.coeffs.length);
            for (int i = n; i < this.coeffs.length; ++i) {
                this.coeffs[i] = ZERO;
            }
        }
        for (n = 0; n < bigDecimalPolynomial.coeffs.length; ++n) {
            this.coeffs[n] = this.coeffs[n].add(bigDecimalPolynomial.coeffs[n]);
        }
    }

    void sub(BigDecimalPolynomial bigDecimalPolynomial) {
        int n;
        if (bigDecimalPolynomial.coeffs.length > this.coeffs.length) {
            n = this.coeffs.length;
            this.coeffs = this.copyOf(this.coeffs, bigDecimalPolynomial.coeffs.length);
            for (int i = n; i < this.coeffs.length; ++i) {
                this.coeffs[i] = ZERO;
            }
        }
        for (n = 0; n < bigDecimalPolynomial.coeffs.length; ++n) {
            this.coeffs[n] = this.coeffs[n].subtract(bigDecimalPolynomial.coeffs[n]);
        }
    }

    public BigIntPolynomial round() {
        int n = this.coeffs.length;
        BigIntPolynomial bigIntPolynomial = new BigIntPolynomial(n);
        for (int i = 0; i < n; ++i) {
            bigIntPolynomial.coeffs[i] = this.coeffs[i].setScale(0, 6).toBigInteger();
        }
        return bigIntPolynomial;
    }

    public Object clone() {
        return new BigDecimalPolynomial((BigDecimal[])this.coeffs.clone());
    }

    private BigDecimal[] copyOf(BigDecimal[] bigDecimalArray, int n) {
        BigDecimal[] bigDecimalArray2 = new BigDecimal[n];
        System.arraycopy(bigDecimalArray, 0, bigDecimalArray2, 0, bigDecimalArray.length < n ? bigDecimalArray.length : n);
        return bigDecimalArray2;
    }

    private BigDecimal[] copyOfRange(BigDecimal[] bigDecimalArray, int n, int n2) {
        int n3 = n2 - n;
        BigDecimal[] bigDecimalArray2 = new BigDecimal[n2 - n];
        System.arraycopy(bigDecimalArray, n, bigDecimalArray2, 0, bigDecimalArray.length - n < n3 ? bigDecimalArray.length - n : n3);
        return bigDecimalArray2;
    }

    public BigDecimal[] getCoeffs() {
        BigDecimal[] bigDecimalArray = new BigDecimal[this.coeffs.length];
        System.arraycopy(this.coeffs, 0, bigDecimalArray, 0, this.coeffs.length);
        return bigDecimalArray;
    }
}

