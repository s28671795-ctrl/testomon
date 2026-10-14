/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningKeyGenerationParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningPublicKeyParameters;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.DenseTernaryPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.Polynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.ProductFormPolynomial;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.SparseTernaryPolynomial;

public class NTRUSigningPrivateKeyParameters
extends AsymmetricKeyParameter {
    private List<Basis> bases;
    private NTRUSigningPublicKeyParameters publicKey;

    public NTRUSigningPrivateKeyParameters(byte[] byArray, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters) throws IOException {
        this(new ByteArrayInputStream(byArray), nTRUSigningKeyGenerationParameters);
    }

    public NTRUSigningPrivateKeyParameters(InputStream inputStream2, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters) throws IOException {
        super(true);
        this.bases = new ArrayList<Basis>();
        for (int i = 0; i <= nTRUSigningKeyGenerationParameters.B; ++i) {
            this.add(new Basis(inputStream2, nTRUSigningKeyGenerationParameters, i != 0));
        }
        this.publicKey = new NTRUSigningPublicKeyParameters(inputStream2, nTRUSigningKeyGenerationParameters.getSigningParameters());
    }

    public NTRUSigningPrivateKeyParameters(List<Basis> list, NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters) {
        super(true);
        this.bases = new ArrayList<Basis>(list);
        this.publicKey = nTRUSigningPublicKeyParameters;
    }

    private void add(Basis basis) {
        this.bases.add(basis);
    }

    public Basis getBasis(int n) {
        return this.bases.get(n);
    }

    public NTRUSigningPublicKeyParameters getPublicKey() {
        return this.publicKey;
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < this.bases.size(); ++i) {
            this.bases.get(i).encode(byteArrayOutputStream, i != 0);
        }
        byteArrayOutputStream.write(this.publicKey.getEncoded());
        return byteArrayOutputStream.toByteArray();
    }

    public void writeTo(OutputStream outputStream2) throws IOException {
        outputStream2.write(this.getEncoded());
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n;
        if (this.bases == null) {
            return n;
        }
        n += this.bases.hashCode();
        for (Basis basis : this.bases) {
            n += basis.hashCode();
        }
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters = (NTRUSigningPrivateKeyParameters)object;
        if (this.bases == null != (nTRUSigningPrivateKeyParameters.bases == null)) {
            return false;
        }
        if (this.bases == null) {
            return true;
        }
        if (this.bases.size() != nTRUSigningPrivateKeyParameters.bases.size()) {
            return false;
        }
        for (int i = 0; i < this.bases.size(); ++i) {
            Basis basis = this.bases.get(i);
            Basis basis2 = nTRUSigningPrivateKeyParameters.bases.get(i);
            if (!basis.f.equals(basis2.f)) {
                return false;
            }
            if (!basis.fPrime.equals(basis2.fPrime)) {
                return false;
            }
            if (i != 0 && !basis.h.equals(basis2.h)) {
                return false;
            }
            if (basis.params.equals(basis2.params)) continue;
            return false;
        }
        return true;
    }

    public static class Basis {
        public Polynomial f;
        public Polynomial fPrime;
        public IntegerPolynomial h;
        NTRUSigningKeyGenerationParameters params;

        protected Basis(Polynomial polynomial, Polynomial polynomial2, IntegerPolynomial integerPolynomial, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters) {
            this.f = polynomial;
            this.fPrime = polynomial2;
            this.h = integerPolynomial;
            this.params = nTRUSigningKeyGenerationParameters;
        }

        Basis(InputStream inputStream2, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters, boolean bl) throws IOException {
            IntegerPolynomial integerPolynomial;
            int n = nTRUSigningKeyGenerationParameters.N;
            int n2 = nTRUSigningKeyGenerationParameters.q;
            int n3 = nTRUSigningKeyGenerationParameters.d1;
            int n4 = nTRUSigningKeyGenerationParameters.d2;
            int n5 = nTRUSigningKeyGenerationParameters.d3;
            boolean bl2 = nTRUSigningKeyGenerationParameters.sparse;
            this.params = nTRUSigningKeyGenerationParameters;
            if (nTRUSigningKeyGenerationParameters.polyType == 1) {
                this.f = ProductFormPolynomial.fromBinary(inputStream2, n, n3, n4, n5 + 1, n5);
            } else {
                integerPolynomial = IntegerPolynomial.fromBinary3Tight(inputStream2, n);
                Polynomial polynomial = this.f = bl2 ? new SparseTernaryPolynomial(integerPolynomial) : new DenseTernaryPolynomial(integerPolynomial);
            }
            if (nTRUSigningKeyGenerationParameters.basisType == 0) {
                integerPolynomial = IntegerPolynomial.fromBinary(inputStream2, n, n2);
                int n6 = 0;
                while (n6 < integerPolynomial.coeffs.length) {
                    int n7 = n6++;
                    integerPolynomial.coeffs[n7] = integerPolynomial.coeffs[n7] - n2 / 2;
                }
                this.fPrime = integerPolynomial;
            } else {
                this.fPrime = nTRUSigningKeyGenerationParameters.polyType == 1 ? ProductFormPolynomial.fromBinary(inputStream2, n, n3, n4, n5 + 1, n5) : IntegerPolynomial.fromBinary3Tight(inputStream2, n);
            }
            if (bl) {
                this.h = IntegerPolynomial.fromBinary(inputStream2, n, n2);
            }
        }

        void encode(OutputStream outputStream2, boolean bl) throws IOException {
            int n = this.params.q;
            outputStream2.write(this.getEncoded(this.f));
            if (this.params.basisType == 0) {
                IntegerPolynomial integerPolynomial = this.fPrime.toIntegerPolynomial();
                int n2 = 0;
                while (n2 < integerPolynomial.coeffs.length) {
                    int n3 = n2++;
                    integerPolynomial.coeffs[n3] = integerPolynomial.coeffs[n3] + n / 2;
                }
                outputStream2.write(integerPolynomial.toBinary(n));
            } else {
                outputStream2.write(this.getEncoded(this.fPrime));
            }
            if (bl) {
                outputStream2.write(this.h.toBinary(n));
            }
        }

        private byte[] getEncoded(Polynomial polynomial) {
            if (polynomial instanceof ProductFormPolynomial) {
                return ((ProductFormPolynomial)polynomial).toBinary();
            }
            return polynomial.toIntegerPolynomial().toBinary3Tight();
        }

        public int hashCode() {
            int n = 1;
            n = 31 * n + (this.f == null ? 0 : this.f.hashCode());
            n = 31 * n + (this.fPrime == null ? 0 : this.fPrime.hashCode());
            n = 31 * n + (this.h == null ? 0 : this.h.hashCode());
            n = 31 * n + (this.params == null ? 0 : this.params.hashCode());
            return n;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (!(object instanceof Basis)) {
                return false;
            }
            Basis basis = (Basis)object;
            if (this.f == null ? basis.f != null : !this.f.equals(basis.f)) {
                return false;
            }
            if (this.fPrime == null ? basis.fPrime != null : !this.fPrime.equals(basis.fPrime)) {
                return false;
            }
            if (this.h == null ? basis.h != null : !this.h.equals(basis.h)) {
                return false;
            }
            return !(this.params == null ? basis.params != null : !this.params.equals(basis.params));
        }
    }
}

