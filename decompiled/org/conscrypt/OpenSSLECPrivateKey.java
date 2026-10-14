/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLECGroupContext;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLKeyHolder;
import org.conscrypt.OpenSSLX509CertificateFactory;

final class OpenSSLECPrivateKey
implements ECPrivateKey,
OpenSSLKeyHolder {
    private static final long serialVersionUID = -4036633595001083922L;
    private static final String ALGORITHM = "EC";
    protected transient OpenSSLKey key;
    protected transient OpenSSLECGroupContext group;

    OpenSSLECPrivateKey(OpenSSLECGroupContext group, OpenSSLKey key) {
        this.group = group;
        this.key = key;
    }

    OpenSSLECPrivateKey(OpenSSLKey key) {
        this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(key.getNativeRef())));
        this.key = key;
    }

    OpenSSLECPrivateKey(ECPrivateKeySpec ecKeySpec) throws InvalidKeySpecException {
        try {
            this.group = OpenSSLECGroupContext.getInstance(ecKeySpec.getParams());
            BigInteger privKey = ecKeySpec.getS();
            this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(this.group.getNativeRef(), null, privKey.toByteArray()));
        }
        catch (Exception e) {
            throw new InvalidKeySpecException(e);
        }
    }

    static OpenSSLKey wrapPlatformKey(ECPrivateKey ecPrivateKey) throws InvalidKeyException {
        OpenSSLECGroupContext group;
        try {
            group = OpenSSLECGroupContext.getInstance(ecPrivateKey.getParams());
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException("Unknown group parameters", e);
        }
        return OpenSSLECPrivateKey.wrapPlatformKey(ecPrivateKey, group);
    }

    static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException {
        ECParameterSpec params = null;
        if (privateKey instanceof ECKey) {
            params = ((ECKey)((Object)privateKey)).getParams();
        } else if (publicKey instanceof ECKey) {
            params = ((ECKey)((Object)publicKey)).getParams();
        }
        if (params == null) {
            throw new InvalidKeyException("EC parameters not available. Private: " + privateKey + ", public: " + publicKey);
        }
        return OpenSSLECPrivateKey.wrapJCAPrivateKeyForTLSStackOnly(privateKey, params);
    }

    static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, ECParameterSpec params) throws InvalidKeyException {
        OpenSSLECGroupContext group;
        if (params == null && privateKey instanceof ECKey) {
            params = ((ECKey)((Object)privateKey)).getParams();
        }
        if (params == null) {
            throw new InvalidKeyException("EC parameters not available: " + privateKey);
        }
        try {
            group = OpenSSLECGroupContext.getInstance(params);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException("Invalid EC parameters: " + params);
        }
        return new OpenSSLKey(NativeCrypto.getECPrivateKeyWrapper(privateKey, group.getNativeRef()), true);
    }

    private static OpenSSLKey wrapPlatformKey(ECPrivateKey ecPrivateKey, OpenSSLECGroupContext group) throws InvalidKeyException {
        return new OpenSSLKey(NativeCrypto.getECPrivateKeyWrapper(ecPrivateKey, group.getNativeRef()), true);
    }

    static OpenSSLKey getInstance(ECPrivateKey ecPrivateKey) throws InvalidKeyException {
        try {
            OpenSSLECGroupContext group = OpenSSLECGroupContext.getInstance(ecPrivateKey.getParams());
            if (ecPrivateKey.getFormat() == null) {
                return OpenSSLECPrivateKey.wrapPlatformKey(ecPrivateKey, group);
            }
            BigInteger privKey = ecPrivateKey.getS();
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(group.getNativeRef(), null, privKey.toByteArray()));
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
        if (this.key.isHardwareBacked()) {
            return null;
        }
        return "PKCS#8";
    }

    @Override
    public byte[] getEncoded() {
        if (this.key.isHardwareBacked()) {
            return null;
        }
        return NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef());
    }

    @Override
    public ECParameterSpec getParams() {
        return this.group.getECParameterSpec();
    }

    @Override
    public BigInteger getS() {
        if (this.key.isHardwareBacked()) {
            throw new UnsupportedOperationException("Private key value S cannot be extracted");
        }
        return this.getPrivateKey();
    }

    private BigInteger getPrivateKey() {
        return new BigInteger(NativeCrypto.EC_KEY_get_private_key(this.key.getNativeRef()));
    }

    @Override
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof OpenSSLECPrivateKey) {
            OpenSSLECPrivateKey other = (OpenSSLECPrivateKey)o;
            return this.key.equals(other.key);
        }
        if (!(o instanceof ECPrivateKey)) {
            return false;
        }
        ECPrivateKey other = (ECPrivateKey)o;
        if (!this.getPrivateKey().equals(other.getS())) {
            return false;
        }
        ECParameterSpec spec = this.getParams();
        ECParameterSpec otherSpec = other.getParams();
        return spec.getCurve().equals(otherSpec.getCurve()) && spec.getGenerator().equals(otherSpec.getGenerator()) && spec.getOrder().equals(otherSpec.getOrder()) && spec.getCofactor() == otherSpec.getCofactor();
    }

    public int hashCode() {
        return Arrays.hashCode(NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("OpenSSLECPrivateKey{");
        sb.append("params={");
        sb.append(NativeCrypto.EVP_PKEY_print_params(this.key.getNativeRef()));
        sb.append("}}");
        return sb.toString();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        byte[] encoded = (byte[])stream.readObject();
        try {
            this.key = new OpenSSLKey(NativeCrypto.EVP_parse_private_key(encoded));
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

