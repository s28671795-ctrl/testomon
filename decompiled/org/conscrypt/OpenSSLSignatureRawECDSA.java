/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLKey;

public class OpenSSLSignatureRawECDSA
extends SignatureSpi {
    private OpenSSLKey key;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    @Override
    protected void engineUpdate(byte input) {
        this.buffer.write(input);
    }

    @Override
    protected void engineUpdate(byte[] input, int offset, int len) {
        this.buffer.write(input, offset, len);
    }

    @Override
    protected Object engineGetParameter(String param2) throws InvalidParameterException {
        return null;
    }

    private static OpenSSLKey verifyKey(OpenSSLKey key) throws InvalidKeyException {
        int pkeyType = NativeCrypto.EVP_PKEY_type(key.getNativeRef());
        if (pkeyType != 408) {
            throw new InvalidKeyException("Non-EC key used to initialize EC signature.");
        }
        return key;
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        this.key = OpenSSLSignatureRawECDSA.verifyKey(OpenSSLKey.fromPrivateKey(privateKey));
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        this.key = OpenSSLSignatureRawECDSA.verifyKey(OpenSSLKey.fromPublicKey(publicKey));
    }

    @Override
    protected void engineSetParameter(String param2, Object value) throws InvalidParameterException {
    }

    @Override
    protected byte[] engineSign() throws SignatureException {
        if (this.key == null) {
            throw new SignatureException("No key provided");
        }
        int output_size = NativeCrypto.ECDSA_size(this.key.getNativeRef());
        byte[] outputBuffer = new byte[output_size];
        try {
            int bytes_written = NativeCrypto.ECDSA_sign(this.buffer.toByteArray(), outputBuffer, this.key.getNativeRef());
            if (bytes_written < 0) {
                throw new SignatureException("Could not compute signature.");
            }
            if (bytes_written != output_size) {
                byte[] newBuffer = new byte[bytes_written];
                System.arraycopy(outputBuffer, 0, newBuffer, 0, bytes_written);
                outputBuffer = newBuffer;
            }
            byte[] byArray = outputBuffer;
            return byArray;
        }
        catch (Exception ex) {
            throw new SignatureException(ex);
        }
        finally {
            this.buffer.reset();
        }
    }

    @Override
    protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
        if (this.key == null) {
            throw new SignatureException("No key provided");
        }
        try {
            int result = NativeCrypto.ECDSA_verify(this.buffer.toByteArray(), sigBytes, this.key.getNativeRef());
            if (result == -1) {
                throw new SignatureException("Could not verify signature.");
            }
            boolean bl = result == 1;
            return bl;
        }
        catch (Exception ex) {
            throw new SignatureException(ex);
        }
        finally {
            this.buffer.reset();
        }
    }
}

