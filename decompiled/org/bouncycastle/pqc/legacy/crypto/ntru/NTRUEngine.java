/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.legacy.crypto.ntru.IndexGenerator;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionPrivateKeyParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionPublicKeyParameters;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.DenseTernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Polynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.ProductFormPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.SparseTernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.TernaryPolynomial;
import org.bouncycastle.util.Arrays;

public class NTRUEngine
implements AsymmetricBlockCipher {
    private boolean forEncryption;
    private NTRUEncryptionParameters params;
    private NTRUEncryptionPublicKeyParameters pubKey;
    private NTRUEncryptionPrivateKeyParameters privKey;
    private SecureRandom random;

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        this.forEncryption = bl;
        SecureRandom secureRandom = null;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom)cipherParameters;
            secureRandom = parametersWithRandom.getRandom();
            cipherParameters = parametersWithRandom.getParameters();
        }
        if (bl) {
            this.pubKey = (NTRUEncryptionPublicKeyParameters)cipherParameters;
            this.privKey = null;
            this.params = this.pubKey.getParameters();
            this.random = CryptoServicesRegistrar.getSecureRandom(secureRandom);
        } else {
            this.pubKey = null;
            this.privKey = (NTRUEncryptionPrivateKeyParameters)cipherParameters;
            this.params = this.privKey.getParameters();
            this.random = null;
        }
    }

    @Override
    public int getInputBlockSize() {
        return this.params.maxMsgLenBytes;
    }

    @Override
    public int getOutputBlockSize() {
        return (this.params.N * this.log2(this.params.q) + 7) / 8;
    }

    @Override
    public byte[] processBlock(byte[] byArray, int n, int n2) throws InvalidCipherTextException {
        byte[] byArray2 = new byte[n2];
        System.arraycopy(byArray, n, byArray2, 0, n2);
        if (this.forEncryption) {
            return this.encrypt(byArray2, this.pubKey);
        }
        return this.decrypt(byArray2, this.privKey);
    }

    private byte[] encrypt(byte[] byArray, NTRUEncryptionPublicKeyParameters nTRUEncryptionPublicKeyParameters) {
        IntegerPolynomial integerPolynomial;
        IntegerPolynomial integerPolynomial2;
        IntegerPolynomial integerPolynomial3 = nTRUEncryptionPublicKeyParameters.h;
        int n = this.params.N;
        int n2 = this.params.q;
        int n3 = this.params.maxMsgLenBytes;
        int n4 = this.params.db;
        int n5 = this.params.bufferLenBits;
        int n6 = this.params.dm0;
        int n7 = this.params.pkLen;
        int n8 = this.params.minCallsMask;
        boolean bl = this.params.hashSeed;
        byte[] byArray2 = this.params.oid;
        int n9 = byArray.length;
        if (n3 > 255) {
            throw new IllegalArgumentException("llen values bigger than 1 are not supported");
        }
        if (n9 > n3) {
            throw new DataLengthException("Message too long: " + n9 + ">" + n3);
        }
        do {
            byte[] byArray3 = new byte[n4 / 8];
            this.random.nextBytes(byArray3);
            byte[] byArray4 = new byte[n3 + 1 - n9];
            byte[] byArray5 = new byte[n5 / 8];
            System.arraycopy(byArray3, 0, byArray5, 0, byArray3.length);
            byArray5[byArray3.length] = (byte)n9;
            System.arraycopy(byArray, 0, byArray5, byArray3.length + 1, byArray.length);
            System.arraycopy(byArray4, 0, byArray5, byArray3.length + 1 + byArray.length, byArray4.length);
            integerPolynomial2 = IntegerPolynomial.fromBinary3Sves(byArray5, n);
            byte[] byArray6 = integerPolynomial3.toBinary(n2);
            byte[] byArray7 = this.copyOf(byArray6, n7 / 8);
            byte[] byArray8 = this.buildSData(byArray2, byArray, n9, byArray3, byArray7);
            Polynomial polynomial = this.generateBlindingPoly(byArray8, byArray5);
            integerPolynomial = polynomial.mult(integerPolynomial3, n2);
            IntegerPolynomial integerPolynomial4 = (IntegerPolynomial)integerPolynomial.clone();
            integerPolynomial4.modPositive(4);
            byte[] byArray9 = integerPolynomial4.toBinary(4);
            IntegerPolynomial integerPolynomial5 = this.MGF(byArray9, n, n8, bl);
            integerPolynomial2.add(integerPolynomial5);
            integerPolynomial2.mod3();
        } while (integerPolynomial2.count(-1) < n6 || integerPolynomial2.count(0) < n6 || integerPolynomial2.count(1) < n6);
        integerPolynomial.add(integerPolynomial2, n2);
        integerPolynomial.ensurePositive(n2);
        return integerPolynomial.toBinary(n2);
    }

    private byte[] buildSData(byte[] byArray, byte[] byArray2, int n, byte[] byArray3, byte[] byArray4) {
        byte[] byArray5 = new byte[byArray.length + n + byArray3.length + byArray4.length];
        System.arraycopy(byArray, 0, byArray5, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray5, byArray.length, byArray2.length);
        System.arraycopy(byArray3, 0, byArray5, byArray.length + byArray2.length, byArray3.length);
        System.arraycopy(byArray4, 0, byArray5, byArray.length + byArray2.length + byArray3.length, byArray4.length);
        return byArray5;
    }

    protected IntegerPolynomial encrypt(IntegerPolynomial integerPolynomial, TernaryPolynomial ternaryPolynomial, IntegerPolynomial integerPolynomial2) {
        IntegerPolynomial integerPolynomial3 = ternaryPolynomial.mult(integerPolynomial2, this.params.q);
        integerPolynomial3.add(integerPolynomial, this.params.q);
        integerPolynomial3.ensurePositive(this.params.q);
        return integerPolynomial3;
    }

    private Polynomial generateBlindingPoly(byte[] byArray, byte[] byArray2) {
        IndexGenerator indexGenerator = new IndexGenerator(byArray, this.params);
        if (this.params.polyType == 1) {
            SparseTernaryPolynomial sparseTernaryPolynomial = new SparseTernaryPolynomial(this.generateBlindingCoeffs(indexGenerator, this.params.dr1));
            SparseTernaryPolynomial sparseTernaryPolynomial2 = new SparseTernaryPolynomial(this.generateBlindingCoeffs(indexGenerator, this.params.dr2));
            SparseTernaryPolynomial sparseTernaryPolynomial3 = new SparseTernaryPolynomial(this.generateBlindingCoeffs(indexGenerator, this.params.dr3));
            return new ProductFormPolynomial(sparseTernaryPolynomial, sparseTernaryPolynomial2, sparseTernaryPolynomial3);
        }
        int n = this.params.dr;
        boolean bl = this.params.sparse;
        int[] nArray = this.generateBlindingCoeffs(indexGenerator, n);
        if (bl) {
            return new SparseTernaryPolynomial(nArray);
        }
        return new DenseTernaryPolynomial(nArray);
    }

    private int[] generateBlindingCoeffs(IndexGenerator indexGenerator, int n) {
        int n2 = this.params.N;
        int[] nArray = new int[n2];
        for (int i = -1; i <= 1; i += 2) {
            int n3 = 0;
            while (n3 < n) {
                int n4 = indexGenerator.nextIndex();
                if (nArray[n4] != 0) continue;
                nArray[n4] = i;
                ++n3;
            }
        }
        return nArray;
    }

    private IntegerPolynomial MGF(byte[] byArray, int n, int n2, boolean bl) {
        Object object;
        int n3;
        Digest digest = this.params.hashAlg;
        int n4 = digest.getDigestSize();
        byte[] byArray2 = new byte[n2 * n4];
        byte[] byArray3 = bl ? this.calcHash(digest, byArray) : byArray;
        for (n3 = 0; n3 < n2; ++n3) {
            digest.update(byArray3, 0, byArray3.length);
            this.putInt(digest, n3);
            object = this.calcHash(digest);
            System.arraycopy(object, 0, byArray2, n3 * n4, n4);
        }
        object = new IntegerPolynomial(n);
        while (true) {
            int n5 = 0;
            for (int i = 0; i != byArray2.length; ++i) {
                int n6 = byArray2[i] & 0xFF;
                if (n6 >= 243) continue;
                for (int j = 0; j < 4; ++j) {
                    int n7 = n6 % 3;
                    ((IntegerPolynomial)object).coeffs[n5] = n7 - 1;
                    if (++n5 == n) {
                        return object;
                    }
                    n6 = (n6 - n7) / 3;
                }
                ((IntegerPolynomial)object).coeffs[n5] = n6 - 1;
                if (++n5 != n) continue;
                return object;
            }
            if (n5 >= n) {
                return object;
            }
            digest.update(byArray3, 0, byArray3.length);
            this.putInt(digest, n3);
            byte[] byArray4 = this.calcHash(digest);
            byArray2 = byArray4;
            ++n3;
        }
    }

    private void putInt(Digest digest, int n) {
        digest.update((byte)(n >> 24));
        digest.update((byte)(n >> 16));
        digest.update((byte)(n >> 8));
        digest.update((byte)n);
    }

    private byte[] calcHash(Digest digest) {
        byte[] byArray = new byte[digest.getDigestSize()];
        digest.doFinal(byArray, 0);
        return byArray;
    }

    private byte[] calcHash(Digest digest, byte[] byArray) {
        byte[] byArray2 = new byte[digest.getDigestSize()];
        digest.update(byArray, 0, byArray.length);
        digest.doFinal(byArray2, 0);
        return byArray2;
    }

    private byte[] decrypt(byte[] byArray, NTRUEncryptionPrivateKeyParameters nTRUEncryptionPrivateKeyParameters) throws InvalidCipherTextException {
        Polynomial polynomial = nTRUEncryptionPrivateKeyParameters.t;
        IntegerPolynomial integerPolynomial = nTRUEncryptionPrivateKeyParameters.fp;
        IntegerPolynomial integerPolynomial2 = nTRUEncryptionPrivateKeyParameters.h;
        int n = this.params.N;
        int n2 = this.params.q;
        int n3 = this.params.db;
        int n4 = this.params.maxMsgLenBytes;
        int n5 = this.params.dm0;
        int n6 = this.params.pkLen;
        int n7 = this.params.minCallsMask;
        boolean bl = this.params.hashSeed;
        byte[] byArray2 = this.params.oid;
        if (n4 > 255) {
            throw new DataLengthException("maxMsgLenBytes values bigger than 255 are not supported");
        }
        int n8 = n3 / 8;
        IntegerPolynomial integerPolynomial3 = IntegerPolynomial.fromBinary(byArray, n, n2);
        IntegerPolynomial integerPolynomial4 = this.decrypt(integerPolynomial3, polynomial, integerPolynomial);
        if (integerPolynomial4.count(-1) < n5) {
            throw new InvalidCipherTextException("Less than dm0 coefficients equal -1");
        }
        if (integerPolynomial4.count(0) < n5) {
            throw new InvalidCipherTextException("Less than dm0 coefficients equal 0");
        }
        if (integerPolynomial4.count(1) < n5) {
            throw new InvalidCipherTextException("Less than dm0 coefficients equal 1");
        }
        IntegerPolynomial integerPolynomial5 = (IntegerPolynomial)integerPolynomial3.clone();
        integerPolynomial5.sub(integerPolynomial4);
        integerPolynomial5.modPositive(n2);
        IntegerPolynomial integerPolynomial6 = (IntegerPolynomial)integerPolynomial5.clone();
        integerPolynomial6.modPositive(4);
        byte[] byArray3 = integerPolynomial6.toBinary(4);
        IntegerPolynomial integerPolynomial7 = this.MGF(byArray3, n, n7, bl);
        IntegerPolynomial integerPolynomial8 = integerPolynomial4;
        integerPolynomial8.sub(integerPolynomial7);
        integerPolynomial8.mod3();
        byte[] byArray4 = integerPolynomial8.toBinary3Sves();
        byte[] byArray5 = new byte[n8];
        System.arraycopy(byArray4, 0, byArray5, 0, n8);
        int n9 = byArray4[n8] & 0xFF;
        if (n9 > n4) {
            throw new InvalidCipherTextException("Message too long: " + n9 + ">" + n4);
        }
        byte[] byArray6 = new byte[n9];
        System.arraycopy(byArray4, n8 + 1, byArray6, 0, n9);
        byte[] byArray7 = new byte[byArray4.length - (n8 + 1 + n9)];
        System.arraycopy(byArray4, n8 + 1 + n9, byArray7, 0, byArray7.length);
        if (!Arrays.constantTimeAreEqual(byArray7, new byte[byArray7.length])) {
            throw new InvalidCipherTextException("The message is not followed by zeroes");
        }
        byte[] byArray8 = integerPolynomial2.toBinary(n2);
        byte[] byArray9 = this.copyOf(byArray8, n6 / 8);
        byte[] byArray10 = this.buildSData(byArray2, byArray6, n9, byArray5, byArray9);
        Polynomial polynomial2 = this.generateBlindingPoly(byArray10, byArray6);
        IntegerPolynomial integerPolynomial9 = polynomial2.mult(integerPolynomial2);
        integerPolynomial9.modPositive(n2);
        if (!integerPolynomial9.equals(integerPolynomial5)) {
            throw new InvalidCipherTextException("Invalid message encoding");
        }
        return byArray6;
    }

    protected IntegerPolynomial decrypt(IntegerPolynomial integerPolynomial, Polynomial polynomial, IntegerPolynomial integerPolynomial2) {
        IntegerPolynomial integerPolynomial3;
        if (this.params.fastFp) {
            integerPolynomial3 = polynomial.mult(integerPolynomial, this.params.q);
            integerPolynomial3.mult(3);
            integerPolynomial3.add(integerPolynomial);
        } else {
            integerPolynomial3 = polynomial.mult(integerPolynomial, this.params.q);
        }
        integerPolynomial3.center0(this.params.q);
        integerPolynomial3.mod3();
        IntegerPolynomial integerPolynomial4 = this.params.fastFp ? integerPolynomial3 : new DenseTernaryPolynomial(integerPolynomial3).mult(integerPolynomial2, 3);
        integerPolynomial4.center0(3);
        return integerPolynomial4;
    }

    private byte[] copyOf(byte[] byArray, int n) {
        byte[] byArray2 = new byte[n];
        System.arraycopy(byArray, 0, byArray2, 0, n < byArray.length ? n : byArray.length);
        return byArray2;
    }

    private int log2(int n) {
        if (n == 2048) {
            return 11;
        }
        throw new IllegalStateException("log2 not fully implemented");
    }
}

