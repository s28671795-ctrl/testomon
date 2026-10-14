/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLECGroupContext;
import org.conscrypt.OpenSSLECPointContext;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLKeyHolder;
import org.conscrypt.OpenSSLX509CertificateFactory;

final class OpenSSLECPublicKey
implements ECPublicKey,
OpenSSLKeyHolder {
    private static final long serialVersionUID = 3215842926808298020L;
    private static final String ALGORITHM = "EC";
    protected transient OpenSSLKey key;
    protected transient OpenSSLECGroupContext group;

    OpenSSLECPublicKey(OpenSSLECGroupContext group, OpenSSLKey key) {
        this.group = group;
        this.key = key;
    }

    OpenSSLECPublicKey(OpenSSLKey key) {
        this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(key.getNativeRef())));
        this.key = key;
    }

    OpenSSLECPublicKey(ECPublicKeySpec ecKeySpec) throws InvalidKeySpecException {
        try {
            this.group = OpenSSLECGroupContext.getInstance(ecKeySpec.getParams());
            OpenSSLECPointContext pubKey = OpenSSLECPointContext.getInstance(this.group, ecKeySpec.getW());
            this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(this.group.getNativeRef(), pubKey.getNativeRef(), null));
        }
        catch (Exception e) {
            throw new InvalidKeySpecException(e);
        }
    }

    static OpenSSLKey getInstance(ECPublicKey ecPublicKey) throws InvalidKeyException {
        try {
            OpenSSLECGroupContext group = OpenSSLECGroupContext.getInstance(ecPublicKey.getParams());
            OpenSSLECPointContext pubKey = OpenSSLECPointContext.getInstance(group, ecPublicKey.getW());
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(group.getNativeRef(), pubKey.getNativeRef(), null));
        }
        catch (Exception e) {
            throw new InvalidKeyException(e);
        }
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public byte[] getEncoded() {
        return NativeCrypto.EVP_marshal_public_key(this.key.getNativeRef());
    }

    @Override
    public ECParameterSpec getParams() {
        return this.group.getECParameterSpec();
    }

    private ECPoint getPublicKey() {
        OpenSSLECPointContext pubKey = new OpenSSLECPointContext(this.group, new NativeRef.EC_POINT(NativeCrypto.EC_KEY_get_public_key(this.key.getNativeRef())));
        return pubKey.getECPoint();
    }

    @Override
    public ECPoint getW() {
        return this.getPublicKey();
    }

    @Override
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof OpenSSLECPublicKey) {
            OpenSSLECPublicKey other = (OpenSSLECPublicKey)o;
            return this.key.equals(other.key);
        }
        if (!(o instanceof ECPublicKey)) {
            return false;
        }
        ECPublicKey other = (ECPublicKey)o;
        if (!this.getPublicKey().equals(other.getW())) {
            return false;
        }
        ECParameterSpec spec = this.getParams();
        ECParameterSpec otherSpec = other.getParams();
        return spec.getCurve().equals(otherSpec.getCurve()) && spec.getGenerator().equals(otherSpec.getGenerator()) && spec.getOrder().equals(otherSpec.getOrder()) && spec.getCofactor() == otherSpec.getCofactor();
    }

    public int hashCode() {
        return Arrays.hashCode(NativeCrypto.EVP_marshal_public_key(this.key.getNativeRef()));
    }

    public String toString() {
        return NativeCrypto.EVP_PKEY_print_public(this.key.getNativeRef());
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        byte[] encoded = (byte[])stream.readObject();
        try {
            this.key = new OpenSSLKey(NativeCrypto.EVP_parse_public_key(encoded));
        }
        catch (OpenSSLX509CertificateFactory.ParsingException e) {
            throw new IOException(e);
        }
        this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(this.key.getNativeRef())));
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        if (this.key.isHardwareBacked()) {
            throw new NotSerializableException("Hardware backed keys cannot be serialized");
        }
        stream.defaultWriteObject();
        stream.writeObject(this.getEncoded());
    }
}

