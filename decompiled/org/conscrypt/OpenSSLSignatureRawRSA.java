/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLRSAPrivateCrtKey;
import org.conscrypt.OpenSSLRSAPrivateKey;
import org.conscrypt.OpenSSLRSAPublicKey;

public final class OpenSSLSignatureRawRSA
extends SignatureSpi {
    private OpenSSLKey key;
    private byte[] inputBuffer;
    private int inputOffset;
    private boolean inputIsTooLong;

    @Override
    protected void engineUpdate(byte input) {
        int oldOffset = this.inputOffset++;
        if (this.inputOffset > this.inputBuffer.length) {
            this.inputIsTooLong = true;
            return;
        }
        this.inputBuffer[oldOffset] = input;
    }

    @Override
    protected void engineUpdate(byte[] input, int offset, int len) {
        int oldOffset = this.inputOffset;
        this.inputOffset += len;
        if (this.inputOffset > this.inputBuffer.length) {
            this.inputIsTooLong = true;
            return;
        }
        System.arraycopy(input, offset, this.inputBuffer, oldOffset, len);
    }

    @Override
    protected Object engineGetParameter(String param2) throws InvalidParameterException {
        return null;
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        RSAPrivateKey rsaPrivateKey;
        if (privateKey instanceof OpenSSLRSAPrivateKey) {
            rsaPrivateKey = (OpenSSLRSAPrivateKey)privateKey;
            this.key = rsaPrivateKey.getOpenSSLKey();
        } else if (privateKey instanceof RSAPrivateCrtKey) {
            rsaPrivateKey = (RSAPrivateCrtKey)privateKey;
            this.key = OpenSSLRSAPrivateCrtKey.getInstance((RSAPrivateCrtKey)rsaPrivateKey);
        } else if (privateKey instanceof RSAPrivateKey) {
            rsaPrivateKey = (RSAPrivateKey)privateKey;
            this.key = OpenSSLRSAPrivateKey.getInstance(rsaPrivateKey);
        } else {
            throw new InvalidKeyException("Need RSA private key");
        }
        int maxSize = NativeCrypto.RSA_size(this.key.getNativeRef());
        this.inputBuffer = new byte[maxSize];
        this.inputOffset = 0;
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        RSAPublicKey rsaPublicKey;
        if (publicKey instanceof OpenSSLRSAPublicKey) {
            rsaPublicKey = (OpenSSLRSAPublicKey)publicKey;
            this.key = rsaPublicKey.getOpenSSLKey();
        } else if (publicKey instanceof RSAPublicKey) {
            rsaPublicKey = (RSAPublicKey)publicKey;
            this.key = OpenSSLRSAPublicKey.getInstance(rsaPublicKey);
        } else {
            throw new InvalidKeyException("Need RSA public key");
        }
        int maxSize = NativeCrypto.RSA_size(this.key.getNativeRef());
        this.inputBuffer = new byte[maxSize];
        this.inputOffset = 0;
    }

    @Override
    protected void engineSetParameter(String param2, Object value) throws InvalidParameterException {
    }

    @Override
    protected byte[] engineSign() throws SignatureException {
        if (this.key == null) {
            throw new SignatureException("Need RSA private key");
        }
        if (this.inputIsTooLong) {
            throw new SignatureException("input length " + this.inputOffset + " != " + this.inputBuffer.length + " (modulus size)");
        }
        byte[] outputBuffer = new byte[this.inputBuffer.length];
        try {
            NativeCrypto.RSA_private_encrypt(this.inputOffset, this.inputBuffer, outputBuffer, this.key.getNativeRef(), 1);
            byte[] byArray = outputBuffer;
            return byArray;
        }
        catch (Exception ex) {
            throw new SignatureException(ex);
        }
        finally {
            this.inputOffset = 0;
        }
    }

    @Override
    protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
        if (this.key == null) {
            throw new SignatureException("Need RSA public key");
        }
        if (this.inputIsTooLong) {
            return false;
        }
        if (sigBytes.length > this.inputBuffer.length) {
            throw new SignatureException("Input signature length is too large: " + sigBytes.length + " > " + this.inputBuffer.length);
        }
        byte[] outputBuffer = new byte[this.inputBuffer.length];
        try {
            int resultSize;
            try {
                resultSize = NativeCrypto.RSA_public_decrypt(sigBytes.length, sigBytes, outputBuffer, this.key.getNativeRef(), 1);
            }
            catch (SignatureException e) {
                throw e;
            }
            catch (Exception e) {
                boolean bl = false;
                this.inputOffset = 0;
                return bl;
            }
            boolean matches = resultSize == this.inputOffset;
            for (int i = 0; i < resultSize; ++i) {
                if (this.inputBuffer[i] == outputBuffer[i]) continue;
                matches = false;
            }
            boolean bl = matches;
            return bl;
        }
        catch (Exception ex) {
            throw new SignatureException(ex);
        }
        finally {
            this.inputOffset = 0;
        }
    }
}

