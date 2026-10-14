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
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class XoodyakEngine
implements AEADCipher {
    private boolean forEncryption;
    private byte[] state;
    private int phase;
    private MODE mode;
    private int Rabsorb;
    private final int f_bPrime = 48;
    private final int Rkout = 24;
    private byte[] K;
    private byte[] iv;
    private final int PhaseDown = 1;
    private final int PhaseUp = 2;
    private final int NLANES = 12;
    private final int NROWS = 3;
    private final int NCOLUMS = 4;
    private final int MAXROUNDS = 12;
    private final int TAGLEN = 16;
    final int Rkin = 44;
    private byte[] tag;
    private final int[] RC = new int[]{88, 56, 960, 208, 288, 20, 96, 44, 896, 240, 416, 18};
    private boolean aadFinished;
    private boolean encrypted;
    private boolean initialised = false;
    private final ByteArrayOutputStream aadData = new ByteArrayOutputStream();
    private final ByteArrayOutputStream message = new ByteArrayOutputStream();

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = bl;
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("Xoodyak init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV)cipherParameters;
        this.iv = parametersWithIV.getIV();
        if (this.iv == null || this.iv.length != 16) {
            throw new IllegalArgumentException("Xoodyak requires exactly 16 bytes of IV");
        }
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("Xoodyak init parameters must include a key");
        }
        KeyParameter keyParameter = (KeyParameter)parametersWithIV.getParameters();
        this.K = keyParameter.getKey();
        if (this.K.length != 16) {
            throw new IllegalArgumentException("Xoodyak key must be 128 bits long");
        }
        CryptoServicesRegistrar.checkConstraints((CryptoServiceProperties)new DefaultServiceProperties(this.getAlgorithmName(), 128, cipherParameters, Utils.getPurpose(bl)));
        this.state = new byte[48];
        this.tag = new byte[16];
        this.initialised = true;
        this.reset();
    }

    @Override
    public String getAlgorithmName() {
        return "Xoodyak AEAD";
    }

    @Override
    public void processAADByte(byte by) {
        if (this.aadFinished) {
            throw new IllegalArgumentException("AAD cannot be added after reading a full block(" + this.getBlockSize() + " bytes) of input for " + (this.forEncryption ? "encryption" : "decryption"));
        }
        this.aadData.write(by);
    }

    @Override
    public void processAADBytes(byte[] byArray, int n, int n2) {
        if (this.aadFinished) {
            throw new IllegalArgumentException("AAD cannot be added after reading a full block(" + this.getBlockSize() + " bytes) of input for " + (this.forEncryption ? "encryption" : "decryption"));
        }
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        this.aadData.write(byArray, n, n2);
    }

    @Override
    public int processByte(byte by, byte[] byArray, int n) throws DataLengthException {
        return this.processBytes(new byte[]{by}, 0, 1, byArray, n);
    }

    private void processAAD() {
        if (!this.aadFinished) {
            byte[] byArray = this.aadData.toByteArray();
            this.AbsorbAny(byArray, 0, byArray.length, this.Rabsorb, 3);
            this.aadFinished = true;
        }
    }

    @Override
    public int processBytes(byte[] byArray, int n, int n2, byte[] byArray2, int n3) throws DataLengthException {
        if (!this.initialised) {
            throw new IllegalArgumentException("Need call init function before encryption/decryption");
        }
        if (this.mode != MODE.ModeKeyed) {
            throw new IllegalArgumentException("Xoodyak has not been initialised");
        }
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        this.message.write(byArray, n, n2);
        int n4 = this.message.size() - (this.forEncryption ? 0 : 16);
        if (n4 >= this.getBlockSize()) {
            byte[] byArray3 = this.message.toByteArray();
            n2 = n4 / this.getBlockSize() * this.getBlockSize();
            if (n2 + n3 > byArray2.length) {
                throw new OutputLengthException("output buffer is too short");
            }
            this.processAAD();
            this.encrypt(byArray3, 0, n2, byArray2, n3);
            this.message.reset();
            this.message.write(byArray3, n2, byArray3.length - n2);
            return n2;
        }
        return 0;
    }

    private int encrypt(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        int n4;
        int n5;
        byte[] byArray3 = new byte[24];
        int n6 = n5 = this.encrypted ? 0 : 128;
        for (int i = n2; i != 0 || !this.encrypted; i -= n4) {
            n4 = Math.min(i, 24);
            if (this.forEncryption) {
                System.arraycopy(byArray, n, byArray3, 0, n4);
            }
            this.Up(null, 0, n5);
            for (int j = 0; j < n4; ++j) {
                byArray2[n3 + j] = (byte)(byArray[n++] ^ this.state[j]);
            }
            if (this.forEncryption) {
                this.Down(byArray3, 0, n4, 0);
            } else {
                this.Down(byArray2, n3, n4, 0);
            }
            n5 = 0;
            n3 += n4;
            this.encrypted = true;
        }
        return n2;
    }

    @Override
    public int doFinal(byte[] byArray, int n) throws IllegalStateException, InvalidCipherTextException {
        if (!this.initialised) {
            throw new IllegalArgumentException("Need call init function before encryption/decryption");
        }
        byte[] byArray2 = this.message.toByteArray();
        int n2 = this.message.size();
        if (this.forEncryption && n2 + 16 + n > byArray.length || !this.forEncryption && n2 - 16 + n > byArray.length) {
            throw new OutputLengthException("output buffer too short");
        }
        this.processAAD();
        int n3 = 0;
        if (this.forEncryption) {
            this.encrypt(byArray2, 0, n2, byArray, n);
            this.tag = new byte[16];
            this.Up(this.tag, 16, 64);
            System.arraycopy(this.tag, 0, byArray, n += n2, 16);
            n3 = n2 + 16;
        } else {
            int n4;
            n3 = n4 = n2 - 16;
            this.encrypt(byArray2, 0, n4, byArray, n);
            this.tag = new byte[16];
            this.Up(this.tag, 16, 64);
            for (int i = 0; i < 16; ++i) {
                if (this.tag[i] == byArray2[n4++]) continue;
                throw new IllegalArgumentException("Mac does not match");
            }
        }
        this.reset(false);
        return n3;
    }

    @Override
    public byte[] getMac() {
        return this.tag;
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
            this.tag = null;
        }
        Arrays.fill(this.state, (byte)0);
        this.aadFinished = false;
        this.encrypted = false;
        this.phase = 2;
        this.message.reset();
        this.aadData.reset();
        int n = this.K.length;
        int n2 = this.iv.length;
        byte[] byArray = new byte[44];
        this.mode = MODE.ModeKeyed;
        this.Rabsorb = 44;
        System.arraycopy(this.K, 0, byArray, 0, n);
        System.arraycopy(this.iv, 0, byArray, n, n2);
        byArray[n + n2] = (byte)n2;
        this.AbsorbAny(byArray, 0, n + n2 + 1, this.Rabsorb, 2);
    }

    private void AbsorbAny(byte[] byArray, int n, int n2, int n3, int n4) {
        int n5;
        do {
            if (this.phase != 2) {
                this.Up(null, 0, 0);
            }
            n5 = Math.min(n2, n3);
            this.Down(byArray, n, n5, n4);
            n4 = 0;
            n += n5;
        } while ((n2 -= n5) != 0);
    }

    private void Up(byte[] byArray, int n, int n2) {
        if (this.mode != MODE.ModeHash) {
            this.state[47] = (byte)(this.state[47] ^ n2);
        }
        int[] nArray = new int[12];
        Pack.littleEndianToInt(this.state, 0, nArray, 0, nArray.length);
        int[] nArray2 = new int[12];
        int[] nArray3 = new int[4];
        int[] nArray4 = new int[4];
        for (int i = 0; i < 12; ++i) {
            int n3;
            int n4;
            for (n4 = 0; n4 < 4; ++n4) {
                nArray3[n4] = nArray[this.index(n4, 0)] ^ nArray[this.index(n4, 1)] ^ nArray[this.index(n4, 2)];
            }
            for (n4 = 0; n4 < 4; ++n4) {
                n3 = nArray3[n4 + 3 & 3];
                nArray4[n4] = this.ROTL32(n3, 5) ^ this.ROTL32(n3, 14);
            }
            for (n4 = 0; n4 < 4; ++n4) {
                for (n3 = 0; n3 < 3; ++n3) {
                    int n5 = this.index(n4, n3);
                    nArray[n5] = nArray[n5] ^ nArray4[n4];
                }
            }
            for (n4 = 0; n4 < 4; ++n4) {
                nArray2[this.index((int)n4, (int)0)] = nArray[this.index(n4, 0)];
                nArray2[this.index((int)n4, (int)1)] = nArray[this.index(n4 + 3, 1)];
                nArray2[this.index((int)n4, (int)2)] = this.ROTL32(nArray[this.index(n4, 2)], 11);
            }
            nArray2[0] = nArray2[0] ^ this.RC[i];
            for (n4 = 0; n4 < 4; ++n4) {
                for (n3 = 0; n3 < 3; ++n3) {
                    nArray[this.index((int)n4, (int)n3)] = nArray2[this.index(n4, n3)] ^ ~nArray2[this.index(n4, n3 + 1)] & nArray2[this.index(n4, n3 + 2)];
                }
            }
            for (n4 = 0; n4 < 4; ++n4) {
                nArray2[this.index((int)n4, (int)0)] = nArray[this.index(n4, 0)];
                nArray2[this.index((int)n4, (int)1)] = this.ROTL32(nArray[this.index(n4, 1)], 1);
                nArray2[this.index((int)n4, (int)2)] = this.ROTL32(nArray[this.index(n4 + 2, 2)], 8);
            }
            System.arraycopy(nArray2, 0, nArray, 0, 12);
        }
        Pack.intToLittleEndian(nArray, 0, nArray.length, this.state, 0);
        this.phase = 2;
        if (byArray != null) {
            System.arraycopy(this.state, 0, byArray, 0, n);
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

    public int getBlockSize() {
        return 24;
    }

    public int getKeyBytesSize() {
        return 16;
    }

    public int getIVBytesSize() {
        return 16;
    }

    static enum MODE {
        ModeHash,
        ModeKeyed;

    }
}

