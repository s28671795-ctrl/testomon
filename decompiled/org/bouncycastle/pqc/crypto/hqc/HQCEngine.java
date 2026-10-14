/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.pqc.crypto.hqc.GF2PolynomialCalculator;
import org.bouncycastle.pqc.crypto.hqc.KeccakRandomGenerator;
import org.bouncycastle.pqc.crypto.hqc.ReedMuller;
import org.bouncycastle.pqc.crypto.hqc.ReedSolomon;
import org.bouncycastle.pqc.crypto.hqc.Utils;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

class HQCEngine {
    private int n;
    private int n1;
    private int n2;
    private int k;
    private int delta;
    private int w;
    private int wr;
    private int we;
    private int g;
    private int rejectionThreshold;
    private int fft;
    private int mulParam;
    private int SEED_SIZE = 40;
    private byte G_FCT_DOMAIN = (byte)3;
    private byte H_FCT_DOMAIN = (byte)4;
    private byte K_FCT_DOMAIN = (byte)5;
    private int N_BYTE;
    private int n1n2;
    private int N_BYTE_64;
    private int K_BYTE;
    private int K_BYTE_64;
    private int N1_BYTE_64;
    private int N1N2_BYTE_64;
    private int N1N2_BYTE;
    private int N1_BYTE;
    private int GF_POLY_WT = 5;
    private int GF_POLY_M2 = 4;
    private int SALT_SIZE_BYTES = 16;
    private int SALT_SIZE_64 = 2;
    private int[] generatorPoly;
    private int SHA512_BYTES = 64;
    private long RED_MASK;
    private GF2PolynomialCalculator gfCalculator;

    public HQCEngine(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int[] nArray) {
        this.n = n;
        this.k = n4;
        this.delta = n6;
        this.w = n7;
        this.wr = n8;
        this.we = n9;
        this.n1 = n2;
        this.n2 = n3;
        this.n1n2 = n2 * n3;
        this.generatorPoly = nArray;
        this.g = n5;
        this.rejectionThreshold = n10;
        this.fft = n11;
        this.mulParam = (int)Math.ceil(n3 / 128);
        this.N_BYTE = Utils.getByteSizeFromBitSize(n);
        this.K_BYTE = n4;
        this.N_BYTE_64 = Utils.getByte64SizeFromBitSize(n);
        this.K_BYTE_64 = Utils.getByteSizeFromBitSize(n4);
        this.N1_BYTE_64 = Utils.getByteSizeFromBitSize(n2);
        this.N1N2_BYTE_64 = Utils.getByte64SizeFromBitSize(n2 * n3);
        this.N1N2_BYTE = Utils.getByteSizeFromBitSize(n2 * n3);
        this.N1_BYTE = Utils.getByteSizeFromBitSize(n2);
        this.RED_MASK = (1L << (int)((long)n % 64L)) - 1L;
        this.gfCalculator = new GF2PolynomialCalculator(this.N_BYTE_64, n, this.RED_MASK);
    }

    public void genKeyPair(byte[] byArray, byte[] byArray2, byte[] byArray3) {
        byte[] byArray4 = new byte[this.SEED_SIZE];
        KeccakRandomGenerator keccakRandomGenerator = new KeccakRandomGenerator(256);
        keccakRandomGenerator.randomGeneratorInit(byArray3, null, byArray3.length, 0);
        keccakRandomGenerator.squeeze(byArray4, 40);
        KeccakRandomGenerator keccakRandomGenerator2 = new KeccakRandomGenerator(256);
        keccakRandomGenerator2.seedExpanderInit(byArray4, byArray4.length);
        long[] lArray = new long[this.N_BYTE_64];
        long[] lArray2 = new long[this.N_BYTE_64];
        this.generateRandomFixedWeight(lArray, keccakRandomGenerator2, this.w);
        this.generateRandomFixedWeight(lArray2, keccakRandomGenerator2, this.w);
        byte[] byArray5 = new byte[this.SEED_SIZE];
        keccakRandomGenerator.squeeze(byArray5, 40);
        KeccakRandomGenerator keccakRandomGenerator3 = new KeccakRandomGenerator(256);
        keccakRandomGenerator3.seedExpanderInit(byArray5, byArray5.length);
        long[] lArray3 = new long[this.N_BYTE_64];
        this.generatePublicKeyH(lArray3, keccakRandomGenerator3);
        long[] lArray4 = new long[this.N_BYTE_64];
        this.gfCalculator.multLongs(lArray4, lArray2, lArray3);
        GF2PolynomialCalculator.addLongs(lArray4, lArray4, lArray);
        byte[] byArray6 = new byte[this.N_BYTE];
        Utils.fromLongArrayToByteArray(byArray6, lArray4);
        byte[] byArray7 = Arrays.concatenate(byArray5, byArray6);
        byte[] byArray8 = Arrays.concatenate(byArray4, byArray7);
        System.arraycopy(byArray7, 0, byArray, 0, byArray7.length);
        System.arraycopy(byArray8, 0, byArray2, 0, byArray8.length);
    }

    public void encaps(byte[] byArray, byte[] byArray2, byte[] byArray3, byte[] byArray4, byte[] byArray5, byte[] byArray6, byte[] byArray7) {
        byte[] byArray8 = new byte[this.K_BYTE];
        byte[] byArray9 = new byte[this.SEED_SIZE];
        KeccakRandomGenerator keccakRandomGenerator = new KeccakRandomGenerator(256);
        keccakRandomGenerator.randomGeneratorInit(byArray6, null, byArray6.length, 0);
        keccakRandomGenerator.squeeze(byArray9, 40);
        byte[] byArray10 = new byte[this.SEED_SIZE];
        keccakRandomGenerator.squeeze(byArray10, 40);
        keccakRandomGenerator.squeeze(byArray8, this.K_BYTE);
        byte[] byArray11 = new byte[this.SHA512_BYTES];
        byte[] byArray12 = new byte[this.K_BYTE + this.SEED_SIZE + this.SALT_SIZE_BYTES];
        keccakRandomGenerator.squeeze(byArray7, this.SALT_SIZE_BYTES);
        System.arraycopy(byArray8, 0, byArray12, 0, byArray8.length);
        System.arraycopy(byArray5, 0, byArray12, this.K_BYTE, this.SEED_SIZE);
        System.arraycopy(byArray7, 0, byArray12, this.K_BYTE + this.SEED_SIZE, this.SALT_SIZE_BYTES);
        KeccakRandomGenerator keccakRandomGenerator2 = new KeccakRandomGenerator(256);
        keccakRandomGenerator2.SHAKE256_512_ds(byArray11, byArray12, byArray12.length, new byte[]{this.G_FCT_DOMAIN});
        long[] lArray = new long[this.N_BYTE_64];
        byte[] byArray13 = new byte[this.N_BYTE];
        this.extractPublicKeys(lArray, byArray13, byArray5);
        long[] lArray2 = new long[this.N1N2_BYTE_64];
        this.encrypt(byArray, lArray2, lArray, byArray13, byArray8, byArray11);
        Utils.fromLongArrayToByteArray(byArray2, lArray2);
        keccakRandomGenerator2.SHAKE256_512_ds(byArray4, byArray8, byArray8.length, new byte[]{this.H_FCT_DOMAIN});
        byte[] byArray14 = new byte[this.K_BYTE + this.N_BYTE + this.N1N2_BYTE];
        byArray14 = Arrays.concatenate(byArray8, byArray);
        byArray14 = Arrays.concatenate(byArray14, byArray2);
        keccakRandomGenerator2.SHAKE256_512_ds(byArray3, byArray14, byArray14.length, new byte[]{this.K_FCT_DOMAIN});
    }

    public void decaps(byte[] byArray, byte[] byArray2, byte[] byArray3) {
        long[] lArray = new long[this.N_BYTE_64];
        long[] lArray2 = new long[this.N_BYTE_64];
        byte[] byArray4 = new byte[40 + this.N_BYTE];
        this.extractKeysFromSecretKeys(lArray, lArray2, byArray4, byArray3);
        byte[] byArray5 = new byte[this.N_BYTE];
        byte[] byArray6 = new byte[this.N1N2_BYTE];
        byte[] byArray7 = new byte[this.SHA512_BYTES];
        byte[] byArray8 = new byte[this.SALT_SIZE_BYTES];
        this.extractCiphertexts(byArray5, byArray6, byArray7, byArray8, byArray2);
        byte[] byArray9 = new byte[this.k];
        this.decrypt(byArray9, byArray9, byArray5, byArray6, lArray2);
        byte[] byArray10 = new byte[this.SHA512_BYTES];
        byte[] byArray11 = new byte[this.K_BYTE + this.SALT_SIZE_BYTES + this.SEED_SIZE];
        System.arraycopy(byArray9, 0, byArray11, 0, byArray9.length);
        System.arraycopy(byArray4, 0, byArray11, this.K_BYTE, this.SEED_SIZE);
        System.arraycopy(byArray8, 0, byArray11, this.K_BYTE + this.SEED_SIZE, this.SALT_SIZE_BYTES);
        KeccakRandomGenerator keccakRandomGenerator = new KeccakRandomGenerator(256);
        keccakRandomGenerator.SHAKE256_512_ds(byArray10, byArray11, byArray11.length, new byte[]{this.G_FCT_DOMAIN});
        long[] lArray3 = new long[this.N_BYTE_64];
        byte[] byArray12 = new byte[this.N_BYTE];
        this.extractPublicKeys(lArray3, byArray12, byArray4);
        byte[] byArray13 = new byte[this.N_BYTE];
        byte[] byArray14 = new byte[this.N1N2_BYTE];
        long[] lArray4 = new long[this.N1N2_BYTE_64];
        this.encrypt(byArray13, lArray4, lArray3, byArray12, byArray9, byArray10);
        Utils.fromLongArrayToByteArray(byArray14, lArray4);
        byte[] byArray15 = new byte[this.SHA512_BYTES];
        keccakRandomGenerator.SHAKE256_512_ds(byArray15, byArray9, byArray9.length, new byte[]{this.H_FCT_DOMAIN});
        byte[] byArray16 = new byte[this.K_BYTE + this.N_BYTE + this.N1N2_BYTE];
        byArray16 = Arrays.concatenate(byArray9, byArray5);
        byArray16 = Arrays.concatenate(byArray16, byArray6);
        keccakRandomGenerator.SHAKE256_512_ds(byArray, byArray16, byArray16.length, new byte[]{this.K_FCT_DOMAIN});
        boolean bl = true;
        if (!Arrays.areEqual(byArray5, byArray13)) {
            bl = false;
        }
        if (!Arrays.areEqual(byArray6, byArray14)) {
            bl = false;
        }
        if (!Arrays.areEqual(byArray7, byArray15)) {
            bl = false;
        }
        if (!bl) {
            for (int i = 0; i < this.getSessionKeySize(); ++i) {
                byArray[i] = 0;
            }
        }
    }

    int getSessionKeySize() {
        return this.SHA512_BYTES;
    }

    private void encrypt(byte[] byArray, long[] lArray, long[] lArray2, byte[] byArray2, byte[] byArray3, byte[] byArray4) {
        KeccakRandomGenerator keccakRandomGenerator = new KeccakRandomGenerator(256);
        keccakRandomGenerator.seedExpanderInit(byArray4, this.SEED_SIZE);
        long[] lArray3 = new long[this.N_BYTE_64];
        long[] lArray4 = new long[this.N_BYTE_64];
        long[] lArray5 = new long[this.N_BYTE_64];
        this.generateRandomFixedWeight(lArray4, keccakRandomGenerator, this.wr);
        this.generateRandomFixedWeight(lArray5, keccakRandomGenerator, this.wr);
        this.generateRandomFixedWeight(lArray3, keccakRandomGenerator, this.we);
        long[] lArray6 = new long[this.N_BYTE_64];
        this.gfCalculator.multLongs(lArray6, lArray5, lArray2);
        GF2PolynomialCalculator.addLongs(lArray6, lArray6, lArray4);
        Utils.fromLongArrayToByteArray(byArray, lArray6);
        byte[] byArray5 = new byte[this.n1];
        long[] lArray7 = new long[this.N1N2_BYTE_64];
        long[] lArray8 = new long[this.N_BYTE_64];
        ReedSolomon.encode(byArray5, byArray3, this.K_BYTE * 8, this.n1, this.k, this.g, this.generatorPoly);
        ReedMuller.encode(lArray7, byArray5, this.n1, this.mulParam);
        System.arraycopy(lArray7, 0, lArray8, 0, lArray7.length);
        long[] lArray9 = new long[this.N_BYTE_64];
        Utils.fromByteArrayToLongArray(lArray9, byArray2);
        long[] lArray10 = new long[this.N_BYTE_64];
        this.gfCalculator.multLongs(lArray10, lArray5, lArray9);
        GF2PolynomialCalculator.addLongs(lArray10, lArray10, lArray8);
        GF2PolynomialCalculator.addLongs(lArray10, lArray10, lArray3);
        Utils.resizeArray(lArray, this.n1n2, lArray10, this.n, this.N1N2_BYTE_64, this.N1N2_BYTE_64);
    }

    private void decrypt(byte[] byArray, byte[] byArray2, byte[] byArray3, byte[] byArray4, long[] lArray) {
        long[] lArray2 = new long[this.N_BYTE_64];
        Utils.fromByteArrayToLongArray(lArray2, byArray3);
        long[] lArray3 = new long[this.N1N2_BYTE_64];
        Utils.fromByteArrayToLongArray(lArray3, byArray4);
        long[] lArray4 = new long[this.N_BYTE_64];
        System.arraycopy(lArray3, 0, lArray4, 0, lArray3.length);
        long[] lArray5 = new long[this.N_BYTE_64];
        this.gfCalculator.multLongs(lArray5, lArray, lArray2);
        GF2PolynomialCalculator.addLongs(lArray5, lArray5, lArray4);
        byte[] byArray5 = new byte[this.n1];
        ReedMuller.decode(byArray5, lArray5, this.n1, this.mulParam);
        ReedSolomon.decode(byArray2, byArray5, this.n1, this.fft, this.delta, this.k, this.g);
        System.arraycopy(byArray2, 0, byArray, 0, byArray.length);
    }

    private void generateRandomFixedWeight(long[] lArray, KeccakRandomGenerator keccakRandomGenerator, int n) {
        int n2;
        int n3;
        int n4;
        int[] nArray = new int[this.wr];
        byte[] byArray = new byte[this.wr * 4];
        int[] nArray2 = new int[this.wr];
        int[] nArray3 = new int[this.wr];
        long[] lArray2 = new long[this.wr];
        keccakRandomGenerator.expandSeed(byArray, 4 * n);
        Pack.littleEndianToInt(byArray, 0, nArray, 0, nArray.length);
        for (n4 = 0; n4 < n; ++n4) {
            nArray2[n4] = (int)((long)n4 + ((long)nArray[n4] & 0xFFFFFFFFL) % (long)(this.n - n4));
        }
        for (n4 = n - 1; n4 >= 0; --n4) {
            n3 = 0;
            for (n2 = n4 + 1; n2 < n; ++n2) {
                if (nArray2[n2] != nArray2[n4]) continue;
                n3 |= 1;
            }
            n2 = -n3;
            nArray2[n4] = n2 & n4 ^ ~n2 & nArray2[n4];
        }
        for (n4 = 0; n4 < n; ++n4) {
            nArray3[n4] = nArray2[n4] >>> 6;
            n3 = nArray2[n4] & 0x3F;
            lArray2[n4] = 1L << n3;
        }
        long l = 0L;
        n2 = 0;
        while (n2 < this.N_BYTE_64) {
            l = 0L;
            for (int i = 0; i < n; ++i) {
                int n5 = n2 - nArray3[i];
                int n6 = 1 ^ (n5 | -n5) >>> 31;
                long l2 = -n6;
                l |= lArray2[i] & l2;
            }
            int n7 = n2++;
            lArray[n7] = lArray[n7] | l;
        }
    }

    void generatePublicKeyH(long[] lArray, KeccakRandomGenerator keccakRandomGenerator) {
        byte[] byArray = new byte[this.N_BYTE];
        keccakRandomGenerator.expandSeed(byArray, this.N_BYTE);
        long[] lArray2 = new long[this.N_BYTE_64];
        Utils.fromByteArrayToLongArray(lArray2, byArray);
        int n = this.N_BYTE_64 - 1;
        lArray2[n] = lArray2[n] & Utils.bitMask(this.n, 64L);
        System.arraycopy(lArray2, 0, lArray, 0, lArray.length);
    }

    private void extractPublicKeys(long[] lArray, byte[] byArray, byte[] byArray2) {
        byte[] byArray3 = new byte[this.SEED_SIZE];
        System.arraycopy(byArray2, 0, byArray3, 0, byArray3.length);
        KeccakRandomGenerator keccakRandomGenerator = new KeccakRandomGenerator(256);
        keccakRandomGenerator.seedExpanderInit(byArray3, byArray3.length);
        long[] lArray2 = new long[this.N_BYTE_64];
        this.generatePublicKeyH(lArray2, keccakRandomGenerator);
        System.arraycopy(lArray2, 0, lArray, 0, lArray.length);
        System.arraycopy(byArray2, 40, byArray, 0, byArray.length);
    }

    private void extractKeysFromSecretKeys(long[] lArray, long[] lArray2, byte[] byArray, byte[] byArray2) {
        byte[] byArray3 = new byte[this.SEED_SIZE];
        System.arraycopy(byArray2, 0, byArray3, 0, byArray3.length);
        KeccakRandomGenerator keccakRandomGenerator = new KeccakRandomGenerator(256);
        keccakRandomGenerator.seedExpanderInit(byArray3, byArray3.length);
        this.generateRandomFixedWeight(lArray, keccakRandomGenerator, this.w);
        this.generateRandomFixedWeight(lArray2, keccakRandomGenerator, this.w);
        System.arraycopy(byArray2, this.SEED_SIZE, byArray, 0, byArray.length);
    }

    private void extractCiphertexts(byte[] byArray, byte[] byArray2, byte[] byArray3, byte[] byArray4, byte[] byArray5) {
        System.arraycopy(byArray5, 0, byArray, 0, byArray.length);
        System.arraycopy(byArray5, byArray.length, byArray2, 0, byArray2.length);
        System.arraycopy(byArray5, byArray.length + byArray2.length, byArray3, 0, byArray3.length);
        System.arraycopy(byArray5, byArray.length + byArray2.length + byArray3.length, byArray4, 0, byArray4.length);
    }
}

