/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionKeyParameters;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionParameters;
import org.bouncycastle.pqc.legacy.math.ntru.polynomial.IntegerPolynomial;

public class NTRUEncryptionPublicKeyParameters
extends NTRUEncryptionKeyParameters {
    public IntegerPolynomial h;

    public NTRUEncryptionPublicKeyParameters(IntegerPolynomial integerPolynomial, NTRUEncryptionParameters nTRUEncryptionParameters) {
        super(false, nTRUEncryptionParameters);
        this.h = integerPolynomial;
    }

    public NTRUEncryptionPublicKeyParameters(byte[] byArray, NTRUEncryptionParameters nTRUEncryptionParameters) {
        super(false, nTRUEncryptionParameters);
        this.h = IntegerPolynomial.fromBinary(byArray, nTRUEncryptionParameters.N, nTRUEncryptionParameters.q);
    }

    public NTRUEncryptionPublicKeyParameters(InputStream inputStream2, NTRUEncryptionParameters nTRUEncryptionParameters) throws IOException {
        super(false, nTRUEncryptionParameters);
        this.h = IntegerPolynomial.fromBinary(inputStream2, nTRUEncryptionParameters.N, nTRUEncryptionParameters.q);
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
        if (!(object instanceof NTRUEncryptionPublicKeyParameters)) {
            return false;
        }
        NTRUEncryptionPublicKeyParameters nTRUEncryptionPublicKeyParameters = (NTRUEncryptionPublicKeyParameters)object;
        if (this.h == null ? nTRUEncryptionPublicKeyParameters.h != null : !this.h.equals(nTRUEncryptionPublicKeyParameters.h)) {
            return false;
        }
        return !(this.params == null ? nTRUEncryptionPublicKeyParameters.params != null : !this.params.equals(nTRUEncryptionPublicKeyParameters.params));
    }
}

