/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;
import org.conscrypt.EvpMdRef;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;

public class OpenSSLMessageDigestJDK
extends MessageDigestSpi
implements Cloneable {
    private final NativeRef.EVP_MD_CTX ctx;
    private final long evp_md;
    private final int size;
    private final byte[] singleByte = new byte[1];
    private boolean digestInitializedInContext;

    private OpenSSLMessageDigestJDK(long evp_md, int size) throws NoSuchAlgorithmException {
        NativeRef.EVP_MD_CTX ctxLocal;
        this.evp_md = evp_md;
        this.size = size;
        this.ctx = ctxLocal = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
    }

    private OpenSSLMessageDigestJDK(long evp_md, int size, NativeRef.EVP_MD_CTX ctx, boolean digestInitializedInContext) {
        this.evp_md = evp_md;
        this.size = size;
        this.ctx = ctx;
        this.digestInitializedInContext = digestInitializedInContext;
    }

    private synchronized void ensureDigestInitializedInContext() {
        if (!this.digestInitializedInContext) {
            NativeRef.EVP_MD_CTX ctxLocal = this.ctx;
            NativeCrypto.EVP_DigestInit_ex(ctxLocal, this.evp_md);
            this.digestInitializedInContext = true;
        }
    }

    @Override
    protected synchronized void engineReset() {
        NativeRef.EVP_MD_CTX ctxLocal = this.ctx;
        NativeCrypto.EVP_MD_CTX_cleanup(ctxLocal);
        this.digestInitializedInContext = false;
    }

    @Override
    protected int engineGetDigestLength() {
        return this.size;
    }

    @Override
    protected synchronized void engineUpdate(byte input) {
        this.singleByte[0] = input;
        this.engineUpdate(this.singleByte, 0, 1);
    }

    @Override
    protected synchronized void engineUpdate(byte[] input, int offset, int len) {
        this.ensureDigestInitializedInContext();
        NativeCrypto.EVP_DigestUpdate(this.ctx, input, offset, len);
    }

    @Override
    protected synchronized void engineUpdate(ByteBuffer input) {
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
        this.ensureDigestInitializedInContext();
        NativeCrypto.EVP_DigestUpdateDirect(this.ctx, ptr, len);
        input.position(position + len);
    }

    @Override
    protected synchronized byte[] engineDigest() {
        this.ensureDigestInitializedInContext();
        byte[] result = new byte[this.size];
        NativeCrypto.EVP_DigestFinal_ex(this.ctx, result, 0);
        this.digestInitializedInContext = false;
        return result;
    }

    @Override
    public Object clone() {
        NativeRef.EVP_MD_CTX ctxCopy = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
        if (this.digestInitializedInContext) {
            NativeCrypto.EVP_MD_CTX_copy_ex(ctxCopy, this.ctx);
        }
        return new OpenSSLMessageDigestJDK(this.evp_md, this.size, ctxCopy, this.digestInitializedInContext);
    }

    public static final class SHA512
    extends OpenSSLMessageDigestJDK {
        public SHA512() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA512.EVP_MD, EvpMdRef.SHA512.SIZE_BYTES);
        }
    }

    public static final class SHA384
    extends OpenSSLMessageDigestJDK {
        public SHA384() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA384.EVP_MD, EvpMdRef.SHA384.SIZE_BYTES);
        }
    }

    public static final class SHA256
    extends OpenSSLMessageDigestJDK {
        public SHA256() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA256.EVP_MD, EvpMdRef.SHA256.SIZE_BYTES);
        }
    }

    public static final class SHA224
    extends OpenSSLMessageDigestJDK {
        public SHA224() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA224.EVP_MD, EvpMdRef.SHA224.SIZE_BYTES);
        }
    }

    public static final class SHA1
    extends OpenSSLMessageDigestJDK {
        public SHA1() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA1.EVP_MD, EvpMdRef.SHA1.SIZE_BYTES);
        }
    }

    public static final class MD5
    extends OpenSSLMessageDigestJDK {
        public MD5() throws NoSuchAlgorithmException {
            super(EvpMdRef.MD5.EVP_MD, EvpMdRef.MD5.SIZE_BYTES);
        }
    }
}

