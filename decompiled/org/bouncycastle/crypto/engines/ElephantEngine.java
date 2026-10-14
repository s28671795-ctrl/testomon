/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.engines;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
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

public class ElephantEngine
implements AEADCipher {
    private boolean forEncryption;
    private final String algorithmName;
    private final ElephantParameters parameters;
    private final int BLOCK_SIZE;
    private int nBits;
    private int nSBox;
    private final int nRounds;
    private byte lfsrIV;
    private byte[] tag;
    private byte[] npub;
    private byte[] expanded_key;
    private final byte CRYPTO_KEYBYTES = (byte)16;
    private final byte CRYPTO_NPUBBYTES = (byte)12;
    private final byte CRYPTO_ABYTES;
    private boolean initialised;
    private int nb_its;
    private byte[] ad;
    private int adOff;
    private int adlen;
    private final byte[] tag_buffer;
    private byte[] previous_mask;
    private byte[] current_mask;
    private byte[] next_mask;
    private final byte[] buffer;
    private State m_state = State.Uninitialized;
    private final ByteArrayOutputStream aadData = new ByteArrayOutputStream();
    private int inputOff;
    private byte[] inputMessage;
    private final byte[] previous_outputMessage;
    private final byte[] outputMessage;
    private final byte[] sBoxLayer = new byte[]{-18, -19, -21, -32, -30, -31, -28, -17, -25, -22, -24, -27, -23, -20, -29, -26, -34, -35, -37, -48, -46, -47, -44, -33, -41, -38, -40, -43, -39, -36, -45, -42, -66, -67, -69, -80, -78, -79, -76, -65, -73, -70, -72, -75, -71, -68, -77, -74, 14, 13, 11, 0, 2, 1, 4, 15, 7, 10, 8, 5, 9, 12, 3, 6, 46, 45, 43, 32, 34, 33, 36, 47, 39, 42, 40, 37, 41, 44, 35, 38, 30, 29, 27, 16, 18, 17, 20, 31, 23, 26, 24, 21, 25, 28, 19, 22, 78, 77, 75, 64, 66, 65, 68, 79, 71, 74, 72, 69, 73, 76, 67, 70, -2, -3, -5, -16, -14, -15, -12, -1, -9, -6, -8, -11, -7, -4, -13, -10, 126, 125, 123, 112, 114, 113, 116, 127, 119, 122, 120, 117, 121, 124, 115, 118, -82, -83, -85, -96, -94, -95, -92, -81, -89, -86, -88, -91, -87, -84, -93, -90, -114, -115, -117, -128, -126, -127, -124, -113, -121, -118, -120, -123, -119, -116, -125, -122, 94, 93, 91, 80, 82, 81, 84, 95, 87, 90, 88, 85, 89, 92, 83, 86, -98, -99, -101, -112, -110, -111, -108, -97, -105, -102, -104, -107, -103, -100, -109, -106, -50, -51, -53, -64, -62, -63, -60, -49, -57, -54, -56, -59, -55, -52, -61, -58, 62, 61, 59, 48, 50, 49, 52, 63, 55, 58, 56, 53, 57, 60, 51, 54, 110, 109, 107, 96, 98, 97, 100, 111, 103, 106, 104, 101, 105, 108, 99, 102};
    private final byte[] KeccakRoundConstants = new byte[]{1, -126, -118, 0, -117, 1, -127, 9, -118, -120, 9, 10, -117, -117, -119, 3, 2, -128};
    private final int[] KeccakRhoOffsets = new int[]{0, 1, 6, 4, 3, 4, 4, 6, 7, 4, 3, 2, 3, 1, 7, 1, 5, 7, 5, 0, 2, 2, 5, 0, 6};

    public ElephantEngine(ElephantParameters elephantParameters) {
        switch (elephantParameters) {
            case elephant160: {
                this.BLOCK_SIZE = 20;
                this.nBits = 160;
                this.nSBox = 20;
                this.nRounds = 80;
                this.lfsrIV = (byte)117;
                this.CRYPTO_ABYTES = (byte)8;
                this.algorithmName = "Elephant 160 AEAD";
                break;
            }
            case elephant176: {
                this.BLOCK_SIZE = 22;
                this.nBits = 176;
                this.nSBox = 22;
                this.nRounds = 90;
                this.lfsrIV = (byte)69;
                this.CRYPTO_ABYTES = (byte)8;
                this.algorithmName = "Elephant 176 AEAD";
                break;
            }
            case elephant200: {
                this.BLOCK_SIZE = 25;
                this.nRounds = 18;
                this.CRYPTO_ABYTES = (byte)16;
                this.algorithmName = "Elephant 200 AEAD";
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid parameter settings for Elephant");
            }
        }
        this.parameters = elephantParameters;
        this.tag_buffer = new byte[this.BLOCK_SIZE];
        this.previous_mask = new byte[this.BLOCK_SIZE];
        this.current_mask = new byte[this.BLOCK_SIZE];
        this.next_mask = new byte[this.BLOCK_SIZE];
        this.buffer = new byte[this.BLOCK_SIZE];
        this.previous_outputMessage = new byte[this.BLOCK_SIZE];
        this.outputMessage = new byte[this.BLOCK_SIZE];
        this.initialised = false;
        this.reset(false);
    }

    private void permutation(byte[] byArray) {
        switch (this.parameters) {
            case elephant160: 
            case elephant176: {
                byte by = this.lfsrIV;
                byte[] byArray2 = new byte[this.nSBox];
                for (int i = 0; i < this.nRounds; ++i) {
                    int n;
                    byArray[0] = (byte)(byArray[0] ^ by);
                    int n2 = this.nSBox - 1;
                    byArray[n2] = (byte)(byArray[n2] ^ (byte)((by & 1) << 7 | (by & 2) << 5 | (by & 4) << 3 | (by & 8) << 1 | (by & 0x10) >>> 1 | (by & 0x20) >>> 3 | (by & 0x40) >>> 5 | (by & 0x80) >>> 7));
                    by = (byte)((by << 1 | (0x40 & by) >>> 6 ^ (0x20 & by) >>> 5) & 0x7F);
                    for (n = 0; n < this.nSBox; ++n) {
                        byArray[n] = this.sBoxLayer[byArray[n] & 0xFF];
                    }
                    Arrays.fill(byArray2, (byte)0);
                    for (int j = 0; j < this.nSBox; ++j) {
                        for (int k = 0; k < 8; ++k) {
                            n = (j << 3) + k;
                            if (n != this.nBits - 1) {
                                n = (n * this.nBits >> 2) % (this.nBits - 1);
                            }
                            int n3 = n >>> 3;
                            byArray2[n3] = (byte)(byArray2[n3] ^ ((byArray[j] & 0xFF) >>> k & 1) << (n & 7));
                        }
                    }
                    System.arraycopy(byArray2, 0, byArray, 0, this.nSBox);
                }
                break;
            }
            case elephant200: {
                for (int i = 0; i < this.nRounds; ++i) {
                    this.KeccakP200Round(byArray, i);
                }
                break;
            }
        }
    }

    private byte rotl(byte by) {
        return (byte)((by & 0xFF) << 1 | (by & 0xFF) >>> 7);
    }

    private byte ROL8(byte n, int n2) {
        return (byte)(n2 != 0 ? (n & 0xFF) << n2 ^ (n & 0xFF) >>> 8 - n2 : n);
    }

    private int index(int n, int n2) {
        return n + n2 * 5;
    }

    private void KeccakP200Round(byte[] byArray, int n) {
        int n2;
        int n3;
        byte[] byArray2 = new byte[25];
        for (n3 = 0; n3 < 5; ++n3) {
            for (n2 = 0; n2 < 5; ++n2) {
                int n4 = n3;
                byArray2[n4] = (byte)(byArray2[n4] ^ byArray[this.index(n3, n2)]);
            }
        }
        for (n3 = 0; n3 < 5; ++n3) {
            byArray2[n3 + 5] = (byte)(this.ROL8(byArray2[(n3 + 1) % 5], 1) ^ byArray2[(n3 + 4) % 5]);
        }
        for (n3 = 0; n3 < 5; ++n3) {
            for (n2 = 0; n2 < 5; ++n2) {
                int n5 = this.index(n3, n2);
                byArray[n5] = (byte)(byArray[n5] ^ byArray2[n3 + 5]);
            }
        }
        for (n3 = 0; n3 < 5; ++n3) {
            for (n2 = 0; n2 < 5; ++n2) {
                byArray2[this.index((int)n3, (int)n2)] = this.ROL8(byArray[this.index(n3, n2)], this.KeccakRhoOffsets[this.index(n3, n2)]);
            }
        }
        for (n3 = 0; n3 < 5; ++n3) {
            for (n2 = 0; n2 < 5; ++n2) {
                byArray[this.index((int)n2, (int)((2 * n3 + 3 * n2) % 5))] = byArray2[this.index(n3, n2)];
            }
        }
        for (n2 = 0; n2 < 5; ++n2) {
            for (n3 = 0; n3 < 5; ++n3) {
                byArray2[n3] = (byte)(byArray[this.index(n3, n2)] ^ ~byArray[this.index((n3 + 1) % 5, n2)] & byArray[this.index((n3 + 2) % 5, n2)]);
            }
            for (n3 = 0; n3 < 5; ++n3) {
                byArray[this.index((int)n3, (int)n2)] = byArray2[n3];
            }
        }
        byArray[0] = (byte)(byArray[0] ^ this.KeccakRoundConstants[n]);
    }

    private void lfsr_step(byte[] byArray, byte[] byArray2) {
        switch (this.parameters) {
            case elephant160: {
                byArray[this.BLOCK_SIZE - 1] = (byte)(((byArray2[0] & 0xFF) << 3 | (byArray2[0] & 0xFF) >>> 5) ^ (byArray2[3] & 0xFF) << 7 ^ (byArray2[13] & 0xFF) >>> 7);
                break;
            }
            case elephant176: {
                byArray[this.BLOCK_SIZE - 1] = (byte)(this.rotl(byArray2[0]) ^ (byArray2[3] & 0xFF) << 7 ^ (byArray2[19] & 0xFF) >>> 7);
                break;
            }
            case elephant200: {
                byArray[this.BLOCK_SIZE - 1] = (byte)(this.rotl(byArray2[0]) ^ this.rotl(byArray2[2]) ^ byArray2[13] << 1);
            }
        }
        System.arraycopy(byArray2, 1, byArray, 0, this.BLOCK_SIZE - 1);
    }

    private void xor_block(byte[] byArray, byte[] byArray2, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            int n3 = i;
            byArray[n3] = (byte)(byArray[n3] ^ byArray2[i + n]);
        }
    }

    private void get_c_block(byte[] byArray, byte[] byArray2, int n, int n2, int n3) {
        int n4 = n3 * this.BLOCK_SIZE;
        if (n4 == n2) {
            Arrays.fill(byArray, 0, this.BLOCK_SIZE, (byte)0);
            byArray[0] = 1;
            return;
        }
        int n5 = n2 - n4;
        if (this.BLOCK_SIZE <= n5) {
            System.arraycopy(byArray2, n, byArray, 0, this.BLOCK_SIZE);
        } else {
            if (n5 > 0) {
                System.arraycopy(byArray2, n, byArray, 0, n5);
            }
            Arrays.fill(byArray, n5, this.BLOCK_SIZE, (byte)0);
            byArray[n5] = 1;
        }
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = bl;
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException(this.algorithmName + " init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV)cipherParameters;
        this.npub = parametersWithIV.getIV();
        if (this.npub == null || this.npub.length != 12) {
            throw new IllegalArgumentException(this.algorithmName + " requires exactly 12 bytes of IV");
        }
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException(this.algorithmName + " init parameters must include a key");
        }
        KeyParameter keyParameter = (KeyParameter)parametersWithIV.getParameters();
        byte[] byArray = keyParameter.getKey();
        if (byArray.length != 16) {
            throw new IllegalArgumentException(this.algorithmName + " key must be 128 bits long");
        }
        this.expanded_key = new byte[this.BLOCK_SIZE];
        System.arraycopy(byArray, 0, this.expanded_key, 0, 16);
        this.permutation(this.expanded_key);
        CryptoServicesRegistrar.checkConstraints((CryptoServiceProperties)new DefaultServiceProperties(this.getAlgorithmName(), 128, cipherParameters, Utils.getPurpose(bl)));
        this.initialised = true;
        this.m_state = bl ? State.EncInit : State.DecInit;
        this.inputMessage = new byte[this.BLOCK_SIZE + (bl ? (byte)0 : this.CRYPTO_ABYTES)];
        this.reset(false);
    }

    @Override
    public String getAlgorithmName() {
        return this.algorithmName;
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
        byte[] byArray3 = this.aadData.toByteArray();
        if (this.inputOff + n2 - (this.forEncryption ? (byte)0 : this.CRYPTO_ABYTES) >= this.BLOCK_SIZE) {
            switch (this.m_state) {
                case EncInit: 
                case DecInit: {
                    this.processAADBytes(this.tag_buffer);
                }
            }
            int n4 = this.inputOff + n2 - (this.forEncryption ? (byte)0 : this.CRYPTO_ABYTES);
            int n5 = byArray3.length;
            int n6 = n4 / this.BLOCK_SIZE;
            int n7 = 1 + (n4 % this.BLOCK_SIZE != 0 ? n6 : n6 - 1);
            int n8 = 1 + (12 + n5) / this.BLOCK_SIZE;
            byte[] byArray4 = new byte[Math.max(n6, 1) * this.BLOCK_SIZE];
            System.arraycopy(this.inputMessage, 0, byArray4, 0, this.inputOff);
            int n9 = byArray4.length - this.inputOff;
            System.arraycopy(byArray, n, byArray4, this.inputOff, byArray4.length - this.inputOff);
            this.processBytes(byArray4, byArray2, n3, n6, n7, n6, n4, n8);
            this.inputOff = n2 - n9;
            System.arraycopy(byArray, n + n9, this.inputMessage, 0, this.inputOff);
            this.nb_its += n6;
            return n6 * this.BLOCK_SIZE;
        }
        System.arraycopy(byArray, n, this.inputMessage, this.inputOff, n2);
        this.inputOff += n2;
        return 0;
    }

    @Override
    public int doFinal(byte[] byArray, int n) throws IllegalStateException, InvalidCipherTextException {
        if (!this.initialised) {
            throw new IllegalArgumentException(this.algorithmName + " needs call init function before doFinal");
        }
        int n2 = this.inputOff;
        if (this.forEncryption && n2 + n + this.CRYPTO_ABYTES > byArray.length || !this.forEncryption && n2 + n - this.CRYPTO_ABYTES > byArray.length) {
            throw new OutputLengthException("output buffer is too short");
        }
        byte[] byArray2 = this.aadData.toByteArray();
        switch (this.m_state) {
            case EncInit: 
            case DecInit: {
                this.processAADBytes(this.tag_buffer);
            }
        }
        int n3 = n2 + this.nb_its * this.BLOCK_SIZE - (this.forEncryption ? (byte)0 : this.CRYPTO_ABYTES);
        int n4 = byArray2.length;
        int n5 = 1 + n3 / this.BLOCK_SIZE;
        int n6 = n3 % this.BLOCK_SIZE != 0 ? n5 : n5 - 1;
        int n7 = 1 + (12 + n4) / this.BLOCK_SIZE;
        int n8 = Math.max(n5 + 1, n7 - 1);
        n += this.processBytes(this.inputMessage, byArray, n, n8, n6, n5, n3, n7);
        this.tag = new byte[this.CRYPTO_ABYTES];
        this.xor_block(this.tag_buffer, this.expanded_key, 0, this.BLOCK_SIZE);
        this.permutation(this.tag_buffer);
        this.xor_block(this.tag_buffer, this.expanded_key, 0, this.BLOCK_SIZE);
        if (this.forEncryption) {
            System.arraycopy(this.tag_buffer, 0, this.tag, 0, this.CRYPTO_ABYTES);
            System.arraycopy(this.tag, 0, byArray, n, this.tag.length);
            n3 += this.CRYPTO_ABYTES;
        } else {
            this.inputOff -= this.CRYPTO_ABYTES;
            for (int i = 0; i < this.CRYPTO_ABYTES; ++i) {
                if (this.tag_buffer[i] == this.inputMessage[this.inputOff + i]) continue;
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
        switch (this.m_state) {
            case Uninitialized: {
                throw new IllegalArgumentException(this.algorithmName + " needs call init function before getUpdateOutputSize");
            }
            case DecFinal: 
            case EncFinal: {
                return 0;
            }
            case EncInit: 
            case EncAad: 
            case EncData: {
                return this.inputOff + n + this.CRYPTO_ABYTES;
            }
        }
        return Math.max(0, n + this.inputOff - this.CRYPTO_ABYTES);
    }

    @Override
    public int getOutputSize(int n) {
        switch (this.m_state) {
            case Uninitialized: {
                throw new IllegalArgumentException(this.algorithmName + " needs call init function before getUpdateOutputSize");
            }
            case DecFinal: 
            case EncFinal: {
                return 0;
            }
            case EncInit: 
            case EncAad: 
            case EncData: {
                return n + this.CRYPTO_ABYTES;
            }
        }
        return Math.max(0, n - this.CRYPTO_ABYTES);
    }

    @Override
    public void reset() {
        this.reset(true);
    }

    private void reset(boolean bl) {
        if (bl) {
            this.tag = null;
        }
        this.aadData.reset();
        Arrays.fill(this.tag_buffer, (byte)0);
        this.inputOff = 0;
        this.nb_its = 0;
        this.adOff = -1;
    }

    public int getKeyBytesSize() {
        return 16;
    }

    public int getIVBytesSize() {
        return 12;
    }

    public int getBlockSize() {
        return this.CRYPTO_ABYTES;
    }

    private void checkAad() {
        switch (this.m_state) {
            case DecData: {
                throw new IllegalArgumentException(this.algorithmName + " cannot process AAD when the length of the plaintext to be processed exceeds the a block size");
            }
            case EncData: {
                throw new IllegalArgumentException(this.algorithmName + " cannot process AAD when the length of the ciphertext to be processed exceeds the a block size");
            }
            case EncFinal: {
                throw new IllegalArgumentException(this.algorithmName + " cannot be reused for encryption");
            }
        }
    }

    private void processAADBytes(byte[] byArray) {
        this.checkAad();
        if (this.adOff == -1) {
            this.adlen = this.aadData.size();
            this.ad = this.aadData.toByteArray();
            this.adOff = 0;
        }
        int n = 0;
        switch (this.m_state) {
            case DecInit: {
                System.arraycopy(this.expanded_key, 0, this.current_mask, 0, this.BLOCK_SIZE);
                System.arraycopy(this.npub, 0, byArray, 0, 12);
                n += 12;
                this.m_state = State.DecAad;
                break;
            }
            case EncInit: {
                System.arraycopy(this.expanded_key, 0, this.current_mask, 0, this.BLOCK_SIZE);
                System.arraycopy(this.npub, 0, byArray, 0, 12);
                n += 12;
                this.m_state = State.EncAad;
                break;
            }
            case EncAad: 
            case DecAad: {
                if (this.adOff != this.adlen) break;
                Arrays.fill(byArray, 0, this.BLOCK_SIZE, (byte)0);
                byArray[0] = 1;
                return;
            }
            case DecData: {
                throw new IllegalArgumentException(this.algorithmName + " cannot process AAD when the length of the plaintext to be processed exceeds the a block size");
            }
            case EncData: {
                throw new IllegalArgumentException(this.algorithmName + " cannot process AAD when the length of the ciphertext to be processed exceeds the a block size");
            }
            case EncFinal: {
                throw new IllegalArgumentException(this.algorithmName + " cannot be reused for encryption");
            }
        }
        int n2 = this.BLOCK_SIZE - n;
        int n3 = this.adlen - this.adOff;
        if (n2 <= n3) {
            System.arraycopy(this.ad, this.adOff, byArray, n, n2);
            this.adOff += n2;
        } else {
            if (n3 > 0) {
                System.arraycopy(this.ad, this.adOff, byArray, n, n3);
                this.adOff += n3;
            }
            Arrays.fill(byArray, n + n3, n + n2, (byte)0);
            byArray[n + n3] = 1;
            switch (this.m_state) {
                case DecAad: {
                    this.m_state = State.DecData;
                    break;
                }
                case EncAad: {
                    this.m_state = State.EncData;
                }
            }
        }
    }

    private int processBytes(byte[] byArray, byte[] byArray2, int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = 0;
        for (int i = this.nb_its; i < n2; ++i) {
            this.lfsr_step(this.next_mask, this.current_mask);
            if (i < n3) {
                System.arraycopy(this.npub, 0, this.buffer, 0, 12);
                Arrays.fill(this.buffer, 12, this.BLOCK_SIZE, (byte)0);
                this.xor_block(this.buffer, this.current_mask, 0, this.BLOCK_SIZE);
                this.xor_block(this.buffer, this.next_mask, 0, this.BLOCK_SIZE);
                this.permutation(this.buffer);
                this.xor_block(this.buffer, this.current_mask, 0, this.BLOCK_SIZE);
                this.xor_block(this.buffer, this.next_mask, 0, this.BLOCK_SIZE);
                int n8 = i == n3 - 1 ? n5 - i * this.BLOCK_SIZE : this.BLOCK_SIZE;
                this.xor_block(this.buffer, byArray, 0, n8);
                System.arraycopy(this.buffer, 0, byArray2, n, n8);
                if (this.forEncryption) {
                    System.arraycopy(this.buffer, 0, this.outputMessage, 0, n8);
                } else {
                    System.arraycopy(byArray, 0, this.outputMessage, 0, n8);
                }
                n7 += n8;
            }
            if (i > 0 && i <= n4) {
                this.get_c_block(this.buffer, this.previous_outputMessage, 0, n5, i - 1);
                this.xor_block(this.buffer, this.previous_mask, 0, this.BLOCK_SIZE);
                this.xor_block(this.buffer, this.next_mask, 0, this.BLOCK_SIZE);
                this.permutation(this.buffer);
                this.xor_block(this.buffer, this.previous_mask, 0, this.BLOCK_SIZE);
                this.xor_block(this.buffer, this.next_mask, 0, this.BLOCK_SIZE);
                this.xor_block(this.tag_buffer, this.buffer, 0, this.BLOCK_SIZE);
            }
            if (i + 1 < n6) {
                this.processAADBytes(this.buffer);
                this.xor_block(this.buffer, this.next_mask, 0, this.BLOCK_SIZE);
                this.permutation(this.buffer);
                this.xor_block(this.buffer, this.next_mask, 0, this.BLOCK_SIZE);
                this.xor_block(this.tag_buffer, this.buffer, 0, this.BLOCK_SIZE);
            }
            byte[] byArray3 = this.previous_mask;
            this.previous_mask = this.current_mask;
            this.current_mask = this.next_mask;
            this.next_mask = byArray3;
            System.arraycopy(this.outputMessage, 0, this.previous_outputMessage, 0, this.BLOCK_SIZE);
        }
        return n7;
    }

    public static enum ElephantParameters {
        elephant160,
        elephant176,
        elephant200;

    }

    private static enum State {
        Uninitialized,
        EncInit,
        EncAad,
        EncData,
        EncFinal,
        DecInit,
        DecAad,
        DecData,
        DecFinal;

    }
}

