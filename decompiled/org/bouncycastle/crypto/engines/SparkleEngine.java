/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServiceProperties;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import org.bouncycastle.crypto.digests.SparkleDigest;
import org.bouncycastle.crypto.engines.Utils;
import org.bouncycastle.crypto.modes.AEADCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Pack;

public class SparkleEngine
implements AEADCipher {
    private static final int[] RCON = new int[]{-1209970334, -1083090816, 951376470, 844003128, -1156479509, 1333558103, -809524792, -1028445891};
    private String algorithmName;
    private final int[] state;
    private final int[] k;
    private final int[] npub;
    private byte[] tag;
    private boolean encrypted;
    private State m_state = State.Uninitialized;
    private byte[] initialAssociatedText;
    private final int m_bufferSizeDecrypt;
    private final byte[] m_buf;
    private int m_bufPos = 0;
    private final int SCHWAEMM_KEY_LEN;
    private final int SCHWAEMM_NONCE_LEN;
    private final int SPARKLE_STEPS_SLIM;
    private final int SPARKLE_STEPS_BIG;
    private final int KEY_WORDS;
    private final int KEY_BYTES;
    private final int TAG_WORDS;
    private final int TAG_BYTES;
    private final int STATE_WORDS;
    private final int RATE_WORDS;
    private final int RATE_BYTES;
    private final int CAP_MASK;
    private final int _A0;
    private final int _A1;
    private final int _M2;
    private final int _M3;

    public SparkleEngine(SparkleParameters sparkleParameters) {
        int n;
        int n2;
        int n3;
        switch (sparkleParameters) {
            case SCHWAEMM128_128: {
                this.SCHWAEMM_KEY_LEN = 128;
                this.SCHWAEMM_NONCE_LEN = 128;
                n3 = 128;
                n2 = 256;
                n = 128;
                this.SPARKLE_STEPS_SLIM = 7;
                this.SPARKLE_STEPS_BIG = 10;
                this.algorithmName = "SCHWAEMM128-128";
                break;
            }
            case SCHWAEMM256_128: {
                this.SCHWAEMM_KEY_LEN = 128;
                this.SCHWAEMM_NONCE_LEN = 256;
                n3 = 128;
                n2 = 384;
                n = 128;
                this.SPARKLE_STEPS_SLIM = 7;
                this.SPARKLE_STEPS_BIG = 11;
                this.algorithmName = "SCHWAEMM256-128";
                break;
            }
            case SCHWAEMM192_192: {
                this.SCHWAEMM_KEY_LEN = 192;
                this.SCHWAEMM_NONCE_LEN = 192;
                n3 = 192;
                n2 = 384;
                n = 192;
                this.SPARKLE_STEPS_SLIM = 7;
                this.SPARKLE_STEPS_BIG = 11;
                this.algorithmName = "SCHWAEMM192-192";
                break;
            }
            case SCHWAEMM256_256: {
                this.SCHWAEMM_KEY_LEN = 256;
                this.SCHWAEMM_NONCE_LEN = 256;
                n3 = 256;
                n2 = 512;
                n = 256;
                this.SPARKLE_STEPS_SLIM = 8;
                this.SPARKLE_STEPS_BIG = 12;
                this.algorithmName = "SCHWAEMM256-256";
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid definition of SCHWAEMM instance");
            }
        }
        this.KEY_WORDS = this.SCHWAEMM_KEY_LEN >>> 5;
        this.KEY_BYTES = this.SCHWAEMM_KEY_LEN >>> 3;
        this.TAG_WORDS = n3 >>> 5;
        this.TAG_BYTES = n3 >>> 3;
        this.STATE_WORDS = n2 >>> 5;
        this.RATE_WORDS = this.SCHWAEMM_NONCE_LEN >>> 5;
        this.RATE_BYTES = this.SCHWAEMM_NONCE_LEN >>> 3;
        int n4 = n >>> 6;
        int n5 = n >>> 5;
        this.CAP_MASK = this.RATE_WORDS > n5 ? n5 - 1 : -1;
        this._A0 = 1 << n4 << 24;
        this._A1 = (1 ^ 1 << n4) << 24;
        this._M2 = (2 ^ 1 << n4) << 24;
        this._M3 = (3 ^ 1 << n4) << 24;
        this.state = new int[this.STATE_WORDS];
        this.k = new int[this.KEY_WORDS];
        this.npub = new int[this.RATE_WORDS];
        this.m_bufferSizeDecrypt = this.RATE_BYTES + this.TAG_BYTES;
        this.m_buf = new byte[this.m_bufferSizeDecrypt];
    }

    public int getKeyBytesSize() {
        return this.KEY_BYTES;
    }

    public int getIVBytesSize() {
        return this.RATE_BYTES;
    }

    @Override
    public String getAlgorithmName() {
        return this.algorithmName;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        int n;
        byte[] byArray;
        CipherParameters cipherParameters2;
        KeyParameter keyParameter = null;
        if (cipherParameters instanceof AEADParameters) {
            cipherParameters2 = (AEADParameters)cipherParameters;
            keyParameter = ((AEADParameters)cipherParameters2).getKey();
            byArray = ((AEADParameters)cipherParameters2).getNonce();
            this.initialAssociatedText = ((AEADParameters)cipherParameters2).getAssociatedText();
            n = ((AEADParameters)cipherParameters2).getMacSize();
            if (n != this.TAG_BYTES * 8) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + n);
            }
        } else if (cipherParameters instanceof ParametersWithIV) {
            cipherParameters2 = (ParametersWithIV)cipherParameters;
            CipherParameters cipherParameters3 = ((ParametersWithIV)cipherParameters2).getParameters();
            if (cipherParameters3 instanceof KeyParameter) {
                keyParameter = (KeyParameter)cipherParameters3;
            }
            byArray = ((ParametersWithIV)cipherParameters2).getIV();
            this.initialAssociatedText = null;
        } else {
            throw new IllegalArgumentException("invalid parameters passed to Sparkle");
        }
        if (keyParameter == null) {
            throw new IllegalArgumentException("Sparkle init parameters must include a key");
        }
        int n2 = this.KEY_WORDS * 4;
        if (n2 != keyParameter.getKeyLength()) {
            throw new IllegalArgumentException(this.algorithmName + " requires exactly " + n2 + " bytes of key");
        }
        n = this.RATE_WORDS * 4;
        if (byArray == null || n != byArray.length) {
            throw new IllegalArgumentException(this.algorithmName + " requires exactly " + n + " bytes of IV");
        }
        Pack.littleEndianToInt(keyParameter.getKey(), 0, this.k);
        Pack.littleEndianToInt(byArray, 0, this.npub);
        CryptoServicesRegistrar.checkConstraints((CryptoServiceProperties)new DefaultServiceProperties(this.getAlgorithmName(), 128, cipherParameters, Utils.getPurpose(bl)));
        this.m_state = bl ? State.EncInit : State.DecInit;
        this.reset();
    }

    @Override
    public void processAADByte(byte by) {
        this.checkAAD();
        if (this.m_bufPos == this.RATE_BYTES) {
            this.processBufferAAD(this.m_buf, 0);
            this.m_bufPos = 0;
        }
        this.m_buf[this.m_bufPos++] = by;
    }

    @Override
    public void processAADBytes(byte[] byArray, int n, int n2) {
        if (n > byArray.length - n2) {
            throw new DataLengthException("input buffer too short");
        }
        if (n2 <= 0) {
            return;
        }
        this.checkAAD();
        if (this.m_bufPos > 0) {
            int n3 = this.RATE_BYTES - this.m_bufPos;
            if (n2 <= n3) {
                System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n2);
                this.m_bufPos += n2;
                return;
            }
            System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n3);
            n += n3;
            n2 -= n3;
            this.processBufferAAD(this.m_buf, 0);
        }
        while (n2 > this.RATE_BYTES) {
            this.processBufferAAD(byArray, n);
            n += this.RATE_BYTES;
            n2 -= this.RATE_BYTES;
        }
        System.arraycopy(byArray, n, this.m_buf, 0, n2);
        this.m_bufPos = n2;
    }

    @Override
    public int processByte(byte by, byte[] byArray, int n) throws DataLengthException {
        return this.processBytes(new byte[]{by}, 0, 1, byArray, n);
    }

    @Override
    public int processBytes(byte[] byArray, int n, int n2, byte[] byArray2, int n3) throws DataLengthException {
        if (n > byArray.length - n2) {
            throw new DataLengthException("input buffer too short");
        }
        boolean bl = this.checkData();
        int n4 = 0;
        if (bl) {
            if (this.m_bufPos > 0) {
                int n5 = this.RATE_BYTES - this.m_bufPos;
                if (n2 <= n5) {
                    System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n2);
                    this.m_bufPos += n2;
                    return 0;
                }
                System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n5);
                n += n5;
                n2 -= n5;
                this.processBufferEncrypt(this.m_buf, 0, byArray2, n3);
                n4 = this.RATE_BYTES;
            }
            while (n2 > this.RATE_BYTES) {
                this.processBufferEncrypt(byArray, n, byArray2, n3 + n4);
                n += this.RATE_BYTES;
                n2 -= this.RATE_BYTES;
                n4 += this.RATE_BYTES;
            }
        } else {
            int n6 = this.m_bufferSizeDecrypt - this.m_bufPos;
            if (n2 <= n6) {
                System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n2);
                this.m_bufPos += n2;
                return 0;
            }
            if (this.m_bufPos > this.RATE_BYTES) {
                this.processBufferDecrypt(this.m_buf, 0, byArray2, n3);
                this.m_bufPos -= this.RATE_BYTES;
                System.arraycopy(this.m_buf, this.RATE_BYTES, this.m_buf, 0, this.m_bufPos);
                n4 = this.RATE_BYTES;
                if (n2 <= (n6 += this.RATE_BYTES)) {
                    System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n2);
                    this.m_bufPos += n2;
                    return n4;
                }
            }
            n6 = this.RATE_BYTES - this.m_bufPos;
            System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n6);
            n += n6;
            n2 -= n6;
            this.processBufferDecrypt(this.m_buf, 0, byArray2, n3 + n4);
            n4 += this.RATE_BYTES;
            while (n2 > this.m_bufferSizeDecrypt) {
                this.processBufferDecrypt(byArray, n, byArray2, n3 + n4);
                n += this.RATE_BYTES;
                n2 -= this.RATE_BYTES;
                n4 += this.RATE_BYTES;
            }
        }
        System.arraycopy(byArray, n, this.m_buf, 0, n2);
        this.m_bufPos = n2;
        return n4;
    }

    @Override
    public int doFinal(byte[] byArray, int n) throws IllegalStateException, InvalidCipherTextException {
        int n2;
        boolean bl = this.checkData();
        if (bl) {
            n2 = this.m_bufPos + this.TAG_BYTES;
        } else {
            if (this.m_bufPos < this.TAG_BYTES) {
                throw new InvalidCipherTextException("data too short");
            }
            this.m_bufPos -= this.TAG_BYTES;
            n2 = this.m_bufPos;
        }
        if (n > byArray.length - n2) {
            throw new OutputLengthException("output buffer too short");
        }
        if (this.encrypted || this.m_bufPos > 0) {
            int n3;
            int n4 = this.STATE_WORDS - 1;
            this.state[n4] = this.state[n4] ^ (this.m_bufPos < this.RATE_BYTES ? this._M2 : this._M3);
            int[] nArray = new int[this.RATE_WORDS];
            for (n3 = 0; n3 < this.m_bufPos; ++n3) {
                int n5 = n3 >>> 2;
                nArray[n5] = nArray[n5] | (this.m_buf[n3] & 0xFF) << ((n3 & 3) << 3);
            }
            if (this.m_bufPos < this.RATE_BYTES) {
                if (!bl) {
                    n3 = (this.m_bufPos & 3) << 3;
                    int n6 = this.m_bufPos >>> 2;
                    nArray[n6] = nArray[n6] | this.state[this.m_bufPos >>> 2] >>> n3 << n3;
                    n3 = (this.m_bufPos >>> 2) + 1;
                    System.arraycopy(this.state, n3, nArray, n3, this.RATE_WORDS - n3);
                }
                int n7 = this.m_bufPos >>> 2;
                nArray[n7] = nArray[n7] ^ 128 << ((this.m_bufPos & 3) << 3);
            }
            n3 = 0;
            while (n3 < this.RATE_WORDS / 2) {
                int n8 = n3 + this.RATE_WORDS / 2;
                int n9 = this.state[n3];
                int n10 = this.state[n8];
                if (bl) {
                    this.state[n3] = n10 ^ nArray[n3] ^ this.state[this.RATE_WORDS + n3];
                    this.state[n8] = n9 ^ n10 ^ nArray[n8] ^ this.state[this.RATE_WORDS + (n8 & this.CAP_MASK)];
                } else {
                    this.state[n3] = n9 ^ n10 ^ nArray[n3] ^ this.state[this.RATE_WORDS + n3];
                    this.state[n8] = n9 ^ nArray[n8] ^ this.state[this.RATE_WORDS + (n8 & this.CAP_MASK)];
                }
                int n11 = n3++;
                nArray[n11] = nArray[n11] ^ n9;
                int n12 = n8;
                nArray[n12] = nArray[n12] ^ n10;
            }
            for (n3 = 0; n3 < this.m_bufPos; ++n3) {
                byArray[n++] = (byte)(nArray[n3 >>> 2] >>> ((n3 & 3) << 3));
            }
            SparkleEngine.sparkle_opt(this.state, this.SPARKLE_STEPS_BIG);
        }
        for (int i = 0; i < this.KEY_WORDS; ++i) {
            int n13 = this.RATE_WORDS + i;
            this.state[n13] = this.state[n13] ^ this.k[i];
        }
        this.tag = new byte[this.TAG_BYTES];
        Pack.intToLittleEndian(this.state, this.RATE_WORDS, this.TAG_WORDS, this.tag, 0);
        if (bl) {
            System.arraycopy(this.tag, 0, byArray, n, this.TAG_BYTES);
        } else if (!Arrays.constantTimeAreEqual(this.TAG_BYTES, this.tag, 0, this.m_buf, this.m_bufPos)) {
            throw new InvalidCipherTextException(this.algorithmName + " mac does not match");
        }
        this.reset(!bl);
        return n2;
    }

    @Override
    public byte[] getMac() {
        return this.tag;
    }

    @Override
    public int getUpdateOutputSize(int n) {
        int n2 = Math.max(0, n) - 1;
        switch (this.m_state) {
            case DecInit: 
            case DecAad: {
                n2 = Math.max(0, n2 - this.TAG_BYTES);
                break;
            }
            case DecData: 
            case DecFinal: {
                n2 = Math.max(0, n2 + this.m_bufPos - this.TAG_BYTES);
                break;
            }
            case EncData: 
            case EncFinal: {
                n2 = Math.max(0, n2 + this.m_bufPos);
                break;
            }
        }
        return n2 - n2 % this.RATE_BYTES;
    }

    @Override
    public int getOutputSize(int n) {
        int n2 = Math.max(0, n);
        switch (this.m_state) {
            case DecInit: 
            case DecAad: {
                return Math.max(0, n2 - this.TAG_BYTES);
            }
            case DecData: 
            case DecFinal: {
                return Math.max(0, n2 + this.m_bufPos - this.TAG_BYTES);
            }
            case EncData: 
            case EncFinal: {
                return n2 + this.m_bufPos + this.TAG_BYTES;
            }
        }
        return n2 + this.TAG_BYTES;
    }

    @Override
    public void reset() {
        this.reset(true);
    }

    private void checkAAD() {
        switch (this.m_state) {
            case DecInit: {
                this.m_state = State.DecAad;
                break;
            }
            case EncInit: {
                this.m_state = State.EncAad;
                break;
            }
            case DecAad: 
            case EncAad: {
                break;
            }
            case EncFinal: {
                throw new IllegalStateException(this.getAlgorithmName() + " cannot be reused for encryption");
            }
            default: {
                throw new IllegalStateException(this.getAlgorithmName() + " needs to be initialized");
            }
        }
    }

    private boolean checkData() {
        switch (this.m_state) {
            case DecInit: 
            case DecAad: {
                this.finishAAD(State.DecData);
                return false;
            }
            case EncInit: 
            case EncAad: {
                this.finishAAD(State.EncData);
                return true;
            }
            case DecData: {
                return false;
            }
            case EncData: {
                return true;
            }
            case EncFinal: {
                throw new IllegalStateException(this.getAlgorithmName() + " cannot be reused for encryption");
            }
        }
        throw new IllegalStateException(this.getAlgorithmName() + " needs to be initialized");
    }

    private void finishAAD(State state) {
        switch (this.m_state) {
            case DecAad: 
            case EncAad: {
                this.processFinalAAD();
                break;
            }
        }
        this.m_bufPos = 0;
        this.m_state = state;
    }

    private void processBufferAAD(byte[] byArray, int n) {
        for (int i = 0; i < this.RATE_WORDS / 2; ++i) {
            int n2 = i + this.RATE_WORDS / 2;
            int n3 = this.state[i];
            int n4 = this.state[n2];
            int n5 = Pack.littleEndianToInt(byArray, n + i * 4);
            int n6 = Pack.littleEndianToInt(byArray, n + n2 * 4);
            this.state[i] = n4 ^ n5 ^ this.state[this.RATE_WORDS + i];
            this.state[n2] = n3 ^ n4 ^ n6 ^ this.state[this.RATE_WORDS + (n2 & this.CAP_MASK)];
        }
        SparkleEngine.sparkle_opt(this.state, this.SPARKLE_STEPS_SLIM);
    }

    private void processBufferDecrypt(byte[] byArray, int n, byte[] byArray2, int n2) {
        if (n2 > byArray2.length - this.RATE_BYTES) {
            throw new OutputLengthException("output buffer too short");
        }
        for (int i = 0; i < this.RATE_WORDS / 2; ++i) {
            int n3 = i + this.RATE_WORDS / 2;
            int n4 = this.state[i];
            int n5 = this.state[n3];
            int n6 = Pack.littleEndianToInt(byArray, n + i * 4);
            int n7 = Pack.littleEndianToInt(byArray, n + n3 * 4);
            this.state[i] = n4 ^ n5 ^ n6 ^ this.state[this.RATE_WORDS + i];
            this.state[n3] = n4 ^ n7 ^ this.state[this.RATE_WORDS + (n3 & this.CAP_MASK)];
            Pack.intToLittleEndian(n6 ^ n4, byArray2, n2 + i * 4);
            Pack.intToLittleEndian(n7 ^ n5, byArray2, n2 + n3 * 4);
        }
        SparkleEngine.sparkle_opt(this.state, this.SPARKLE_STEPS_SLIM);
        this.encrypted = true;
    }

    private void processBufferEncrypt(byte[] byArray, int n, byte[] byArray2, int n2) {
        if (n2 > byArray2.length - this.RATE_BYTES) {
            throw new OutputLengthException("output buffer too short");
        }
        for (int i = 0; i < this.RATE_WORDS / 2; ++i) {
            int n3 = i + this.RATE_WORDS / 2;
            int n4 = this.state[i];
            int n5 = this.state[n3];
            int n6 = Pack.littleEndianToInt(byArray, n + i * 4);
            int n7 = Pack.littleEndianToInt(byArray, n + n3 * 4);
            this.state[i] = n5 ^ n6 ^ this.state[this.RATE_WORDS + i];
            this.state[n3] = n4 ^ n5 ^ n7 ^ this.state[this.RATE_WORDS + (n3 & this.CAP_MASK)];
            Pack.intToLittleEndian(n6 ^ n4, byArray2, n2 + i * 4);
            Pack.intToLittleEndian(n7 ^ n5, byArray2, n2 + n3 * 4);
        }
        SparkleEngine.sparkle_opt(this.state, this.SPARKLE_STEPS_SLIM);
        this.encrypted = true;
    }

    private void processFinalAAD() {
        if (this.m_bufPos < this.RATE_BYTES) {
            int n = this.STATE_WORDS - 1;
            this.state[n] = this.state[n] ^ this._A0;
            this.m_buf[this.m_bufPos] = -128;
            while (++this.m_bufPos < this.RATE_BYTES) {
                this.m_buf[this.m_bufPos] = 0;
            }
        } else {
            int n = this.STATE_WORDS - 1;
            this.state[n] = this.state[n] ^ this._A1;
        }
        for (int i = 0; i < this.RATE_WORDS / 2; ++i) {
            int n = i + this.RATE_WORDS / 2;
            int n2 = this.state[i];
            int n3 = this.state[n];
            int n4 = Pack.littleEndianToInt(this.m_buf, i * 4);
            int n5 = Pack.littleEndianToInt(this.m_buf, n * 4);
            this.state[i] = n3 ^ n4 ^ this.state[this.RATE_WORDS + i];
            this.state[n] = n2 ^ n3 ^ n5 ^ this.state[this.RATE_WORDS + (n & this.CAP_MASK)];
        }
        SparkleEngine.sparkle_opt(this.state, this.SPARKLE_STEPS_BIG);
    }

    private void reset(boolean bl) {
        if (bl) {
            this.tag = null;
        }
        Arrays.clear(this.m_buf);
        this.m_bufPos = 0;
        this.encrypted = false;
        switch (this.m_state) {
            case DecInit: 
            case EncInit: {
                break;
            }
            case DecAad: 
            case DecData: 
            case DecFinal: {
                this.m_state = State.DecInit;
                break;
            }
            case EncData: 
            case EncFinal: 
            case EncAad: {
                this.m_state = State.EncFinal;
                return;
            }
            default: {
                throw new IllegalStateException(this.getAlgorithmName() + " needs to be initialized");
            }
        }
        System.arraycopy(this.npub, 0, this.state, 0, this.RATE_WORDS);
        System.arraycopy(this.k, 0, this.state, this.RATE_WORDS, this.KEY_WORDS);
        SparkleEngine.sparkle_opt(this.state, this.SPARKLE_STEPS_BIG);
        if (this.initialAssociatedText != null) {
            this.processAADBytes(this.initialAssociatedText, 0, this.initialAssociatedText.length);
        }
    }

    private static int ELL(int n) {
        return Integers.rotateRight(n, 16) ^ n & 0xFFFF;
    }

    private static void sparkle_opt(int[] nArray, int n) {
        switch (nArray.length) {
            case 8: {
                SparkleEngine.sparkle_opt8(nArray, n);
                break;
            }
            case 12: {
                SparkleEngine.sparkle_opt12(nArray, n);
                break;
            }
            case 16: {
                SparkleEngine.sparkle_opt16(nArray, n);
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }

    static void sparkle_opt8(int[] nArray, int n) {
        int n2 = nArray[0];
        int n3 = nArray[1];
        int n4 = nArray[2];
        int n5 = nArray[3];
        int n6 = nArray[4];
        int n7 = nArray[5];
        int n8 = nArray[6];
        int n9 = nArray[7];
        for (int i = 0; i < n; ++i) {
            n5 ^= i;
            int n10 = RCON[0];
            n2 += Integers.rotateRight(n3 ^= RCON[i & 7], 31);
            n3 ^= Integers.rotateRight(n2, 24);
            n2 ^= n10;
            n2 += Integers.rotateRight(n3, 17);
            n3 ^= Integers.rotateRight(n2, 17);
            n2 ^= n10;
            n2 += n3;
            n3 ^= Integers.rotateRight(n2, 31);
            n2 ^= n10;
            n2 += Integers.rotateRight(n3, 24);
            n3 ^= Integers.rotateRight(n2, 16);
            n2 ^= n10;
            n10 = RCON[1];
            n4 += Integers.rotateRight(n5, 31);
            n5 ^= Integers.rotateRight(n4, 24);
            n4 ^= n10;
            n4 += Integers.rotateRight(n5, 17);
            n5 ^= Integers.rotateRight(n4, 17);
            n4 ^= n10;
            n4 += n5;
            n5 ^= Integers.rotateRight(n4, 31);
            n4 ^= n10;
            n4 += Integers.rotateRight(n5, 24);
            n5 ^= Integers.rotateRight(n4, 16);
            n4 ^= n10;
            n10 = RCON[2];
            n6 += Integers.rotateRight(n7, 31);
            n7 ^= Integers.rotateRight(n6, 24);
            n6 ^= n10;
            n6 += Integers.rotateRight(n7, 17);
            n7 ^= Integers.rotateRight(n6, 17);
            n6 ^= n10;
            n6 += n7;
            n7 ^= Integers.rotateRight(n6, 31);
            n6 ^= n10;
            n6 += Integers.rotateRight(n7, 24);
            n7 ^= Integers.rotateRight(n6, 16);
            n6 ^= n10;
            n10 = RCON[3];
            n8 += Integers.rotateRight(n9, 31);
            n9 ^= Integers.rotateRight(n8, 24);
            n8 ^= n10;
            n8 += Integers.rotateRight(n9, 17);
            n9 ^= Integers.rotateRight(n8, 17);
            n8 ^= n10;
            n8 += n9;
            n9 ^= Integers.rotateRight(n8, 31);
            n8 ^= n10;
            n8 += Integers.rotateRight(n9, 24);
            n9 ^= Integers.rotateRight(n8, 16);
            n8 ^= n10;
            n10 = SparkleEngine.ELL(n2 ^ n4);
            int n11 = SparkleEngine.ELL(n3 ^ n5);
            int n12 = n2 ^ n6;
            int n13 = n3 ^ n7;
            int n14 = n4 ^ n8;
            int n15 = n5 ^ n9;
            n6 = n2;
            n7 = n3;
            n8 = n4;
            n9 = n5;
            n2 = n14 ^ n11;
            n3 = n15 ^ n10;
            n4 = n12 ^ n11;
            n5 = n13 ^ n10;
        }
        nArray[0] = n2;
        nArray[1] = n3;
        nArray[2] = n4;
        nArray[3] = n5;
        nArray[4] = n6;
        nArray[5] = n7;
        nArray[6] = n8;
        nArray[7] = n9;
    }

    static void sparkle_opt12(int[] nArray, int n) {
        int n2 = nArray[0];
        int n3 = nArray[1];
        int n4 = nArray[2];
        int n5 = nArray[3];
        int n6 = nArray[4];
        int n7 = nArray[5];
        int n8 = nArray[6];
        int n9 = nArray[7];
        int n10 = nArray[8];
        int n11 = nArray[9];
        int n12 = nArray[10];
        int n13 = nArray[11];
        for (int i = 0; i < n; ++i) {
            n5 ^= i;
            int n14 = RCON[0];
            n2 += Integers.rotateRight(n3 ^= RCON[i & 7], 31);
            n3 ^= Integers.rotateRight(n2, 24);
            n2 ^= n14;
            n2 += Integers.rotateRight(n3, 17);
            n3 ^= Integers.rotateRight(n2, 17);
            n2 ^= n14;
            n2 += n3;
            n3 ^= Integers.rotateRight(n2, 31);
            n2 ^= n14;
            n2 += Integers.rotateRight(n3, 24);
            n3 ^= Integers.rotateRight(n2, 16);
            n2 ^= n14;
            n14 = RCON[1];
            n4 += Integers.rotateRight(n5, 31);
            n5 ^= Integers.rotateRight(n4, 24);
            n4 ^= n14;
            n4 += Integers.rotateRight(n5, 17);
            n5 ^= Integers.rotateRight(n4, 17);
            n4 ^= n14;
            n4 += n5;
            n5 ^= Integers.rotateRight(n4, 31);
            n4 ^= n14;
            n4 += Integers.rotateRight(n5, 24);
            n5 ^= Integers.rotateRight(n4, 16);
            n4 ^= n14;
            n14 = RCON[2];
            n6 += Integers.rotateRight(n7, 31);
            n7 ^= Integers.rotateRight(n6, 24);
            n6 ^= n14;
            n6 += Integers.rotateRight(n7, 17);
            n7 ^= Integers.rotateRight(n6, 17);
            n6 ^= n14;
            n6 += n7;
            n7 ^= Integers.rotateRight(n6, 31);
            n6 ^= n14;
            n6 += Integers.rotateRight(n7, 24);
            n7 ^= Integers.rotateRight(n6, 16);
            n6 ^= n14;
            n14 = RCON[3];
            n8 += Integers.rotateRight(n9, 31);
            n9 ^= Integers.rotateRight(n8, 24);
            n8 ^= n14;
            n8 += Integers.rotateRight(n9, 17);
            n9 ^= Integers.rotateRight(n8, 17);
            n8 ^= n14;
            n8 += n9;
            n9 ^= Integers.rotateRight(n8, 31);
            n8 ^= n14;
            n8 += Integers.rotateRight(n9, 24);
            n9 ^= Integers.rotateRight(n8, 16);
            n8 ^= n14;
            n14 = RCON[4];
            n10 += Integers.rotateRight(n11, 31);
            n11 ^= Integers.rotateRight(n10, 24);
            n10 ^= n14;
            n10 += Integers.rotateRight(n11, 17);
            n11 ^= Integers.rotateRight(n10, 17);
            n10 ^= n14;
            n10 += n11;
            n11 ^= Integers.rotateRight(n10, 31);
            n10 ^= n14;
            n10 += Integers.rotateRight(n11, 24);
            n11 ^= Integers.rotateRight(n10, 16);
            n10 ^= n14;
            n14 = RCON[5];
            n12 += Integers.rotateRight(n13, 31);
            n13 ^= Integers.rotateRight(n12, 24);
            n12 ^= n14;
            n12 += Integers.rotateRight(n13, 17);
            n13 ^= Integers.rotateRight(n12, 17);
            n12 ^= n14;
            n12 += n13;
            n13 ^= Integers.rotateRight(n12, 31);
            n12 ^= n14;
            n12 += Integers.rotateRight(n13, 24);
            n13 ^= Integers.rotateRight(n12, 16);
            n12 ^= n14;
            n14 = SparkleEngine.ELL(n2 ^ n4 ^ n6);
            int n15 = SparkleEngine.ELL(n3 ^ n5 ^ n7);
            int n16 = n2 ^ n8;
            int n17 = n3 ^ n9;
            int n18 = n4 ^ n10;
            int n19 = n5 ^ n11;
            int n20 = n6 ^ n12;
            int n21 = n7 ^ n13;
            n8 = n2;
            n9 = n3;
            n10 = n4;
            n11 = n5;
            n12 = n6;
            n13 = n7;
            n2 = n18 ^ n15;
            n3 = n19 ^ n14;
            n4 = n20 ^ n15;
            n5 = n21 ^ n14;
            n6 = n16 ^ n15;
            n7 = n17 ^ n14;
        }
        nArray[0] = n2;
        nArray[1] = n3;
        nArray[2] = n4;
        nArray[3] = n5;
        nArray[4] = n6;
        nArray[5] = n7;
        nArray[6] = n8;
        nArray[7] = n9;
        nArray[8] = n10;
        nArray[9] = n11;
        nArray[10] = n12;
        nArray[11] = n13;
    }

    public static void sparkle_opt12(SparkleDigest.Friend friend, int[] nArray, int n) {
        if (null == friend) {
            throw new NullPointerException("This method is only for use by SparkleDigest");
        }
        SparkleEngine.sparkle_opt12(nArray, n);
    }

    static void sparkle_opt16(int[] nArray, int n) {
        int n2 = nArray[0];
        int n3 = nArray[1];
        int n4 = nArray[2];
        int n5 = nArray[3];
        int n6 = nArray[4];
        int n7 = nArray[5];
        int n8 = nArray[6];
        int n9 = nArray[7];
        int n10 = nArray[8];
        int n11 = nArray[9];
        int n12 = nArray[10];
        int n13 = nArray[11];
        int n14 = nArray[12];
        int n15 = nArray[13];
        int n16 = nArray[14];
        int n17 = nArray[15];
        for (int i = 0; i < n; ++i) {
            n5 ^= i;
            int n18 = RCON[0];
            n2 += Integers.rotateRight(n3 ^= RCON[i & 7], 31);
            n3 ^= Integers.rotateRight(n2, 24);
            n2 ^= n18;
            n2 += Integers.rotateRight(n3, 17);
            n3 ^= Integers.rotateRight(n2, 17);
            n2 ^= n18;
            n2 += n3;
            n3 ^= Integers.rotateRight(n2, 31);
            n2 ^= n18;
            n2 += Integers.rotateRight(n3, 24);
            n3 ^= Integers.rotateRight(n2, 16);
            n2 ^= n18;
            n18 = RCON[1];
            n4 += Integers.rotateRight(n5, 31);
            n5 ^= Integers.rotateRight(n4, 24);
            n4 ^= n18;
            n4 += Integers.rotateRight(n5, 17);
            n5 ^= Integers.rotateRight(n4, 17);
            n4 ^= n18;
            n4 += n5;
            n5 ^= Integers.rotateRight(n4, 31);
            n4 ^= n18;
            n4 += Integers.rotateRight(n5, 24);
            n5 ^= Integers.rotateRight(n4, 16);
            n4 ^= n18;
            n18 = RCON[2];
            n6 += Integers.rotateRight(n7, 31);
            n7 ^= Integers.rotateRight(n6, 24);
            n6 ^= n18;
            n6 += Integers.rotateRight(n7, 17);
            n7 ^= Integers.rotateRight(n6, 17);
            n6 ^= n18;
            n6 += n7;
            n7 ^= Integers.rotateRight(n6, 31);
            n6 ^= n18;
            n6 += Integers.rotateRight(n7, 24);
            n7 ^= Integers.rotateRight(n6, 16);
            n6 ^= n18;
            n18 = RCON[3];
            n8 += Integers.rotateRight(n9, 31);
            n9 ^= Integers.rotateRight(n8, 24);
            n8 ^= n18;
            n8 += Integers.rotateRight(n9, 17);
            n9 ^= Integers.rotateRight(n8, 17);
            n8 ^= n18;
            n8 += n9;
            n9 ^= Integers.rotateRight(n8, 31);
            n8 ^= n18;
            n8 += Integers.rotateRight(n9, 24);
            n9 ^= Integers.rotateRight(n8, 16);
            n8 ^= n18;
            n18 = RCON[4];
            n10 += Integers.rotateRight(n11, 31);
            n11 ^= Integers.rotateRight(n10, 24);
            n10 ^= n18;
            n10 += Integers.rotateRight(n11, 17);
            n11 ^= Integers.rotateRight(n10, 17);
            n10 ^= n18;
            n10 += n11;
            n11 ^= Integers.rotateRight(n10, 31);
            n10 ^= n18;
            n10 += Integers.rotateRight(n11, 24);
            n11 ^= Integers.rotateRight(n10, 16);
            n10 ^= n18;
            n18 = RCON[5];
            n12 += Integers.rotateRight(n13, 31);
            n13 ^= Integers.rotateRight(n12, 24);
            n12 ^= n18;
            n12 += Integers.rotateRight(n13, 17);
            n13 ^= Integers.rotateRight(n12, 17);
            n12 ^= n18;
            n12 += n13;
            n13 ^= Integers.rotateRight(n12, 31);
            n12 ^= n18;
            n12 += Integers.rotateRight(n13, 24);
            n13 ^= Integers.rotateRight(n12, 16);
            n12 ^= n18;
            n18 = RCON[6];
            n14 += Integers.rotateRight(n15, 31);
            n15 ^= Integers.rotateRight(n14, 24);
            n14 ^= n18;
            n14 += Integers.rotateRight(n15, 17);
            n15 ^= Integers.rotateRight(n14, 17);
            n14 ^= n18;
            n14 += n15;
            n15 ^= Integers.rotateRight(n14, 31);
            n14 ^= n18;
            n14 += Integers.rotateRight(n15, 24);
            n15 ^= Integers.rotateRight(n14, 16);
            n14 ^= n18;
            n18 = RCON[7];
            n16 += Integers.rotateRight(n17, 31);
            n17 ^= Integers.rotateRight(n16, 24);
            n16 ^= n18;
            n16 += Integers.rotateRight(n17, 17);
            n17 ^= Integers.rotateRight(n16, 17);
            n16 ^= n18;
            n16 += n17;
            n17 ^= Integers.rotateRight(n16, 31);
            n16 ^= n18;
            n16 += Integers.rotateRight(n17, 24);
            n17 ^= Integers.rotateRight(n16, 16);
            n16 ^= n18;
            n18 = SparkleEngine.ELL(n2 ^ n4 ^ n6 ^ n8);
            int n19 = SparkleEngine.ELL(n3 ^ n5 ^ n7 ^ n9);
            int n20 = n2 ^ n10;
            int n21 = n3 ^ n11;
            int n22 = n4 ^ n12;
            int n23 = n5 ^ n13;
            int n24 = n6 ^ n14;
            int n25 = n7 ^ n15;
            int n26 = n8 ^ n16;
            int n27 = n9 ^ n17;
            n10 = n2;
            n11 = n3;
            n12 = n4;
            n13 = n5;
            n14 = n6;
            n15 = n7;
            n16 = n8;
            n17 = n9;
            n2 = n22 ^ n19;
            n3 = n23 ^ n18;
            n4 = n24 ^ n19;
            n5 = n25 ^ n18;
            n6 = n26 ^ n19;
            n7 = n27 ^ n18;
            n8 = n20 ^ n19;
            n9 = n21 ^ n18;
        }
        nArray[0] = n2;
        nArray[1] = n3;
        nArray[2] = n4;
        nArray[3] = n5;
        nArray[4] = n6;
        nArray[5] = n7;
        nArray[6] = n8;
        nArray[7] = n9;
        nArray[8] = n10;
        nArray[9] = n11;
        nArray[10] = n12;
        nArray[11] = n13;
        nArray[12] = n14;
        nArray[13] = n15;
        nArray[14] = n16;
        nArray[15] = n17;
    }

    public static void sparkle_opt16(SparkleDigest.Friend friend, int[] nArray, int n) {
        if (null == friend) {
            throw new NullPointerException("This method is only for use by SparkleDigest");
        }
        SparkleEngine.sparkle_opt16(nArray, n);
    }

    public static enum SparkleParameters {
        SCHWAEMM128_128,
        SCHWAEMM256_128,
        SCHWAEMM192_192,
        SCHWAEMM256_256;

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

