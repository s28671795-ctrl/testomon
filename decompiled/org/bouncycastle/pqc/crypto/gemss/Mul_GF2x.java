/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import org.bouncycastle.pqc.crypto.gemss.Pointer;

abstract class Mul_GF2x {
    Mul_GF2x() {
    }

    public abstract void mul_gf2x(Pointer var1, Pointer var2, Pointer var3);

    public abstract void sqr_gf2x(long[] var1, long[] var2, int var3);

    public abstract void mul_gf2x_xor(Pointer var1, Pointer var2, Pointer var3);

    private static long SQR32_NO_SIMD_GF2X(long l) {
        l = (l ^ l << 16) & 0xFFFF0000FFFFL;
        l = (l ^ l << 8) & 0xFF00FF00FF00FFL;
        l = (l ^ l << 4) & 0xF0F0F0F0F0F0F0FL;
        l = (l ^ l << 2) & 0x3333333333333333L;
        return (l ^ l << 1) & 0x5555555555555555L;
    }

    private static long SQR64LOW_NO_SIMD_GF2X(long l) {
        l = (l & 0xFFFFFFFFL ^ l << 16) & 0xFFFF0000FFFFL;
        l = (l ^ l << 8) & 0xFF00FF00FF00FFL;
        l = (l ^ l << 4) & 0xF0F0F0F0F0F0F0FL;
        l = (l ^ l << 2) & 0x3333333333333333L;
        return (l ^ l << 1) & 0x5555555555555555L;
    }

    private static void SQR64_NO_SIMD_GF2X(long[] lArray, int n, long l) {
        lArray[n + 1] = Mul_GF2x.SQR32_NO_SIMD_GF2X(l >>> 32);
        lArray[n] = Mul_GF2x.SQR64LOW_NO_SIMD_GF2X(l);
    }

    private static void SQR128_NO_SIMD_GF2X(long[] lArray, int n, long[] lArray2, int n2) {
        Mul_GF2x.SQR64_NO_SIMD_GF2X(lArray, n + 2, lArray2[n2 + 1]);
        Mul_GF2x.SQR64_NO_SIMD_GF2X(lArray, n, lArray2[n2]);
    }

    private static void SQR256_NO_SIMD_GF2X(long[] lArray, int n, long[] lArray2, int n2) {
        Mul_GF2x.SQR128_NO_SIMD_GF2X(lArray, n + 4, lArray2, n2 + 2);
        Mul_GF2x.SQR128_NO_SIMD_GF2X(lArray, n, lArray2, n2);
    }

    private static long MUL32_NO_SIMD_GF2X(long l, long l2) {
        long l3 = -(l2 & 1L) & l;
        l3 ^= (-(l2 >>> 1 & 1L) & l) << 1;
        l3 ^= (-(l2 >>> 2 & 1L) & l) << 2;
        l3 ^= (-(l2 >>> 3 & 1L) & l) << 3;
        l3 ^= (-(l2 >>> 4 & 1L) & l) << 4;
        l3 ^= (-(l2 >>> 5 & 1L) & l) << 5;
        l3 ^= (-(l2 >>> 6 & 1L) & l) << 6;
        l3 ^= (-(l2 >>> 7 & 1L) & l) << 7;
        l3 ^= (-(l2 >>> 8 & 1L) & l) << 8;
        l3 ^= (-(l2 >>> 9 & 1L) & l) << 9;
        l3 ^= (-(l2 >>> 10 & 1L) & l) << 10;
        l3 ^= (-(l2 >>> 11 & 1L) & l) << 11;
        l3 ^= (-(l2 >>> 12 & 1L) & l) << 12;
        l3 ^= (-(l2 >>> 13 & 1L) & l) << 13;
        l3 ^= (-(l2 >>> 14 & 1L) & l) << 14;
        l3 ^= (-(l2 >>> 15 & 1L) & l) << 15;
        l3 ^= (-(l2 >>> 16 & 1L) & l) << 16;
        l3 ^= (-(l2 >>> 17 & 1L) & l) << 17;
        l3 ^= (-(l2 >>> 18 & 1L) & l) << 18;
        l3 ^= (-(l2 >>> 19 & 1L) & l) << 19;
        l3 ^= (-(l2 >>> 20 & 1L) & l) << 20;
        l3 ^= (-(l2 >>> 21 & 1L) & l) << 21;
        l3 ^= (-(l2 >>> 22 & 1L) & l) << 22;
        l3 ^= (-(l2 >>> 23 & 1L) & l) << 23;
        l3 ^= (-(l2 >>> 24 & 1L) & l) << 24;
        l3 ^= (-(l2 >>> 25 & 1L) & l) << 25;
        l3 ^= (-(l2 >>> 26 & 1L) & l) << 26;
        l3 ^= (-(l2 >>> 27 & 1L) & l) << 27;
        l3 ^= (-(l2 >>> 28 & 1L) & l) << 28;
        l3 ^= (-(l2 >>> 29 & 1L) & l) << 29;
        l3 ^= (-(l2 >>> 30 & 1L) & l) << 30;
        return l3 ^= (-(l2 >>> 31 & 1L) & l) << 31;
    }

    private static void MUL64_NO_SIMD_GF2X(long[] lArray, int n, long l, long l2) {
        long l3 = -(l2 & 1L) & l;
        long l4 = -(l2 >>> 63) & l;
        l3 ^= l4 << 63;
        long l5 = l4 >>> 1;
        l4 = -(l2 >>> 1 & 1L) & l;
        l3 ^= l4 << 1;
        l5 ^= l4 >>> 63;
        l4 = -(l2 >>> 2 & 1L) & l;
        l3 ^= l4 << 2;
        l5 ^= l4 >>> 62;
        l4 = -(l2 >>> 3 & 1L) & l;
        l3 ^= l4 << 3;
        l5 ^= l4 >>> 61;
        l4 = -(l2 >>> 4 & 1L) & l;
        l3 ^= l4 << 4;
        l5 ^= l4 >>> 60;
        l4 = -(l2 >>> 5 & 1L) & l;
        l3 ^= l4 << 5;
        l5 ^= l4 >>> 59;
        l4 = -(l2 >>> 6 & 1L) & l;
        l3 ^= l4 << 6;
        l5 ^= l4 >>> 58;
        l4 = -(l2 >>> 7 & 1L) & l;
        l3 ^= l4 << 7;
        l5 ^= l4 >>> 57;
        l4 = -(l2 >>> 8 & 1L) & l;
        l3 ^= l4 << 8;
        l5 ^= l4 >>> 56;
        l4 = -(l2 >>> 9 & 1L) & l;
        l3 ^= l4 << 9;
        l5 ^= l4 >>> 55;
        l4 = -(l2 >>> 10 & 1L) & l;
        l3 ^= l4 << 10;
        l5 ^= l4 >>> 54;
        l4 = -(l2 >>> 11 & 1L) & l;
        l3 ^= l4 << 11;
        l5 ^= l4 >>> 53;
        l4 = -(l2 >>> 12 & 1L) & l;
        l3 ^= l4 << 12;
        l5 ^= l4 >>> 52;
        l4 = -(l2 >>> 13 & 1L) & l;
        l3 ^= l4 << 13;
        l5 ^= l4 >>> 51;
        l4 = -(l2 >>> 14 & 1L) & l;
        l3 ^= l4 << 14;
        l5 ^= l4 >>> 50;
        l4 = -(l2 >>> 15 & 1L) & l;
        l3 ^= l4 << 15;
        l5 ^= l4 >>> 49;
        l4 = -(l2 >>> 16 & 1L) & l;
        l3 ^= l4 << 16;
        l5 ^= l4 >>> 48;
        l4 = -(l2 >>> 17 & 1L) & l;
        l3 ^= l4 << 17;
        l5 ^= l4 >>> 47;
        l4 = -(l2 >>> 18 & 1L) & l;
        l3 ^= l4 << 18;
        l5 ^= l4 >>> 46;
        l4 = -(l2 >>> 19 & 1L) & l;
        l3 ^= l4 << 19;
        l5 ^= l4 >>> 45;
        l4 = -(l2 >>> 20 & 1L) & l;
        l3 ^= l4 << 20;
        l5 ^= l4 >>> 44;
        l4 = -(l2 >>> 21 & 1L) & l;
        l3 ^= l4 << 21;
        l5 ^= l4 >>> 43;
        l4 = -(l2 >>> 22 & 1L) & l;
        l3 ^= l4 << 22;
        l5 ^= l4 >>> 42;
        l4 = -(l2 >>> 23 & 1L) & l;
        l3 ^= l4 << 23;
        l5 ^= l4 >>> 41;
        l4 = -(l2 >>> 24 & 1L) & l;
        l3 ^= l4 << 24;
        l5 ^= l4 >>> 40;
        l4 = -(l2 >>> 25 & 1L) & l;
        l3 ^= l4 << 25;
        l5 ^= l4 >>> 39;
        l4 = -(l2 >>> 26 & 1L) & l;
        l3 ^= l4 << 26;
        l5 ^= l4 >>> 38;
        l4 = -(l2 >>> 27 & 1L) & l;
        l3 ^= l4 << 27;
        l5 ^= l4 >>> 37;
        l4 = -(l2 >>> 28 & 1L) & l;
        l3 ^= l4 << 28;
        l5 ^= l4 >>> 36;
        l4 = -(l2 >>> 29 & 1L) & l;
        l3 ^= l4 << 29;
        l5 ^= l4 >>> 35;
        l4 = -(l2 >>> 30 & 1L) & l;
        l3 ^= l4 << 30;
        l5 ^= l4 >>> 34;
        l4 = -(l2 >>> 31 & 1L) & l;
        l3 ^= l4 << 31;
        l5 ^= l4 >>> 33;
        l4 = -(l2 >>> 32 & 1L) & l;
        l3 ^= l4 << 32;
        l5 ^= l4 >>> 32;
        l4 = -(l2 >>> 33 & 1L) & l;
        l3 ^= l4 << 33;
        l5 ^= l4 >>> 31;
        l4 = -(l2 >>> 34 & 1L) & l;
        l3 ^= l4 << 34;
        l5 ^= l4 >>> 30;
        l4 = -(l2 >>> 35 & 1L) & l;
        l3 ^= l4 << 35;
        l5 ^= l4 >>> 29;
        l4 = -(l2 >>> 36 & 1L) & l;
        l3 ^= l4 << 36;
        l5 ^= l4 >>> 28;
        l4 = -(l2 >>> 37 & 1L) & l;
        l3 ^= l4 << 37;
        l5 ^= l4 >>> 27;
        l4 = -(l2 >>> 38 & 1L) & l;
        l3 ^= l4 << 38;
        l5 ^= l4 >>> 26;
        l4 = -(l2 >>> 39 & 1L) & l;
        l3 ^= l4 << 39;
        l5 ^= l4 >>> 25;
        l4 = -(l2 >>> 40 & 1L) & l;
        l3 ^= l4 << 40;
        l5 ^= l4 >>> 24;
        l4 = -(l2 >>> 41 & 1L) & l;
        l3 ^= l4 << 41;
        l5 ^= l4 >>> 23;
        l4 = -(l2 >>> 42 & 1L) & l;
        l3 ^= l4 << 42;
        l5 ^= l4 >>> 22;
        l4 = -(l2 >>> 43 & 1L) & l;
        l3 ^= l4 << 43;
        l5 ^= l4 >>> 21;
        l4 = -(l2 >>> 44 & 1L) & l;
        l3 ^= l4 << 44;
        l5 ^= l4 >>> 20;
        l4 = -(l2 >>> 45 & 1L) & l;
        l3 ^= l4 << 45;
        l5 ^= l4 >>> 19;
        l4 = -(l2 >>> 46 & 1L) & l;
        l3 ^= l4 << 46;
        l5 ^= l4 >>> 18;
        l4 = -(l2 >>> 47 & 1L) & l;
        l3 ^= l4 << 47;
        l5 ^= l4 >>> 17;
        l4 = -(l2 >>> 48 & 1L) & l;
        l3 ^= l4 << 48;
        l5 ^= l4 >>> 16;
        l4 = -(l2 >>> 49 & 1L) & l;
        l3 ^= l4 << 49;
        l5 ^= l4 >>> 15;
        l4 = -(l2 >>> 50 & 1L) & l;
        l3 ^= l4 << 50;
        l5 ^= l4 >>> 14;
        l4 = -(l2 >>> 51 & 1L) & l;
        l3 ^= l4 << 51;
        l5 ^= l4 >>> 13;
        l4 = -(l2 >>> 52 & 1L) & l;
        l3 ^= l4 << 52;
        l5 ^= l4 >>> 12;
        l4 = -(l2 >>> 53 & 1L) & l;
        l3 ^= l4 << 53;
        l5 ^= l4 >>> 11;
        l4 = -(l2 >>> 54 & 1L) & l;
        l3 ^= l4 << 54;
        l5 ^= l4 >>> 10;
        l4 = -(l2 >>> 55 & 1L) & l;
        l3 ^= l4 << 55;
        l5 ^= l4 >>> 9;
        l4 = -(l2 >>> 56 & 1L) & l;
        l3 ^= l4 << 56;
        l5 ^= l4 >>> 8;
        l4 = -(l2 >>> 57 & 1L) & l;
        l3 ^= l4 << 57;
        l5 ^= l4 >>> 7;
        l4 = -(l2 >>> 58 & 1L) & l;
        l3 ^= l4 << 58;
        l5 ^= l4 >>> 6;
        l4 = -(l2 >>> 59 & 1L) & l;
        l3 ^= l4 << 59;
        l5 ^= l4 >>> 5;
        l4 = -(l2 >>> 60 & 1L) & l;
        l3 ^= l4 << 60;
        l5 ^= l4 >>> 4;
        l4 = -(l2 >>> 61 & 1L) & l;
        l3 ^= l4 << 61;
        l5 ^= l4 >>> 3;
        l4 = -(l2 >>> 62 & 1L) & l;
        lArray[n] = l3 ^ l4 << 62;
        lArray[n + 1] = l5 ^ l4 >>> 2;
    }

    private static void MUL64_NO_SIMD_GF2X_XOR(long[] lArray, int n, long l, long l2) {
        long l3 = -(l2 & 1L) & l;
        long l4 = -(l2 >>> 63) & l;
        l3 ^= l4 << 63;
        long l5 = l4 >>> 1;
        l4 = -(l2 >>> 1 & 1L) & l;
        l3 ^= l4 << 1;
        l5 ^= l4 >>> 63;
        l4 = -(l2 >>> 2 & 1L) & l;
        l3 ^= l4 << 2;
        l5 ^= l4 >>> 62;
        l4 = -(l2 >>> 3 & 1L) & l;
        l3 ^= l4 << 3;
        l5 ^= l4 >>> 61;
        l4 = -(l2 >>> 4 & 1L) & l;
        l3 ^= l4 << 4;
        l5 ^= l4 >>> 60;
        l4 = -(l2 >>> 5 & 1L) & l;
        l3 ^= l4 << 5;
        l5 ^= l4 >>> 59;
        l4 = -(l2 >>> 6 & 1L) & l;
        l3 ^= l4 << 6;
        l5 ^= l4 >>> 58;
        l4 = -(l2 >>> 7 & 1L) & l;
        l3 ^= l4 << 7;
        l5 ^= l4 >>> 57;
        l4 = -(l2 >>> 8 & 1L) & l;
        l3 ^= l4 << 8;
        l5 ^= l4 >>> 56;
        l4 = -(l2 >>> 9 & 1L) & l;
        l3 ^= l4 << 9;
        l5 ^= l4 >>> 55;
        l4 = -(l2 >>> 10 & 1L) & l;
        l3 ^= l4 << 10;
        l5 ^= l4 >>> 54;
        l4 = -(l2 >>> 11 & 1L) & l;
        l3 ^= l4 << 11;
        l5 ^= l4 >>> 53;
        l4 = -(l2 >>> 12 & 1L) & l;
        l3 ^= l4 << 12;
        l5 ^= l4 >>> 52;
        l4 = -(l2 >>> 13 & 1L) & l;
        l3 ^= l4 << 13;
        l5 ^= l4 >>> 51;
        l4 = -(l2 >>> 14 & 1L) & l;
        l3 ^= l4 << 14;
        l5 ^= l4 >>> 50;
        l4 = -(l2 >>> 15 & 1L) & l;
        l3 ^= l4 << 15;
        l5 ^= l4 >>> 49;
        l4 = -(l2 >>> 16 & 1L) & l;
        l3 ^= l4 << 16;
        l5 ^= l4 >>> 48;
        l4 = -(l2 >>> 17 & 1L) & l;
        l3 ^= l4 << 17;
        l5 ^= l4 >>> 47;
        l4 = -(l2 >>> 18 & 1L) & l;
        l3 ^= l4 << 18;
        l5 ^= l4 >>> 46;
        l4 = -(l2 >>> 19 & 1L) & l;
        l3 ^= l4 << 19;
        l5 ^= l4 >>> 45;
        l4 = -(l2 >>> 20 & 1L) & l;
        l3 ^= l4 << 20;
        l5 ^= l4 >>> 44;
        l4 = -(l2 >>> 21 & 1L) & l;
        l3 ^= l4 << 21;
        l5 ^= l4 >>> 43;
        l4 = -(l2 >>> 22 & 1L) & l;
        l3 ^= l4 << 22;
        l5 ^= l4 >>> 42;
        l4 = -(l2 >>> 23 & 1L) & l;
        l3 ^= l4 << 23;
        l5 ^= l4 >>> 41;
        l4 = -(l2 >>> 24 & 1L) & l;
        l3 ^= l4 << 24;
        l5 ^= l4 >>> 40;
        l4 = -(l2 >>> 25 & 1L) & l;
        l3 ^= l4 << 25;
        l5 ^= l4 >>> 39;
        l4 = -(l2 >>> 26 & 1L) & l;
        l3 ^= l4 << 26;
        l5 ^= l4 >>> 38;
        l4 = -(l2 >>> 27 & 1L) & l;
        l3 ^= l4 << 27;
        l5 ^= l4 >>> 37;
        l4 = -(l2 >>> 28 & 1L) & l;
        l3 ^= l4 << 28;
        l5 ^= l4 >>> 36;
        l4 = -(l2 >>> 29 & 1L) & l;
        l3 ^= l4 << 29;
        l5 ^= l4 >>> 35;
        l4 = -(l2 >>> 30 & 1L) & l;
        l3 ^= l4 << 30;
        l5 ^= l4 >>> 34;
        l4 = -(l2 >>> 31 & 1L) & l;
        l3 ^= l4 << 31;
        l5 ^= l4 >>> 33;
        l4 = -(l2 >>> 32 & 1L) & l;
        l3 ^= l4 << 32;
        l5 ^= l4 >>> 32;
        l4 = -(l2 >>> 33 & 1L) & l;
        l3 ^= l4 << 33;
        l5 ^= l4 >>> 31;
        l4 = -(l2 >>> 34 & 1L) & l;
        l3 ^= l4 << 34;
        l5 ^= l4 >>> 30;
        l4 = -(l2 >>> 35 & 1L) & l;
        l3 ^= l4 << 35;
        l5 ^= l4 >>> 29;
        l4 = -(l2 >>> 36 & 1L) & l;
        l3 ^= l4 << 36;
        l5 ^= l4 >>> 28;
        l4 = -(l2 >>> 37 & 1L) & l;
        l3 ^= l4 << 37;
        l5 ^= l4 >>> 27;
        l4 = -(l2 >>> 38 & 1L) & l;
        l3 ^= l4 << 38;
        l5 ^= l4 >>> 26;
        l4 = -(l2 >>> 39 & 1L) & l;
        l3 ^= l4 << 39;
        l5 ^= l4 >>> 25;
        l4 = -(l2 >>> 40 & 1L) & l;
        l3 ^= l4 << 40;
        l5 ^= l4 >>> 24;
        l4 = -(l2 >>> 41 & 1L) & l;
        l3 ^= l4 << 41;
        l5 ^= l4 >>> 23;
        l4 = -(l2 >>> 42 & 1L) & l;
        l3 ^= l4 << 42;
        l5 ^= l4 >>> 22;
        l4 = -(l2 >>> 43 & 1L) & l;
        l3 ^= l4 << 43;
        l5 ^= l4 >>> 21;
        l4 = -(l2 >>> 44 & 1L) & l;
        l3 ^= l4 << 44;
        l5 ^= l4 >>> 20;
        l4 = -(l2 >>> 45 & 1L) & l;
        l3 ^= l4 << 45;
        l5 ^= l4 >>> 19;
        l4 = -(l2 >>> 46 & 1L) & l;
        l3 ^= l4 << 46;
        l5 ^= l4 >>> 18;
        l4 = -(l2 >>> 47 & 1L) & l;
        l3 ^= l4 << 47;
        l5 ^= l4 >>> 17;
        l4 = -(l2 >>> 48 & 1L) & l;
        l3 ^= l4 << 48;
        l5 ^= l4 >>> 16;
        l4 = -(l2 >>> 49 & 1L) & l;
        l3 ^= l4 << 49;
        l5 ^= l4 >>> 15;
        l4 = -(l2 >>> 50 & 1L) & l;
        l3 ^= l4 << 50;
        l5 ^= l4 >>> 14;
        l4 = -(l2 >>> 51 & 1L) & l;
        l3 ^= l4 << 51;
        l5 ^= l4 >>> 13;
        l4 = -(l2 >>> 52 & 1L) & l;
        l3 ^= l4 << 52;
        l5 ^= l4 >>> 12;
        l4 = -(l2 >>> 53 & 1L) & l;
        l3 ^= l4 << 53;
        l5 ^= l4 >>> 11;
        l4 = -(l2 >>> 54 & 1L) & l;
        l3 ^= l4 << 54;
        l5 ^= l4 >>> 10;
        l4 = -(l2 >>> 55 & 1L) & l;
        l3 ^= l4 << 55;
        l5 ^= l4 >>> 9;
        l4 = -(l2 >>> 56 & 1L) & l;
        l3 ^= l4 << 56;
        l5 ^= l4 >>> 8;
        l4 = -(l2 >>> 57 & 1L) & l;
        l3 ^= l4 << 57;
        l5 ^= l4 >>> 7;
        l4 = -(l2 >>> 58 & 1L) & l;
        l3 ^= l4 << 58;
        l5 ^= l4 >>> 6;
        l4 = -(l2 >>> 59 & 1L) & l;
        l3 ^= l4 << 59;
        l5 ^= l4 >>> 5;
        l4 = -(l2 >>> 60 & 1L) & l;
        l3 ^= l4 << 60;
        l5 ^= l4 >>> 4;
        l4 = -(l2 >>> 61 & 1L) & l;
        l3 ^= l4 << 61;
        l5 ^= l4 >>> 3;
        l4 = -(l2 >>> 62 & 1L) & l;
        int n2 = n;
        lArray[n2] = lArray[n2] ^ (l3 ^ l4 << 62);
        int n3 = n + 1;
        lArray[n3] = lArray[n3] ^ (l5 ^ l4 >>> 2);
    }

    private static void mul128_no_simd_gf2x(long[] lArray, int n, long[] lArray2, int n2, long[] lArray3, int n3) {
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n, lArray2[n2], lArray3[n3]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n + 2, lArray2[n2 + 1], lArray3[n3 + 1]);
        int n4 = n + 2;
        lArray[n4] = lArray[n4] ^ lArray[n + 1];
        lArray[n + 1] = lArray[n] ^ lArray[n + 2];
        int n5 = n + 2;
        lArray[n5] = lArray[n5] ^ lArray[n + 3];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 1, lArray2[n2] ^ lArray2[n2 + 1], lArray3[n3] ^ lArray3[n3 + 1]);
    }

    private static void mul128_no_simd_gf2x(long[] lArray, int n, long l, long l2, long l3, long l4) {
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n, l, l3);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n + 2, l2, l4);
        int n2 = n + 2;
        lArray[n2] = lArray[n2] ^ lArray[n + 1];
        lArray[n + 1] = lArray[n] ^ lArray[n + 2];
        int n3 = n + 2;
        lArray[n3] = lArray[n3] ^ lArray[n + 3];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 1, l ^ l2, l3 ^ l4);
    }

    private static void mul128_no_simd_gf2x_xor(long[] lArray, int n, long l, long l2, long l3, long l4, long[] lArray2) {
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray2, 0, l, l3);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray2, 2, l2, l4);
        int n2 = n;
        lArray[n2] = lArray[n2] ^ lArray2[0];
        lArray2[2] = lArray2[2] ^ lArray2[1];
        int n3 = n + 1;
        lArray[n3] = lArray[n3] ^ (lArray2[0] ^ lArray2[2]);
        int n4 = n + 2;
        lArray[n4] = lArray[n4] ^ (lArray2[2] ^ lArray2[3]);
        int n5 = n + 3;
        lArray[n5] = lArray[n5] ^ lArray2[3];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 1, l ^ l2, l3 ^ l4);
    }

    public static void mul192_no_simd_gf2x(long[] lArray, int n, long[] lArray2, int n2, long[] lArray3, int n3) {
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n, lArray2[n2], lArray3[n3]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n + 4, lArray2[n2 + 2], lArray3[n3 + 2]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n + 2, lArray2[n2 + 1], lArray3[n3 + 1]);
        int n4 = n + 1;
        lArray[n4] = lArray[n4] ^ lArray[n + 2];
        int n5 = n + 3;
        lArray[n5] = lArray[n5] ^ lArray[n + 4];
        lArray[n + 4] = lArray[n + 3] ^ lArray[n + 5];
        lArray[n + 2] = lArray[n + 3] ^ lArray[n + 1] ^ lArray[n];
        lArray[n + 3] = lArray[n + 1] ^ lArray[n + 4];
        int n6 = n + 1;
        lArray[n6] = lArray[n6] ^ lArray[n];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 1, lArray2[n2] ^ lArray2[n2 + 1], lArray3[n3] ^ lArray3[n3 + 1]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 3, lArray2[n2 + 1] ^ lArray2[n2 + 2], lArray3[n3 + 1] ^ lArray3[n3 + 2]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 2, lArray2[n2] ^ lArray2[n2 + 2], lArray3[n3] ^ lArray3[n3 + 2]);
    }

    public static void mul192_no_simd_gf2x_xor(long[] lArray, int n, long[] lArray2, int n2, long[] lArray3, int n3, long[] lArray4) {
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 0, lArray2[n2], lArray3[n3]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 4, lArray2[n2 + 2], lArray3[n3 + 2]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 2, lArray2[n2 + 1], lArray3[n3 + 1]);
        int n4 = n;
        lArray[n4] = lArray[n4] ^ lArray4[0];
        lArray4[1] = lArray4[1] ^ lArray4[2];
        lArray4[3] = lArray4[3] ^ lArray4[4];
        lArray4[4] = lArray4[3] ^ lArray4[5];
        lArray4[0] = lArray4[0] ^ lArray4[1];
        int n5 = n + 1;
        lArray[n5] = lArray[n5] ^ lArray4[0];
        int n6 = n + 2;
        lArray[n6] = lArray[n6] ^ (lArray4[3] ^ lArray4[0]);
        int n7 = n + 3;
        lArray[n7] = lArray[n7] ^ (lArray4[1] ^ lArray4[4]);
        int n8 = n + 4;
        lArray[n8] = lArray[n8] ^ lArray4[4];
        int n9 = n + 5;
        lArray[n9] = lArray[n9] ^ lArray4[5];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 1, lArray2[n2] ^ lArray2[n2 + 1], lArray3[n3] ^ lArray3[n3 + 1]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 3, lArray2[n2 + 1] ^ lArray2[n2 + 2], lArray3[n3 + 1] ^ lArray3[n3 + 2]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 2, lArray2[n2] ^ lArray2[n2 + 2], lArray3[n3] ^ lArray3[n3 + 2]);
    }

    private static void mul288_no_simd_gf2x(long[] lArray, int n, long[] lArray2, int n2, long[] lArray3, int n3, long[] lArray4) {
        Mul_GF2x.mul128_no_simd_gf2x(lArray, n, lArray2[n2], lArray2[n2 + 1], lArray3[n3], lArray3[n3 + 1]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n + 4, lArray2[n2 + 2], lArray3[n3 + 2]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, n + 7, lArray2[n2 + 3], lArray3[n3 + 3]);
        int n4 = n + 7;
        lArray[n4] = lArray[n4] ^ lArray[n + 5];
        int n5 = n + 8;
        lArray[n5] = lArray[n5] ^ Mul_GF2x.MUL32_NO_SIMD_GF2X(lArray2[n2 + 4], lArray3[n3 + 4]);
        lArray[n + 5] = lArray[n + 7] ^ lArray[n + 4];
        int n6 = n + 7;
        lArray[n6] = lArray[n6] ^ lArray[n + 8];
        lArray[n + 6] = lArray[n + 7] ^ lArray[n + 4];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 5, lArray2[n2 + 2] ^ lArray2[n2 + 3], lArray3[n3 + 2] ^ lArray3[n3 + 3]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 7, lArray2[n2 + 3] ^ lArray2[n2 + 4], lArray3[n3 + 3] ^ lArray3[n3 + 4]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 6, lArray2[n2 + 2] ^ lArray2[n2 + 4], lArray3[n3 + 2] ^ lArray3[n3 + 4]);
        int n7 = n + 4;
        lArray[n7] = lArray[n7] ^ lArray[n + 2];
        int n8 = n + 5;
        lArray[n8] = lArray[n8] ^ lArray[n + 3];
        long l = lArray2[n2] ^ lArray2[n2 + 2];
        long l2 = lArray2[n2 + 1] ^ lArray2[n2 + 3];
        long l3 = lArray3[n3] ^ lArray3[n3 + 2];
        long l4 = lArray3[n3 + 1] ^ lArray3[n3 + 3];
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 0, l, l3);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 2, l2, l4);
        lArray4[2] = lArray4[2] ^ lArray4[1];
        lArray4[3] = lArray4[3] ^ Mul_GF2x.MUL32_NO_SIMD_GF2X(lArray2[n2 + 4], lArray3[n3 + 4]);
        lArray[n + 2] = lArray[n + 4] ^ lArray[n] ^ lArray4[0];
        lArray[n + 3] = lArray[n + 5] ^ lArray[n + 1] ^ lArray4[2] ^ lArray4[0];
        lArray4[2] = lArray4[2] ^ lArray4[3];
        int n9 = n + 4;
        lArray[n9] = lArray[n9] ^ (lArray[n + 6] ^ lArray4[2] ^ lArray4[0]);
        int n10 = n + 5;
        lArray[n10] = lArray[n10] ^ (lArray[n + 7] ^ lArray4[2]);
        int n11 = n + 6;
        lArray[n11] = lArray[n11] ^ (lArray[n + 8] ^ lArray4[3]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 3, l ^ l2, l3 ^ l4);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 5, l2 ^ lArray2[n2 + 4], l4 ^ lArray3[n3 + 4]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 4, l ^ lArray2[n2 + 4], l3 ^ lArray3[n3 + 4]);
    }

    private static void mul288_no_simd_gf2x_xor(long[] lArray, int n, long[] lArray2, int n2, long[] lArray3, int n3, long[] lArray4) {
        Mul_GF2x.mul128_no_simd_gf2x(lArray4, 0, lArray2[n2], lArray2[n2 + 1], lArray3[n3], lArray3[n3 + 1]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 4, lArray2[n2 + 2], lArray3[n3 + 2]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 7, lArray2[n2 + 3], lArray3[n3 + 3]);
        lArray4[7] = lArray4[7] ^ lArray4[5];
        lArray4[8] = lArray4[8] ^ Mul_GF2x.MUL32_NO_SIMD_GF2X(lArray2[n2 + 4], lArray3[n3 + 4]);
        lArray4[5] = lArray4[7] ^ lArray4[4];
        lArray4[7] = lArray4[7] ^ lArray4[8];
        lArray4[6] = lArray4[7] ^ lArray4[4];
        lArray4[4] = lArray4[4] ^ lArray4[2];
        lArray4[5] = lArray4[5] ^ lArray4[3];
        int n4 = n;
        lArray[n4] = lArray[n4] ^ lArray4[0];
        int n5 = n + 1;
        lArray[n5] = lArray[n5] ^ lArray4[1];
        int n6 = n + 2;
        lArray[n6] = lArray[n6] ^ (lArray4[4] ^ lArray4[0]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray4, 5, lArray2[n2 + 2] ^ lArray2[n2 + 3], lArray3[n3 + 2] ^ lArray3[n3 + 3]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray4, 7, lArray2[n2 + 3] ^ lArray2[n2 + 4], lArray3[n3 + 3] ^ lArray3[n3 + 4]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray4, 6, lArray2[n2 + 2] ^ lArray2[n2 + 4], lArray3[n3 + 2] ^ lArray3[n3 + 4]);
        int n7 = n + 3;
        lArray[n7] = lArray[n7] ^ (lArray4[5] ^ lArray4[1]);
        int n8 = n + 4;
        lArray[n8] = lArray[n8] ^ (lArray4[4] ^ lArray4[6]);
        int n9 = n + 5;
        lArray[n9] = lArray[n9] ^ (lArray4[5] ^ lArray4[7]);
        int n10 = n + 6;
        lArray[n10] = lArray[n10] ^ (lArray4[6] ^ lArray4[8]);
        int n11 = n + 7;
        lArray[n11] = lArray[n11] ^ lArray4[7];
        int n12 = n + 8;
        lArray[n12] = lArray[n12] ^ lArray4[8];
        long l = lArray2[n2] ^ lArray2[n2 + 2];
        long l2 = lArray2[n2 + 1] ^ lArray2[n2 + 3];
        long l3 = lArray3[n3] ^ lArray3[n3 + 2];
        long l4 = lArray3[n3 + 1] ^ lArray3[n3 + 3];
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 0, l, l3);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 2, l2, l4);
        lArray4[2] = lArray4[2] ^ lArray4[1];
        lArray4[3] = lArray4[3] ^ Mul_GF2x.MUL32_NO_SIMD_GF2X(lArray2[n2 + 4], lArray3[n3 + 4]);
        int n13 = n + 2;
        lArray[n13] = lArray[n13] ^ lArray4[0];
        int n14 = n + 3;
        lArray[n14] = lArray[n14] ^ (lArray4[2] ^ lArray4[0]);
        lArray4[2] = lArray4[2] ^ lArray4[3];
        int n15 = n + 4;
        lArray[n15] = lArray[n15] ^ (lArray4[2] ^ lArray4[0]);
        int n16 = n + 5;
        lArray[n16] = lArray[n16] ^ lArray4[2];
        int n17 = n + 6;
        lArray[n17] = lArray[n17] ^ lArray4[3];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 3, l ^ l2, l3 ^ l4);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 5, l2 ^ lArray2[n2 + 4], l4 ^ lArray3[n3 + 4]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, n + 4, l ^ lArray2[n2 + 4], l3 ^ lArray3[n3 + 4]);
    }

    private static void mul384_no_simd_gf2x(long[] lArray, long[] lArray2, int n, long[] lArray3, int n2, long[] lArray4) {
        Mul_GF2x.mul192_no_simd_gf2x(lArray, 0, lArray2, n, lArray3, n2);
        Mul_GF2x.mul192_no_simd_gf2x(lArray, 6, lArray2, n + 3, lArray3, n2 + 3);
        long l = lArray2[n] ^ lArray2[n + 3];
        long l2 = lArray2[n + 1] ^ lArray2[n + 4];
        long l3 = lArray2[n + 2] ^ lArray2[n + 5];
        long l4 = lArray3[n2] ^ lArray3[n2 + 3];
        long l5 = lArray3[n2 + 1] ^ lArray3[n2 + 4];
        long l6 = lArray3[n2 + 2] ^ lArray3[n2 + 5];
        lArray[6] = lArray[6] ^ lArray[3];
        lArray[7] = lArray[7] ^ lArray[4];
        lArray[8] = lArray[8] ^ lArray[5];
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 0, l, l4);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 4, l3, l6);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 2, l2, l5);
        lArray[3] = lArray[6] ^ lArray[0] ^ lArray4[0];
        lArray4[1] = lArray4[1] ^ lArray4[2];
        lArray4[0] = lArray4[0] ^ lArray4[1];
        lArray4[3] = lArray4[3] ^ lArray4[4];
        lArray4[4] = lArray4[3] ^ lArray4[5];
        lArray[5] = lArray[8] ^ lArray[2] ^ lArray4[3] ^ lArray4[0];
        lArray[6] = lArray[6] ^ (lArray[9] ^ lArray4[1] ^ lArray4[4]);
        lArray[4] = lArray[7] ^ lArray[1] ^ lArray4[0];
        lArray[7] = lArray[7] ^ (lArray[10] ^ lArray4[4]);
        lArray[8] = lArray[8] ^ (lArray[11] ^ lArray4[5]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, 4, l ^ l2, l4 ^ l5);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, 6, l2 ^ l3, l5 ^ l6);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, 5, l ^ l3, l4 ^ l6);
    }

    private static void mul384_no_simd_gf2x_xor(long[] lArray, long[] lArray2, int n, long[] lArray3, int n2, long[] lArray4) {
        Mul_GF2x.mul192_no_simd_gf2x(lArray4, 0, lArray2, n, lArray3, n2);
        Mul_GF2x.mul192_no_simd_gf2x(lArray4, 6, lArray2, n + 3, lArray3, n2 + 3);
        long l = lArray2[n] ^ lArray2[n + 3];
        long l2 = lArray2[n + 1] ^ lArray2[n + 4];
        long l3 = lArray2[n + 2] ^ lArray2[n + 5];
        long l4 = lArray3[n2] ^ lArray3[n2 + 3];
        long l5 = lArray3[n2 + 1] ^ lArray3[n2 + 4];
        long l6 = lArray3[n2 + 2] ^ lArray3[n2 + 5];
        lArray4[6] = lArray4[6] ^ lArray4[3];
        lArray4[7] = lArray4[7] ^ lArray4[4];
        lArray4[8] = lArray4[8] ^ lArray4[5];
        lArray[0] = lArray[0] ^ lArray4[0];
        lArray[1] = lArray[1] ^ lArray4[1];
        lArray[2] = lArray[2] ^ lArray4[2];
        lArray[3] = lArray[3] ^ (lArray4[6] ^ lArray4[0]);
        lArray[5] = lArray[5] ^ (lArray4[8] ^ lArray4[2]);
        lArray[6] = lArray[6] ^ (lArray4[6] ^ lArray4[9]);
        lArray[4] = lArray[4] ^ (lArray4[7] ^ lArray4[1]);
        lArray[7] = lArray[7] ^ (lArray4[7] ^ lArray4[10]);
        lArray[8] = lArray[8] ^ (lArray4[8] ^ lArray4[11]);
        lArray[9] = lArray[9] ^ lArray4[9];
        lArray[10] = lArray[10] ^ lArray4[10];
        lArray[11] = lArray[11] ^ lArray4[11];
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 0, l, l4);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 4, l3, l6);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 2, l2, l5);
        lArray[3] = lArray[3] ^ lArray4[0];
        lArray4[1] = lArray4[1] ^ lArray4[2];
        lArray4[0] = lArray4[0] ^ lArray4[1];
        lArray4[3] = lArray4[3] ^ lArray4[4];
        lArray4[4] = lArray4[3] ^ lArray4[5];
        lArray[5] = lArray[5] ^ (lArray4[3] ^ lArray4[0]);
        lArray[6] = lArray[6] ^ (lArray4[1] ^ lArray4[4]);
        lArray[4] = lArray[4] ^ lArray4[0];
        lArray[7] = lArray[7] ^ lArray4[4];
        lArray[8] = lArray[8] ^ lArray4[5];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, 4, l ^ l2, l4 ^ l5);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, 6, l2 ^ l3, l5 ^ l6);
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, 5, l ^ l3, l4 ^ l6);
    }

    private static void mul416_no_simd_gf2x(long[] lArray, long[] lArray2, int n, long[] lArray3, int n2, long[] lArray4) {
        Mul_GF2x.mul192_no_simd_gf2x(lArray, 0, lArray2, n, lArray3, n2);
        Mul_GF2x.mul128_no_simd_gf2x(lArray, 6, lArray2[n + 3], lArray2[n + 4], lArray3[n2 + 3], lArray3[n2 + 4]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray, 10, lArray2[n + 5], lArray3[n2 + 5]);
        lArray[12] = Mul_GF2x.MUL32_NO_SIMD_GF2X(lArray2[n + 6], lArray3[n2 + 6]) ^ lArray[11];
        lArray[11] = lArray[10] ^ lArray[12];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray, 11, lArray2[n + 5] ^ lArray2[n + 6], lArray3[n2 + 5] ^ lArray3[n2 + 6]);
        lArray[8] = lArray[8] ^ lArray[10];
        lArray[11] = lArray[11] ^ lArray[9];
        lArray[10] = lArray[8] ^ lArray[12];
        lArray[8] = lArray[8] ^ lArray[6];
        lArray[9] = lArray[11] ^ lArray[7];
        Mul_GF2x.mul128_no_simd_gf2x_xor(lArray, 8, lArray2[n + 3] ^ lArray2[n + 5], lArray2[n + 4] ^ lArray2[n + 6], lArray3[n2 + 3] ^ lArray3[n2 + 5], lArray3[n2 + 4] ^ lArray3[n2 + 6], lArray4);
        long l = lArray2[n] ^ lArray2[n + 3];
        long l2 = lArray2[n + 1] ^ lArray2[n + 4];
        long l3 = lArray2[n + 2] ^ lArray2[n + 5];
        long l4 = lArray2[n + 6];
        long l5 = lArray3[n2] ^ lArray3[n2 + 3];
        long l6 = lArray3[n2 + 1] ^ lArray3[n2 + 4];
        long l7 = lArray3[n2 + 2] ^ lArray3[n2 + 5];
        long l8 = lArray3[n2 + 6];
        lArray[6] = lArray[6] ^ lArray[3];
        lArray[7] = lArray[7] ^ lArray[4];
        lArray[8] = lArray[8] ^ lArray[5];
        Mul_GF2x.mul128_no_simd_gf2x(lArray4, 0, l, l2, l5, l6);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 4, l3, l7);
        lArray4[6] = Mul_GF2x.MUL32_NO_SIMD_GF2X(l4, l8) ^ lArray4[5];
        lArray4[5] = lArray4[4] ^ lArray4[6];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray4, 5, l3 ^ l4, l7 ^ l8);
        lArray[3] = lArray[6] ^ lArray[0] ^ lArray4[0];
        lArray[4] = lArray[7] ^ lArray[1] ^ lArray4[1];
        lArray4[2] = lArray4[2] ^ lArray4[4];
        lArray4[3] = lArray4[3] ^ lArray4[5];
        lArray[5] = lArray[8] ^ lArray[2] ^ lArray4[2] ^ lArray4[0];
        lArray[6] = lArray[6] ^ (lArray[9] ^ lArray4[3] ^ lArray4[1]);
        lArray[7] = lArray[7] ^ (lArray[10] ^ lArray4[2] ^ lArray4[6]);
        lArray[8] = lArray[8] ^ (lArray[11] ^ lArray4[3]);
        lArray[9] = lArray[9] ^ (lArray[12] ^ lArray4[6]);
        Mul_GF2x.mul128_no_simd_gf2x_xor(lArray, 5, l ^ l3, l2 ^ l4, l5 ^ l7, l6 ^ l8, lArray4);
    }

    private static void mul416_no_simd_gf2x_xor(long[] lArray, long[] lArray2, int n, long[] lArray3, int n2, long[] lArray4, long[] lArray5) {
        Mul_GF2x.mul192_no_simd_gf2x(lArray4, 0, lArray2, n, lArray3, n2);
        Mul_GF2x.mul128_no_simd_gf2x(lArray4, 6, lArray2[n + 3], lArray2[n + 4], lArray3[n2 + 3], lArray3[n2 + 4]);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 10, lArray2[n + 5], lArray3[n2 + 5]);
        lArray4[12] = Mul_GF2x.MUL32_NO_SIMD_GF2X(lArray2[n + 6], lArray3[n2 + 6]) ^ lArray4[11];
        lArray4[11] = lArray4[10] ^ lArray4[12];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray4, 11, lArray2[n + 5] ^ lArray2[n + 6], lArray3[n2 + 5] ^ lArray3[n2 + 6]);
        lArray4[8] = lArray4[8] ^ lArray4[10];
        lArray4[11] = lArray4[11] ^ lArray4[9];
        lArray4[10] = lArray4[8] ^ lArray4[12];
        lArray4[8] = lArray4[8] ^ lArray4[6];
        lArray4[9] = lArray4[11] ^ lArray4[7];
        lArray4[6] = lArray4[6] ^ lArray4[3];
        lArray4[7] = lArray4[7] ^ lArray4[4];
        lArray4[8] = lArray4[8] ^ lArray4[5];
        Mul_GF2x.mul128_no_simd_gf2x_xor(lArray4, 8, lArray2[n + 3] ^ lArray2[n + 5], lArray2[n + 4] ^ lArray2[n + 6], lArray3[n2 + 3] ^ lArray3[n2 + 5], lArray3[n2 + 4] ^ lArray3[n2 + 6], lArray5);
        lArray[0] = lArray[0] ^ lArray4[0];
        lArray[1] = lArray[1] ^ lArray4[1];
        lArray[2] = lArray[2] ^ lArray4[2];
        lArray[3] = lArray[3] ^ (lArray4[6] ^ lArray4[0]);
        lArray[4] = lArray[4] ^ (lArray4[7] ^ lArray4[1]);
        lArray[5] = lArray[5] ^ (lArray4[8] ^ lArray4[2]);
        lArray[6] = lArray[6] ^ (lArray4[6] ^ lArray4[9]);
        lArray[7] = lArray[7] ^ (lArray4[7] ^ lArray4[10]);
        lArray[8] = lArray[8] ^ (lArray4[8] ^ lArray4[11]);
        lArray[9] = lArray[9] ^ (lArray4[9] ^ lArray4[12]);
        lArray[10] = lArray[10] ^ lArray4[10];
        lArray[11] = lArray[11] ^ lArray4[11];
        lArray[12] = lArray[12] ^ lArray4[12];
        long l = lArray2[n] ^ lArray2[n + 3];
        long l2 = lArray2[n + 1] ^ lArray2[n + 4];
        long l3 = lArray2[n + 2] ^ lArray2[n + 5];
        long l4 = lArray2[n + 6];
        long l5 = lArray3[n2] ^ lArray3[n2 + 3];
        long l6 = lArray3[n2 + 1] ^ lArray3[n2 + 4];
        long l7 = lArray3[n2 + 2] ^ lArray3[n2 + 5];
        long l8 = lArray3[n2 + 6];
        Mul_GF2x.mul128_no_simd_gf2x(lArray4, 0, l, l2, l5, l6);
        Mul_GF2x.MUL64_NO_SIMD_GF2X(lArray4, 4, l3, l7);
        lArray4[6] = Mul_GF2x.MUL32_NO_SIMD_GF2X(l4, l8) ^ lArray4[5];
        lArray4[5] = lArray4[4] ^ lArray4[6];
        Mul_GF2x.MUL64_NO_SIMD_GF2X_XOR(lArray4, 5, l3 ^ l4, l7 ^ l8);
        lArray[3] = lArray[3] ^ lArray4[0];
        lArray[4] = lArray[4] ^ lArray4[1];
        lArray4[2] = lArray4[2] ^ lArray4[4];
        lArray4[3] = lArray4[3] ^ lArray4[5];
        lArray[5] = lArray[5] ^ (lArray4[2] ^ lArray4[0]);
        lArray[6] = lArray[6] ^ (lArray4[3] ^ lArray4[1]);
        lArray[7] = lArray[7] ^ (lArray4[2] ^ lArray4[6]);
        lArray[8] = lArray[8] ^ lArray4[3];
        lArray[9] = lArray[9] ^ lArray4[6];
        Mul_GF2x.mul128_no_simd_gf2x_xor(lArray, 5, l ^ l3, l2 ^ l4, l5 ^ l7, l6 ^ l8, lArray4);
    }

    private static void mul544_no_simd_gf2x(long[] lArray, long[] lArray2, int n, long[] lArray3, int n2, long[] lArray4, long[] lArray5, long[] lArray6) {
        Mul_GF2x.mul128_no_simd_gf2x(lArray, 0, lArray2[n], lArray2[n + 1], lArray3[n2], lArray3[n2 + 1]);
        Mul_GF2x.mul128_no_simd_gf2x(lArray, 4, lArray2[n + 2], lArray2[n + 3], lArray3[n2 + 2], lArray3[n2 + 3]);
        lArray[4] = lArray[4] ^ lArray[2];
        lArray[5] = lArray[5] ^ lArray[3];
        lArray[2] = lArray[4] ^ lArray[0];
        lArray[3] = lArray[5] ^ lArray[1];
        lArray[4] = lArray[4] ^ lArray[6];
        lArray[5] = lArray[5] ^ lArray[7];
        Mul_GF2x.mul128_no_simd_gf2x_xor(lArray, 2, lArray2[n] ^ lArray2[n + 2], lArray2[n + 1] ^ lArray2[n + 3], lArray3[n2] ^ lArray3[n2 + 2], lArray3[n2 + 1] ^ lArray3[n2 + 3], lArray6);
        Mul_GF2x.mul288_no_simd_gf2x(lArray, 8, lArray2, n + 4, lArray3, n2 + 4, lArray6);
        lArray[8] = lArray[8] ^ lArray[4];
        lArray[9] = lArray[9] ^ lArray[5];
        lArray[10] = lArray[10] ^ lArray[6];
        lArray[11] = lArray[11] ^ lArray[7];
        lArray[4] = lArray[8] ^ lArray[0];
        lArray[5] = lArray[9] ^ lArray[1];
        lArray[6] = lArray[10] ^ lArray[2];
        lArray[7] = lArray[11] ^ lArray[3];
        lArray[8] = lArray[8] ^ lArray[12];
        lArray[9] = lArray[9] ^ lArray[13];
        lArray[10] = lArray[10] ^ lArray[14];
        lArray[11] = lArray[11] ^ lArray[15];
        lArray[12] = lArray[12] ^ lArray[16];
        lArray4[0] = lArray2[n] ^ lArray2[n + 4];
        lArray4[1] = lArray2[n + 1] ^ lArray2[n + 5];
        lArray4[2] = lArray2[n + 2] ^ lArray2[n + 6];
        lArray4[3] = lArray2[n + 3] ^ lArray2[n + 7];
        lArray4[4] = lArray2[n + 8];
        lArray5[0] = lArray3[n2] ^ lArray3[n2 + 4];
        lArray5[1] = lArray3[n2 + 1] ^ lArray3[n2 + 5];
        lArray5[2] = lArray3[n2 + 2] ^ lArray3[n2 + 6];
        lArray5[3] = lArray3[n2 + 3] ^ lArray3[n2 + 7];
        lArray5[4] = lArray3[n2 + 8];
        Mul_GF2x.mul288_no_simd_gf2x_xor(lArray, 4, lArray4, 0, lArray5, 0, lArray6);
    }

    private static void mul544_no_simd_gf2x_xor(long[] lArray, long[] lArray2, int n, long[] lArray3, int n2, long[] lArray4, long[] lArray5, long[] lArray6, long[] lArray7) {
        Mul_GF2x.mul128_no_simd_gf2x(lArray6, 0, lArray2[n], lArray2[n + 1], lArray3[n2], lArray3[n2 + 1]);
        Mul_GF2x.mul128_no_simd_gf2x(lArray6, 4, lArray2[n + 2], lArray2[n + 3], lArray3[n2 + 2], lArray3[n2 + 3]);
        lArray6[4] = lArray6[4] ^ lArray6[2];
        lArray6[5] = lArray6[5] ^ lArray6[3];
        lArray6[2] = lArray6[4] ^ lArray6[0];
        lArray6[3] = lArray6[5] ^ lArray6[1];
        lArray6[4] = lArray6[4] ^ lArray6[6];
        lArray6[5] = lArray6[5] ^ lArray6[7];
        Mul_GF2x.mul128_no_simd_gf2x_xor(lArray6, 2, lArray2[n] ^ lArray2[n + 2], lArray2[n + 1] ^ lArray2[n + 3], lArray3[n2] ^ lArray3[n2 + 2], lArray3[n2 + 1] ^ lArray3[n2 + 3], lArray7);
        Mul_GF2x.mul288_no_simd_gf2x(lArray6, 8, lArray2, n + 4, lArray3, n2 + 4, lArray7);
        lArray6[8] = lArray6[8] ^ lArray6[4];
        lArray6[9] = lArray6[9] ^ lArray6[5];
        lArray6[10] = lArray6[10] ^ lArray6[6];
        lArray6[11] = lArray6[11] ^ lArray6[7];
        lArray[0] = lArray[0] ^ lArray6[0];
        lArray[1] = lArray[1] ^ lArray6[1];
        lArray[2] = lArray[2] ^ lArray6[2];
        lArray[3] = lArray[3] ^ lArray6[3];
        lArray[4] = lArray[4] ^ (lArray6[8] ^ lArray6[0]);
        lArray[5] = lArray[5] ^ (lArray6[9] ^ lArray6[1]);
        lArray[6] = lArray[6] ^ (lArray6[10] ^ lArray6[2]);
        lArray[7] = lArray[7] ^ (lArray6[11] ^ lArray6[3]);
        lArray[8] = lArray[8] ^ (lArray6[8] ^ lArray6[12]);
        lArray[9] = lArray[9] ^ (lArray6[9] ^ lArray6[13]);
        lArray[10] = lArray[10] ^ (lArray6[10] ^ lArray6[14]);
        lArray[11] = lArray[11] ^ (lArray6[11] ^ lArray6[15]);
        lArray[12] = lArray[12] ^ (lArray6[12] ^ lArray6[16]);
        lArray[13] = lArray[13] ^ lArray6[13];
        lArray[14] = lArray[14] ^ lArray6[14];
        lArray[15] = lArray[15] ^ lArray6[15];
        lArray[16] = lArray[16] ^ lArray6[16];
        lArray4[0] = lArray2[n] ^ lArray2[n + 4];
        lArray4[1] = lArray2[n + 1] ^ lArray2[n + 5];
        lArray4[2] = lArray2[n + 2] ^ lArray2[n + 6];
        lArray4[3] = lArray2[n + 3] ^ lArray2[n + 7];
        lArray4[4] = lArray2[n + 8];
        lArray5[0] = lArray3[n2] ^ lArray3[n2 + 4];
        lArray5[1] = lArray3[n2 + 1] ^ lArray3[n2 + 5];
        lArray5[2] = lArray3[n2 + 2] ^ lArray3[n2 + 6];
        lArray5[3] = lArray3[n2 + 3] ^ lArray3[n2 + 7];
        lArray5[4] = lArray3[n2 + 8];
        Mul_GF2x.mul288_no_simd_gf2x_xor(lArray, 4, lArray4, 0, lArray5, 0, lArray6);
    }

    public static class Mul12
    extends Mul_GF2x {
        private long[] Buffer = new long[12];

        @Override
        public void mul_gf2x(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul_GF2x.mul384_no_simd_gf2x(pointer.array, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.Buffer);
        }

        @Override
        public void sqr_gf2x(long[] lArray, long[] lArray2, int n) {
            Mul_GF2x.SQR128_NO_SIMD_GF2X(lArray, 8, lArray2, n + 4);
            Mul_GF2x.SQR256_NO_SIMD_GF2X(lArray, 0, lArray2, n);
        }

        @Override
        public void mul_gf2x_xor(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul_GF2x.mul384_no_simd_gf2x_xor(pointer.array, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.Buffer);
        }
    }

    public static class Mul13
    extends Mul_GF2x {
        private long[] Buffer = new long[13];
        private long[] Buffer2 = new long[4];

        @Override
        public void mul_gf2x(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul_GF2x.mul416_no_simd_gf2x(pointer.array, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.Buffer);
        }

        @Override
        public void sqr_gf2x(long[] lArray, long[] lArray2, int n) {
            lArray[12] = Mul_GF2x.SQR32_NO_SIMD_GF2X(lArray2[n + 6]);
            Mul_GF2x.SQR128_NO_SIMD_GF2X(lArray, 8, lArray2, n + 4);
            Mul_GF2x.SQR256_NO_SIMD_GF2X(lArray, 0, lArray2, n);
        }

        @Override
        public void mul_gf2x_xor(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul_GF2x.mul416_no_simd_gf2x_xor(pointer.array, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.Buffer, this.Buffer2);
        }
    }

    public static class Mul17
    extends Mul_GF2x {
        private long[] AA = new long[5];
        private long[] BB = new long[5];
        private long[] Buffer1 = new long[17];
        private long[] Buffer2 = new long[4];

        @Override
        public void mul_gf2x(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul_GF2x.mul544_no_simd_gf2x(pointer.array, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.AA, this.BB, this.Buffer1);
        }

        @Override
        public void sqr_gf2x(long[] lArray, long[] lArray2, int n) {
            lArray[16] = Mul_GF2x.SQR32_NO_SIMD_GF2X(lArray2[n + 8]);
            Mul_GF2x.SQR256_NO_SIMD_GF2X(lArray, 8, lArray2, n + 4);
            Mul_GF2x.SQR256_NO_SIMD_GF2X(lArray, 0, lArray2, n);
        }

        @Override
        public void mul_gf2x_xor(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul_GF2x.mul544_no_simd_gf2x_xor(pointer.array, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.AA, this.BB, this.Buffer1, this.Buffer2);
        }
    }

    public static class Mul6
    extends Mul_GF2x {
        private long[] Buffer = new long[6];

        @Override
        public void mul_gf2x(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul6.mul192_no_simd_gf2x(pointer.array, 0, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp);
        }

        @Override
        public void sqr_gf2x(long[] lArray, long[] lArray2, int n) {
            Mul_GF2x.SQR64_NO_SIMD_GF2X(lArray, 4, lArray2[n + 2]);
            Mul_GF2x.SQR128_NO_SIMD_GF2X(lArray, 0, lArray2, n);
        }

        @Override
        public void mul_gf2x_xor(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul6.mul192_no_simd_gf2x_xor(pointer.array, pointer.cp, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.Buffer);
        }
    }

    public static class Mul9
    extends Mul_GF2x {
        private long[] Buffer = new long[9];

        @Override
        public void mul_gf2x(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul_GF2x.mul288_no_simd_gf2x(pointer.array, 0, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.Buffer);
        }

        @Override
        public void sqr_gf2x(long[] lArray, long[] lArray2, int n) {
            lArray[8] = Mul_GF2x.SQR32_NO_SIMD_GF2X(lArray2[n + 4]);
            Mul_GF2x.SQR256_NO_SIMD_GF2X(lArray, 0, lArray2, n);
        }

        @Override
        public void mul_gf2x_xor(Pointer pointer, Pointer pointer2, Pointer pointer3) {
            Mul_GF2x.mul288_no_simd_gf2x_xor(pointer.array, pointer.cp, pointer2.array, pointer2.cp, pointer3.array, pointer3.cp, this.Buffer);
        }
    }
}

