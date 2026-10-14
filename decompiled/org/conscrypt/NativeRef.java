/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import org.conscrypt.NativeCrypto;

abstract class NativeRef {
    final long address;

    NativeRef(long address) {
        if (address == 0L) {
            throw new NullPointerException("address == 0");
        }
        this.address = address;
    }

    public boolean equals(Object o) {
        if (!(o instanceof NativeRef)) {
            return false;
        }
        return ((NativeRef)o).address == this.address;
    }

    public int hashCode() {
        return (int)(this.address ^ this.address >>> 32);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.address != 0L) {
                this.doFree(this.address);
            }
        }
        finally {
            super.finalize();
        }
    }

    abstract void doFree(long var1);

    static final class SSL_SESSION
    extends NativeRef {
        SSL_SESSION(long nativePointer) {
            super(nativePointer);
        }

        @Override
        void doFree(long context) {
            NativeCrypto.SSL_SESSION_free(context);
        }
    }

    static final class HMAC_CTX
    extends NativeRef {
        HMAC_CTX(long nativePointer) {
            super(nativePointer);
        }

        @Override
        void doFree(long context) {
            NativeCrypto.HMAC_CTX_free(context);
        }
    }

    static final class EVP_PKEY_CTX
    extends NativeRef {
        EVP_PKEY_CTX(long nativePointer) {
            super(nativePointer);
        }

        @Override
        void doFree(long context) {
            NativeCrypto.EVP_PKEY_CTX_free(context);
        }
    }

    static final class EVP_PKEY
    extends NativeRef {
        EVP_PKEY(long nativePointer) {
            super(nativePointer);
        }

        @Override
        void doFree(long context) {
            NativeCrypto.EVP_PKEY_free(context);
        }
    }

    static final class EVP_MD_CTX
    extends NativeRef {
        EVP_MD_CTX(long nativePointer) {
            super(nativePointer);
        }

        @Override
        void doFree(long context) {
            NativeCrypto.EVP_MD_CTX_destroy(context);
        }
    }

    static final class EVP_CIPHER_CTX
    extends NativeRef {
        EVP_CIPHER_CTX(long nativePointer) {
            super(nativePointer);
        }

        @Override
        void doFree(long context) {
            NativeCrypto.EVP_CIPHER_CTX_free(context);
        }
    }

    static final class EC_POINT
    extends NativeRef {
        EC_POINT(long nativePointer) {
            super(nativePointer);
        }

        @Override
        void doFree(long context) {
            NativeCrypto.EC_POINT_clear_free(context);
        }
    }

    static final class EC_GROUP
    extends NativeRef {
        EC_GROUP(long ctx) {
            super(ctx);
        }

        @Override
        void doFree(long context) {
            NativeCrypto.EC_GROUP_clear_free(context);
        }
    }
}

