/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigDecimalPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Constants;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.util.Arrays;

public class BigIntPolynomial {
    private static final double LOG_10_2 = Math.log10(2.0);
    BigInteger[] coeffs;

    BigIntPolynomial(int n) {
        this.coeffs = new BigInteger[n];
        for (int i = 0; i < n; ++i) {
            this.coeffs[i] = Constants.BIGINT_ZERO;
        }
    }

    BigIntPolynomial(BigInteger[] bigIntegerArray) {
        this.coeffs = bigIntegerArray;
    }

    public BigIntPolynomial(IntegerPolynomial integerPolynomial) {
        this.coeffs = new BigInteger[integerPolynomial.coeffs.length];
        for (int i = 0; i < this.coeffs.length; ++i) {
            this.coeffs[i] = BigInteger.valueOf(integerPolynomial.coeffs[i]);
        }
    }

    static BigIntPolynomial generateRandomSmall(int n, int n2, int n3) {
        int n4;
        ArrayList<BigInteger> arrayList = new ArrayList<BigInteger>();
        for (n4 = 0; n4 < n2; ++n4) {
            arrayList.add(Constants.BIGINT_ONE);
        }
        for (n4 = 0; n4 < n3; ++n4) {
            arrayList.add(BigInteger.valueOf(-1L));
        }
        while (arrayList.size() < n) {
            arrayList.add(Constants.BIGINT_ZERO);
        }
        Collections.shuffle(arrayList, CryptoServicesRegistrar.getSecureRandom());
        BigIntPolynomial bigIntPolynomial = new BigIntPolynomial(n);
        for (int i = 0; i < arrayList.size(); ++i) {
            bigIntPolynomial.coeffs[i] = (BigInteger)arrayList.get(i);
        }
        return bigIntPolynomial;
    }

    public BigIntPolynomial mult(BigIntPolynomial bigIntPolynomial) {
        int n = this.coeffs.length;
        if (bigIntPolynomial.coeffs.length != n) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        BigIntPolynomial bigIntPolynomial2 = this.multRecursive(bigIntPolynomial);
        if (bigIntPolynomial2.coeffs.length > n) {
            for (int i = n; i < bigIntPolynomial2.coeffs.length; ++i) {
                bigIntPolynomial2.coeffs[i - n] = bigIntPolynomial2.coeffs[i - n].add(bigIntPolynomial2.coeffs[i]);
            }
            bigIntPolynomial2.coeffs = Arrays.copyOf(bigIntPolynomial2.coeffs, n);
        }
        return bigIntPolynomial2;
    }

    private BigIntPolynomial multRecursive(BigIntPolynomial bigIntPolynomial) {
        int n;
        BigInteger[] bigIntegerArray = this.coeffs;
        BigInteger[] bigIntegerArray2 = bigIntPolynomial.coeffs;
        int n2 = bigIntPolynomial.coeffs.length;
        if (n2 <= 1) {
            BigInteger[] bigIntegerArray3 = Arrays.clone(this.coeffs);
            for (int i = 0; i < this.coeffs.length; ++i) {
                bigIntegerArray3[i] = bigIntegerArray3[i].multiply(bigIntPolynomial.coeffs[0]);
            }
            return new BigIntPolynomial(bigIntegerArray3);
        }
        int n3 = n2 / 2;
        BigIntPolynomial bigIntPolynomial2 = new BigIntPolynomial(Arrays.copyOf(bigIntegerArray, n3));
        BigIntPolynomial bigIntPolynomial3 = new BigIntPolynomial(Arrays.copyOfRange(bigIntegerArray, n3, n2));
        BigIntPolynomial bigIntPolynomial4 = new BigIntPolynomial(Arrays.copyOf(bigIntegerArray2, n3));
        BigIntPolynomial bigIntPolynomial5 = new BigIntPolynomial(Arrays.copyOfRange(bigIntegerArray2, n3, n2));
        BigIntPolynomial bigIntPolynomial6 = (BigIntPolynomial)bigIntPolynomial2.clone();
        bigIntPolynomial6.add(bigIntPolynomial3);
        BigIntPolynomial bigIntPolynomial7 = (BigIntPolynomial)bigIntPolynomial4.clone();
        bigIntPolynomial7.add(bigIntPolynomial5);
        BigIntPolynomial bigIntPolynomial8 = bigIntPolynomial2.multRecursive(bigIntPolynomial4);
        BigIntPolynomial bigIntPolynomial9 = bigIntPolynomial3.multRecursive(bigIntPolynomial5);
        BigIntPolynomial bigIntPolynomial10 = bigIntPolynomial6.multRecursive(bigIntPolynomial7);
        bigIntPolynomial10.sub(bigIntPolynomial8);
        bigIntPolynomial10.sub(bigIntPolynomial9);
        BigIntPolynomial bigIntPolynomial11 = new BigIntPolynomial(2 * n2 - 1);
        for (n = 0; n < bigIntPolynomial8.coeffs.length; ++n) {
            bigIntPolynomial11.coeffs[n] = bigIntPolynomial8.coeffs[n];
        }
        for (n = 0; n < bigIntPolynomial10.coeffs.length; ++n) {
            bigIntPolynomial11.coeffs[n3 + n] = bigIntPolynomial11.coeffs[n3 + n].add(bigIntPolynomial10.coeffs[n]);
        }
        for (n = 0; n < bigIntPolynomial9.coeffs.length; ++n) {
            bigIntPolynomial11.coeffs[2 * n3 + n] = bigIntPolynomial11.coeffs[2 * n3 + n].add(bigIntPolynomial9.coeffs[n]);
        }
        return bigIntPolynomial11;
    }

    void add(BigIntPolynomial bigIntPolynomial, BigInteger bigInteger) {
        this.add(bigIntPolynomial);
        this.mod(bigInteger);
    }

    public void add(BigIntPolynomial bigIntPolynomial) {
        int n;
        if (bigIntPolynomial.coeffs.length > this.coeffs.length) {
            n = this.coeffs.length;
            this.coeffs = Arrays.copyOf(this.coeffs, bigIntPolynomial.coeffs.length);
            for (int i = n; i < this.coeffs.length; ++i) {
                this.coeffs[i] = Constants.BIGINT_ZERO;
            }
        }
        for (n = 0; n < bigIntPolynomial.coeffs.length; ++n) {
            this.coeffs[n] = this.coeffs[n].add(bigIntPolynomial.coeffs[n]);
        }
    }

    public void sub(BigIntPolynomial bigIntPolynomial) {
        int n;
        if (bigIntPolynomial.coeffs.length > this.coeffs.length) {
            n = this.coeffs.length;
            this.coeffs = Arrays.copyOf(this.coeffs, bigIntPolynomial.coeffs.length);
            for (int i = n; i < this.coeffs.length; ++i) {
                this.coeffs[i] = Constants.BIGINT_ZERO;
            }
        }
        for (n = 0; n < bigIntPolynomial.coeffs.length; ++n) {
            this.coeffs[n] = this.coeffs[n].subtract(bigIntPolynomial.coeffs[n]);
        }
    }

    public void mult(BigInteger bigInteger) {
        for (int i = 0; i < this.coeffs.length; ++i) {
            this.coeffs[i] = this.coeffs[i].multiply(bigInteger);
        }
    }

    void mult(int n) {
        this.mult(BigInteger.valueOf(n));
    }

    public void div(BigInteger bigInteger) {
        BigInteger bigInteger2 = bigInteger.add(Constants.BIGINT_ONE).divide(BigInteger.valueOf(2L));
        for (int i = 0; i < this.coeffs.length; ++i) {
            this.coeffs[i] = this.coeffs[i].compareTo(Constants.BIGINT_ZERO) > 0 ? this.coeffs[i].add(bigInteger2) : this.coeffs[i].add(bigInteger2.negate());
            this.coeffs[i] = this.coeffs[i].divide(bigInteger);
        }
    }

    public BigDecimalPolynomial div(BigDecimal bigDecimal, int n) {
        BigInteger bigInteger = this.maxCoeffAbs();
        int n2 = (int)((double)bigInteger.bitLength() * LOG_10_2) + 1;
        BigDecimal bigDecimal2 = Constants.BIGDEC_ONE.divide(bigDecimal, n2 + n + 1, 6);
        BigDecimalPolynomial bigDecimalPolynomial = new BigDecimalPolynomial(this.coeffs.length);
        for (int i = 0; i < this.coeffs.length; ++i) {
            bigDecimalPolynomial.coeffs[i] = new BigDecimal(this.coeffs[i]).multiply(bigDecimal2).setScale(n, 6);
        }
        return bigDecimalPolynomial;
    }

    public int getMaxCoeffLength() {
        return (int)((double)this.maxCoeffAbs().bitLength() * LOG_10_2) + 1;
    }

    private BigInteger maxCoeffAbs() {
        BigInteger bigInteger = this.coeffs[0].abs();
        for (int i = 1; i < this.coeffs.length; ++i) {
            BigInteger bigInteger2 = this.coeffs[i].abs();
            if (bigInteger2.compareTo(bigInteger) <= 0) continue;
            bigInteger = bigInteger2;
        }
        return bigInteger;
    }

    public void mod(BigInteger bigInteger) {
        for (int i = 0; i < this.coeffs.length; ++i) {
            this.coeffs[i] = this.coeffs[i].mod(bigInteger);
        }
    }

    BigInteger sumCoeffs() {
        BigInteger bigInteger = Constants.BIGINT_ZERO;
        for (int i = 0; i < this.coeffs.length; ++i) {
            bigInteger = bigInteger.add(this.coeffs[i]);
        }
        return bigInteger;
    }

    public Object clone() {
        return new BigIntPolynomial((BigInteger[])this.coeffs.clone());
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + Arrays.hashCode(this.coeffs);
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        BigIntPolynomial bigIntPolynomial = (BigIntPolynomial)object;
        return Arrays.areEqual(this.coeffs, bigIntPolynomial.coeffs);
    }

    public BigInteger[] getCoeffs() {
        return Arrays.clone(this.coeffs);
    }
}

