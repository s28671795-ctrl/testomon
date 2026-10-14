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
import org.bouncycastle.util.Pack;

public class ISAPEngine
implements AEADCipher {
    private String algorithmName;
    private boolean forEncryption;
    private boolean initialised;
    final int CRYPTO_KEYBYTES = 16;
    final int CRYPTO_NPUBBYTES = 16;
    final int ISAP_STATE_SZ = 40;
    private byte[] k;
    private byte[] c;
    private byte[] ad;
    private byte[] npub;
    private byte[] mac;
    private ByteArrayOutputStream aadData = new ByteArrayOutputStream();
    private final ByteArrayOutputStream message = new ByteArrayOutputStream();
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private int ISAP_rH;
    private int ISAP_rH_SZ;
    private ISAP_AEAD ISAPAEAD;

    public ISAPEngine(IsapType isapType) {
        switch (isapType) {
            case ISAP_A_128A: {
                this.ISAPAEAD = new ISAPAEAD_A_128A();
                this.algorithmName = "ISAP-A-128A AEAD";
                break;
            }
            case ISAP_K_128A: {
                this.ISAPAEAD = new ISAPAEAD_K_128A();
                this.algorithmName = "ISAP-K-128A AEAD";
                break;
            }
            case ISAP_A_128: {
                this.ISAPAEAD = new ISAPAEAD_A_128();
                this.algorithmName = "ISAP-A-128 AEAD";
                break;
            }
            case ISAP_K_128: {
                this.ISAPAEAD = new ISAPAEAD_K_128();
                this.algorithmName = "ISAP-K-128 AEAD";
            }
        }
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = bl;
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("ISAP AEAD init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV)cipherParameters;
        byte[] byArray = parametersWithIV.getIV();
        if (byArray == null || byArray.length != 16) {
            throw new IllegalArgumentException("ISAP AEAD requires exactly 12 bytes of IV");
        }
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("ISAP AEAD init parameters must include a key");
        }
        KeyParameter keyParameter = (KeyParameter)parametersWithIV.getParameters();
        byte[] byArray2 = keyParameter.getKey();
        if (byArray2.length != 16) {
            throw new IllegalArgumentException("ISAP AEAD key must be 128 bits long");
        }
        CryptoServicesRegistrar.checkConstraints((CryptoServiceProperties)new DefaultServiceProperties(this.getAlgorithmName(), 128, cipherParameters, Utils.getPurpose(bl)));
        this.npub = new byte[byArray.length];
        this.k = new byte[byArray2.length];
        System.arraycopy(byArray, 0, this.npub, 0, byArray.length);
        System.arraycopy(byArray2, 0, this.k, 0, byArray2.length);
        this.ISAPAEAD.init();
        this.initialised = true;
        this.reset();
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
            throw new DataLengthException("input buffer too short" + (this.forEncryption ? "encryption" : "decryption"));
        }
        this.aadData.write(byArray, n, n2);
    }

    @Override
    public int processByte(byte by, byte[] byArray, int n) throws DataLengthException {
        return this.processBytes(new byte[]{by}, 0, 1, byArray, n);
    }

    @Override
    public int processBytes(byte[] byArray, int n, int n2, byte[] byArray2, int n3) throws DataLengthException {
        if (!this.initialised) {
            throw new IllegalArgumentException("Need call init function before encryption/decryption");
        }
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        this.message.write(byArray, n, n2);
        if (this.forEncryption && this.message.size() >= this.ISAP_rH_SZ) {
            n2 = this.message.size() / this.ISAP_rH_SZ * this.ISAP_rH_SZ;
            if (n3 + n2 > byArray2.length) {
                throw new OutputLengthException("output buffer is too short");
            }
            byte[] byArray3 = this.message.toByteArray();
            this.ISAPAEAD.isap_enc(byArray3, 0, n2, byArray2, n3, byArray2.length);
            this.outputStream.write(byArray2, n3, n2);
            this.message.reset();
            this.message.write(byArray3, n2, byArray3.length - n2);
            return n2;
        }
        return 0;
    }

    @Override
    public int doFinal(byte[] byArray, int n) throws IllegalStateException, InvalidCipherTextException {
        int n2;
        if (!this.initialised) {
            throw new IllegalArgumentException("Need call init function before encryption/decryption");
        }
        if (this.forEncryption) {
            byte[] byArray2 = this.message.toByteArray();
            n2 = byArray2.length;
            if (n + n2 + 16 > byArray.length) {
                throw new OutputLengthException("output buffer is too short");
            }
            this.ISAPAEAD.isap_enc(byArray2, 0, n2, byArray, n, byArray.length);
            this.outputStream.write(byArray, n, n2);
            this.ad = this.aadData.toByteArray();
            this.c = this.outputStream.toByteArray();
            this.mac = new byte[16];
            this.ISAPAEAD.isap_mac(this.ad, this.ad.length, this.c, this.c.length, this.mac, 0);
            System.arraycopy(this.mac, 0, byArray, n += n2, 16);
            n2 += 16;
        } else {
            this.ad = this.aadData.toByteArray();
            this.c = this.message.toByteArray();
            this.mac = new byte[16];
            n2 = this.c.length - this.mac.length;
            if (n2 + n > byArray.length) {
                throw new OutputLengthException("output buffer is too short");
            }
            this.ISAPAEAD.isap_mac(this.ad, this.ad.length, this.c, n2, this.mac, 0);
            this.ISAPAEAD.reset();
            for (int i = 0; i < 16; ++i) {
                if (this.mac[i] == this.c[n2 + i]) continue;
                throw new IllegalArgumentException("Mac does not match");
            }
            this.ISAPAEAD.isap_enc(this.c, 0, n2, byArray, n, byArray.length);
        }
        return n2;
    }

    @Override
    public byte[] getMac() {
        return this.mac;
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
        this.aadData.reset();
        this.ISAPAEAD.reset();
        this.message.reset();
        this.outputStream.reset();
    }

    public int getKeyBytesSize() {
        return 16;
    }

    public int getIVBytesSize() {
        return 16;
    }

    public int getBlockSize() {
        return this.ISAP_rH_SZ;
    }

    public abstract class ISAPAEAD_A
    implements ISAP_AEAD {
        protected long[] k64;
        protected long[] npub64;
        protected long ISAP_IV1_64;
        protected long ISAP_IV2_64;
        protected long ISAP_IV3_64;
        protected long x0;
        protected long x1;
        protected long x2;
        protected long x3;
        protected long x4;
        protected long t0;
        protected long t1;
        protected long t2;
        protected long t3;
        protected long t4;

        public ISAPAEAD_A() {
            ISAPEngine.this.ISAP_rH = 64;
            ISAPEngine.this.ISAP_rH_SZ = ISAPEngine.this.ISAP_rH + 7 >> 3;
        }

        @Override
        public void init() {
            this.npub64 = new long[this.getLongSize(ISAPEngine.this.npub.length)];
            Pack.littleEndianToLong(ISAPEngine.this.npub, 0, this.npub64, 0, this.npub64.length);
            this.npub64[0] = this.U64BIG(this.npub64[0]);
            this.npub64[1] = this.U64BIG(this.npub64[1]);
            this.k64 = new long[this.getLongSize(ISAPEngine.this.k.length)];
            Pack.littleEndianToLong(ISAPEngine.this.k, 0, this.k64, 0, this.k64.length);
            this.k64[0] = this.U64BIG(this.k64[0]);
            this.k64[1] = this.U64BIG(this.k64[1]);
            this.reset();
        }

        protected abstract void PX1();

        protected abstract void PX2();

        protected void ABSORB_MAC(byte[] byArray, int n) {
            long[] lArray = new long[byArray.length >> 3];
            Pack.littleEndianToLong(byArray, 0, lArray, 0, lArray.length);
            int n2 = 0;
            while (n >= ISAPEngine.this.ISAP_rH_SZ) {
                this.x0 ^= this.U64BIG(lArray[n2++]);
                this.P12();
                n -= ISAPEngine.this.ISAP_rH_SZ;
            }
            for (int i = 0; i < n; ++i) {
                this.x0 ^= ((long)byArray[(n2 << 3) + i] & 0xFFL) << (7 - i << 3);
            }
            this.x0 ^= 128L << (7 - n << 3);
            this.P12();
        }

        @Override
        public void isap_mac(byte[] byArray, int n, byte[] byArray2, int n2, byte[] byArray3, int n3) {
            this.x0 = this.npub64[0];
            this.x1 = this.npub64[1];
            this.x2 = this.ISAP_IV1_64;
            this.x4 = 0L;
            this.x3 = 0L;
            this.P12();
            this.ABSORB_MAC(byArray, n);
            this.x4 ^= 1L;
            this.ABSORB_MAC(byArray2, n2);
            Pack.longToLittleEndian(this.U64BIG(this.x0), byArray3, 0);
            Pack.longToLittleEndian(this.U64BIG(this.x1), byArray3, 8);
            long l = this.x2;
            long l2 = this.x3;
            long l3 = this.x4;
            this.isap_rk(this.ISAP_IV2_64, byArray3, 16);
            this.x2 = l;
            this.x3 = l2;
            this.x4 = l3;
            this.P12();
            Pack.longToLittleEndian(this.U64BIG(this.x0), byArray3, n3);
            Pack.longToLittleEndian(this.U64BIG(this.x1), byArray3, n3 + 8);
        }

        public void isap_rk(long l, byte[] byArray, int n) {
            this.x0 = this.k64[0];
            this.x1 = this.k64[1];
            this.x2 = l;
            this.x4 = 0L;
            this.x3 = 0L;
            this.P12();
            for (int i = 0; i < (n << 3) - 1; ++i) {
                this.x0 ^= ((long)((byArray[i >>> 3] >>> 7 - (i & 7) & 1) << 7) & 0xFFL) << 56;
                this.PX2();
            }
            this.x0 ^= ((long)byArray[n - 1] & 1L) << 7 << 56;
            this.P12();
        }

        @Override
        public void isap_enc(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
            long[] lArray = new long[n2 >> 3];
            Pack.littleEndianToLong(byArray, n, lArray, 0, lArray.length);
            long[] lArray2 = new long[lArray.length];
            int n5 = 0;
            while (n2 >= ISAPEngine.this.ISAP_rH_SZ) {
                lArray2[n5] = this.U64BIG(this.x0) ^ lArray[n5];
                this.PX1();
                ++n5;
                n2 -= ISAPEngine.this.ISAP_rH_SZ;
            }
            Pack.longToLittleEndian(lArray2, 0, lArray2.length, byArray2, n3);
            byte[] byArray3 = Pack.longToLittleEndian(this.x0);
            while (n2 > 0) {
                byArray2[(n5 << 3) + n3 + n2 - 1] = (byte)(byArray3[ISAPEngine.this.ISAP_rH_SZ - n2] ^ byArray[(n5 << 3) + n + --n2]);
            }
        }

        @Override
        public void reset() {
            this.isap_rk(this.ISAP_IV3_64, ISAPEngine.this.npub, 16);
            this.x3 = this.npub64[0];
            this.x4 = this.npub64[1];
            this.PX1();
        }

        private int getLongSize(int n) {
            return (n >>> 3) + ((n & 7) != 0 ? 1 : 0);
        }

        private long ROTR(long l, long l2) {
            return l >>> (int)l2 | l << (int)(64L - l2);
        }

        protected long U64BIG(long l) {
            return this.ROTR(l, 8L) & 0xFF000000FF000000L | this.ROTR(l, 24L) & 0xFF000000FF0000L | this.ROTR(l, 40L) & 0xFF000000FF00L | this.ROTR(l, 56L) & 0xFF000000FFL;
        }

        protected void ROUND(long l) {
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

        public void P12() {
            this.ROUND(240L);
            this.ROUND(225L);
            this.ROUND(210L);
            this.ROUND(195L);
            this.ROUND(180L);
            this.ROUND(165L);
            this.P6();
        }

        protected void P6() {
            this.ROUND(150L);
            this.ROUND(135L);
            this.ROUND(120L);
            this.ROUND(105L);
            this.ROUND(90L);
            this.ROUND(75L);
        }
    }

    private class ISAPAEAD_A_128
    extends ISAPAEAD_A {
        public ISAPAEAD_A_128() {
            this.ISAP_IV1_64 = 108156764298152972L;
            this.ISAP_IV2_64 = 180214358336080908L;
            this.ISAP_IV3_64 = 252271952374008844L;
        }

        @Override
        protected void PX1() {
            this.P12();
        }

        @Override
        protected void PX2() {
            this.P12();
        }
    }

    private class ISAPAEAD_A_128A
    extends ISAPAEAD_A {
        public ISAPAEAD_A_128A() {
            this.ISAP_IV1_64 = 108156764297430540L;
            this.ISAP_IV2_64 = 180214358335358476L;
            this.ISAP_IV3_64 = 252271952373286412L;
        }

        @Override
        protected void PX1() {
            this.P6();
        }

        @Override
        protected void PX2() {
            this.ROUND(75L);
        }
    }

    private abstract class ISAPAEAD_K
    implements ISAP_AEAD {
        final int ISAP_STATE_SZ_CRYPTO_NPUBBYTES = 24;
        protected short[] ISAP_IV1_16;
        protected short[] ISAP_IV2_16;
        protected short[] ISAP_IV3_16;
        protected short[] k16;
        protected short[] iv16;
        private final int[] KeccakF400RoundConstants = new int[]{1, 32898, 32906, 32768, 32907, 1, 32897, 32777, 138, 136, 32777, 10, 32907, 139, 32905, 32771, 32770, 128, 32778, 10};
        protected short[] SX = new short[25];
        protected short[] E = new short[25];
        protected short[] C = new short[5];

        public ISAPAEAD_K() {
            ISAPEngine.this.ISAP_rH = 144;
            ISAPEngine.this.ISAP_rH_SZ = ISAPEngine.this.ISAP_rH + 7 >> 3;
        }

        @Override
        public void init() {
            this.k16 = new short[ISAPEngine.this.k.length >> 1];
            this.byteToShort(ISAPEngine.this.k, this.k16, this.k16.length);
            this.iv16 = new short[ISAPEngine.this.npub.length >> 1];
            this.byteToShort(ISAPEngine.this.npub, this.iv16, this.iv16.length);
            this.reset();
        }

        @Override
        public void reset() {
            this.SX = new short[25];
            this.E = new short[25];
            this.C = new short[5];
            this.isap_rk(this.ISAP_IV3_16, ISAPEngine.this.npub, 16, this.SX, 24, this.C);
            System.arraycopy(this.iv16, 0, this.SX, 17, 8);
            this.PermuteRoundsKX(this.SX, this.E, this.C);
        }

        protected abstract void PermuteRoundsHX(short[] var1, short[] var2, short[] var3);

        protected abstract void PermuteRoundsKX(short[] var1, short[] var2, short[] var3);

        protected abstract void PermuteRoundsBX(short[] var1, short[] var2, short[] var3);

        protected void ABSORB_MAC(short[] sArray, byte[] byArray, int n, short[] sArray2, short[] sArray3) {
            int n2;
            int n3 = 0;
            for (n2 = n; n2 > ISAPEngine.this.ISAP_rH_SZ; n2 -= ISAPEngine.this.ISAP_rH_SZ) {
                this.byteToShortXor(byArray, sArray, ISAPEngine.this.ISAP_rH_SZ >> 1);
                n3 += ISAPEngine.this.ISAP_rH_SZ;
                this.PermuteRoundsHX(sArray, sArray2, sArray3);
            }
            if (n2 == ISAPEngine.this.ISAP_rH_SZ) {
                this.byteToShortXor(byArray, sArray, ISAPEngine.this.ISAP_rH_SZ >> 1);
                this.PermuteRoundsHX(sArray, sArray2, sArray3);
                sArray[0] = (short)(sArray[0] ^ 0x80);
                this.PermuteRoundsHX(sArray, sArray2, sArray3);
            } else {
                for (int i = 0; i < n2; ++i) {
                    int n4 = i >> 1;
                    sArray[n4] = (short)(sArray[n4] ^ (byArray[n3++] & 0xFF) << ((i & 1) << 3));
                }
                int n5 = n2 >> 1;
                sArray[n5] = (short)(sArray[n5] ^ 128 << ((n2 & 1) << 3));
                this.PermuteRoundsHX(sArray, sArray2, sArray3);
            }
        }

        public void isap_rk(short[] sArray, byte[] byArray, int n, short[] sArray2, int n2, short[] sArray3) {
            short[] sArray4 = new short[25];
            short[] sArray5 = new short[25];
            System.arraycopy(this.k16, 0, sArray4, 0, 8);
            System.arraycopy(sArray, 0, sArray4, 8, 4);
            this.PermuteRoundsKX(sArray4, sArray5, sArray3);
            for (int i = 0; i < (n << 3) - 1; ++i) {
                sArray4[0] = (short)(sArray4[0] ^ (byArray[i >> 3] >>> 7 - (i & 7) & 1) << 7);
                this.PermuteRoundsBX(sArray4, sArray5, sArray3);
            }
            sArray4[0] = (short)(sArray4[0] ^ (byArray[n - 1] & 1) << 7);
            this.PermuteRoundsKX(sArray4, sArray5, sArray3);
            System.arraycopy(sArray4, 0, sArray2, 0, n2 == 24 ? 17 : 8);
        }

        @Override
        public void isap_mac(byte[] byArray, int n, byte[] byArray2, int n2, byte[] byArray3, int n3) {
            this.SX = new short[25];
            System.arraycopy(this.iv16, 0, this.SX, 0, 8);
            System.arraycopy(this.ISAP_IV1_16, 0, this.SX, 8, 4);
            this.PermuteRoundsHX(this.SX, this.E, this.C);
            this.ABSORB_MAC(this.SX, byArray, n, this.E, this.C);
            this.SX[24] = (short)(this.SX[24] ^ 0x100);
            this.ABSORB_MAC(this.SX, byArray2, n2, this.E, this.C);
            this.shortToByte(this.SX, byArray3, n3);
            this.isap_rk(this.ISAP_IV2_16, byArray3, 16, this.SX, 16, this.C);
            this.PermuteRoundsHX(this.SX, this.E, this.C);
            this.shortToByte(this.SX, byArray3, n3);
        }

        @Override
        public void isap_enc(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
            int n5;
            while (n2 >= ISAPEngine.this.ISAP_rH_SZ) {
                for (n5 = 0; n5 < ISAPEngine.this.ISAP_rH_SZ; ++n5) {
                    byArray2[n3++] = (byte)(this.SX[n5 >> 1] >>> ((n5 & 1) << 3) ^ byArray[n++]);
                }
                n2 -= ISAPEngine.this.ISAP_rH_SZ;
                this.PermuteRoundsKX(this.SX, this.E, this.C);
            }
            for (n5 = 0; n5 < n2; ++n5) {
                byArray2[n3++] = (byte)(this.SX[n5 >> 1] >>> ((n5 & 1) << 3) ^ byArray[n++]);
            }
        }

        private void byteToShortXor(byte[] byArray, short[] sArray, int n) {
            for (int i = 0; i < n; ++i) {
                int n2 = i;
                sArray[n2] = (short)(sArray[n2] ^ Pack.littleEndianToShort(byArray, i << 1));
            }
        }

        private void byteToShort(byte[] byArray, short[] sArray, int n) {
            for (int i = 0; i < n; ++i) {
                sArray[i] = Pack.littleEndianToShort(byArray, i << 1);
            }
        }

        private void shortToByte(short[] sArray, byte[] byArray, int n) {
            for (int i = 0; i < 8; ++i) {
                Pack.shortToLittleEndian(sArray[i], byArray, n + (i << 1));
            }
        }

        protected void rounds12X(short[] sArray, short[] sArray2, short[] sArray3) {
            this.prepareThetaX(sArray, sArray3);
            this.rounds_8_18(sArray, sArray2, sArray3);
        }

        protected void rounds_4_18(short[] sArray, short[] sArray2, short[] sArray3) {
            this.thetaRhoPiChiIotaPrepareTheta(4, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(5, sArray2, sArray, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(6, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(7, sArray2, sArray, sArray3);
            this.rounds_8_18(sArray, sArray2, sArray3);
        }

        protected void rounds_8_18(short[] sArray, short[] sArray2, short[] sArray3) {
            this.thetaRhoPiChiIotaPrepareTheta(8, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(9, sArray2, sArray, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(10, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(11, sArray2, sArray, sArray3);
            this.rounds_12_18(sArray, sArray2, sArray3);
        }

        protected void rounds_12_18(short[] sArray, short[] sArray2, short[] sArray3) {
            this.thetaRhoPiChiIotaPrepareTheta(12, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(13, sArray2, sArray, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(14, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(15, sArray2, sArray, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(16, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(17, sArray2, sArray, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(18, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIota(sArray2, sArray, sArray3);
        }

        protected void prepareThetaX(short[] sArray, short[] sArray2) {
            sArray2[0] = (short)(sArray[0] ^ sArray[5] ^ sArray[10] ^ sArray[15] ^ sArray[20]);
            sArray2[1] = (short)(sArray[1] ^ sArray[6] ^ sArray[11] ^ sArray[16] ^ sArray[21]);
            sArray2[2] = (short)(sArray[2] ^ sArray[7] ^ sArray[12] ^ sArray[17] ^ sArray[22]);
            sArray2[3] = (short)(sArray[3] ^ sArray[8] ^ sArray[13] ^ sArray[18] ^ sArray[23]);
            sArray2[4] = (short)(sArray[4] ^ sArray[9] ^ sArray[14] ^ sArray[19] ^ sArray[24]);
        }

        private short ROL16(short s2, int n) {
            return (short)((s2 & 0xFFFF) << n ^ (s2 & 0xFFFF) >>> 16 - n);
        }

        protected void thetaRhoPiChiIotaPrepareTheta(int n, short[] sArray, short[] sArray2, short[] sArray3) {
            short s2 = (short)(sArray3[4] ^ this.ROL16(sArray3[1], 1));
            short s3 = (short)(sArray3[0] ^ this.ROL16(sArray3[2], 1));
            short s4 = (short)(sArray3[1] ^ this.ROL16(sArray3[3], 1));
            short s5 = (short)(sArray3[2] ^ this.ROL16(sArray3[4], 1));
            short s6 = (short)(sArray3[3] ^ this.ROL16(sArray3[0], 1));
            short s7 = sArray[0] = (short)(sArray[0] ^ s2);
            sArray[6] = (short)(sArray[6] ^ s3);
            short s8 = this.ROL16(sArray[6], 12);
            sArray[12] = (short)(sArray[12] ^ s4);
            short s9 = this.ROL16(sArray[12], 11);
            sArray[18] = (short)(sArray[18] ^ s5);
            short s10 = this.ROL16(sArray[18], 5);
            sArray[24] = (short)(sArray[24] ^ s6);
            short s11 = this.ROL16(sArray[24], 14);
            sArray3[0] = sArray2[0] = (short)(s7 ^ ~s8 & s9 ^ this.KeccakF400RoundConstants[n]);
            sArray3[1] = sArray2[1] = (short)(s8 ^ ~s9 & s10);
            sArray3[2] = sArray2[2] = (short)(s9 ^ ~s10 & s11);
            sArray3[3] = sArray2[3] = (short)(s10 ^ ~s11 & s7);
            sArray3[4] = sArray2[4] = (short)(s11 ^ ~s7 & s8);
            sArray[3] = (short)(sArray[3] ^ s5);
            s7 = this.ROL16(sArray[3], 12);
            sArray[9] = (short)(sArray[9] ^ s6);
            s8 = this.ROL16(sArray[9], 4);
            sArray[10] = (short)(sArray[10] ^ s2);
            s9 = this.ROL16(sArray[10], 3);
            sArray[16] = (short)(sArray[16] ^ s3);
            s10 = this.ROL16(sArray[16], 13);
            sArray[22] = (short)(sArray[22] ^ s4);
            s11 = this.ROL16(sArray[22], 13);
            sArray2[5] = (short)(s7 ^ ~s8 & s9);
            sArray3[0] = (short)(sArray3[0] ^ sArray2[5]);
            sArray2[6] = (short)(s8 ^ ~s9 & s10);
            sArray3[1] = (short)(sArray3[1] ^ sArray2[6]);
            sArray2[7] = (short)(s9 ^ ~s10 & s11);
            sArray3[2] = (short)(sArray3[2] ^ sArray2[7]);
            sArray2[8] = (short)(s10 ^ ~s11 & s7);
            sArray3[3] = (short)(sArray3[3] ^ sArray2[8]);
            sArray2[9] = (short)(s11 ^ ~s7 & s8);
            sArray3[4] = (short)(sArray3[4] ^ sArray2[9]);
            sArray[1] = (short)(sArray[1] ^ s3);
            s7 = this.ROL16(sArray[1], 1);
            sArray[7] = (short)(sArray[7] ^ s4);
            s8 = this.ROL16(sArray[7], 6);
            sArray[13] = (short)(sArray[13] ^ s5);
            s9 = this.ROL16(sArray[13], 9);
            sArray[19] = (short)(sArray[19] ^ s6);
            s10 = this.ROL16(sArray[19], 8);
            sArray[20] = (short)(sArray[20] ^ s2);
            s11 = this.ROL16(sArray[20], 2);
            sArray2[10] = (short)(s7 ^ ~s8 & s9);
            sArray3[0] = (short)(sArray3[0] ^ sArray2[10]);
            sArray2[11] = (short)(s8 ^ ~s9 & s10);
            sArray3[1] = (short)(sArray3[1] ^ sArray2[11]);
            sArray2[12] = (short)(s9 ^ ~s10 & s11);
            sArray3[2] = (short)(sArray3[2] ^ sArray2[12]);
            sArray2[13] = (short)(s10 ^ ~s11 & s7);
            sArray3[3] = (short)(sArray3[3] ^ sArray2[13]);
            sArray2[14] = (short)(s11 ^ ~s7 & s8);
            sArray3[4] = (short)(sArray3[4] ^ sArray2[14]);
            sArray[4] = (short)(sArray[4] ^ s6);
            s7 = this.ROL16(sArray[4], 11);
            sArray[5] = (short)(sArray[5] ^ s2);
            s8 = this.ROL16(sArray[5], 4);
            sArray[11] = (short)(sArray[11] ^ s3);
            s9 = this.ROL16(sArray[11], 10);
            sArray[17] = (short)(sArray[17] ^ s4);
            s10 = this.ROL16(sArray[17], 15);
            sArray[23] = (short)(sArray[23] ^ s5);
            s11 = this.ROL16(sArray[23], 8);
            sArray2[15] = (short)(s7 ^ ~s8 & s9);
            sArray3[0] = (short)(sArray3[0] ^ sArray2[15]);
            sArray2[16] = (short)(s8 ^ ~s9 & s10);
            sArray3[1] = (short)(sArray3[1] ^ sArray2[16]);
            sArray2[17] = (short)(s9 ^ ~s10 & s11);
            sArray3[2] = (short)(sArray3[2] ^ sArray2[17]);
            sArray2[18] = (short)(s10 ^ ~s11 & s7);
            sArray3[3] = (short)(sArray3[3] ^ sArray2[18]);
            sArray2[19] = (short)(s11 ^ ~s7 & s8);
            sArray3[4] = (short)(sArray3[4] ^ sArray2[19]);
            sArray[2] = (short)(sArray[2] ^ s4);
            s7 = this.ROL16(sArray[2], 14);
            sArray[8] = (short)(sArray[8] ^ s5);
            s8 = this.ROL16(sArray[8], 7);
            sArray[14] = (short)(sArray[14] ^ s6);
            s9 = this.ROL16(sArray[14], 7);
            sArray[15] = (short)(sArray[15] ^ s2);
            s10 = this.ROL16(sArray[15], 9);
            sArray[21] = (short)(sArray[21] ^ s3);
            s11 = this.ROL16(sArray[21], 2);
            sArray2[20] = (short)(s7 ^ ~s8 & s9);
            sArray3[0] = (short)(sArray3[0] ^ sArray2[20]);
            sArray2[21] = (short)(s8 ^ ~s9 & s10);
            sArray3[1] = (short)(sArray3[1] ^ sArray2[21]);
            sArray2[22] = (short)(s9 ^ ~s10 & s11);
            sArray3[2] = (short)(sArray3[2] ^ sArray2[22]);
            sArray2[23] = (short)(s10 ^ ~s11 & s7);
            sArray3[3] = (short)(sArray3[3] ^ sArray2[23]);
            sArray2[24] = (short)(s11 ^ ~s7 & s8);
            sArray3[4] = (short)(sArray3[4] ^ sArray2[24]);
        }

        protected void thetaRhoPiChiIota(short[] sArray, short[] sArray2, short[] sArray3) {
            short s2 = (short)(sArray3[4] ^ this.ROL16(sArray3[1], 1));
            short s3 = (short)(sArray3[0] ^ this.ROL16(sArray3[2], 1));
            short s4 = (short)(sArray3[1] ^ this.ROL16(sArray3[3], 1));
            short s5 = (short)(sArray3[2] ^ this.ROL16(sArray3[4], 1));
            short s6 = (short)(sArray3[3] ^ this.ROL16(sArray3[0], 1));
            short s7 = sArray[0] = (short)(sArray[0] ^ s2);
            sArray[6] = (short)(sArray[6] ^ s3);
            short s8 = this.ROL16(sArray[6], 12);
            sArray[12] = (short)(sArray[12] ^ s4);
            short s9 = this.ROL16(sArray[12], 11);
            sArray[18] = (short)(sArray[18] ^ s5);
            short s10 = this.ROL16(sArray[18], 5);
            sArray[24] = (short)(sArray[24] ^ s6);
            short s11 = this.ROL16(sArray[24], 14);
            sArray2[0] = (short)(s7 ^ ~s8 & s9 ^ this.KeccakF400RoundConstants[19]);
            sArray2[1] = (short)(s8 ^ ~s9 & s10);
            sArray2[2] = (short)(s9 ^ ~s10 & s11);
            sArray2[3] = (short)(s10 ^ ~s11 & s7);
            sArray2[4] = (short)(s11 ^ ~s7 & s8);
            sArray[3] = (short)(sArray[3] ^ s5);
            s7 = this.ROL16(sArray[3], 12);
            sArray[9] = (short)(sArray[9] ^ s6);
            s8 = this.ROL16(sArray[9], 4);
            sArray[10] = (short)(sArray[10] ^ s2);
            s9 = this.ROL16(sArray[10], 3);
            sArray[16] = (short)(sArray[16] ^ s3);
            s10 = this.ROL16(sArray[16], 13);
            sArray[22] = (short)(sArray[22] ^ s4);
            s11 = this.ROL16(sArray[22], 13);
            sArray2[5] = (short)(s7 ^ ~s8 & s9);
            sArray2[6] = (short)(s8 ^ ~s9 & s10);
            sArray2[7] = (short)(s9 ^ ~s10 & s11);
            sArray2[8] = (short)(s10 ^ ~s11 & s7);
            sArray2[9] = (short)(s11 ^ ~s7 & s8);
            sArray[1] = (short)(sArray[1] ^ s3);
            s7 = this.ROL16(sArray[1], 1);
            sArray[7] = (short)(sArray[7] ^ s4);
            s8 = this.ROL16(sArray[7], 6);
            sArray[13] = (short)(sArray[13] ^ s5);
            s9 = this.ROL16(sArray[13], 9);
            sArray[19] = (short)(sArray[19] ^ s6);
            s10 = this.ROL16(sArray[19], 8);
            sArray[20] = (short)(sArray[20] ^ s2);
            s11 = this.ROL16(sArray[20], 2);
            sArray2[10] = (short)(s7 ^ ~s8 & s9);
            sArray2[11] = (short)(s8 ^ ~s9 & s10);
            sArray2[12] = (short)(s9 ^ ~s10 & s11);
            sArray2[13] = (short)(s10 ^ ~s11 & s7);
            sArray2[14] = (short)(s11 ^ ~s7 & s8);
            sArray[4] = (short)(sArray[4] ^ s6);
            s7 = this.ROL16(sArray[4], 11);
            sArray[5] = (short)(sArray[5] ^ s2);
            s8 = this.ROL16(sArray[5], 4);
            sArray[11] = (short)(sArray[11] ^ s3);
            s9 = this.ROL16(sArray[11], 10);
            sArray[17] = (short)(sArray[17] ^ s4);
            s10 = this.ROL16(sArray[17], 15);
            sArray[23] = (short)(sArray[23] ^ s5);
            s11 = this.ROL16(sArray[23], 8);
            sArray2[15] = (short)(s7 ^ ~s8 & s9);
            sArray2[16] = (short)(s8 ^ ~s9 & s10);
            sArray2[17] = (short)(s9 ^ ~s10 & s11);
            sArray2[18] = (short)(s10 ^ ~s11 & s7);
            sArray2[19] = (short)(s11 ^ ~s7 & s8);
            sArray[2] = (short)(sArray[2] ^ s4);
            s7 = this.ROL16(sArray[2], 14);
            sArray[8] = (short)(sArray[8] ^ s5);
            s8 = this.ROL16(sArray[8], 7);
            sArray[14] = (short)(sArray[14] ^ s6);
            s9 = this.ROL16(sArray[14], 7);
            sArray[15] = (short)(sArray[15] ^ s2);
            s10 = this.ROL16(sArray[15], 9);
            sArray[21] = (short)(sArray[21] ^ s3);
            s11 = this.ROL16(sArray[21], 2);
            sArray2[20] = (short)(s7 ^ ~s8 & s9);
            sArray2[21] = (short)(s8 ^ ~s9 & s10);
            sArray2[22] = (short)(s9 ^ ~s10 & s11);
            sArray2[23] = (short)(s10 ^ ~s11 & s7);
            sArray2[24] = (short)(s11 ^ ~s7 & s8);
        }
    }

    private class ISAPAEAD_K_128
    extends ISAPAEAD_K {
        public ISAPAEAD_K_128() {
            this.ISAP_IV1_16 = new short[]{-32767, 400, 3092, 3084};
            this.ISAP_IV2_16 = new short[]{-32766, 400, 3092, 3084};
            this.ISAP_IV3_16 = new short[]{-32765, 400, 3092, 3084};
        }

        @Override
        protected void PermuteRoundsHX(short[] sArray, short[] sArray2, short[] sArray3) {
            this.prepareThetaX(sArray, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(0, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(1, sArray2, sArray, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(2, sArray, sArray2, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(3, sArray2, sArray, sArray3);
            this.rounds_4_18(sArray, sArray2, sArray3);
        }

        @Override
        protected void PermuteRoundsKX(short[] sArray, short[] sArray2, short[] sArray3) {
            this.rounds12X(sArray, sArray2, sArray3);
        }

        @Override
        protected void PermuteRoundsBX(short[] sArray, short[] sArray2, short[] sArray3) {
            this.rounds12X(sArray, sArray2, sArray3);
        }
    }

    private class ISAPAEAD_K_128A
    extends ISAPAEAD_K {
        public ISAPAEAD_K_128A() {
            this.ISAP_IV1_16 = new short[]{-32767, 400, 272, 2056};
            this.ISAP_IV2_16 = new short[]{-32766, 400, 272, 2056};
            this.ISAP_IV3_16 = new short[]{-32765, 400, 272, 2056};
        }

        @Override
        protected void PermuteRoundsHX(short[] sArray, short[] sArray2, short[] sArray3) {
            this.prepareThetaX(sArray, sArray3);
            this.rounds_4_18(sArray, sArray2, sArray3);
        }

        @Override
        protected void PermuteRoundsKX(short[] sArray, short[] sArray2, short[] sArray3) {
            this.prepareThetaX(sArray, sArray3);
            this.rounds_12_18(sArray, sArray2, sArray3);
        }

        @Override
        protected void PermuteRoundsBX(short[] sArray, short[] sArray2, short[] sArray3) {
            this.prepareThetaX(sArray, sArray3);
            this.thetaRhoPiChiIotaPrepareTheta(19, sArray, sArray2, sArray3);
            System.arraycopy(sArray2, 0, sArray, 0, sArray2.length);
        }
    }

    private static interface ISAP_AEAD {
        public void isap_enc(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6);

        public void init();

        public void isap_mac(byte[] var1, int var2, byte[] var3, int var4, byte[] var5, int var6);

        public void reset();
    }

    public static enum IsapType {
        ISAP_A_128A,
        ISAP_K_128A,
        ISAP_A_128,
        ISAP_K_128;

    }
}

