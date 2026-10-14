/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.ByteArrayOutputStream;
import org.conscrypt.NativeCrypto;

final class OpenSSLBIOSink {
    private final long ctx;
    private final ByteArrayOutputStream buffer;
    private int position;

    static OpenSSLBIOSink create() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        return new OpenSSLBIOSink(buffer);
    }

    private OpenSSLBIOSink(ByteArrayOutputStream buffer) {
        this.ctx = NativeCrypto.create_BIO_OutputStream(buffer);
        this.buffer = buffer;
    }

    int available() {
        return this.buffer.size() - this.position;
    }

    void reset() {
        this.buffer.reset();
        this.position = 0;
    }

    long skip(long byteCount) {
        int maxLength = Math.min(this.available(), (int)byteCount);
        this.position += maxLength;
        if (this.position == this.buffer.size()) {
            this.reset();
        }
        return maxLength;
    }

    long getContext() {
        return this.ctx;
    }

    byte[] toByteArray() {
        return this.buffer.toByteArray();
    }

    int position() {
        return this.position;
    }

    protected void finalize() throws Throwable {
        try {
            NativeCrypto.BIO_free_all(this.ctx);
        }
        finally {
            super.finalize();
        }
    }
}

