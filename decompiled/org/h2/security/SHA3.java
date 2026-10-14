/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security;

import java.security.MessageDigest;
import java.util.Arrays;
import org.h2.util.Bits;

public final class SHA3
extends MessageDigest {
    private static final long[] ROUND_CONSTANTS;
    private final int digestLength;
    private final int rate;
    private long state00;
    private long state01;
    private long state02;
    private long state03;
    private long state04;
    private long state05;
    private long state06;
    private long state07;
    private long state08;
    private long state09;
    private long state10;
    private long state11;
    private long state12;
    private long state13;
    private long state14;
    private long state15;
    private long state16;
    private long state17;
    private long state18;
    private long state19;
    private long state20;
    private long state21;
    private long state22;
    private long state23;
    private long state24;
    private final byte[] buf;
    private int bufcnt;

    public static SHA3 getSha3_224() {
        return new SHA3("SHA3-224", 28);
    }

    public static SHA3 getSha3_256() {
        return new SHA3("SHA3-256", 32);
    }

    public static SHA3 getSha3_384() {
        return new SHA3("SHA3-384", 48);
    }

    public static SHA3 getSha3_512() {
        return new SHA3("SHA3-512", 64);
    }

    private SHA3(String string, int n) {
        super(string);
        this.digestLength = n;
        this.rate = 200 - n * 2;
        this.buf = new byte[this.rate];
    }

    @Override
    protected byte[] engineDigest() {
        this.buf[this.bufcnt] = 6;
        Arrays.fill(this.buf, this.bufcnt + 1, this.rate, (byte)0);
        int n = this.rate - 1;
        this.buf[n] = (byte)(this.buf[n] | 0x80);
        this.absorbQueue();
        byte[] byArray = new byte[this.digestLength];
        switch (this.digestLength) {
            case 64: {
                Bits.writeLongLE(byArray, 56, this.state07);
                Bits.writeLongLE(byArray, 48, this.state06);
            }
            case 48: {
                Bits.writeLongLE(byArray, 40, this.state05);
                Bits.writeLongLE(byArray, 32, this.state04);
            }
            case 32: {
                Bits.writeLongLE(byArray, 24, this.state03);
                break;
            }
            case 28: {
                Bits.writeIntLE(byArray, 24, (int)this.state03);
            }
        }
        Bits.writeLongLE(byArray, 16, this.state02);
        Bits.writeLongLE(byArray, 8, this.state01);
        Bits.writeLongLE(byArray, 0, this.state00);
        this.engineReset();
        return byArray;
    }

    @Override
    protected int engineGetDigestLength() {
        return this.digestLength;
    }

    @Override
    protected void engineReset() {
        this.state00 = 0L;
        this.state01 = 0L;
        this.state02 = 0L;
        this.state03 = 0L;
        this.state04 = 0L;
        this.state05 = 0L;
        this.state06 = 0L;
        this.state07 = 0L;
        this.state08 = 0L;
        this.state09 = 0L;
        this.state10 = 0L;
        this.state11 = 0L;
        this.state12 = 0L;
        this.state13 = 0L;
        this.state14 = 0L;
        this.state15 = 0L;
        this.state16 = 0L;
        this.state17 = 0L;
        this.state18 = 0L;
        this.state19 = 0L;
        this.state20 = 0L;
        this.state21 = 0L;
        this.state22 = 0L;
        this.state23 = 0L;
        this.state24 = 0L;
        Arrays.fill(this.buf, (byte)0);
        this.bufcnt = 0;
    }

    @Override
    protected void engineUpdate(byte by) {
        this.buf[this.bufcnt++] = by;
        if (this.bufcnt == this.rate) {
            this.absorbQueue();
        }
    }

    @Override
    protected void engineUpdate(byte[] byArray, int n, int n2) {
        while (n2 > 0) {
            if (this.bufcnt == 0 && n2 >= this.rate) {
                do {
                    this.absorb(byArray, n);
                    n += this.rate;
                } while ((n2 -= this.rate) >= this.rate);
                continue;
            }
            int n3 = Math.min(n2, this.rate - this.bufcnt);
            System.arraycopy(byArray, n, this.buf, this.bufcnt, n3);
            this.bufcnt += n3;
            n += n3;
            n2 -= n3;
            if (this.bufcnt != this.rate) continue;
            this.absorbQueue();
        }
    }

    private void absorbQueue() {
        this.absorb(this.buf, 0);
        this.bufcnt = 0;
    }

    private void absorb(byte[] byArray, int n) {
        switch (this.digestLength) {
            case 28: {
                this.state17 ^= Bits.readLongLE(byArray, n + 136);
            }
            case 32: {
                this.state13 ^= Bits.readLongLE(byArray, n + 104);
                this.state14 ^= Bits.readLongLE(byArray, n + 112);
                this.state15 ^= Bits.readLongLE(byArray, n + 120);
                this.state16 ^= Bits.readLongLE(byArray, n + 128);
            }
            case 48: {
                this.state09 ^= Bits.readLongLE(byArray, n + 72);
                this.state10 ^= Bits.readLongLE(byArray, n + 80);
                this.state11 ^= Bits.readLongLE(byArray, n + 88);
                this.state12 ^= Bits.readLongLE(byArray, n + 96);
            }
        }
        this.state00 ^= Bits.readLongLE(byArray, n);
        this.state01 ^= Bits.readLongLE(byArray, n + 8);
        this.state02 ^= Bits.readLongLE(byArray, n + 16);
        this.state03 ^= Bits.readLongLE(byArray, n + 24);
        this.state04 ^= Bits.readLongLE(byArray, n + 32);
        this.state05 ^= Bits.readLongLE(byArray, n + 40);
        this.state06 ^= Bits.readLongLE(byArray, n + 48);
        this.state07 ^= Bits.readLongLE(byArray, n + 56);
        this.state08 ^= Bits.readLongLE(byArray, n + 64);
        for (int i = 0; i < 24; ++i) {
            long l = this.state00 ^ this.state05 ^ this.state10 ^ this.state15 ^ this.state20;
            long l2 = this.state01 ^ this.state06 ^ this.state11 ^ this.state16 ^ this.state21;
            long l3 = this.state02 ^ this.state07 ^ this.state12 ^ this.state17 ^ this.state22;
            long l4 = this.state03 ^ this.state08 ^ this.state13 ^ this.state18 ^ this.state23;
            long l5 = this.state04 ^ this.state09 ^ this.state14 ^ this.state19 ^ this.state24;
            long l6 = (l2 << 1 | l2 >>> 63) ^ l5;
            this.state00 ^= l6;
            this.state05 ^= l6;
            this.state10 ^= l6;
            this.state15 ^= l6;
            this.state20 ^= l6;
            l6 = (l3 << 1 | l3 >>> 63) ^ l;
            this.state01 ^= l6;
            this.state06 ^= l6;
            this.state11 ^= l6;
            this.state16 ^= l6;
            this.state21 ^= l6;
            l6 = (l4 << 1 | l4 >>> 63) ^ l2;
            this.state02 ^= l6;
            this.state07 ^= l6;
            this.state12 ^= l6;
            this.state17 ^= l6;
            this.state22 ^= l6;
            l6 = (l5 << 1 | l5 >>> 63) ^ l3;
            this.state03 ^= l6;
            this.state08 ^= l6;
            this.state13 ^= l6;
            this.state18 ^= l6;
            this.state23 ^= l6;
            l6 = (l << 1 | l >>> 63) ^ l4;
            this.state04 ^= l6;
            this.state09 ^= l6;
            this.state14 ^= l6;
            this.state19 ^= l6;
            this.state24 ^= l6;
            long l7 = this.state00;
            long l8 = this.state06 << 44 | this.state06 >>> 20;
            long l9 = this.state12 << 43 | this.state12 >>> 21;
            long l10 = this.state18 << 21 | this.state18 >>> 43;
            long l11 = this.state24 << 14 | this.state24 >>> 50;
            long l12 = this.state03 << 28 | this.state03 >>> 36;
            long l13 = this.state09 << 20 | this.state09 >>> 44;
            long l14 = this.state10 << 3 | this.state10 >>> 61;
            long l15 = this.state16 << 45 | this.state16 >>> 19;
            long l16 = this.state22 << 61 | this.state22 >>> 3;
            long l17 = this.state01 << 1 | this.state01 >>> 63;
            long l18 = this.state07 << 6 | this.state07 >>> 58;
            long l19 = this.state13 << 25 | this.state13 >>> 39;
            long l20 = this.state19 << 8 | this.state19 >>> 56;
            long l21 = this.state20 << 18 | this.state20 >>> 46;
            long l22 = this.state04 << 27 | this.state04 >>> 37;
            long l23 = this.state05 << 36 | this.state05 >>> 28;
            long l24 = this.state11 << 10 | this.state11 >>> 54;
            long l25 = this.state17 << 15 | this.state17 >>> 49;
            long l26 = this.state23 << 56 | this.state23 >>> 8;
            long l27 = this.state02 << 62 | this.state02 >>> 2;
            long l28 = this.state08 << 55 | this.state08 >>> 9;
            long l29 = this.state14 << 39 | this.state14 >>> 25;
            long l30 = this.state15 << 41 | this.state15 >>> 23;
            long l31 = this.state21 << 2 | this.state21 >>> 62;
            this.state00 = l7 ^ (l8 ^ 0xFFFFFFFFFFFFFFFFL) & l9 ^ ROUND_CONSTANTS[i];
            this.state01 = l8 ^ (l9 ^ 0xFFFFFFFFFFFFFFFFL) & l10;
            this.state02 = l9 ^ (l10 ^ 0xFFFFFFFFFFFFFFFFL) & l11;
            this.state03 = l10 ^ (l11 ^ 0xFFFFFFFFFFFFFFFFL) & l7;
            this.state04 = l11 ^ (l7 ^ 0xFFFFFFFFFFFFFFFFL) & l8;
            this.state05 = l12 ^ (l13 ^ 0xFFFFFFFFFFFFFFFFL) & l14;
            this.state06 = l13 ^ (l14 ^ 0xFFFFFFFFFFFFFFFFL) & l15;
            this.state07 = l14 ^ (l15 ^ 0xFFFFFFFFFFFFFFFFL) & l16;
            this.state08 = l15 ^ (l16 ^ 0xFFFFFFFFFFFFFFFFL) & l12;
            this.state09 = l16 ^ (l12 ^ 0xFFFFFFFFFFFFFFFFL) & l13;
            this.state10 = l17 ^ (l18 ^ 0xFFFFFFFFFFFFFFFFL) & l19;
            this.state11 = l18 ^ (l19 ^ 0xFFFFFFFFFFFFFFFFL) & l20;
            this.state12 = l19 ^ (l20 ^ 0xFFFFFFFFFFFFFFFFL) & l21;
            this.state13 = l20 ^ (l21 ^ 0xFFFFFFFFFFFFFFFFL) & l17;
            this.state14 = l21 ^ (l17 ^ 0xFFFFFFFFFFFFFFFFL) & l18;
            this.state15 = l22 ^ (l23 ^ 0xFFFFFFFFFFFFFFFFL) & l24;
            this.state16 = l23 ^ (l24 ^ 0xFFFFFFFFFFFFFFFFL) & l25;
            this.state17 = l24 ^ (l25 ^ 0xFFFFFFFFFFFFFFFFL) & l26;
            this.state18 = l25 ^ (l26 ^ 0xFFFFFFFFFFFFFFFFL) & l22;
            this.state19 = l26 ^ (l22 ^ 0xFFFFFFFFFFFFFFFFL) & l23;
            this.state20 = l27 ^ (l28 ^ 0xFFFFFFFFFFFFFFFFL) & l29;
            this.state21 = l28 ^ (l29 ^ 0xFFFFFFFFFFFFFFFFL) & l30;
            this.state22 = l29 ^ (l30 ^ 0xFFFFFFFFFFFFFFFFL) & l31;
            this.state23 = l30 ^ (l31 ^ 0xFFFFFFFFFFFFFFFFL) & l27;
            this.state24 = l31 ^ (l27 ^ 0xFFFFFFFFFFFFFFFFL) & l28;
        }
    }

    static {
        long[] lArray = new long[24];
        int n = 1;
        for (int i = 0; i < 24; ++i) {
            lArray[i] = 0L;
            for (int j = 0; j < 7; ++j) {
                int n2 = n;
                n = (byte)(n2 < 0 ? n2 << 1 ^ 0x71 : n2 << 1);
                if ((n2 & 1) == 0) continue;
                int n3 = i;
                lArray[n3] = lArray[n3] ^ 1L << (1 << j) - 1;
            }
        }
        ROUND_CONSTANTS = lArray;
    }
}

