/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

public class GeMSSUtils {
    static long NORBITS_UINT(long l) {
        l |= l << 32;
        l >>>= 32;
        return --l >>> 63;
    }

    static long XORBITS_UINT(long l) {
        l ^= l << 1;
        l ^= l << 2;
        return (l & 0x8888888888888888L) * 0x1111111111111111L >>> 63;
    }

    static long ORBITS_UINT(long l) {
        l |= l << 32;
        l >>>= 32;
        return (l += 0xFFFFFFFFL) >>> 32;
    }

    static long CMP_LT_UINT(long l, long l2) {
        return (l >>> 63 ^ l2 >>> 63) & (l >>> 63) - (l2 >>> 63) >>> 63 ^ (l >>> 63 ^ l2 >>> 63 ^ 1L) & (l & Long.MAX_VALUE) - (l2 & Long.MAX_VALUE) >>> 63;
    }

    static long maskUINT(int n) {
        return n != 0 ? (1L << n) - 1L : -1L;
    }

    static int Highest_One(int n) {
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n ^ n >>> 1;
    }
}

