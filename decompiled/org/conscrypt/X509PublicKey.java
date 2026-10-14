/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.PublicKey;
import java.util.Arrays;

final class X509PublicKey
implements PublicKey {
    private static final long serialVersionUID = -8610156854731664298L;
    private final String algorithm;
    private final byte[] encoded;

    X509PublicKey(String algorithm, byte[] encoded) {
        this.algorithm = algorithm;
        this.encoded = encoded;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public byte[] getEncoded() {
        return this.encoded;
    }

    public String toString() {
        return "X509PublicKey [algorithm=" + this.algorithm + ", encoded=" + Arrays.toString(this.encoded) + "]";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.algorithm == null ? 0 : this.algorithm.hashCode());
        result = 31 * result + Arrays.hashCode(this.encoded);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        X509PublicKey other = (X509PublicKey)obj;
        if (this.algorithm == null ? other.algorithm != null : !this.algorithm.equals(other.algorithm)) {
            return false;
        }
        return Arrays.equals(this.encoded, other.encoded);
    }
}

