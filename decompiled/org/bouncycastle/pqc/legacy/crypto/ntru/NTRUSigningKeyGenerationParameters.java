/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningParameters;

public class NTRUSigningKeyGenerationParameters
extends KeyGenerationParameters
implements Cloneable {
    public static final int BASIS_TYPE_STANDARD = 0;
    public static final int BASIS_TYPE_TRANSPOSE = 1;
    public static final int KEY_GEN_ALG_RESULTANT = 0;
    public static final int KEY_GEN_ALG_FLOAT = 1;
    public static final NTRUSigningKeyGenerationParameters APR2011_439 = new NTRUSigningKeyGenerationParameters(439, 2048, 146, 1, 1, 0.165, 490.0, 280.0, false, true, 0, new SHA256Digest());
    public static final NTRUSigningKeyGenerationParameters APR2011_439_PROD = new NTRUSigningKeyGenerationParameters(439, 2048, 9, 8, 5, 1, 1, 0.165, 490.0, 280.0, false, true, 0, new SHA256Digest());
    public static final NTRUSigningKeyGenerationParameters APR2011_743 = new NTRUSigningKeyGenerationParameters(743, 2048, 248, 1, 1, 0.127, 560.0, 360.0, true, false, 0, new SHA512Digest());
    public static final NTRUSigningKeyGenerationParameters APR2011_743_PROD = new NTRUSigningKeyGenerationParameters(743, 2048, 11, 11, 15, 1, 1, 0.127, 560.0, 360.0, true, false, 0, new SHA512Digest());
    public static final NTRUSigningKeyGenerationParameters TEST157 = new NTRUSigningKeyGenerationParameters(157, 256, 29, 1, 1, 0.38, 200.0, 80.0, false, false, 0, new SHA256Digest());
    public static final NTRUSigningKeyGenerationParameters TEST157_PROD = new NTRUSigningKeyGenerationParameters(157, 256, 5, 5, 8, 1, 1, 0.38, 200.0, 80.0, false, false, 0, new SHA256Digest());
    public int N;
    public int q;
    public int d;
    public int d1;
    public int d2;
    public int d3;
    public int B;
    double beta;
    public double betaSq;
    double normBound;
    public double normBoundSq;
    public int signFailTolerance = 100;
    double keyNormBound;
    public double keyNormBoundSq;
    public boolean primeCheck;
    public int basisType;
    int bitsF = 6;
    public boolean sparse;
    public int keyGenAlg;
    public Digest hashAlg;
    public int polyType;

    public NTRUSigningKeyGenerationParameters(int n, int n2, int n3, int n4, int n5, double d, double d2, double d3, boolean bl, boolean bl2, int n6, Digest digest) {
        super(CryptoServicesRegistrar.getSecureRandom(), n);
        this.N = n;
        this.q = n2;
        this.d = n3;
        this.B = n4;
        this.basisType = n5;
        this.beta = d;
        this.normBound = d2;
        this.keyNormBound = d3;
        this.primeCheck = bl;
        this.sparse = bl2;
        this.keyGenAlg = n6;
        this.hashAlg = digest;
        this.polyType = 0;
        this.init();
    }

    public NTRUSigningKeyGenerationParameters(int n, int n2, int n3, int n4, int n5, int n6, int n7, double d, double d2, double d3, boolean bl, boolean bl2, int n8, Digest digest) {
        super(CryptoServicesRegistrar.getSecureRandom(), n);
        this.N = n;
        this.q = n2;
        this.d1 = n3;
        this.d2 = n4;
        this.d3 = n5;
        this.B = n6;
        this.basisType = n7;
        this.beta = d;
        this.normBound = d2;
        this.keyNormBound = d3;
        this.primeCheck = bl;
        this.sparse = bl2;
        this.keyGenAlg = n8;
        this.hashAlg = digest;
        this.polyType = 1;
        this.init();
    }

    private void init() {
        this.betaSq = this.beta * this.beta;
        this.normBoundSq = this.normBound * this.normBound;
        this.keyNormBoundSq = this.keyNormBound * this.keyNormBound;
    }

    public NTRUSigningKeyGenerationParameters(InputStream inputStream2) throws IOException {
        super(CryptoServicesRegistrar.getSecureRandom(), 0);
        DataInputStream dataInputStream = new DataInputStream(inputStream2);
        this.N = dataInputStream.readInt();
        this.q = dataInputStream.readInt();
        this.d = dataInputStream.readInt();
        this.d1 = dataInputStream.readInt();
        this.d2 = dataInputStream.readInt();
        this.d3 = dataInputStream.readInt();
        this.B = dataInputStream.readInt();
        this.basisType = dataInputStream.readInt();
        this.beta = dataInputStream.readDouble();
        this.normBound = dataInputStream.readDouble();
        this.keyNormBound = dataInputStream.readDouble();
        this.signFailTolerance = dataInputStream.readInt();
        this.primeCheck = dataInputStream.readBoolean();
        this.sparse = dataInputStream.readBoolean();
        this.bitsF = dataInputStream.readInt();
        this.keyGenAlg = dataInputStream.read();
        String string = dataInputStream.readUTF();
        if ("SHA-512".equals(string)) {
            this.hashAlg = new SHA512Digest();
        } else if ("SHA-256".equals(string)) {
            this.hashAlg = new SHA256Digest();
        }
        this.polyType = dataInputStream.read();
        this.init();
    }

    public void writeTo(OutputStream outputStream2) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream2);
        dataOutputStream.writeInt(this.N);
        dataOutputStream.writeInt(this.q);
        dataOutputStream.writeInt(this.d);
        dataOutputStream.writeInt(this.d1);
        dataOutputStream.writeInt(this.d2);
        dataOutputStream.writeInt(this.d3);
        dataOutputStream.writeInt(this.B);
        dataOutputStream.writeInt(this.basisType);
        dataOutputStream.writeDouble(this.beta);
        dataOutputStream.writeDouble(this.normBound);
        dataOutputStream.writeDouble(this.keyNormBound);
        dataOutputStream.writeInt(this.signFailTolerance);
        dataOutputStream.writeBoolean(this.primeCheck);
        dataOutputStream.writeBoolean(this.sparse);
        dataOutputStream.writeInt(this.bitsF);
        dataOutputStream.write(this.keyGenAlg);
        dataOutputStream.writeUTF(this.hashAlg.getAlgorithmName());
        dataOutputStream.write(this.polyType);
    }

    public NTRUSigningParameters getSigningParameters() {
        return new NTRUSigningParameters(this.N, this.q, this.d, this.B, this.beta, this.normBound, this.hashAlg);
    }

    public NTRUSigningKeyGenerationParameters clone() {
        if (this.polyType == 0) {
            return new NTRUSigningKeyGenerationParameters(this.N, this.q, this.d, this.B, this.basisType, this.beta, this.normBound, this.keyNormBound, this.primeCheck, this.sparse, this.keyGenAlg, this.hashAlg);
        }
        return new NTRUSigningKeyGenerationParameters(this.N, this.q, this.d1, this.d2, this.d3, this.B, this.basisType, this.beta, this.normBound, this.keyNormBound, this.primeCheck, this.sparse, this.keyGenAlg, this.hashAlg);
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.B;
        n = 31 * n + this.N;
        n = 31 * n + this.basisType;
        long l = Double.doubleToLongBits(this.beta);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.betaSq);
        n = 31 * n + (int)(l ^ l >>> 32);
        n = 31 * n + this.bitsF;
        n = 31 * n + this.d;
        n = 31 * n + this.d1;
        n = 31 * n + this.d2;
        n = 31 * n + this.d3;
        n = 31 * n + (this.hashAlg == null ? 0 : this.hashAlg.getAlgorithmName().hashCode());
        n = 31 * n + this.keyGenAlg;
        l = Double.doubleToLongBits(this.keyNormBound);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.keyNormBoundSq);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.normBound);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.normBoundSq);
        n = 31 * n + (int)(l ^ l >>> 32);
        n = 31 * n + this.polyType;
        n = 31 * n + (this.primeCheck ? 1231 : 1237);
        n = 31 * n + this.q;
        n = 31 * n + this.signFailTolerance;
        n = 31 * n + (this.sparse ? 1231 : 1237);
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof NTRUSigningKeyGenerationParameters)) {
            return false;
        }
        NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters = (NTRUSigningKeyGenerationParameters)object;
        if (this.B != nTRUSigningKeyGenerationParameters.B) {
            return false;
        }
        if (this.N != nTRUSigningKeyGenerationParameters.N) {
            return false;
        }
        if (this.basisType != nTRUSigningKeyGenerationParameters.basisType) {
            return false;
        }
        if (Double.doubleToLongBits(this.beta) != Double.doubleToLongBits(nTRUSigningKeyGenerationParameters.beta)) {
            return false;
        }
        if (Double.doubleToLongBits(this.betaSq) != Double.doubleToLongBits(nTRUSigningKeyGenerationParameters.betaSq)) {
            return false;
        }
        if (this.bitsF != nTRUSigningKeyGenerationParameters.bitsF) {
            return false;
        }
        if (this.d != nTRUSigningKeyGenerationParameters.d) {
            return false;
        }
        if (this.d1 != nTRUSigningKeyGenerationParameters.d1) {
            return false;
        }
        if (this.d2 != nTRUSigningKeyGenerationParameters.d2) {
            return false;
        }
        if (this.d3 != nTRUSigningKeyGenerationParameters.d3) {
            return false;
        }
        if (this.hashAlg == null ? nTRUSigningKeyGenerationParameters.hashAlg != null : !this.hashAlg.getAlgorithmName().equals(nTRUSigningKeyGenerationParameters.hashAlg.getAlgorithmName())) {
            return false;
        }
        if (this.keyGenAlg != nTRUSigningKeyGenerationParameters.keyGenAlg) {
            return false;
        }
        if (Double.doubleToLongBits(this.keyNormBound) != Double.doubleToLongBits(nTRUSigningKeyGenerationParameters.keyNormBound)) {
            return false;
        }
        if (Double.doubleToLongBits(this.keyNormBoundSq) != Double.doubleToLongBits(nTRUSigningKeyGenerationParameters.keyNormBoundSq)) {
            return false;
        }
        if (Double.doubleToLongBits(this.normBound) != Double.doubleToLongBits(nTRUSigningKeyGenerationParameters.normBound)) {
            return false;
        }
        if (Double.doubleToLongBits(this.normBoundSq) != Double.doubleToLongBits(nTRUSigningKeyGenerationParameters.normBoundSq)) {
            return false;
        }
        if (this.polyType != nTRUSigningKeyGenerationParameters.polyType) {
            return false;
        }
        if (this.primeCheck != nTRUSigningKeyGenerationParameters.primeCheck) {
            return false;
        }
        if (this.q != nTRUSigningKeyGenerationParameters.q) {
            return false;
        }
        if (this.signFailTolerance != nTRUSigningKeyGenerationParameters.signFailTolerance) {
            return false;
        }
        return this.sparse == nTRUSigningKeyGenerationParameters.sparse;
    }

    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        StringBuilder stringBuilder = new StringBuilder("SignatureParameters(N=" + this.N + " q=" + this.q);
        if (this.polyType == 0) {
            stringBuilder.append(" polyType=SIMPLE d=" + this.d);
        } else {
            stringBuilder.append(" polyType=PRODUCT d1=" + this.d1 + " d2=" + this.d2 + " d3=" + this.d3);
        }
        stringBuilder.append(" B=" + this.B + " basisType=" + this.basisType + " beta=" + decimalFormat.format(this.beta) + " normBound=" + decimalFormat.format(this.normBound) + " keyNormBound=" + decimalFormat.format(this.keyNormBound) + " prime=" + this.primeCheck + " sparse=" + this.sparse + " keyGenAlg=" + this.keyGenAlg + " hashAlg=" + this.hashAlg + ")");
        return stringBuilder.toString();
    }
}

