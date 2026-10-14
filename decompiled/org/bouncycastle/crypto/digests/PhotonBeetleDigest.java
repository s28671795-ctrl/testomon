/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Bytes;

public class PhotonBeetleDigest
implements Digest {
    private byte[] state;
    private byte[][] state_2d;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final int INITIAL_RATE_INBYTES = 16;
    private int RATE_INBYTES = 4;
    private int SQUEEZE_RATE_INBYTES = 16;
    private int STATE_INBYTES = 32;
    private int TAG_INBYTES = 32;
    private int LAST_THREE_BITS_OFFSET = 5;
    private int ROUND = 12;
    private int D = 8;
    private int Dq = 3;
    private int Dr = 7;
    private int DSquare = 64;
    private int S = 4;
    private int S_1 = 3;
    private byte[][] RC = new byte[][]{{1, 3, 7, 14, 13, 11, 6, 12, 9, 2, 5, 10}, {0, 2, 6, 15, 12, 10, 7, 13, 8, 3, 4, 11}, {2, 0, 4, 13, 14, 8, 5, 15, 10, 1, 6, 9}, {6, 4, 0, 9, 10, 12, 1, 11, 14, 5, 2, 13}, {14, 12, 8, 1, 2, 4, 9, 3, 6, 13, 10, 5}, {15, 13, 9, 0, 3, 5, 8, 2, 7, 12, 11, 4}, {13, 15, 11, 2, 1, 7, 10, 0, 5, 14, 9, 6}, {9, 11, 15, 6, 5, 3, 14, 4, 1, 10, 13, 2}};
    private byte[][] MixColMatrix = new byte[][]{{2, 4, 2, 11, 2, 8, 5, 6}, {12, 9, 8, 13, 7, 7, 5, 2}, {4, 4, 13, 13, 9, 4, 13, 9}, {1, 6, 5, 1, 12, 13, 15, 14}, {15, 12, 9, 13, 14, 5, 14, 13}, {9, 14, 5, 15, 4, 12, 9, 6}, {12, 2, 2, 10, 3, 1, 1, 14}, {15, 1, 13, 10, 5, 10, 2, 3}};
    private byte[] sbox = new byte[]{12, 5, 6, 11, 9, 0, 10, 13, 3, 14, 15, 8, 4, 7, 1, 2};

    public PhotonBeetleDigest() {
        this.state = new byte[this.STATE_INBYTES];
        this.state_2d = new byte[this.D][this.D];
    }

    @Override
    public String getAlgorithmName() {
        return "Photon-Beetle Hash";
    }

    @Override
    public int getDigestSize() {
        return this.TAG_INBYTES;
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
        if (32 + n > byArray.length) {
            throw new OutputLengthException("output buffer is too short");
        }
        byte[] byArray2 = this.buffer.toByteArray();
        int n2 = byArray2.length;
        if (n2 == 0) {
            int n3 = this.STATE_INBYTES - 1;
            this.state[n3] = (byte)(this.state[n3] ^ 1 << this.LAST_THREE_BITS_OFFSET);
        } else if (n2 <= 16) {
            System.arraycopy(byArray2, 0, this.state, 0, n2);
            if (n2 < 16) {
                int n4 = n2;
                this.state[n4] = (byte)(this.state[n4] ^ 1);
            }
            int n5 = this.STATE_INBYTES - 1;
            this.state[n5] = (byte)(this.state[n5] ^ (n2 < 16 ? 1 : 2) << this.LAST_THREE_BITS_OFFSET);
        } else {
            int n6;
            System.arraycopy(byArray2, 0, this.state, 0, 16);
            int n7 = ((n2 -= 16) + this.RATE_INBYTES - 1) / this.RATE_INBYTES;
            for (n6 = 0; n6 < n7 - 1; ++n6) {
                this.PHOTON_Permutation();
                Bytes.xorTo((int)this.RATE_INBYTES, (byte[])byArray2, (int)(16 + n6 * this.RATE_INBYTES), (byte[])this.state, (int)0);
            }
            this.PHOTON_Permutation();
            int n8 = n2 - n6 * this.RATE_INBYTES;
            Bytes.xorTo((int)n8, (byte[])byArray2, (int)(16 + n6 * this.RATE_INBYTES), (byte[])this.state, (int)0);
            if (n8 < this.RATE_INBYTES) {
                int n9 = n8;
                this.state[n9] = (byte)(this.state[n9] ^ 1);
            }
            int n10 = this.STATE_INBYTES - 1;
            this.state[n10] = (byte)(this.state[n10] ^ (n2 % this.RATE_INBYTES == 0 ? 1 : 2) << this.LAST_THREE_BITS_OFFSET);
        }
        this.PHOTON_Permutation();
        System.arraycopy(this.state, 0, byArray, n, this.SQUEEZE_RATE_INBYTES);
        this.PHOTON_Permutation();
        System.arraycopy(this.state, 0, byArray, n + this.SQUEEZE_RATE_INBYTES, this.TAG_INBYTES - this.SQUEEZE_RATE_INBYTES);
        return this.TAG_INBYTES;
    }

    @Override
    public void reset() {
        this.buffer.reset();
        Arrays.fill(this.state, (byte)0);
    }

    void PHOTON_Permutation() {
        int n;
        for (n = 0; n < this.DSquare; ++n) {
            this.state_2d[n >>> this.Dq][n & this.Dr] = (byte)((this.state[n >> 1] & 0xFF) >>> 4 * (n & 1) & 0xF);
        }
        for (int i = 0; i < this.ROUND; ++i) {
            int n2;
            for (n = 0; n < this.D; ++n) {
                byte[] byArray = this.state_2d[n];
                byArray[0] = (byte)(byArray[0] ^ this.RC[n][i]);
            }
            for (n = 0; n < this.D; ++n) {
                for (n2 = 0; n2 < this.D; ++n2) {
                    this.state_2d[n][n2] = this.sbox[this.state_2d[n][n2]];
                }
            }
            for (n = 1; n < this.D; ++n) {
                System.arraycopy(this.state_2d[n], 0, this.state, 0, this.D);
                System.arraycopy(this.state, n, this.state_2d[n], 0, this.D - n);
                System.arraycopy(this.state, 0, this.state_2d[n], this.D - n, n);
            }
            for (n2 = 0; n2 < this.D; ++n2) {
                for (n = 0; n < this.D; ++n) {
                    byte by = 0;
                    for (int j = 0; j < this.D; ++j) {
                        int n3 = this.MixColMatrix[n][j];
                        int n4 = 0;
                        byte by2 = this.state_2d[j][n2];
                        for (int k = 0; k < this.S; ++k) {
                            if ((by2 >>> k & 1) != 0) {
                                n4 ^= n3;
                            }
                            if ((n3 >>> this.S_1 & 1) != 0) {
                                n3 <<= 1;
                                n3 ^= 3;
                                continue;
                            }
                            n3 <<= 1;
                        }
                        by = (byte)(by ^ n4 & 0xF);
                    }
                    this.state[n] = by;
                }
                for (n = 0; n < this.D; ++n) {
                    this.state_2d[n][n2] = this.state[n];
                }
            }
        }
        for (n = 0; n < this.DSquare; n += 2) {
            this.state[n >>> 1] = (byte)(this.state_2d[n >>> this.Dq][n & this.Dr] & 0xF | (this.state_2d[n >>> this.Dq][n + 1 & this.Dr] & 0xF) << 4);
        }
    }
}

