/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUSigningParameters;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;

public class NTRUSigningPublicKeyParameters
extends AsymmetricKeyParameter {
    private NTRUSigningParameters params;
    public IntegerPolynomial h;

    public NTRUSigningPublicKeyParameters(IntegerPolynomial integerPolynomial, NTRUSigningParameters nTRUSigningParameters) {
        super(false);
        this.h = integerPolynomial;
        this.params = nTRUSigningParameters;
    }

    public NTRUSigningPublicKeyParameters(byte[] byArray, NTRUSigningParameters nTRUSigningParameters) {
        super(false);
        this.h = IntegerPolynomial.fromBinary(byArray, nTRUSigningParameters.N, nTRUSigningParameters.q);
        this.params = nTRUSigningParameters;
    }

    public NTRUSigningPublicKeyParameters(InputStream inputStream2, NTRUSigningParameters nTRUSigningParameters) throws IOException {
        super(false);
        this.h = IntegerPolynomial.fromBinary(inputStream2, nTRUSigningParameters.N, nTRUSigningParameters.q);
        this.params = nTRUSigningParameters;
    }

    public byte[] getEncoded() {
        return this.h.toBinary(this.params.q);
    }

    public void writeTo(OutputStream outputStream2) throws IOException {
        outputStream2.write(this.getEncoded());
    }

    public int hashCode() {
        int n = 1;
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
        if (this.getClass() != object.getClass()) {
            return false;
        }
        NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters = (NTRUSigningPublicKeyParameters)object;
        if (this.h == null ? nTRUSigningPublicKeyParameters.h != null : !this.h.equals(nTRUSigningPublicKeyParameters.h)) {
            return false;
        }
        return !(this.params == null ? nTRUSigningPublicKeyParameters.params != null : !this.params.equals(nTRUSigningPublicKeyParameters.params));
    }
}

