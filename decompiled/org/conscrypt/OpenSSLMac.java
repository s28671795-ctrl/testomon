/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.MacSpi;
import javax.crypto.SecretKey;
import org.conscrypt.EvpMdRef;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;

public abstract class OpenSSLMac
extends MacSpi {
    private NativeRef.HMAC_CTX ctx;
    private final long evp_md;
    private byte[] keyBytes;
    private final int size;
    private final byte[] singleByte = new byte[1];

    private OpenSSLMac(long evp_md, int size) {
        this.evp_md = evp_md;
        this.size = size;
    }

    @Override
    protected int engineGetMacLength() {
        return this.size;
    }

    @Override
    protected void engineInit(Key key, AlgorithmParameterSpec params) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException("key must be a SecretKey");
        }
        if (params != null) {
            throw new InvalidAlgorithmParameterException("unknown parameter type");
        }
        this.keyBytes = key.getEncoded();
        if (this.keyBytes == null) {
            throw new InvalidKeyException("key cannot be encoded");
        }
        this.resetContext();
    }

    private final void resetContext() {
        NativeRef.HMAC_CTX ctxLocal = new NativeRef.HMAC_CTX(NativeCrypto.HMAC_CTX_new());
        if (this.keyBytes != null) {
            NativeCrypto.HMAC_Init_ex(ctxLocal, this.keyBytes, this.evp_md);
        }
        this.ctx = ctxLocal;
    }

    @Override
    protected void engineUpdate(byte input) {
        this.singleByte[0] = input;
        this.engineUpdate(this.singleByte, 0, 1);
    }

    @Override
    protected void engineUpdate(byte[] input, int offset, int len) {
        NativeRef.HMAC_CTX ctxLocal = this.ctx;
        NativeCrypto.HMAC_Update(ctxLocal, input, offset, len);
    }

    @Override
    protected void engineUpdate(ByteBuffer input) {
        if (!input.hasRemaining()) {
            return;
        }
        if (!input.isDirect()) {
            super.engineUpdate(input);
            return;
        }
        long baseAddress = NativeCrypto.getDirectBufferAddress(input);
        if (baseAddress == 0L) {
            super.engineUpdate(input);
            return;
        }
        int position = input.position();
        if (position < 0) {
            throw new RuntimeException("Negative position");
        }
        long ptr = baseAddress + (long)position;
        int len = input.remaining();
        if (len < 0) {
            throw new RuntimeException("Negative remaining amount");
        }
        NativeRef.HMAC_CTX ctxLocal = this.ctx;
        NativeCrypto.HMAC_UpdateDirect(ctxLocal, ptr, len);
        input.position(position + len);
    }

    @Override
    protected byte[] engineDoFinal() {
        NativeRef.HMAC_CTX ctxLocal = this.ctx;
        byte[] output = NativeCrypto.HMAC_Final(ctxLocal);
        this.resetContext();
        return output;
    }

    @Override
    protected void engineReset() {
        this.resetContext();
    }

    public static final class HmacSHA512
    extends OpenSSLMac {
        public HmacSHA512() {
            super(EvpMdRef.SHA512.EVP_MD, EvpMdRef.SHA512.SIZE_BYTES);
        }
    }

    public static final class HmacSHA384
    extends OpenSSLMac {
        public HmacSHA384() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA384.EVP_MD, EvpMdRef.SHA384.SIZE_BYTES);
        }
    }

    public static final class HmacSHA256
    extends OpenSSLMac {
        public HmacSHA256() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA256.EVP_MD, EvpMdRef.SHA256.SIZE_BYTES);
        }
    }

    public static final class HmacSHA224
    extends OpenSSLMac {
        public HmacSHA224() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA224.EVP_MD, EvpMdRef.SHA224.SIZE_BYTES);
        }
    }

    public static final class HmacSHA1
    extends OpenSSLMac {
        public HmacSHA1() {
            super(EvpMdRef.SHA1.EVP_MD, EvpMdRef.SHA1.SIZE_BYTES);
        }
    }

    public static final class HmacMD5
    extends OpenSSLMac {
        public HmacMD5() {
            super(EvpMdRef.MD5.EVP_MD, EvpMdRef.MD5.SIZE_BYTES);
        }
    }
}

