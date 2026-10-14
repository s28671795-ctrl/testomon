/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLBIOInputStream;
import org.conscrypt.OpenSSLECPrivateKey;
import org.conscrypt.OpenSSLECPublicKey;
import org.conscrypt.OpenSSLKeyHolder;
import org.conscrypt.OpenSSLRSAPrivateKey;
import org.conscrypt.OpenSSLRSAPublicKey;
import org.conscrypt.OpenSSLX509CertificateFactory;
import org.conscrypt.Platform;

final class OpenSSLKey {
    private final NativeRef.EVP_PKEY ctx;
    private final boolean wrapped;
    private final boolean hardwareBacked;

    OpenSSLKey(long ctx) {
        this(ctx, false);
    }

    OpenSSLKey(long ctx, boolean wrapped) {
        this(ctx, wrapped, false);
    }

    OpenSSLKey(long ctx, boolean wrapped, boolean hardwareBacked) {
        this.ctx = new NativeRef.EVP_PKEY(ctx);
        this.wrapped = wrapped;
        this.hardwareBacked = hardwareBacked;
    }

    NativeRef.EVP_PKEY getNativeRef() {
        return this.ctx;
    }

    boolean isWrapped() {
        return this.wrapped;
    }

    boolean isHardwareBacked() {
        return this.hardwareBacked;
    }

    static OpenSSLKey fromPrivateKey(PrivateKey key) throws InvalidKeyException {
        if (key instanceof OpenSSLKeyHolder) {
            return ((OpenSSLKeyHolder)((Object)key)).getOpenSSLKey();
        }
        String keyFormat = key.getFormat();
        if (keyFormat == null) {
            return OpenSSLKey.wrapPrivateKey(key);
        }
        if (!"PKCS#8".equals(key.getFormat())) {
            throw new InvalidKeyException("Unknown key format " + keyFormat);
        }
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("Key encoding is null");
        }
        try {
            return new OpenSSLKey(NativeCrypto.EVP_parse_private_key(key.getEncoded()));
        }
        catch (OpenSSLX509CertificateFactory.ParsingException e) {
            throw new InvalidKeyException(e);
        }
    }

    static OpenSSLKey fromPrivateKeyPemInputStream(InputStream is) throws InvalidKeyException {
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            long keyCtx = NativeCrypto.PEM_read_bio_PrivateKey(bis.getBioContext());
            if (keyCtx == 0L) {
                OpenSSLKey openSSLKey = null;
                return openSSLKey;
            }
            OpenSSLKey openSSLKey = new OpenSSLKey(keyCtx);
            return openSSLKey;
        }
        catch (Exception e) {
            throw new InvalidKeyException(e);
        }
        finally {
            bis.release();
        }
    }

    static OpenSSLKey fromPrivateKeyForTLSStackOnly(PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException {
        OpenSSLKey result = OpenSSLKey.getOpenSSLKey(privateKey);
        if (result != null) {
            return result;
        }
        result = OpenSSLKey.fromKeyMaterial(privateKey);
        if (result != null) {
            return result;
        }
        return OpenSSLKey.wrapJCAPrivateKeyForTLSStackOnly(privateKey, publicKey);
    }

    static OpenSSLKey fromECPrivateKeyForTLSStackOnly(PrivateKey key, ECParameterSpec ecParams) throws InvalidKeyException {
        OpenSSLKey result = OpenSSLKey.getOpenSSLKey(key);
        if (result != null) {
            return result;
        }
        result = OpenSSLKey.fromKeyMaterial(key);
        if (result != null) {
            return result;
        }
        return OpenSSLECPrivateKey.wrapJCAPrivateKeyForTLSStackOnly(key, ecParams);
    }

    private static OpenSSLKey getOpenSSLKey(PrivateKey key) {
        if (key instanceof OpenSSLKeyHolder) {
            return ((OpenSSLKeyHolder)((Object)key)).getOpenSSLKey();
        }
        if ("RSA".equals(key.getAlgorithm())) {
            return Platform.wrapRsaKey(key);
        }
        return null;
    }

    private static OpenSSLKey fromKeyMaterial(PrivateKey key) throws InvalidKeyException {
        if (!"PKCS#8".equals(key.getFormat())) {
            return null;
        }
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            return null;
        }
        try {
            return new OpenSSLKey(NativeCrypto.EVP_parse_private_key(encoded));
        }
        catch (OpenSSLX509CertificateFactory.ParsingException e) {
            throw new InvalidKeyException(e);
        }
    }

    private static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException {
        String keyAlgorithm = privateKey.getAlgorithm();
        if ("RSA".equals(keyAlgorithm)) {
            return OpenSSLRSAPrivateKey.wrapJCAPrivateKeyForTLSStackOnly(privateKey, publicKey);
        }
        if ("EC".equals(keyAlgorithm)) {
            return OpenSSLECPrivateKey.wrapJCAPrivateKeyForTLSStackOnly(privateKey, publicKey);
        }
        throw new InvalidKeyException("Unsupported key algorithm: " + keyAlgorithm);
    }

    private static OpenSSLKey wrapPrivateKey(PrivateKey key) throws InvalidKeyException {
        if (key instanceof RSAPrivateKey) {
            return OpenSSLRSAPrivateKey.wrapPlatformKey((RSAPrivateKey)key);
        }
        if (key instanceof ECPrivateKey) {
            return OpenSSLECPrivateKey.wrapPlatformKey((ECPrivateKey)key);
        }
        throw new InvalidKeyException("Unknown key type: " + key.toString());
    }

    static OpenSSLKey fromPublicKey(PublicKey key) throws InvalidKeyException {
        if (key instanceof OpenSSLKeyHolder) {
            return ((OpenSSLKeyHolder)((Object)key)).getOpenSSLKey();
        }
        if (!"X.509".equals(key.getFormat())) {
            throw new InvalidKeyException("Unknown key format " + key.getFormat());
        }
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("Key encoding is null");
        }
        try {
            return new OpenSSLKey(NativeCrypto.EVP_parse_public_key(key.getEncoded()));
        }
        catch (Exception e) {
            throw new InvalidKeyException(e);
        }
    }

    static OpenSSLKey fromPublicKeyPemInputStream(InputStream is) throws InvalidKeyException {
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            long keyCtx = NativeCrypto.PEM_read_bio_PUBKEY(bis.getBioContext());
            if (keyCtx == 0L) {
                OpenSSLKey openSSLKey = null;
                return openSSLKey;
            }
            OpenSSLKey openSSLKey = new OpenSSLKey(keyCtx);
            return openSSLKey;
        }
        catch (Exception e) {
            throw new InvalidKeyException(e);
        }
        finally {
            bis.release();
        }
    }

    PublicKey getPublicKey() throws NoSuchAlgorithmException {
        switch (NativeCrypto.EVP_PKEY_type(this.ctx)) {
            case 6: {
                return new OpenSSLRSAPublicKey(this);
            }
            case 408: {
                return new OpenSSLECPublicKey(this);
            }
        }
        throw new NoSuchAlgorithmException("unknown PKEY type");
    }

    static PublicKey getPublicKey(X509EncodedKeySpec keySpec, int type) throws InvalidKeySpecException {
        OpenSSLKey key;
        X509EncodedKeySpec x509KeySpec = keySpec;
        try {
            key = new OpenSSLKey(NativeCrypto.EVP_parse_public_key(x509KeySpec.getEncoded()));
        }
        catch (Exception e) {
            throw new InvalidKeySpecException(e);
        }
        if (NativeCrypto.EVP_PKEY_type(key.getNativeRef()) != type) {
            throw new InvalidKeySpecException("Unexpected key type");
        }
        try {
            return key.getPublicKey();
        }
        catch (NoSuchAlgorithmException e) {
            throw new InvalidKeySpecException(e);
        }
    }

    PrivateKey getPrivateKey() throws NoSuchAlgorithmException {
        switch (NativeCrypto.EVP_PKEY_type(this.ctx)) {
            case 6: {
                return new OpenSSLRSAPrivateKey(this);
            }
            case 408: {
                return new OpenSSLECPrivateKey(this);
            }
        }
        throw new NoSuchAlgorithmException("unknown PKEY type");
    }

    static PrivateKey getPrivateKey(PKCS8EncodedKeySpec keySpec, int type) throws InvalidKeySpecException {
        OpenSSLKey key;
        PKCS8EncodedKeySpec pkcs8KeySpec = keySpec;
        try {
            key = new OpenSSLKey(NativeCrypto.EVP_parse_private_key(pkcs8KeySpec.getEncoded()));
        }
        catch (Exception e) {
            throw new InvalidKeySpecException(e);
        }
        if (NativeCrypto.EVP_PKEY_type(key.getNativeRef()) != type) {
            throw new InvalidKeySpecException("Unexpected key type");
        }
        try {
            return key.getPrivateKey();
        }
        catch (NoSuchAlgorithmException e) {
            throw new InvalidKeySpecException(e);
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OpenSSLKey)) {
            return false;
        }
        OpenSSLKey other = (OpenSSLKey)o;
        if (this.ctx.equals(other.getNativeRef())) {
            return true;
        }
        return NativeCrypto.EVP_PKEY_cmp(this.ctx, other.getNativeRef()) == 1;
    }

    public int hashCode() {
        return this.ctx.hashCode();
    }
}

