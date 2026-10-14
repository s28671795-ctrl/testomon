/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionKeyParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionParameters;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.DenseTernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Polynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.ProductFormPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.SparseTernaryPolynomial;

public class NTRUEncryptionPrivateKeyParameters
extends NTRUEncryptionKeyParameters {
    public Polynomial t;
    public IntegerPolynomial fp;
    public IntegerPolynomial h;

    public NTRUEncryptionPrivateKeyParameters(IntegerPolynomial integerPolynomial, Polynomial polynomial, IntegerPolynomial integerPolynomial2, NTRUEncryptionParameters nTRUEncryptionParameters) {
        super(true, nTRUEncryptionParameters);
        this.h = integerPolynomial;
        this.t = polynomial;
        this.fp = integerPolynomial2;
    }

    public NTRUEncryptionPrivateKeyParameters(byte[] byArray, NTRUEncryptionParameters nTRUEncryptionParameters) throws IOException {
        this(new ByteArrayInputStream(byArray), nTRUEncryptionParameters);
    }

    public NTRUEncryptionPrivateKeyParameters(InputStream inputStream2, NTRUEncryptionParameters nTRUEncryptionParameters) throws IOException {
        super(true, nTRUEncryptionParameters);
        if (nTRUEncryptionParameters.polyType == 1) {
            int n = nTRUEncryptionParameters.N;
            int n2 = nTRUEncryptionParameters.df1;
            int n3 = nTRUEncryptionParameters.df2;
            int n4 = nTRUEncryptionParameters.df3;
            int n5 = nTRUEncryptionParameters.fastFp ? nTRUEncryptionParameters.df3 : nTRUEncryptionParameters.df3 - 1;
            this.h = IntegerPolynomial.fromBinary(inputStream2, nTRUEncryptionParameters.N, nTRUEncryptionParameters.q);
            this.t = ProductFormPolynomial.fromBinary(inputStream2, n, n2, n3, n4, n5);
        } else {
            this.h = IntegerPolynomial.fromBinary(inputStream2, nTRUEncryptionParameters.N, nTRUEncryptionParameters.q);
            IntegerPolynomial integerPolynomial = IntegerPolynomial.fromBinary3Tight(inputStream2, nTRUEncryptionParameters.N);
            this.t = nTRUEncryptionParameters.sparse ? new SparseTernaryPolynomial(integerPolynomial) : new DenseTernaryPolynomial(integerPolynomial);
        }
        this.init();
    }

    private void init() {
        if (this.params.fastFp) {
            this.fp = new IntegerPolynomial(this.params.N);
            this.fp.coeffs[0] = 1;
        } else {
            this.fp = this.t.toIntegerPolynomial().invertF3();
        }
    }

    public byte[] getEncoded() {
        byte[] byArray = this.h.toBinary(this.params.q);
        byte[] byArray2 = this.t instanceof ProductFormPolynomial ? ((ProductFormPolynomial)this.t).toBinary() : this.t.toIntegerPolynomial().toBinary3Tight();
        byte[] byArray3 = new byte[byArray.length + byArray2.length];
        System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        return byArray3;
    }

    public void writeTo(OutputStream outputStream2) throws IOException {
        outputStream2.write(this.getEncoded());
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + (this.params == null ? 0 : this.params.hashCode());
        n = 31 * n + (this.t == null ? 0 : this.t.hashCode());
        n = 31 * n + (this.h == null ? 0 : this.h.hashCode());
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof NTRUEncryptionPrivateKeyParameters)) {
            return false;
        }
        NTRUEncryptionPrivateKeyParameters nTRUEncryptionPrivateKeyParameters = (NTRUEncryptionPrivateKeyParameters)object;
        if (this.params == null ? nTRUEncryptionPrivateKeyParameters.params != null : !this.params.equals(nTRUEncryptionPrivateKeyParameters.params)) {
            return false;
        }
        if (this.t == null ? nTRUEncryptionPrivateKeyParameters.t != null : !this.t.equals(nTRUEncryptionPrivateKeyParameters.t)) {
            return false;
        }
        return this.h.equals(nTRUEncryptionPrivateKeyParameters.h);
    }
}

