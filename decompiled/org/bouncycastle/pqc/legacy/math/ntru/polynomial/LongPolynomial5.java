/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.TernaryPolynomial;
import org.bouncycastle.util.Arrays;

public class LongPolynomial5 {
    private long[] coeffs;
    private int numCoeffs;

    public LongPolynomial5(IntegerPolynomial integerPolynomial) {
        this.numCoeffs = integerPolynomial.coeffs.length;
        this.coeffs = new long[(this.numCoeffs + 4) / 5];
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < this.numCoeffs; ++i) {
            int n3 = n++;
            this.coeffs[n3] = this.coeffs[n3] | (long)integerPolynomial.coeffs[i] << n2;
            if ((n2 += 12) < 60) continue;
            n2 = 0;
        }
    }

    private LongPolynomial5(long[] lArray, int n) {
        this.coeffs = lArray;
        this.numCoeffs = n;
    }

    public LongPolynomial5 mult(TernaryPolynomial ternaryPolynomial) {
        int n;
        long l;
        long l2;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        long[][] lArray = new long[5][this.coeffs.length + (ternaryPolynomial.size() + 4) / 5 - 1];
        int[] nArray = ternaryPolynomial.getOnes();
        for (int i = 0; i != nArray.length; ++i) {
            n7 = nArray[i];
            n6 = n7 / 5;
            n5 = n7 - n6 * 5;
            for (n4 = 0; n4 < this.coeffs.length; ++n4) {
                lArray[n5][n6] = lArray[n5][n6] + this.coeffs[n4] & 0x7FF7FF7FF7FF7FFL;
                ++n6;
            }
        }
        int[] nArray2 = ternaryPolynomial.getNegOnes();
        for (n7 = 0; n7 != nArray2.length; ++n7) {
            n6 = nArray2[n7];
            n5 = n6 / 5;
            n4 = n6 - n5 * 5;
            for (int i = 0; i < this.coeffs.length; ++i) {
                lArray[n4][n5] = 0x800800800800800L + lArray[n4][n5] - this.coeffs[i] & 0x7FF7FF7FF7FF7FFL;
                ++n5;
            }
        }
        long[] lArray2 = Arrays.copyOf(lArray[0], lArray[0].length + 1);
        for (n6 = 1; n6 <= 4; ++n6) {
            n5 = n6 * 12;
            n4 = 60 - n5;
            long l3 = (1L << n4) - 1L;
            n3 = lArray[n6].length;
            for (n2 = 0; n2 < n3; ++n2) {
                l2 = lArray[n6][n2] >> n4;
                l = lArray[n6][n2] & l3;
                lArray2[n2] = lArray2[n2] + (l << n5) & 0x7FF7FF7FF7FF7FFL;
                n = n2 + 1;
                lArray2[n] = lArray2[n] + l2 & 0x7FF7FF7FF7FF7FFL;
            }
        }
        n6 = 12 * (this.numCoeffs % 5);
        for (n5 = this.coeffs.length - 1; n5 < lArray2.length; ++n5) {
            int n8;
            long l4;
            if (n5 == this.coeffs.length - 1) {
                l4 = this.numCoeffs == 5 ? 0L : lArray2[n5] >> n6;
                n8 = 0;
            } else {
                l4 = lArray2[n5];
                n8 = n5 * 5 - this.numCoeffs;
            }
            n3 = n8 / 5;
            n2 = n8 - n3 * 5;
            l2 = l4 << 12 * n2;
            l = l4 >> 12 * (5 - n2);
            lArray2[n3] = lArray2[n3] + l2 & 0x7FF7FF7FF7FF7FFL;
            n = n3 + 1;
            if (n >= this.coeffs.length) continue;
            lArray2[n] = lArray2[n] + l & 0x7FF7FF7FF7FF7FFL;
        }
        return new LongPolynomial5(lArray2, this.numCoeffs);
    }

    public IntegerPolynomial toIntegerPolynomial() {
        int[] nArray = new int[this.numCoeffs];
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < this.numCoeffs; ++i) {
            nArray[i] = (int)(this.coeffs[n] >> n2 & 0x7FFL);
            if ((n2 += 12) < 60) continue;
            n2 = 0;
            ++n;
        }
        return new IntegerPolynomial(nArray);
    }
}

