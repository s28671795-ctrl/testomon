/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

class Reduce {
    Reduce() {
    }

    public static short montgomeryReduce(int n) {
        short s2 = (short)(n * 62209);
        int n2 = s2 * 3329;
        n2 = n - n2;
        return (short)(n2 >>= 16);
    }

    public static short barretReduce(short s2) {
        long l = 0x4000000L;
        short s3 = (short)((l + 1664L) / 3329L);
        short s4 = (short)(s3 * s2 >> 26);
        s4 = (short)(s4 * 3329);
        return (short)(s2 - s4);
    }

    public static short conditionalSubQ(short s2) {
        s2 = (short)(s2 - 3329);
        s2 = (short)(s2 + (s2 >> 15 & 0xD01));
        return s2;
    }
}

