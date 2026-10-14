/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.math.ntru.polynomial;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.BigIntPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.TernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.util.ArrayEncoder;
import org.bouncycastle.pqc.legacy.math.ntru.util.Util;
import org.bouncycastle.util.Arrays;

public class SparseTernaryPolynomial
implements TernaryPolynomial {
    private static final int BITS_PER_INDEX = 11;
    private int N;
    private int[] ones;
    private int[] negOnes;

    SparseTernaryPolynomial(int n, int[] nArray, int[] nArray2) {
        this.N = n;
        this.ones = nArray;
        this.negOnes = nArray2;
    }

    public SparseTernaryPolynomial(IntegerPolynomial integerPolynomial) {
        this(integerPolynomial.coeffs);
    }

    public SparseTernaryPolynomial(int[] nArray) {
        this.N = nArray.length;
        this.ones = new int[this.N];
        this.negOnes = new int[this.N];
        int n = 0;
        int n2 = 0;
        block5: for (int i = 0; i < this.N; ++i) {
            int n3 = nArray[i];
            switch (n3) {
                case 1: {
                    this.ones[n++] = i;
                    continue block5;
                }
                case -1: {
                    this.negOnes[n2++] = i;
                    continue block5;
                }
                case 0: {
                    continue block5;
                }
                default: {
                    throw new IllegalArgumentException("Illegal value: " + n3 + ", must be one of {-1, 0, 1}");
                }
            }
        }
        this.ones = Arrays.copyOf(this.ones, n);
        this.negOnes = Arrays.copyOf(this.negOnes, n2);
    }

    public static SparseTernaryPolynomial fromBinary(InputStream inputStream2, int n, int n2, int n3) throws IOException {
        int n4 = 2048;
        int n5 = 32 - Integer.numberOfLeadingZeros(n4 - 1);
        int n6 = (n2 * n5 + 7) / 8;
        byte[] byArray = Util.readFullLength(inputStream2, n6);
        int[] nArray = ArrayEncoder.decodeModQ(byArray, n2, n4);
        int n7 = (n3 * n5 + 7) / 8;
        byte[] byArray2 = Util.readFullLength(inputStream2, n7);
        int[] nArray2 = ArrayEncoder.decodeModQ(byArray2, n3, n4);
        return new SparseTernaryPolynomial(n, nArray, nArray2);
    }

    public static SparseTernaryPolynomial generateRandom(int n, int n2, int n3, SecureRandom secureRandom) {
        int[] nArray = Util.generateRandomTernary(n, n2, n3, secureRandom);
        return new SparseTernaryPolynomial(nArray);
    }

    @Override
    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial) {
        int n;
        int n2;
        int n3;
        int n4;
        int[] nArray = integerPolynomial.coeffs;
        if (nArray.length != this.N) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        int[] nArray2 = new int[this.N];
        for (n4 = 0; n4 != this.ones.length; ++n4) {
            n3 = this.ones[n4];
            n2 = this.N - 1 - n3;
            for (n = this.N - 1; n >= 0; --n) {
                int n5 = n;
                nArray2[n5] = nArray2[n5] + nArray[n2];
                if (--n2 >= 0) continue;
                n2 = this.N - 1;
            }
        }
        for (n4 = 0; n4 != this.negOnes.length; ++n4) {
            n3 = this.negOnes[n4];
            n2 = this.N - 1 - n3;
            for (n = this.N - 1; n >= 0; --n) {
                int n6 = n;
                nArray2[n6] = nArray2[n6] - nArray[n2];
                if (--n2 >= 0) continue;
                n2 = this.N - 1;
            }
        }
        return new IntegerPolynomial(nArray2);
    }

    @Override
    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial, int n) {
        IntegerPolynomial integerPolynomial2 = this.mult(integerPolynomial);
        integerPolynomial2.mod(n);
        return integerPolynomial2;
    }

    @Override
    public BigIntPolynomial mult(BigIntPolynomial bigIntPolynomial) {
        int n;
        int n2;
        int n3;
        int n4;
        BigInteger[] bigIntegerArray = bigIntPolynomial.coeffs;
        if (bigIntegerArray.length != this.N) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        BigInteger[] bigIntegerArray2 = new BigInteger[this.N];
        for (n4 = 0; n4 < this.N; ++n4) {
            bigIntegerArray2[n4] = BigInteger.ZERO;
        }
        for (n4 = 0; n4 != this.ones.length; ++n4) {
            n3 = this.ones[n4];
            n2 = this.N - 1 - n3;
            for (n = this.N - 1; n >= 0; --n) {
                bigIntegerArray2[n] = bigIntegerArray2[n].add(bigIntegerArray[n2]);
                if (--n2 >= 0) continue;
                n2 = this.N - 1;
            }
        }
        for (n4 = 0; n4 != this.negOnes.length; ++n4) {
            n3 = this.negOnes[n4];
            n2 = this.N - 1 - n3;
            for (n = this.N - 1; n >= 0; --n) {
                bigIntegerArray2[n] = bigIntegerArray2[n].subtract(bigIntegerArray[n2]);
                if (--n2 >= 0) continue;
                n2 = this.N - 1;
            }
        }
        return new BigIntPolynomial(bigIntegerArray2);
    }

    @Override
    public int[] getOnes() {
        return this.ones;
    }

    @Override
    public int[] getNegOnes() {
        return this.negOnes;
    }

    public byte[] toBinary() {
        int n = 2048;
        byte[] byArray = ArrayEncoder.encodeModQ(this.ones, n);
        byte[] byArray2 = ArrayEncoder.encodeModQ(this.negOnes, n);
        byte[] byArray3 = Arrays.copyOf(byArray, byArray.length + byArray2.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        return byArray3;
    }

    @Override
    public IntegerPolynomial toIntegerPolynomial() {
        int n;
        int n2;
        int[] nArray = new int[this.N];
        for (n2 = 0; n2 != this.ones.length; ++n2) {
            n = this.ones[n2];
            nArray[n] = 1;
        }
        for (n2 = 0; n2 != this.negOnes.length; ++n2) {
            n = this.negOnes[n2];
            nArray[n] = -1;
        }
        return new IntegerPolynomial(nArray);
    }

    @Override
    public int size() {
        return this.N;
    }

    @Override
    public void clear() {
        int n;
        for (n = 0; n < this.ones.length; ++n) {
            this.ones[n] = 0;
        }
        for (n = 0; n < this.negOnes.length; ++n) {
            this.negOnes[n] = 0;
        }
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.N;
        n = 31 * n + Arrays.hashCode(this.negOnes);
        n = 31 * n + Arrays.hashCode(this.ones);
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
        SparseTernaryPolynomial sparseTernaryPolynomial = (SparseTernaryPolynomial)object;
        if (this.N != sparseTernaryPolynomial.N) {
            return false;
        }
        if (!Arrays.areEqual(this.negOnes, sparseTernaryPolynomial.negOnes)) {
            return false;
        }
        return Arrays.areEqual(this.ones, sparseTernaryPolynomial.ones);
    }
}

