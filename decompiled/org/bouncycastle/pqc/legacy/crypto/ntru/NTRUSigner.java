/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.nio.ByteBuffer;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSignerPrng;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningPrivateKeyParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningPublicKeyParameters;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Polynomial;

public class NTRUSigner {
    private NTRUSigningParameters params;
    private Digest hashAlg;
    private NTRUSigningPrivateKeyParameters signingKeyPair;
    private NTRUSigningPublicKeyParameters verificationKey;

    public NTRUSigner(NTRUSigningParameters nTRUSigningParameters) {
        this.params = nTRUSigningParameters;
    }

    public void init(boolean bl, CipherParameters cipherParameters) {
        if (bl) {
            this.signingKeyPair = (NTRUSigningPrivateKeyParameters)cipherParameters;
        } else {
            this.verificationKey = (NTRUSigningPublicKeyParameters)cipherParameters;
        }
        this.hashAlg = this.params.hashAlg;
        this.hashAlg.reset();
    }

    public void update(byte by) {
        if (this.hashAlg == null) {
            throw new IllegalStateException("Call initSign or initVerify first!");
        }
        this.hashAlg.update(by);
    }

    public void update(byte[] byArray, int n, int n2) {
        if (this.hashAlg == null) {
            throw new IllegalStateException("Call initSign or initVerify first!");
        }
        this.hashAlg.update(byArray, n, n2);
    }

    public byte[] generateSignature() {
        if (this.hashAlg == null || this.signingKeyPair == null) {
            throw new IllegalStateException("Call initSign first!");
        }
        byte[] byArray = new byte[this.hashAlg.getDigestSize()];
        this.hashAlg.doFinal(byArray, 0);
        return this.signHash(byArray, this.signingKeyPair);
    }

    private byte[] signHash(byte[] byArray, NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters) {
        IntegerPolynomial integerPolynomial;
        IntegerPolynomial integerPolynomial2;
        int n = 0;
        NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters = nTRUSigningPrivateKeyParameters.getPublicKey();
        do {
            if (++n <= this.params.signFailTolerance) continue;
            throw new IllegalStateException("Signing failed: too many retries (max=" + this.params.signFailTolerance + ")");
        } while (!this.verify(integerPolynomial2 = this.createMsgRep(byArray, n), integerPolynomial = this.sign(integerPolynomial2, nTRUSigningPrivateKeyParameters), nTRUSigningPublicKeyParameters.h));
        byte[] byArray2 = integerPolynomial.toBinary(this.params.q);
        ByteBuffer byteBuffer = ByteBuffer.allocate(byArray2.length + 4);
        byteBuffer.put(byArray2);
        byteBuffer.putInt(n);
        return byteBuffer.array();
    }

    private IntegerPolynomial sign(IntegerPolynomial integerPolynomial, NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters) {
        IntegerPolynomial integerPolynomial2;
        IntegerPolynomial integerPolynomial3;
        Polynomial polynomial;
        Polynomial polynomial2;
        int n = this.params.N;
        int n2 = this.params.q;
        int n3 = this.params.B;
        NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters2 = nTRUSigningPrivateKeyParameters;
        NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters = nTRUSigningPrivateKeyParameters.getPublicKey();
        IntegerPolynomial integerPolynomial4 = new IntegerPolynomial(n);
        for (int i = n3; i >= 1; --i) {
            polynomial2 = nTRUSigningPrivateKeyParameters2.getBasis((int)i).f;
            polynomial = nTRUSigningPrivateKeyParameters2.getBasis((int)i).fPrime;
            integerPolynomial3 = polynomial2.mult(integerPolynomial);
            integerPolynomial3.div(n2);
            integerPolynomial3 = polynomial.mult(integerPolynomial3);
            integerPolynomial2 = polynomial.mult(integerPolynomial);
            integerPolynomial2.div(n2);
            integerPolynomial2 = polynomial2.mult(integerPolynomial2);
            IntegerPolynomial integerPolynomial5 = integerPolynomial3;
            integerPolynomial5.sub(integerPolynomial2);
            integerPolynomial4.add(integerPolynomial5);
            IntegerPolynomial integerPolynomial6 = (IntegerPolynomial)nTRUSigningPrivateKeyParameters2.getBasis((int)i).h.clone();
            if (i > 1) {
                integerPolynomial6.sub(nTRUSigningPrivateKeyParameters2.getBasis((int)(i - 1)).h);
            } else {
                integerPolynomial6.sub(nTRUSigningPublicKeyParameters.h);
            }
            integerPolynomial = integerPolynomial5.mult(integerPolynomial6, n2);
        }
        polynomial2 = nTRUSigningPrivateKeyParameters2.getBasis((int)0).f;
        polynomial = nTRUSigningPrivateKeyParameters2.getBasis((int)0).fPrime;
        integerPolynomial3 = polynomial2.mult(integerPolynomial);
        integerPolynomial3.div(n2);
        integerPolynomial3 = polynomial.mult(integerPolynomial3);
        integerPolynomial2 = polynomial.mult(integerPolynomial);
        integerPolynomial2.div(n2);
        integerPolynomial2 = polynomial2.mult(integerPolynomial2);
        integerPolynomial3.sub(integerPolynomial2);
        integerPolynomial4.add(integerPolynomial3);
        integerPolynomial4.modPositive(n2);
        return integerPolynomial4;
    }

    public boolean verifySignature(byte[] byArray) {
        if (this.hashAlg == null || this.verificationKey == null) {
            throw new IllegalStateException("Call initVerify first!");
        }
        byte[] byArray2 = new byte[this.hashAlg.getDigestSize()];
        this.hashAlg.doFinal(byArray2, 0);
        return this.verifyHash(byArray2, byArray, this.verificationKey);
    }

    private boolean verifyHash(byte[] byArray, byte[] byArray2, NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray2);
        byte[] byArray3 = new byte[byArray2.length - 4];
        byteBuffer.get(byArray3);
        IntegerPolynomial integerPolynomial = IntegerPolynomial.fromBinary(byArray3, this.params.N, this.params.q);
        int n = byteBuffer.getInt();
        return this.verify(this.createMsgRep(byArray, n), integerPolynomial, nTRUSigningPublicKeyParameters.h);
    }

    private boolean verify(IntegerPolynomial integerPolynomial, IntegerPolynomial integerPolynomial2, IntegerPolynomial integerPolynomial3) {
        int n = this.params.q;
        double d = this.params.normBoundSq;
        double d2 = this.params.betaSq;
        IntegerPolynomial integerPolynomial4 = integerPolynomial3.mult(integerPolynomial2, n);
        integerPolynomial4.sub(integerPolynomial);
        long l = (long)((double)integerPolynomial2.centeredNormSq(n) + d2 * (double)integerPolynomial4.centeredNormSq(n));
        return (double)l <= d;
    }

    protected IntegerPolynomial createMsgRep(byte[] byArray, int n) {
        int n2 = this.params.N;
        int n3 = this.params.q;
        int n4 = 31 - Integer.numberOfLeadingZeros(n3);
        int n5 = (n4 + 7) / 8;
        IntegerPolynomial integerPolynomial = new IntegerPolynomial(n2);
        ByteBuffer byteBuffer = ByteBuffer.allocate(byArray.length + 4);
        byteBuffer.put(byArray);
        byteBuffer.putInt(n);
        NTRUSignerPrng nTRUSignerPrng = new NTRUSignerPrng(byteBuffer.array(), this.params.hashAlg);
        for (int i = 0; i < n2; ++i) {
            byte[] byArray2 = nTRUSignerPrng.nextBytes(n5);
            int n6 = byArray2[byArray2.length - 1];
            n6 >>= 8 * n5 - n4;
            byArray2[byArray2.length - 1] = (byte)(n6 <<= 8 * n5 - n4);
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(4);
            byteBuffer2.put(byArray2);
            byteBuffer2.rewind();
            integerPolynomial.coeffs[i] = Integer.reverseBytes(byteBuffer2.getInt());
        }
        return integerPolynomial;
    }
}

