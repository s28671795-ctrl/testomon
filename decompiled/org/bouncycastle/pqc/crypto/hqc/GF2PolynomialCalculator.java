/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.hqc;

class GF2PolynomialCalculator {
    private final int VEC_N_SIZE_64;
    private final int PARAM_N;
    private final long RED_MASK;

    GF2PolynomialCalculator(int n, int n2, long l) {
        this.VEC_N_SIZE_64 = n;
        this.PARAM_N = n2;
        this.RED_MASK = l;
    }

    protected void multLongs(long[] lArray, long[] lArray2, long[] lArray3) {
        long[] lArray4 = new long[this.VEC_N_SIZE_64 << 3];
        long[] lArray5 = new long[(this.VEC_N_SIZE_64 << 1) + 1];
        this.karatsuba(lArray5, 0, lArray2, 0, lArray3, 0, this.VEC_N_SIZE_64, lArray4, 0);
        this.reduce(lArray, lArray5);
    }

    private void base_mul(long[] lArray, int n, long l, long l2) {
        long l3;
        int n2;
        long l4 = 0L;
        long l5 = 0L;
        long[] lArray2 = new long[16];
        long[] lArray3 = new long[4];
        lArray2[0] = 0L;
        lArray2[1] = l2 & 0xFFFFFFFFFFFFFFFL;
        lArray2[2] = lArray2[1] << 1;
        lArray2[3] = lArray2[2] ^ lArray2[1];
        lArray2[4] = lArray2[2] << 1;
        lArray2[5] = lArray2[4] ^ lArray2[1];
        lArray2[6] = lArray2[3] << 1;
        lArray2[7] = lArray2[6] ^ lArray2[1];
        lArray2[8] = lArray2[4] << 1;
        lArray2[9] = lArray2[8] ^ lArray2[1];
        lArray2[10] = lArray2[5] << 1;
        lArray2[11] = lArray2[10] ^ lArray2[1];
        lArray2[12] = lArray2[6] << 1;
        lArray2[13] = lArray2[12] ^ lArray2[1];
        lArray2[14] = lArray2[7] << 1;
        lArray2[15] = lArray2[14] ^ lArray2[1];
        long l6 = 0L;
        long l7 = l & 0xFL;
        for (n2 = 0; n2 < 16; ++n2) {
            l3 = l7 - (long)n2;
            l6 ^= lArray2[n2] & -(1L - ((l3 | -l3) >>> 63));
        }
        l5 = l6;
        l4 = 0L;
        for (n2 = 4; n2 < 64; n2 = (int)((byte)(n2 + 4))) {
            l6 = 0L;
            l3 = l >> n2 & 0xFL;
            for (int i = 0; i < 16; ++i) {
                long l8 = l3 - (long)i;
                l6 ^= lArray2[i] & -(1L - ((l8 | -l8) >>> 63));
            }
            l5 ^= l6 << n2;
            l4 ^= l6 >>> 64 - n2;
        }
        lArray3[0] = -(l2 >> 60 & 1L);
        lArray3[1] = -(l2 >> 61 & 1L);
        lArray3[2] = -(l2 >> 62 & 1L);
        lArray3[3] = -(l2 >> 63 & 1L);
        l5 ^= l << 60 & lArray3[0];
        l4 ^= l >>> 4 & lArray3[0];
        l5 ^= l << 61 & lArray3[1];
        l4 ^= l >>> 3 & lArray3[1];
        l5 ^= l << 62 & lArray3[2];
        l4 ^= l >>> 2 & lArray3[2];
        lArray[0 + n] = l5 ^= l << 63 & lArray3[3];
        lArray[1 + n] = l4 ^= l >>> 1 & lArray3[3];
    }

    private void karatsuba_add1(long[] lArray, int n, long[] lArray2, int n2, long[] lArray3, int n3, long[] lArray4, int n4, int n5, int n6) {
        for (int i = 0; i < n6; ++i) {
            lArray[i + n] = lArray3[i + n3] ^ lArray3[i + n5 + n3];
            lArray2[i + n2] = lArray4[i + n4] ^ lArray4[i + n5 + n4];
        }
        if (n6 < n5) {
            lArray[n6 + n] = lArray3[n6 + n3];
            lArray2[n6 + n2] = lArray4[n6 + n4];
        }
    }

    private void karatsuba_add2(long[] lArray, int n, long[] lArray2, int n2, long[] lArray3, int n3, int n4, int n5) {
        int n6;
        for (n6 = 0; n6 < 2 * n4; ++n6) {
            lArray2[n6 + n2] = lArray2[n6 + n2] ^ lArray[n6 + n];
        }
        for (n6 = 0; n6 < 2 * n5; ++n6) {
            lArray2[n6 + n2] = lArray2[n6 + n2] ^ lArray3[n6 + n3];
        }
        for (n6 = 0; n6 < 2 * n4; ++n6) {
            lArray[n6 + n4 + n] = lArray[n6 + n4 + n] ^ lArray2[n6 + n2];
        }
    }

    private void karatsuba(long[] lArray, int n, long[] lArray2, int n2, long[] lArray3, int n3, int n4, long[] lArray4, int n5) {
        if (n4 == 1) {
            this.base_mul(lArray, n, lArray2[0 + n2], lArray3[0 + n3]);
            return;
        }
        int n6 = n4 / 2;
        int n7 = (n4 + 1) / 2;
        int n8 = n5;
        int n9 = n8 + n7;
        int n10 = n9 + n7;
        int n11 = n + n7 * 2;
        int n12 = n2 + n7;
        int n13 = n3 + n7;
        this.karatsuba(lArray, n, lArray2, n2, lArray3, n3, n7, lArray4, n5 += 4 * n7);
        this.karatsuba(lArray, n11, lArray2, n12, lArray3, n13, n6, lArray4, n5);
        this.karatsuba_add1(lArray4, n8, lArray4, n9, lArray2, n2, lArray3, n3, n7, n6);
        this.karatsuba(lArray4, n10, lArray4, n8, lArray4, n9, n7, lArray4, n5);
        this.karatsuba_add2(lArray, n, lArray4, n10, lArray, n11, n7, n6);
    }

    private void reduce(long[] lArray, long[] lArray2) {
        for (int i = 0; i < this.VEC_N_SIZE_64; ++i) {
            long l = lArray2[i + this.VEC_N_SIZE_64 - 1] >>> (this.PARAM_N & 0x3F);
            long l2 = lArray2[i + this.VEC_N_SIZE_64] << (int)(64L - ((long)this.PARAM_N & 0x3FL));
            lArray[i] = lArray2[i] ^ l ^ l2;
        }
        int n = this.VEC_N_SIZE_64 - 1;
        lArray[n] = lArray[n] & this.RED_MASK;
    }

    static void addLongs(long[] lArray, long[] lArray2, long[] lArray3) {
        for (int i = 0; i < lArray2.length; ++i) {
            lArray[i] = lArray2[i] ^ lArray3[i];
        }
    }
}

