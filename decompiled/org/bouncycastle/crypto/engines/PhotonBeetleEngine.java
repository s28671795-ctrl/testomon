/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.engines;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServiceProperties;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import org.bouncycastle.crypto.engines.Utils;
import org.bouncycastle.crypto.modes.AEADCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class PhotonBeetleEngine
implements AEADCipher {
    private boolean input_empty;
    private boolean forEncryption;
    private byte[] K;
    private byte[] N;
    private byte[] state;
    private byte[][] state_2d;
    private byte[] A;
    private byte[] T;
    private boolean encrypted;
    private boolean initialised;
    private final ByteArrayOutputStream aadData = new ByteArrayOutputStream();
    private final ByteArrayOutputStream message = new ByteArrayOutputStream();
    private final int CRYPTO_KEYBYTES = 16;
    private final int CRYPTO_NPUBBYTES = 16;
    private final int RATE_INBYTES;
    private final int RATE_INBYTES_HALF;
    private final int STATE_INBYTES;
    private final int TAG_INBYTES = 16;
    private final int LAST_THREE_BITS_OFFSET;
    private final int ROUND = 12;
    private final int D = 8;
    private final int Dq = 3;
    private final int Dr = 7;
    private final int DSquare = 64;
    private final int S = 4;
    private final int S_1 = 3;
    private final byte[][] RC = new byte[][]{{1, 3, 7, 14, 13, 11, 6, 12, 9, 2, 5, 10}, {0, 2, 6, 15, 12, 10, 7, 13, 8, 3, 4, 11}, {2, 0, 4, 13, 14, 8, 5, 15, 10, 1, 6, 9}, {6, 4, 0, 9, 10, 12, 1, 11, 14, 5, 2, 13}, {14, 12, 8, 1, 2, 4, 9, 3, 6, 13, 10, 5}, {15, 13, 9, 0, 3, 5, 8, 2, 7, 12, 11, 4}, {13, 15, 11, 2, 1, 7, 10, 0, 5, 14, 9, 6}, {9, 11, 15, 6, 5, 3, 14, 4, 1, 10, 13, 2}};
    private final byte[][] MixColMatrix = new byte[][]{{2, 4, 2, 11, 2, 8, 5, 6}, {12, 9, 8, 13, 7, 7, 5, 2}, {4, 4, 13, 13, 9, 4, 13, 9}, {1, 6, 5, 1, 12, 13, 15, 14}, {15, 12, 9, 13, 14, 5, 14, 13}, {9, 14, 5, 15, 4, 12, 9, 6}, {12, 2, 2, 10, 3, 1, 1, 14}, {15, 1, 13, 10, 5, 10, 2, 3}};
    private final byte[] sbox = new byte[]{12, 5, 6, 11, 9, 0, 10, 13, 3, 14, 15, 8, 4, 7, 1, 2};

    public PhotonBeetleEngine(PhotonBeetleParameters photonBeetleParameters) {
        int n = 0;
        int n2 = 0;
        switch (photonBeetleParameters) {
            case pb32: {
                n2 = 32;
                n = 224;
                break;
            }
            case pb128: {
                n2 = 128;
                n = 128;
            }
        }
        this.RATE_INBYTES = n2 + 7 >>> 3;
        this.RATE_INBYTES_HALF = this.RATE_INBYTES >>> 1;
        int n3 = n2 + n;
        this.STATE_INBYTES = n3 + 7 >>> 3;
        this.LAST_THREE_BITS_OFFSET = n3 - (this.STATE_INBYTES - 1 << 3) - 3;
        this.initialised = false;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = bl;
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("Photon-Beetle AEAD init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV)cipherParameters;
        this.N = parametersWithIV.getIV();
        if (this.N == null || this.N.length != 16) {
            throw new IllegalArgumentException("Photon-Beetle AEAD requires exactly 16 bytes of IV");
        }
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("Photon-Beetle AEAD init parameters must include a key");
        }
        KeyParameter keyParameter = (KeyParameter)parametersWithIV.getParameters();
        this.K = keyParameter.getKey();
        if (this.K.length != 16) {
            throw new IllegalArgumentException("Photon-Beetle AEAD key must be 128 bits long");
        }
        CryptoServicesRegistrar.checkConstraints((CryptoServiceProperties)new DefaultServiceProperties(this.getAlgorithmName(), 128, cipherParameters, Utils.getPurpose(bl)));
        this.state = new byte[this.STATE_INBYTES];
        this.state_2d = new byte[8][8];
        this.T = new byte[16];
        this.initialised = true;
        this.reset(false);
    }

    @Override
    public String getAlgorithmName() {
        return "Photon-Beetle AEAD";
    }

    @Override
    public void processAADByte(byte by) {
        this.aadData.write(by);
    }

    @Override
    public void processAADBytes(byte[] byArray, int n, int n2) {
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        this.aadData.write(byArray, n, n2);
    }

    @Override
    public int processByte(byte by, byte[] byArray, int n) throws DataLengthException {
        return this.processBytes(new byte[]{by}, 0, 1, byArray, n);
    }

    @Override
    public int processBytes(byte[] byArray, int n, int n2, byte[] byArray2, int n3) throws DataLengthException {
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        this.message.write(byArray, n, n2);
        return 0;
    }

    @Override
    public int doFinal(byte[] byArray, int n) throws IllegalStateException, InvalidCipherTextException {
        int n2;
        int n3;
        int n4;
        if (!this.initialised) {
            throw new IllegalArgumentException("Need call init function before encryption/decryption");
        }
        int n5 = this.message.size() - (this.forEncryption ? 0 : 16);
        if (this.forEncryption && n5 + 16 + n > byArray.length || !this.forEncryption && n5 + n > byArray.length) {
            throw new OutputLengthException("output buffer too short");
        }
        byte[] byArray2 = this.message.toByteArray();
        int n6 = 0;
        this.A = this.aadData.toByteArray();
        int n7 = this.A.length;
        if (n7 != 0 || n5 != 0) {
            this.input_empty = false;
        }
        byte by = this.select(n5 != 0, n7 % this.RATE_INBYTES == 0, (byte)3, (byte)4);
        byte by2 = this.select(n7 != 0, n5 % this.RATE_INBYTES == 0, (byte)5, (byte)6);
        if (n7 != 0) {
            n4 = (n7 + this.RATE_INBYTES - 1) / this.RATE_INBYTES;
            for (n3 = 0; n3 < n4 - 1; ++n3) {
                this.PHOTON_Permutation();
                this.XOR(this.A, n3 * this.RATE_INBYTES, this.RATE_INBYTES);
            }
            this.PHOTON_Permutation();
            n2 = n7 - n3 * this.RATE_INBYTES;
            this.XOR(this.A, n3 * this.RATE_INBYTES, n2);
            if (n2 < this.RATE_INBYTES) {
                int n8 = n2;
                this.state[n8] = (byte)(this.state[n8] ^ 1);
            }
            int n9 = this.STATE_INBYTES - 1;
            this.state[n9] = (byte)(this.state[n9] ^ by << this.LAST_THREE_BITS_OFFSET);
        }
        if (n5 != 0) {
            n4 = (n5 + this.RATE_INBYTES - 1) / this.RATE_INBYTES;
            for (n3 = 0; n3 < n4 - 1; ++n3) {
                this.PHOTON_Permutation();
                this.rhoohr(byArray, n + n3 * this.RATE_INBYTES, byArray2, n6 + n3 * this.RATE_INBYTES, this.RATE_INBYTES);
            }
            this.PHOTON_Permutation();
            n2 = n5 - n3 * this.RATE_INBYTES;
            this.rhoohr(byArray, n + n3 * this.RATE_INBYTES, byArray2, n6 + n3 * this.RATE_INBYTES, n2);
            if (n2 < this.RATE_INBYTES) {
                int n10 = n2;
                this.state[n10] = (byte)(this.state[n10] ^ 1);
            }
            int n11 = this.STATE_INBYTES - 1;
            this.state[n11] = (byte)(this.state[n11] ^ by2 << this.LAST_THREE_BITS_OFFSET);
        }
        n += n5;
        if (this.input_empty) {
            int n12 = this.STATE_INBYTES - 1;
            this.state[n12] = (byte)(this.state[n12] ^ 1 << this.LAST_THREE_BITS_OFFSET);
        }
        this.PHOTON_Permutation();
        this.T = new byte[16];
        System.arraycopy(this.state, 0, this.T, 0, 16);
        if (this.forEncryption) {
            System.arraycopy(this.T, 0, byArray, n, 16);
            n5 += 16;
        } else {
            for (n3 = 0; n3 < 16; ++n3) {
                if (this.T[n3] == byArray2[n5 + n3]) continue;
                throw new IllegalArgumentException("Mac does not match");
            }
        }
        this.reset(false);
        return n5;
    }

    @Override
    public byte[] getMac() {
        return this.T;
    }

    @Override
    public int getUpdateOutputSize(int n) {
        return n;
    }

    @Override
    public int getOutputSize(int n) {
        return n + 16;
    }

    @Override
    public void reset() {
        if (!this.initialised) {
            throw new IllegalArgumentException("Need call init function before encryption/decryption");
        }
        this.reset(true);
    }

    private void reset(boolean bl) {
        if (bl) {
            this.T = null;
        }
        this.input_empty = true;
        this.aadData.reset();
        this.message.reset();
        System.arraycopy(this.K, 0, this.state, 0, this.K.length);
        System.arraycopy(this.N, 0, this.state, this.K.length, this.N.length);
        this.encrypted = false;
    }

    private void PHOTON_Permutation() {
        int n;
        for (n = 0; n < 64; ++n) {
            this.state_2d[n >>> 3][n & 7] = (byte)((this.state[n >> 1] & 0xFF) >>> 4 * (n & 1) & 0xF);
        }
        for (int i = 0; i < 12; ++i) {
            int n2;
            for (n = 0; n < 8; ++n) {
                byte[] byArray = this.state_2d[n];
                byArray[0] = (byte)(byArray[0] ^ this.RC[n][i]);
            }
            for (n = 0; n < 8; ++n) {
                for (n2 = 0; n2 < 8; ++n2) {
                    this.state_2d[n][n2] = this.sbox[this.state_2d[n][n2]];
                }
            }
            for (n = 1; n < 8; ++n) {
                System.arraycopy(this.state_2d[n], 0, this.state, 0, 8);
                System.arraycopy(this.state, n, this.state_2d[n], 0, 8 - n);
                System.arraycopy(this.state, 0, this.state_2d[n], 8 - n, n);
            }
            for (n2 = 0; n2 < 8; ++n2) {
                for (n = 0; n < 8; ++n) {
                    byte by = 0;
                    for (int j = 0; j < 8; ++j) {
                        int n3 = this.MixColMatrix[n][j];
                        int n4 = 0;
                        byte by2 = this.state_2d[j][n2];
                        for (int k = 0; k < 4; ++k) {
                            if ((by2 >>> k & 1) != 0) {
                                n4 ^= n3;
                            }
                            if ((n3 >>> 3 & 1) != 0) {
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
                for (n = 0; n < 8; ++n) {
                    this.state_2d[n][n2] = this.state[n];
                }
            }
        }
        for (n = 0; n < 64; n += 2) {
            this.state[n >>> 1] = (byte)(this.state_2d[n >>> 3][n & 7] & 0xF | (this.state_2d[n >>> 3][n + 1 & 7] & 0xF) << 4);
        }
    }

    private byte select(boolean bl, boolean bl2, byte by, byte by2) {
        if (bl && bl2) {
            return 1;
        }
        if (bl) {
            return 2;
        }
        if (bl2) {
            return by;
        }
        return by2;
    }

    private void rhoohr(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        int n4;
        byte[] byArray3 = this.state_2d[0];
        int n5 = Math.min(n3, this.RATE_INBYTES_HALF);
        for (n4 = 0; n4 < this.RATE_INBYTES_HALF - 1; ++n4) {
            byArray3[n4] = (byte)((this.state[n4] & 0xFF) >>> 1 | (this.state[n4 + 1] & 1) << 7);
        }
        byArray3[this.RATE_INBYTES_HALF - 1] = (byte)((this.state[n4] & 0xFF) >>> 1 | (this.state[0] & 1) << 7);
        n4 = 0;
        while (n4 < n5) {
            byArray[n4 + n] = (byte)(this.state[n4 + this.RATE_INBYTES_HALF] ^ byArray2[n4++ + n2]);
        }
        while (n4 < n3) {
            byArray[n4 + n] = (byte)(byArray3[n4 - this.RATE_INBYTES_HALF] ^ byArray2[n4++ + n2]);
        }
        if (this.forEncryption) {
            this.XOR(byArray2, n2, n3);
        } else {
            this.XOR(byArray, n2, n3);
        }
    }

    private void XOR(byte[] byArray, int n, int n2) {
        int n3 = 0;
        while (n3 < n2) {
            int n4 = n3++;
            this.state[n4] = (byte)(this.state[n4] ^ byArray[n++]);
        }
    }

    public int getBlockSize() {
        return this.RATE_INBYTES;
    }

    public int getKeyBytesSize() {
        return 16;
    }

    public int getIVBytesSize() {
        return 16;
    }

    public static enum PhotonBeetleParameters {
        pb32,
        pb128;

    }
}

