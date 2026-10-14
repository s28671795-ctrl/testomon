/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.util.Pack;

public class ISAPDigest
implements Digest {
    private long x0;
    private long x1;
    private long x2;
    private long x3;
    private long x4;
    private long t0;
    private long t1;
    private long t2;
    private long t3;
    private long t4;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    private void ROUND(long l) {
        this.t0 = this.x0 ^ this.x1 ^ this.x2 ^ this.x3 ^ l ^ this.x1 & (this.x0 ^ this.x2 ^ this.x4 ^ l);
        this.t1 = this.x0 ^ this.x2 ^ this.x3 ^ this.x4 ^ l ^ (this.x1 ^ this.x2 ^ l) & (this.x1 ^ this.x3);
        this.t2 = this.x1 ^ this.x2 ^ this.x4 ^ l ^ this.x3 & this.x4;
        this.t3 = this.x0 ^ this.x1 ^ this.x2 ^ l ^ (this.x0 ^ 0xFFFFFFFFFFFFFFFFL) & (this.x3 ^ this.x4);
        this.t4 = this.x1 ^ this.x3 ^ this.x4 ^ (this.x0 ^ this.x4) & this.x1;
        this.x0 = this.t0 ^ this.ROTR(this.t0, 19L) ^ this.ROTR(this.t0, 28L);
        this.x1 = this.t1 ^ this.ROTR(this.t1, 39L) ^ this.ROTR(this.t1, 61L);
        this.x2 = this.t2 ^ this.ROTR(this.t2, 1L) ^ this.ROTR(this.t2, 6L) ^ 0xFFFFFFFFFFFFFFFFL;
        this.x3 = this.t3 ^ this.ROTR(this.t3, 10L) ^ this.ROTR(this.t3, 17L);
        this.x4 = this.t4 ^ this.ROTR(this.t4, 7L) ^ this.ROTR(this.t4, 41L);
    }

    private void P12() {
        this.ROUND(240L);
        this.ROUND(225L);
        this.ROUND(210L);
        this.ROUND(195L);
        this.ROUND(180L);
        this.ROUND(165L);
        this.ROUND(150L);
        this.ROUND(135L);
        this.ROUND(120L);
        this.ROUND(105L);
        this.ROUND(90L);
        this.ROUND(75L);
    }

    private long ROTR(long l, long l2) {
        return l >>> (int)l2 | l << (int)(64L - l2);
    }

    protected long U64BIG(long l) {
        return this.ROTR(l, 8L) & 0xFF000000FF000000L | this.ROTR(l, 24L) & 0xFF000000FF0000L | this.ROTR(l, 40L) & 0xFF000000FF00L | this.ROTR(l, 56L) & 0xFF000000FFL;
    }

    @Override
    public String getAlgorithmName() {
        return "ISAP Hash";
    }

    @Override
    public int getDigestSize() {
        return 32;
    }

    @Override
    public void update(byte by) {
        this.buffer.write(by);
    }

    @Override
    public void update(byte[] byArray, int n, int n2) {
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        this.buffer.write(byArray, n, n2);
    }

    @Override
    public int doFinal(byte[] byArray, int n) {
        int n2;
        if (32 + n > byArray.length) {
            throw new OutputLengthException("output buffer is too short");
        }
        this.t4 = 0L;
        this.t3 = 0L;
        this.t2 = 0L;
        this.t1 = 0L;
        this.t0 = 0L;
        this.x0 = -1255492011513352131L;
        this.x1 = -8380609354527731710L;
        this.x2 = -5437372128236807582L;
        this.x3 = 4834782570098516968L;
        this.x4 = 3787428097924915520L;
        byte[] byArray2 = this.buffer.toByteArray();
        long[] lArray = new long[n2 >> 3];
        Pack.littleEndianToLong(byArray2, 0, lArray, 0, lArray.length);
        int n3 = 0;
        for (n2 = byArray2.length; n2 >= 8; n2 -= 8) {
            this.x0 ^= this.U64BIG(lArray[n3++]);
            this.P12();
        }
        this.x0 ^= 128L << (7 - n2 << 3);
        while (n2 > 0) {
            this.x0 ^= ((long)byArray2[(n3 << 3) + --n2] & 0xFFL) << (7 - n2 << 3);
        }
        this.P12();
        long[] lArray2 = new long[4];
        for (n3 = 0; n3 < 3; ++n3) {
            lArray2[n3] = this.U64BIG(this.x0);
            this.P12();
        }
        lArray2[n3] = this.U64BIG(this.x0);
        Pack.longToLittleEndian(lArray2, byArray, n);
        this.buffer.reset();
        return 32;
    }

    @Override
    public void reset() {
        this.buffer.reset();
    }
}

