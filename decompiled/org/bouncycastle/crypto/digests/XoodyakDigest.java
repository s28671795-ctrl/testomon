/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class XoodyakDigest
implements Digest {
    private byte[] state;
    private int phase;
    private MODE mode;
    private int Rabsorb;
    private final int f_bPrime = 48;
    private final int Rhash = 16;
    private final int PhaseDown = 1;
    private final int PhaseUp = 2;
    private final int NLANES = 12;
    private final int NROWS = 3;
    private final int NCOLUMS = 4;
    private final int MAXROUNDS = 12;
    private final int TAGLEN = 16;
    private final int[] RC = new int[]{88, 56, 960, 208, 288, 20, 96, 44, 896, 240, 416, 18};
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public XoodyakDigest() {
        this.state = new byte[48];
        this.reset();
    }

    @Override
    public String getAlgorithmName() {
        return "Xoodyak Hash";
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
        byte[] byArray2 = this.buffer.toByteArray();
        int n3 = 0;
        int n4 = this.buffer.size();
        int n5 = 3;
        do {
            if (this.phase != 2) {
                this.Up(null, 0, 0, 0);
            }
            n2 = Math.min(n4, this.Rabsorb);
            this.Down(byArray2, n3, n2, n5);
            n5 = 0;
            n3 += n2;
        } while ((n4 -= n2) != 0);
        this.Up(byArray, n, 16, 64);
        this.Down(null, 0, 0, 0);
        this.Up(byArray, n + 16, 16, 0);
        return 32;
    }

    @Override
    public void reset() {
        Arrays.fill(this.state, (byte)0);
        this.phase = 2;
        this.mode = MODE.ModeHash;
        this.Rabsorb = 16;
        this.buffer.reset();
    }

    private void Up(byte[] byArray, int n, int n2, int n3) {
        if (this.mode != MODE.ModeHash) {
            this.state[47] = (byte)(this.state[47] ^ n3);
        }
        int[] nArray = new int[12];
        Pack.littleEndianToInt(this.state, 0, nArray, 0, nArray.length);
        int[] nArray2 = new int[12];
        int[] nArray3 = new int[4];
        int[] nArray4 = new int[4];
        for (int i = 0; i < 12; ++i) {
            int n4;
            int n5;
            for (n5 = 0; n5 < 4; ++n5) {
                nArray3[n5] = nArray[this.index(n5, 0)] ^ nArray[this.index(n5, 1)] ^ nArray[this.index(n5, 2)];
            }
            for (n5 = 0; n5 < 4; ++n5) {
                n4 = nArray3[n5 + 3 & 3];
                nArray4[n5] = this.ROTL32(n4, 5) ^ this.ROTL32(n4, 14);
            }
            for (n5 = 0; n5 < 4; ++n5) {
                for (n4 = 0; n4 < 3; ++n4) {
                    int n6 = this.index(n5, n4);
                    nArray[n6] = nArray[n6] ^ nArray4[n5];
                }
            }
            for (n5 = 0; n5 < 4; ++n5) {
                nArray2[this.index((int)n5, (int)0)] = nArray[this.index(n5, 0)];
                nArray2[this.index((int)n5, (int)1)] = nArray[this.index(n5 + 3, 1)];
                nArray2[this.index((int)n5, (int)2)] = this.ROTL32(nArray[this.index(n5, 2)], 11);
            }
            nArray2[0] = nArray2[0] ^ this.RC[i];
            for (n5 = 0; n5 < 4; ++n5) {
                for (n4 = 0; n4 < 3; ++n4) {
                    nArray[this.index((int)n5, (int)n4)] = nArray2[this.index(n5, n4)] ^ ~nArray2[this.index(n5, n4 + 1)] & nArray2[this.index(n5, n4 + 2)];
                }
            }
            for (n5 = 0; n5 < 4; ++n5) {
                nArray2[this.index((int)n5, (int)0)] = nArray[this.index(n5, 0)];
                nArray2[this.index((int)n5, (int)1)] = this.ROTL32(nArray[this.index(n5, 1)], 1);
                nArray2[this.index((int)n5, (int)2)] = this.ROTL32(nArray[this.index(n5 + 2, 2)], 8);
            }
            System.arraycopy(nArray2, 0, nArray, 0, 12);
        }
        Pack.intToLittleEndian(nArray, 0, nArray.length, this.state, 0);
        this.phase = 2;
        if (byArray != null) {
            System.arraycopy(this.state, 0, byArray, n, n2);
        }
    }

    void Down(byte[] byArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n2) {
            int n5 = n4++;
            this.state[n5] = (byte)(this.state[n5] ^ byArray[n++]);
        }
        int n6 = n2;
        this.state[n6] = (byte)(this.state[n6] ^ 1);
        this.state[47] = (byte)(this.state[47] ^ (this.mode == MODE.ModeHash ? n3 & 1 : n3));
        this.phase = 1;
    }

    private int index(int n, int n2) {
        return n2 % 3 * 4 + n % 4;
    }

    private int ROTL32(int n, int n2) {
        return n << (n2 & 0x1F) ^ n >>> (32 - n2 & 0x1F);
    }

    static enum MODE {
        ModeHash,
        ModeKeyed;

    }
}

