/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import java.security.SecureRandom;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.LongPolynomial5;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.TernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.util.Util;
import org.bouncycastle.util.Arrays;

public class DenseTernaryPolynomial
extends IntegerPolynomial
implements TernaryPolynomial {
    DenseTernaryPolynomial(int n) {
        super(n);
        this.checkTernarity();
    }

    public DenseTernaryPolynomial(IntegerPolynomial integerPolynomial) {
        this(integerPolynomial.coeffs);
    }

    public DenseTernaryPolynomial(int[] nArray) {
        super(nArray);
        this.checkTernarity();
    }

    private void checkTernarity() {
        for (int i = 0; i != this.coeffs.length; ++i) {
            int n = this.coeffs[i];
            if (n >= -1 && n <= 1) continue;
            throw new IllegalStateException("Illegal value: " + n + ", must be one of {-1, 0, 1}");
        }
    }

    public static DenseTernaryPolynomial generateRandom(int n, int n2, int n3, SecureRandom secureRandom) {
        int[] nArray = Util.generateRandomTernary(n, n2, n3, secureRandom);
        return new DenseTernaryPolynomial(nArray);
    }

    public static DenseTernaryPolynomial generateRandom(int n, SecureRandom secureRandom) {
        DenseTernaryPolynomial denseTernaryPolynomial = new DenseTernaryPolynomial(n);
        for (int i = 0; i < n; ++i) {
            denseTernaryPolynomial.coeffs[i] = secureRandom.nextInt(3) - 1;
        }
        return denseTernaryPolynomial;
    }

    @Override
    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial, int n) {
        if (n == 2048) {
            IntegerPolynomial integerPolynomial2 = (IntegerPolynomial)integerPolynomial.clone();
            integerPolynomial2.modPositive(2048);
            LongPolynomial5 longPolynomial5 = new LongPolynomial5(integerPolynomial2);
            return longPolynomial5.mult(this).toIntegerPolynomial();
        }
        return super.mult(integerPolynomial, n);
    }

    @Override
    public int[] getOnes() {
        int n = this.coeffs.length;
        int[] nArray = new int[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = this.coeffs[i];
            if (n3 != 1) continue;
            nArray[n2++] = i;
        }
        return Arrays.copyOf(nArray, n2);
    }

    @Override
    public int[] getNegOnes() {
        int n = this.coeffs.length;
        int[] nArray = new int[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = this.coeffs[i];
            if (n3 != -1) continue;
            nArray[n2++] = i;
        }
        return Arrays.copyOf(nArray, n2);
    }

    @Override
    public int size() {
        return this.coeffs.length;
    }
}

