/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import java.security.SecureRandom;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberIndCpa;
import org.bouncycastle.pqc.crypto.crystals.kyber.PolyVec;
import org.bouncycastle.pqc.crypto.crystals.kyber.Symmetric;
import org.bouncycastle.util.Arrays;

class KyberEngine {
    private SecureRandom random;
    private KyberIndCpa indCpa;
    public static final int KyberN = 256;
    public static final int KyberQ = 3329;
    public static final int KyberQinv = 62209;
    public static final int KyberSymBytes = 32;
    private static final int KyberSharedSecretBytes = 32;
    public static final int KyberPolyBytes = 384;
    private static final int KyberEta2 = 2;
    private static final int KyberIndCpaMsgBytes = 32;
    private final int KyberK;
    private final int KyberPolyVecBytes;
    private final int KyberPolyCompressedBytes;
    private final int KyberPolyVecCompressedBytes;
    private final int KyberEta1;
    private final int KyberIndCpaPublicKeyBytes;
    private final int KyberIndCpaSecretKeyBytes;
    private final int KyberIndCpaBytes;
    private final int KyberPublicKeyBytes;
    private final int KyberSecretKeyBytes;
    private final int KyberCipherTextBytes;
    private final int CryptoBytes;
    private final int CryptoSecretKeyBytes;
    private final int CryptoPublicKeyBytes;
    private final int CryptoCipherTextBytes;
    private final int sessionKeyLength;
    private final Symmetric symmetric;

    public Symmetric getSymmetric() {
        return this.symmetric;
    }

    public static int getKyberEta2() {
        return 2;
    }

    public static int getKyberIndCpaMsgBytes() {
        return 32;
    }

    public int getCryptoCipherTextBytes() {
        return this.CryptoCipherTextBytes;
    }

    public int getCryptoPublicKeyBytes() {
        return this.CryptoPublicKeyBytes;
    }

    public int getCryptoSecretKeyBytes() {
        return this.CryptoSecretKeyBytes;
    }

    public int getCryptoBytes() {
        return this.CryptoBytes;
    }

    public int getKyberCipherTextBytes() {
        return this.KyberCipherTextBytes;
    }

    public int getKyberSecretKeyBytes() {
        return this.KyberSecretKeyBytes;
    }

    public int getKyberIndCpaPublicKeyBytes() {
        return this.KyberIndCpaPublicKeyBytes;
    }

    public int getKyberIndCpaSecretKeyBytes() {
        return this.KyberIndCpaSecretKeyBytes;
    }

    public int getKyberIndCpaBytes() {
        return this.KyberIndCpaBytes;
    }

    public int getKyberPublicKeyBytes() {
        return this.KyberPublicKeyBytes;
    }

    public int getKyberPolyCompressedBytes() {
        return this.KyberPolyCompressedBytes;
    }

    public int getKyberK() {
        return this.KyberK;
    }

    public int getKyberPolyVecBytes() {
        return this.KyberPolyVecBytes;
    }

    public int getKyberPolyVecCompressedBytes() {
        return this.KyberPolyVecCompressedBytes;
    }

    public int getKyberEta1() {
        return this.KyberEta1;
    }

    public KyberEngine(int n, boolean bl) {
        this.KyberK = n;
        switch (n) {
            case 2: {
                this.KyberEta1 = 3;
                this.KyberPolyCompressedBytes = 128;
                this.KyberPolyVecCompressedBytes = n * 320;
                this.sessionKeyLength = 32;
                break;
            }
            case 3: {
                this.KyberEta1 = 2;
                this.KyberPolyCompressedBytes = 128;
                this.KyberPolyVecCompressedBytes = n * 320;
                this.sessionKeyLength = 32;
                break;
            }
            case 4: {
                this.KyberEta1 = 2;
                this.KyberPolyCompressedBytes = 160;
                this.KyberPolyVecCompressedBytes = n * 352;
                this.sessionKeyLength = 32;
                break;
            }
            default: {
                throw new IllegalArgumentException("K: " + n + " is not supported for Crystals Kyber");
            }
        }
        this.KyberPolyVecBytes = n * 384;
        this.KyberIndCpaPublicKeyBytes = this.KyberPolyVecBytes + 32;
        this.KyberIndCpaSecretKeyBytes = this.KyberPolyVecBytes;
        this.KyberIndCpaBytes = this.KyberPolyVecCompressedBytes + this.KyberPolyCompressedBytes;
        this.KyberPublicKeyBytes = this.KyberIndCpaPublicKeyBytes;
        this.KyberSecretKeyBytes = this.KyberIndCpaSecretKeyBytes + this.KyberIndCpaPublicKeyBytes + 64;
        this.KyberCipherTextBytes = this.KyberIndCpaBytes;
        this.CryptoBytes = 32;
        this.CryptoSecretKeyBytes = this.KyberSecretKeyBytes;
        this.CryptoPublicKeyBytes = this.KyberPublicKeyBytes;
        this.CryptoCipherTextBytes = this.KyberCipherTextBytes;
        this.symmetric = bl ? new Symmetric.AesSymmetric() : new Symmetric.ShakeSymmetric();
        this.indCpa = new KyberIndCpa(this);
    }

    public void init(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    public byte[][] generateKemKeyPair() {
        byte[][] byArray = this.indCpa.generateKeyPair();
        byte[] byArray2 = new byte[this.KyberIndCpaSecretKeyBytes];
        System.arraycopy(byArray[1], 0, byArray2, 0, this.KyberIndCpaSecretKeyBytes);
        byte[] byArray3 = new byte[32];
        this.symmetric.hash_h(byArray3, byArray[0], 0);
        byte[] byArray4 = new byte[32];
        this.random.nextBytes(byArray4);
        byte[] byArray5 = new byte[this.KyberIndCpaPublicKeyBytes];
        System.arraycopy(byArray[0], 0, byArray5, 0, this.KyberIndCpaPublicKeyBytes);
        return new byte[][]{Arrays.copyOfRange(byArray5, 0, byArray5.length - 32), Arrays.copyOfRange(byArray5, byArray5.length - 32, byArray5.length), byArray2, byArray3, byArray4};
    }

    public byte[][] kemEncrypt(byte[] byArray) {
        if (byArray.length != this.KyberIndCpaPublicKeyBytes) {
            throw new IllegalArgumentException("Input validation Error: Type check failed for ml-kem encapsulation");
        }
        PolyVec polyVec = new PolyVec(this);
        byte[] byArray2 = this.indCpa.unpackPublicKey(polyVec, byArray);
        byte[] byArray3 = this.indCpa.packPublicKey(polyVec, byArray2);
        if (!Arrays.areEqual(byArray3, byArray)) {
            throw new IllegalArgumentException("Input validation: Modulus check failed for ml-kem encapsulation");
        }
        byte[] byArray4 = new byte[64];
        byte[] byArray5 = new byte[64];
        byte[] byArray6 = new byte[32];
        this.random.nextBytes(byArray6);
        System.arraycopy(byArray6, 0, byArray4, 0, 32);
        this.symmetric.hash_h(byArray4, byArray, 32);
        this.symmetric.hash_g(byArray5, byArray4);
        byte[] byArray7 = this.indCpa.encrypt(Arrays.copyOfRange(byArray4, 0, 32), byArray, Arrays.copyOfRange(byArray5, 32, byArray5.length));
        byte[] byArray8 = new byte[this.sessionKeyLength];
        System.arraycopy(byArray5, 0, byArray8, 0, byArray8.length);
        byte[][] byArrayArray = new byte[][]{byArray8, byArray7};
        return byArrayArray;
    }

    public byte[] kemDecrypt(byte[] byArray, byte[] byArray2) {
        byte[] byArray3 = new byte[64];
        byte[] byArray4 = new byte[64];
        byte[] byArray5 = Arrays.copyOfRange(byArray2, this.KyberIndCpaSecretKeyBytes, byArray2.length);
        System.arraycopy(this.indCpa.decrypt(byArray, byArray2), 0, byArray3, 0, 32);
        System.arraycopy(byArray2, this.KyberSecretKeyBytes - 64, byArray3, 32, 32);
        this.symmetric.hash_g(byArray4, byArray3);
        byte[] byArray6 = this.indCpa.encrypt(Arrays.copyOfRange(byArray3, 0, 32), byArray5, Arrays.copyOfRange(byArray4, 32, byArray4.length));
        boolean bl = !Arrays.constantTimeAreEqual(byArray, byArray6);
        this.symmetric.hash_h(byArray4, byArray, 32);
        this.cmov(byArray4, Arrays.copyOfRange(byArray2, this.KyberSecretKeyBytes - 32, this.KyberSecretKeyBytes), 32, bl);
        return Arrays.copyOfRange(byArray4, 0, this.sessionKeyLength);
    }

    private void cmov(byte[] byArray, byte[] byArray2, int n, boolean bl) {
        if (bl) {
            System.arraycopy(byArray2, 0, byArray, 0, n);
        } else {
            System.arraycopy(byArray, 0, byArray, 0, n);
        }
    }

    public void getRandomBytes(byte[] byArray) {
        this.random.nextBytes(byArray);
    }
}

