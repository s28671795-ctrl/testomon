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
import org.bouncycastle.crypto.engines.Utils;
import org.bouncycastle.crypto.modes.AEADCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Longs;
import org.bouncycastle.util.Pack;

public class AsconEngine
implements AEADCipher {
    private final AsconParameters asconParameters;
    private State m_state = State.Uninitialized;
    private byte[] mac;
    private byte[] initialAssociatedText;
    private final String algorithmName;
    private final int CRYPTO_KEYBYTES;
    private final int CRYPTO_ABYTES;
    private final int ASCON_AEAD_RATE;
    private final int nr;
    private long K0;
    private long K1;
    private long K2;
    private long N0;
    private long N1;
    private final long ASCON_IV;
    private long x0;
    private long x1;
    private long x2;
    private long x3;
    private long x4;
    private final int m_bufferSizeDecrypt;
    private final byte[] m_buf;
    private int m_bufPos = 0;

    public AsconEngine(AsconParameters asconParameters) {
        this.asconParameters = asconParameters;
        switch (asconParameters) {
            case ascon80pq: {
                this.CRYPTO_KEYBYTES = 20;
                this.CRYPTO_ABYTES = 16;
                this.ASCON_AEAD_RATE = 8;
                this.ASCON_IV = -6899501409222262784L;
                this.algorithmName = "Ascon-80pq AEAD";
                break;
            }
            case ascon128a: {
                this.CRYPTO_KEYBYTES = 16;
                this.CRYPTO_ABYTES = 16;
                this.ASCON_AEAD_RATE = 16;
                this.ASCON_IV = -9187330011336540160L;
                this.algorithmName = "Ascon-128a AEAD";
                break;
            }
            case ascon128: {
                this.CRYPTO_KEYBYTES = 16;
                this.CRYPTO_ABYTES = 16;
                this.ASCON_AEAD_RATE = 8;
                this.ASCON_IV = -9205344418435956736L;
                this.algorithmName = "Ascon-128 AEAD";
                break;
            }
            default: {
                throw new IllegalArgumentException("invalid parameter setting for ASCON AEAD");
            }
        }
        this.nr = this.ASCON_AEAD_RATE == 8 ? 6 : 8;
        this.m_bufferSizeDecrypt = this.ASCON_AEAD_RATE + this.CRYPTO_ABYTES;
        this.m_buf = new byte[this.m_bufferSizeDecrypt];
    }

    private long PAD(int n) {
        return 128L << 56 - (n << 3);
    }

    private void ROUND(long l) {
        long l2 = this.x0 ^ this.x1 ^ this.x2 ^ this.x3 ^ l ^ this.x1 & (this.x0 ^ this.x2 ^ this.x4 ^ l);
        long l3 = this.x0 ^ this.x2 ^ this.x3 ^ this.x4 ^ l ^ (this.x1 ^ this.x2 ^ l) & (this.x1 ^ this.x3);
        long l4 = this.x1 ^ this.x2 ^ this.x4 ^ l ^ this.x3 & this.x4;
        long l5 = this.x0 ^ this.x1 ^ this.x2 ^ l ^ (this.x0 ^ 0xFFFFFFFFFFFFFFFFL) & (this.x3 ^ this.x4);
        long l6 = this.x1 ^ this.x3 ^ this.x4 ^ (this.x0 ^ this.x4) & this.x1;
        this.x0 = l2 ^ Longs.rotateRight(l2, 19) ^ Longs.rotateRight(l2, 28);
        this.x1 = l3 ^ Longs.rotateRight(l3, 39) ^ Longs.rotateRight(l3, 61);
        this.x2 = l4 ^ Longs.rotateRight(l4, 1) ^ Longs.rotateRight(l4, 6) ^ 0xFFFFFFFFFFFFFFFFL;
        this.x3 = l5 ^ Longs.rotateRight(l5, 10) ^ Longs.rotateRight(l5, 17);
        this.x4 = l6 ^ Longs.rotateRight(l6, 7) ^ Longs.rotateRight(l6, 41);
    }

    private void P(int n) {
        if (n >= 8) {
            if (n == 12) {
                this.ROUND(240L);
                this.ROUND(225L);
                this.ROUND(210L);
                this.ROUND(195L);
            }
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

    private void ascon_aeadinit() {
        this.x0 = this.ASCON_IV;
        if (this.CRYPTO_KEYBYTES == 20) {
            this.x0 ^= this.K0;
        }
        this.x1 = this.K1;
        this.x2 = this.K2;
        this.x3 = this.N0;
        this.x4 = this.N1;
        this.P(12);
        if (this.CRYPTO_KEYBYTES == 20) {
            this.x2 ^= this.K0;
        }
        this.x3 ^= this.K1;
        this.x4 ^= this.K2;
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

    private void processBufferAAD(byte[] byArray, int n) {
        this.x0 ^= Pack.bigEndianToLong(byArray, n);
        if (this.ASCON_AEAD_RATE == 16) {
            this.x1 ^= Pack.bigEndianToLong(byArray, 8 + n);
        }
        this.P(this.nr);
    }

    private void finishAAD(State state) {
        switch (this.m_state) {
            case DecAad: 
            case EncAad: {
                this.m_buf[this.m_bufPos] = -128;
                if (this.m_bufPos >= 8) {
                    this.x0 ^= Pack.bigEndianToLong(this.m_buf, 0);
                    this.x1 ^= Pack.bigEndianToLong(this.m_buf, 8) & -1L << 56 - (this.m_bufPos - 8 << 3);
                } else {
                    this.x0 ^= Pack.bigEndianToLong(this.m_buf, 0) & -1L << 56 - (this.m_bufPos << 3);
                }
                this.P(this.nr);
                break;
            }
        }
        this.x4 ^= 1L;
        this.m_bufPos = 0;
        this.m_state = state;
    }

    private void processBufferDecrypt(byte[] byArray, int n, byte[] byArray2, int n2) {
        if (n2 + this.ASCON_AEAD_RATE > byArray2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        long l = Pack.bigEndianToLong(byArray, n);
        Pack.longToBigEndian(this.x0 ^ l, byArray2, n2);
        this.x0 = l;
        if (this.ASCON_AEAD_RATE == 16) {
            long l2 = Pack.bigEndianToLong(byArray, n + 8);
            Pack.longToBigEndian(this.x1 ^ l2, byArray2, n2 + 8);
            this.x1 = l2;
        }
        this.P(this.nr);
    }

    private void processBufferEncrypt(byte[] byArray, int n, byte[] byArray2, int n2) {
        if (n2 + this.ASCON_AEAD_RATE > byArray2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        this.x0 ^= Pack.bigEndianToLong(byArray, n);
        Pack.longToBigEndian(this.x0, byArray2, n2);
        if (this.ASCON_AEAD_RATE == 16) {
            this.x1 ^= Pack.bigEndianToLong(byArray, n + 8);
            Pack.longToBigEndian(this.x1, byArray2, n2 + 8);
        }
        this.P(this.nr);
    }

    private void processFinalDecrypt(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        if (n2 >= 8) {
            long l = Pack.bigEndianToLong(byArray, n);
            this.x0 ^= l;
            Pack.longToBigEndian(this.x0, byArray2, n3);
            this.x0 = l;
            n += 8;
            n3 += 8;
            this.x1 ^= this.PAD(n2 -= 8);
            if (n2 != 0) {
                long l2 = Pack.littleEndianToLong_High((byte[])byArray, (int)n, (int)n2);
                this.x1 ^= l2;
                Pack.longToLittleEndian_High((long)this.x1, (byte[])byArray2, (int)n3, (int)n2);
                this.x1 &= -1L >>> (n2 << 3);
                this.x1 ^= l2;
            }
        } else {
            this.x0 ^= this.PAD(n2);
            if (n2 != 0) {
                long l = Pack.littleEndianToLong_High((byte[])byArray, (int)n, (int)n2);
                this.x0 ^= l;
                Pack.longToLittleEndian_High((long)this.x0, (byte[])byArray2, (int)n3, (int)n2);
                this.x0 &= -1L >>> (n2 << 3);
                this.x0 ^= l;
            }
        }
        this.finishData(State.DecFinal);
    }

    private void processFinalEncrypt(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        if (n2 >= 8) {
            this.x0 ^= Pack.bigEndianToLong(byArray, n);
            Pack.longToBigEndian(this.x0, byArray2, n3);
            n += 8;
            n3 += 8;
            this.x1 ^= this.PAD(n2 -= 8);
            if (n2 != 0) {
                this.x1 ^= Pack.littleEndianToLong_High((byte[])byArray, (int)n, (int)n2);
                Pack.longToLittleEndian_High((long)this.x1, (byte[])byArray2, (int)n3, (int)n2);
            }
        } else {
            this.x0 ^= this.PAD(n2);
            if (n2 != 0) {
                this.x0 ^= Pack.littleEndianToLong_High((byte[])byArray, (int)n, (int)n2);
                Pack.longToLittleEndian_High((long)this.x0, (byte[])byArray2, (int)n3, (int)n2);
            }
        }
        this.finishData(State.EncFinal);
    }

    private void finishData(State state) {
        switch (this.asconParameters) {
            case ascon128: {
                this.x1 ^= this.K1;
                this.x2 ^= this.K2;
                break;
            }
            case ascon128a: {
                this.x2 ^= this.K1;
                this.x3 ^= this.K2;
                break;
            }
            case ascon80pq: {
                this.x1 ^= this.K0 << 32 | this.K1 >> 32;
                this.x2 ^= this.K1 << 32 | this.K2 >> 32;
                this.x3 ^= this.K2 << 32;
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        this.P(12);
        this.x3 ^= this.K1;
        this.x4 ^= this.K2;
        this.m_state = state;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        byte[] byArray;
        KeyParameter keyParameter;
        Object object;
        if (cipherParameters instanceof AEADParameters) {
            object = (AEADParameters)cipherParameters;
            keyParameter = ((AEADParameters)object).getKey();
            byArray = ((AEADParameters)object).getNonce();
            this.initialAssociatedText = ((AEADParameters)object).getAssociatedText();
            int n = ((AEADParameters)object).getMacSize();
            if (n != this.CRYPTO_ABYTES * 8) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + n);
            }
        } else if (cipherParameters instanceof ParametersWithIV) {
            object = (ParametersWithIV)cipherParameters;
            keyParameter = (KeyParameter)((ParametersWithIV)object).getParameters();
            byArray = ((ParametersWithIV)object).getIV();
            this.initialAssociatedText = null;
        } else {
            throw new IllegalArgumentException("invalid parameters passed to Ascon");
        }
        if (keyParameter == null) {
            throw new IllegalArgumentException("Ascon Init parameters must include a key");
        }
        if (byArray == null || byArray.length != this.CRYPTO_ABYTES) {
            throw new IllegalArgumentException((Object)((Object)this.asconParameters) + " requires exactly " + this.CRYPTO_ABYTES + " bytes of IV");
        }
        object = keyParameter.getKey();
        if (((Object)object).length != this.CRYPTO_KEYBYTES) {
            throw new IllegalArgumentException((Object)((Object)this.asconParameters) + " key must be " + this.CRYPTO_KEYBYTES + " bytes long");
        }
        CryptoServicesRegistrar.checkConstraints((CryptoServiceProperties)new DefaultServiceProperties(this.getAlgorithmName(), 128, cipherParameters, Utils.getPurpose(bl)));
        this.N0 = Pack.bigEndianToLong(byArray, 0);
        this.N1 = Pack.bigEndianToLong(byArray, 8);
        if (this.CRYPTO_KEYBYTES == 16) {
            this.K1 = Pack.bigEndianToLong((byte[])object, 0);
            this.K2 = Pack.bigEndianToLong((byte[])object, 8);
        } else if (this.CRYPTO_KEYBYTES == 20) {
            this.K0 = Pack.bigEndianToInt((byte[])object, 0);
            this.K1 = Pack.bigEndianToLong((byte[])object, 4);
            this.K2 = Pack.bigEndianToLong((byte[])object, 12);
        } else {
            throw new IllegalStateException();
        }
        this.m_state = bl ? State.EncInit : State.DecInit;
        this.reset(true);
    }

    @Override
    public String getAlgorithmName() {
        return this.algorithmName;
    }

    public String getAlgorithmVersion() {
        return "v1.2";
    }

    @Override
    public void processAADByte(byte by) {
        this.checkAAD();
        this.m_buf[this.m_bufPos] = by;
        if (++this.m_bufPos == this.ASCON_AEAD_RATE) {
            this.processBufferAAD(this.m_buf, 0);
        }
    }

    @Override
    public void processAADBytes(byte[] byArray, int n, int n2) {
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (n2 <= 0) {
            return;
        }
        this.checkAAD();
        if (this.m_bufPos > 0) {
            int n3 = this.ASCON_AEAD_RATE - this.m_bufPos;
            if (n2 < n3) {
                System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n2);
                this.m_bufPos += n2;
                return;
            }
            System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n3);
            n += n3;
            n2 -= n3;
            this.processBufferAAD(this.m_buf, 0);
        }
        while (n2 >= this.ASCON_AEAD_RATE) {
            this.processBufferAAD(byArray, n);
            n += this.ASCON_AEAD_RATE;
            n2 -= this.ASCON_AEAD_RATE;
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
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        boolean bl = this.checkData();
        int n4 = 0;
        if (bl) {
            if (this.m_bufPos > 0) {
                int n5 = this.ASCON_AEAD_RATE - this.m_bufPos;
                if (n2 < n5) {
                    System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n2);
                    this.m_bufPos += n2;
                    return 0;
                }
                System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n5);
                n += n5;
                n2 -= n5;
                this.processBufferEncrypt(this.m_buf, 0, byArray2, n3);
                n4 = this.ASCON_AEAD_RATE;
            }
            while (n2 >= this.ASCON_AEAD_RATE) {
                this.processBufferEncrypt(byArray, n, byArray2, n3 + n4);
                n += this.ASCON_AEAD_RATE;
                n2 -= this.ASCON_AEAD_RATE;
                n4 += this.ASCON_AEAD_RATE;
            }
        } else {
            int n6 = this.m_bufferSizeDecrypt - this.m_bufPos;
            if (n2 < n6) {
                System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n2);
                this.m_bufPos += n2;
                return 0;
            }
            while (this.m_bufPos >= this.ASCON_AEAD_RATE) {
                this.processBufferDecrypt(this.m_buf, 0, byArray2, n3 + n4);
                this.m_bufPos -= this.ASCON_AEAD_RATE;
                System.arraycopy(this.m_buf, this.ASCON_AEAD_RATE, this.m_buf, 0, this.m_bufPos);
                n4 += this.ASCON_AEAD_RATE;
                if (n2 >= (n6 += this.ASCON_AEAD_RATE)) continue;
                System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n2);
                this.m_bufPos += n2;
                return n4;
            }
            n6 = this.ASCON_AEAD_RATE - this.m_bufPos;
            System.arraycopy(byArray, n, this.m_buf, this.m_bufPos, n6);
            n += n6;
            n2 -= n6;
            this.processBufferDecrypt(this.m_buf, 0, byArray2, n3 + n4);
            n4 += this.ASCON_AEAD_RATE;
            while (n2 >= this.m_bufferSizeDecrypt) {
                this.processBufferDecrypt(byArray, n, byArray2, n3 + n4);
                n += this.ASCON_AEAD_RATE;
                n2 -= this.ASCON_AEAD_RATE;
                n4 += this.ASCON_AEAD_RATE;
            }
        }
        System.arraycopy(byArray, n, this.m_buf, 0, n2);
        this.m_bufPos = n2;
        return n4;
    }

    @Override
    public int doFinal(byte[] byArray, int n) throws IllegalStateException, InvalidCipherTextException, DataLengthException {
        int n2;
        boolean bl = this.checkData();
        if (bl) {
            n2 = this.m_bufPos + this.CRYPTO_ABYTES;
            if (n + n2 > byArray.length) {
                throw new OutputLengthException("output buffer too short");
            }
            this.processFinalEncrypt(this.m_buf, 0, this.m_bufPos, byArray, n);
            this.mac = new byte[this.CRYPTO_ABYTES];
            Pack.longToBigEndian(this.x3, this.mac, 0);
            Pack.longToBigEndian(this.x4, this.mac, 8);
            System.arraycopy(this.mac, 0, byArray, n + this.m_bufPos, this.CRYPTO_ABYTES);
            this.reset(false);
        } else {
            if (this.m_bufPos < this.CRYPTO_ABYTES) {
                throw new InvalidCipherTextException("data too short");
            }
            this.m_bufPos -= this.CRYPTO_ABYTES;
            n2 = this.m_bufPos;
            if (n + n2 > byArray.length) {
                throw new OutputLengthException("output buffer too short");
            }
            this.processFinalDecrypt(this.m_buf, 0, this.m_bufPos, byArray, n);
            this.x3 ^= Pack.bigEndianToLong(this.m_buf, this.m_bufPos);
            this.x4 ^= Pack.bigEndianToLong(this.m_buf, this.m_bufPos + 8);
            if ((this.x3 | this.x4) != 0L) {
                throw new InvalidCipherTextException("mac check in " + this.getAlgorithmName() + " failed");
            }
            this.reset(true);
        }
        return n2;
    }

    @Override
    public byte[] getMac() {
        return this.mac;
    }

    @Override
    public int getUpdateOutputSize(int n) {
        int n2 = Math.max(0, n);
        switch (this.m_state) {
            case DecInit: 
            case DecAad: {
                n2 = Math.max(0, n2 - this.CRYPTO_ABYTES);
                break;
            }
            case DecData: 
            case DecFinal: {
                n2 = Math.max(0, n2 + this.m_bufPos - this.CRYPTO_ABYTES);
                break;
            }
            case EncFinal: 
            case EncData: {
                n2 += this.m_bufPos;
                break;
            }
        }
        return n2 - n2 % this.ASCON_AEAD_RATE;
    }

    @Override
    public int getOutputSize(int n) {
        int n2 = Math.max(0, n);
        switch (this.m_state) {
            case DecInit: 
            case DecAad: {
                return Math.max(0, n2 - this.CRYPTO_ABYTES);
            }
            case DecData: 
            case DecFinal: {
                return Math.max(0, n2 + this.m_bufPos - this.CRYPTO_ABYTES);
            }
            case EncFinal: 
            case EncData: {
                return n2 + this.m_bufPos + this.CRYPTO_ABYTES;
            }
        }
        return n2 + this.CRYPTO_ABYTES;
    }

    @Override
    public void reset() {
        this.reset(true);
    }

    private void reset(boolean bl) {
        if (bl) {
            this.mac = null;
        }
        Arrays.clear(this.m_buf);
        this.m_bufPos = 0;
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
            case EncAad: 
            case EncFinal: 
            case EncData: {
                this.m_state = State.EncFinal;
                return;
            }
            default: {
                throw new IllegalStateException(this.getAlgorithmName() + " needs to be initialized");
            }
        }
        this.ascon_aeadinit();
        if (this.initialAssociatedText != null) {
            this.processAADBytes(this.initialAssociatedText, 0, this.initialAssociatedText.length);
        }
    }

    public int getKeyBytesSize() {
        return this.CRYPTO_KEYBYTES;
    }

    public int getIVBytesSize() {
        return this.CRYPTO_ABYTES;
    }

    public static enum AsconParameters {
        ascon80pq,
        ascon128a,
        ascon128;

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

