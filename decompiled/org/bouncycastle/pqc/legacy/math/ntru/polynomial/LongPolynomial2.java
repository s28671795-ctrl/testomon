/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.util.Arrays;

public class LongPolynomial2 {
    private long[] coeffs;
    private int numCoeffs;

    public LongPolynomial2(IntegerPolynomial integerPolynomial) {
        this.numCoeffs = integerPolynomial.coeffs.length;
        this.coeffs = new long[(this.numCoeffs + 1) / 2];
        int n = 0;
        int n2 = 0;
        while (n2 < this.numCoeffs) {
            long l;
            int n3;
            for (n3 = integerPolynomial.coeffs[n2++]; n3 < 0; n3 += 2048) {
            }
            long l2 = l = n2 < this.numCoeffs ? (long)integerPolynomial.coeffs[n2++] : 0L;
            while (l < 0L) {
                l += 2048L;
            }
            this.coeffs[n] = (long)n3 + (l << 24);
            ++n;
        }
    }

    private LongPolynomial2(long[] lArray) {
        this.coeffs = lArray;
    }

    private LongPolynomial2(int n) {
        this.coeffs = new long[n];
    }

    public LongPolynomial2 mult(LongPolynomial2 longPolynomial2) {
        int n = this.coeffs.length;
        if (longPolynomial2.coeffs.length != n || this.numCoeffs != longPolynomial2.numCoeffs) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        LongPolynomial2 longPolynomial22 = this.multRecursive(longPolynomial2);
        if (longPolynomial22.coeffs.length > n) {
            if (this.numCoeffs % 2 == 0) {
                for (int i = n; i < longPolynomial22.coeffs.length; ++i) {
                    longPolynomial22.coeffs[i - n] = longPolynomial22.coeffs[i - n] + longPolynomial22.coeffs[i] & 0x7FF0007FFL;
                }
                longPolynomial22.coeffs = Arrays.copyOf(longPolynomial22.coeffs, n);
            } else {
                for (int i = n; i < longPolynomial22.coeffs.length; ++i) {
                    longPolynomial22.coeffs[i - n] = longPolynomial22.coeffs[i - n] + (longPolynomial22.coeffs[i - 1] >> 24);
                    longPolynomial22.coeffs[i - n] = longPolynomial22.coeffs[i - n] + ((longPolynomial22.coeffs[i] & 0x7FFL) << 24);
                    int n2 = i - n;
                    longPolynomial22.coeffs[n2] = longPolynomial22.coeffs[n2] & 0x7FF0007FFL;
                }
                longPolynomial22.coeffs = Arrays.copyOf(longPolynomial22.coeffs, n);
                int n3 = longPolynomial22.coeffs.length - 1;
                longPolynomial22.coeffs[n3] = longPolynomial22.coeffs[n3] & 0x7FFL;
            }
        }
        longPolynomial22 = new LongPolynomial2(longPolynomial22.coeffs);
        longPolynomial22.numCoeffs = this.numCoeffs;
        return longPolynomial22;
    }

    public IntegerPolynomial toIntegerPolynomial() {
        int[] nArray = new int[this.numCoeffs];
        int n = 0;
        for (int i = 0; i < this.coeffs.length; ++i) {
            nArray[n++] = (int)(this.coeffs[i] & 0x7FFL);
            if (n >= this.numCoeffs) continue;
            nArray[n++] = (int)(this.coeffs[i] >> 24 & 0x7FFL);
        }
        return new IntegerPolynomial(nArray);
    }

    private LongPolynomial2 multRecursive(LongPolynomial2 longPolynomial2) {
        int n;
        long[] lArray = this.coeffs;
        long[] lArray2 = longPolynomial2.coeffs;
        int n2 = longPolynomial2.coeffs.length;
        if (n2 <= 32) {
            int n3 = 2 * n2;
            LongPolynomial2 longPolynomial22 = new LongPolynomial2(new long[n3]);
            for (int i = 0; i < n3; ++i) {
                for (int j = Math.max(0, i - n2 + 1); j <= Math.min(i, n2 - 1); ++j) {
                    long l = lArray[i - j] * lArray2[j];
                    long l2 = l & 0x7FF000000L + (l & 0x7FFL);
                    long l3 = l >>> 48 & 0x7FFL;
                    longPolynomial22.coeffs[i] = longPolynomial22.coeffs[i] + l2 & 0x7FF0007FFL;
                    longPolynomial22.coeffs[i + 1] = longPolynomial22.coeffs[i + 1] + l3 & 0x7FF0007FFL;
                }
            }
            return longPolynomial22;
        }
        int n4 = n2 / 2;
        LongPolynomial2 longPolynomial23 = new LongPolynomial2(Arrays.copyOf(lArray, n4));
        LongPolynomial2 longPolynomial24 = new LongPolynomial2(Arrays.copyOfRange(lArray, n4, n2));
        LongPolynomial2 longPolynomial25 = new LongPolynomial2(Arrays.copyOf(lArray2, n4));
        LongPolynomial2 longPolynomial26 = new LongPolynomial2(Arrays.copyOfRange(lArray2, n4, n2));
        LongPolynomial2 longPolynomial27 = (LongPolynomial2)longPolynomial23.clone();
        longPolynomial27.add(longPolynomial24);
        LongPolynomial2 longPolynomial28 = (LongPolynomial2)longPolynomial25.clone();
        longPolynomial28.add(longPolynomial26);
        LongPolynomial2 longPolynomial29 = longPolynomial23.multRecursive(longPolynomial25);
        LongPolynomial2 longPolynomial210 = longPolynomial24.multRecursive(longPolynomial26);
        LongPolynomial2 longPolynomial211 = longPolynomial27.multRecursive(longPolynomial28);
        longPolynomial211.sub(longPolynomial29);
        longPolynomial211.sub(longPolynomial210);
        LongPolynomial2 longPolynomial212 = new LongPolynomial2(2 * n2);
        for (n = 0; n < longPolynomial29.coeffs.length; ++n) {
            longPolynomial212.coeffs[n] = longPolynomial29.coeffs[n] & 0x7FF0007FFL;
        }
        for (n = 0; n < longPolynomial211.coeffs.length; ++n) {
            longPolynomial212.coeffs[n4 + n] = longPolynomial212.coeffs[n4 + n] + longPolynomial211.coeffs[n] & 0x7FF0007FFL;
        }
        for (n = 0; n < longPolynomial210.coeffs.length; ++n) {
            longPolynomial212.coeffs[2 * n4 + n] = longPolynomial212.coeffs[2 * n4 + n] + longPolynomial210.coeffs[n] & 0x7FF0007FFL;
        }
        return longPolynomial212;
    }

    private void add(LongPolynomial2 longPolynomial2) {
        if (longPolynomial2.coeffs.length > this.coeffs.length) {
            this.coeffs = Arrays.copyOf(this.coeffs, longPolynomial2.coeffs.length);
        }
        for (int i = 0; i < longPolynomial2.coeffs.length; ++i) {
            this.coeffs[i] = this.coeffs[i] + longPolynomial2.coeffs[i] & 0x7FF0007FFL;
        }
    }

    private void sub(LongPolynomial2 longPolynomial2) {
        if (longPolynomial2.coeffs.length > this.coeffs.length) {
            this.coeffs = Arrays.copyOf(this.coeffs, longPolynomial2.coeffs.length);
        }
        for (int i = 0; i < longPolynomial2.coeffs.length; ++i) {
            this.coeffs[i] = 0x800000800000L + this.coeffs[i] - longPolynomial2.coeffs[i] & 0x7FF0007FFL;
        }
    }

    public void subAnd(LongPolynomial2 longPolynomial2, int n) {
        long l = ((long)n << 24) + (long)n;
        for (int i = 0; i < longPolynomial2.coeffs.length; ++i) {
            this.coeffs[i] = 0x800000800000L + this.coeffs[i] - longPolynomial2.coeffs[i] & l;
        }
    }

    public void mult2And(int n) {
        long l = ((long)n << 24) + (long)n;
        for (int i = 0; i < this.coeffs.length; ++i) {
            this.coeffs[i] = this.coeffs[i] << 1 & l;
        }
    }

    public Object clone() {
        LongPolynomial2 longPolynomial2 = new LongPolynomial2((long[])this.coeffs.clone());
        longPolynomial2.numCoeffs = this.numCoeffs;
        return longPolynomial2;
    }

    public boolean equals(Object object) {
        if (object instanceof LongPolynomial2) {
            return Arrays.areEqual(this.coeffs, ((LongPolynomial2)object).coeffs);
        }
        return false;
    }
}

