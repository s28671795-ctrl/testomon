/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.Xof;

public class AsconXof
implements Xof {
    AsconParameters asconParameters;
    private final String algorithmName;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private long x0;
    private long x1;
    private long x2;
    private long x3;
    private long x4;
    private final int CRYPTO_BYTES = 32;
    private final int ASCON_PB_ROUNDS;

    public AsconXof(AsconParameters asconParameters) {
        this.asconParameters = asconParameters;
        switch (asconParameters) {
            case AsconXof: {
                this.ASCON_PB_ROUNDS = 12;
                this.algorithmName = "Ascon-Xof";
                break;
            }
            case AsconXofA: {
                this.ASCON_PB_ROUNDS = 8;
                this.algorithmName = "Ascon-XofA";
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid parameter settings for Ascon Hash");
            }
        }
        this.reset();
    }

    private long ROR(long l, int n) {
        return l >>> n | l << 64 - n;
    }

    private void ROUND(long l) {
        long l2 = this.x0 ^ this.x1 ^ this.x2 ^ this.x3 ^ l ^ this.x1 & (this.x0 ^ this.x2 ^ this.x4 ^ l);
        long l3 = this.x0 ^ this.x2 ^ this.x3 ^ this.x4 ^ l ^ (this.x1 ^ this.x2 ^ l) & (this.x1 ^ this.x3);
        long l4 = this.x1 ^ this.x2 ^ this.x4 ^ l ^ this.x3 & this.x4;
        long l5 = this.x0 ^ this.x1 ^ this.x2 ^ l ^ (this.x0 ^ 0xFFFFFFFFFFFFFFFFL) & (this.x3 ^ this.x4);
        long l6 = this.x1 ^ this.x3 ^ this.x4 ^ (this.x0 ^ this.x4) & this.x1;
        this.x0 = l2 ^ this.ROR(l2, 19) ^ this.ROR(l2, 28);
        this.x1 = l3 ^ this.ROR(l3, 39) ^ this.ROR(l3, 61);
        this.x2 = l4 ^ this.ROR(l4, 1) ^ this.ROR(l4, 6) ^ 0xFFFFFFFFFFFFFFFFL;
        this.x3 = l5 ^ this.ROR(l5, 10) ^ this.ROR(l5, 17);
        this.x4 = l6 ^ this.ROR(l6, 7) ^ this.ROR(l6, 41);
    }

    private void P(int n) {
        if (n == 12) {
            this.ROUND(240L);
            this.ROUND(225L);
            this.ROUND(210L);
            this.ROUND(195L);
        }
        if (n >= 8) {
            this.ROUND(180L);
            this.ROUND(165L);
        }
        this.ROUND(150L);
        this.ROUND(135L);
        this.ROUND(120L);
        this.ROUND(105L);
        this.ROUND(90L);
        this.ROUND(75L);
    }

    private long PAD(int n) {
        return 128L << 56 - (n << 3);
    }

    private long LOADBYTES(byte[] byArray, int n, int n2) {
        long l = 0L;
        for (int i = 0; i < n2; ++i) {
            l |= ((long)byArray[i + n] & 0xFFL) << (7 - i << 3);
        }
        return l;
    }

    private void STOREBYTES(byte[] byArray, int n, long l, int n2) {
        for (int i = 0; i < n2; ++i) {
            byArray[i + n] = (byte)(l >>> (7 - i << 3));
        }
    }

    @Override
    public String getAlgorithmName() {
        return this.algorithmName;
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
    public int doOutput(byte[] byArray, int n, int n2) {
        int n3;
        if (32 + n > byArray.length) {
            throw new OutputLengthException("output buffer is too short");
        }
        byte[] byArray2 = this.buffer.toByteArray();
        int n4 = 0;
        int n5 = 8;
        for (n3 = this.buffer.size(); n3 >= n5; n3 -= n5) {
            this.x0 ^= this.LOADBYTES(byArray2, n4, 8);
            this.P(this.ASCON_PB_ROUNDS);
            n4 += n5;
        }
        this.x0 ^= this.LOADBYTES(byArray2, n4, n3);
        this.x0 ^= this.PAD(n3);
        int n6 = 12;
        this.P(n6);
        for (n3 = 32; n3 > n5; n3 -= n5) {
            this.STOREBYTES(byArray, n, this.x0, 8);
            this.P(this.ASCON_PB_ROUNDS);
            n += n5;
        }
        this.STOREBYTES(byArray, n, this.x0, n3);
        this.reset();
        return 32;
    }

    @Override
    public int doFinal(byte[] byArray, int n) {
        return this.doOutput(byArray, n, this.getDigestSize());
    }

    @Override
    public int doFinal(byte[] byArray, int n, int n2) {
        return this.doOutput(byArray, n, n2);
    }

    @Override
    public int getByteLength() {
        return 8;
    }

    @Override
    public void reset() {
        this.buffer.reset();
        switch (this.asconParameters) {
            case AsconXof: {
                this.x0 = -5368810569253202922L;
                this.x1 = 3121280575360345120L;
                this.x2 = 7395939140700676632L;
                this.x3 = 6533890155656471820L;
                this.x4 = 5710016986865767350L;
                break;
            }
            case AsconXofA: {
                this.x0 = 4940560291654768690L;
                this.x1 = -3635129828240960206L;
                this.x2 = -597534922722107095L;
                this.x3 = 2623493988082852443L;
                this.x4 = -6283826724160825537L;
            }
        }
    }

    public static enum AsconParameters {
        AsconXof,
        AsconXofA;

    }
}

