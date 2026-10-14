/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLKeyHolder;

public class OpenSSLRSAPublicKey
implements RSAPublicKey,
OpenSSLKeyHolder {
    private static final long serialVersionUID = 123125005824688292L;
    private transient OpenSSLKey key;
    private BigInteger publicExponent;
    private BigInteger modulus;
    private transient boolean fetchedParams;

    OpenSSLRSAPublicKey(OpenSSLKey key) {
        this.key = key;
    }

    @Override
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    OpenSSLRSAPublicKey(RSAPublicKeySpec spec) throws InvalidKeySpecException {
        try {
            this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(spec.getModulus().toByteArray(), spec.getPublicExponent().toByteArray(), null, null, null, null, null, null));
        }
        catch (Exception e) {
            throw new InvalidKeySpecException(e);
        }
    }

    static OpenSSLKey getInstance(RSAPublicKey rsaPublicKey) throws InvalidKeyException {
        try {
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(rsaPublicKey.getModulus().toByteArray(), rsaPublicKey.getPublicExponent().toByteArray(), null, null, null, null, null, null));
        }
        catch (Exception e) {
            throw new InvalidKeyException(e);
        }
    }

    @Override
    public String getAlgorithm() {
        return "RSA";
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public byte[] getEncoded() {
        return NativeCrypto.EVP_marshal_public_key(this.key.getNativeRef());
    }

    private synchronized void ensureReadParams() {
        if (this.fetchedParams) {
            return;
        }
        byte[][] params = NativeCrypto.get_RSA_public_params(this.key.getNativeRef());
        this.modulus = new BigInteger(params[0]);
        this.publicExponent = new BigInteger(params[1]);
        this.fetchedParams = true;
    }

    @Override
    public BigInteger getModulus() {
        this.ensureReadParams();
        return this.modulus;
    }

    @Override
    public BigInteger getPublicExponent() {
        this.ensureReadParams();
        return this.publicExponent;
    }

    public boolean equals(Object o) {
        RSAPublicKey other;
        if (o == this) {
            return true;
        }
        if (o instanceof OpenSSLRSAPublicKey && this.key.equals(((OpenSSLRSAPublicKey)(other = (OpenSSLRSAPublicKey)o)).getOpenSSLKey())) {
            return true;
        }
        if (!(o instanceof RSAPublicKey)) {
            return false;
        }
        this.ensureReadParams();
        other = (RSAPublicKey)o;
        return this.modulus.equals(other.getModulus()) && this.publicExponent.equals(other.getPublicExponent());
    }

    public int hashCode() {
        this.ensureReadParams();
        return this.modulus.hashCode() ^ this.publicExponent.hashCode();
    }

    public String toString() {
        this.ensureReadParams();
        StringBuilder sb = new StringBuilder("OpenSSLRSAPublicKey{");
        sb.append("modulus=");
        sb.append(this.modulus.toString(16));
        sb.append(',');
        sb.append("publicExponent=");
        sb.append(this.publicExponent.toString(16));
        sb.append('}');
        return sb.toString();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(this.modulus.toByteArray(), this.publicExponent.toByteArray(), null, null, null, null, null, null));
        this.fetchedParams = true;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        this.ensureReadParams();
        stream.defaultWriteObject();
    }
}

