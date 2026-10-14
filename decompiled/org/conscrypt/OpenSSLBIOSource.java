/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLBIOInputStream;

final class OpenSSLBIOSource {
    private OpenSSLBIOInputStream source;

    static OpenSSLBIOSource wrap(ByteBuffer buffer) {
        return new OpenSSLBIOSource(new OpenSSLBIOInputStream(new ByteBufferInputStream(buffer), false));
    }

    private OpenSSLBIOSource(OpenSSLBIOInputStream source2) {
        this.source = source2;
    }

    long getContext() {
        return this.source.getBioContext();
    }

    private synchronized void release() {
        if (this.source != null) {
            NativeCrypto.BIO_free_all(this.source.getBioContext());
            this.source = null;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.release();
        }
        finally {
            super.finalize();
        }
    }

    private static class ByteBufferInputStream
    extends InputStream {
        private final ByteBuffer source;

        ByteBufferInputStream(ByteBuffer source2) {
            this.source = source2;
        }

        @Override
        public int read() throws IOException {
            if (this.source.remaining() > 0) {
                return this.source.get();
            }
            return -1;
        }

        @Override
        public int available() throws IOException {
            return this.source.limit() - this.source.position();
        }

        @Override
        public int read(byte[] buffer) throws IOException {
            int originalPosition = this.source.position();
            this.source.get(buffer);
            return this.source.position() - originalPosition;
        }

        @Override
        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            int toRead = Math.min(this.source.remaining(), byteCount);
            int originalPosition = this.source.position();
            this.source.get(buffer, byteOffset, toRead);
            return this.source.position() - originalPosition;
        }

        @Override
        public void reset() throws IOException {
            this.source.reset();
        }

        @Override
        public long skip(long byteCount) throws IOException {
            long originalPosition = this.source.position();
            this.source.position((int)(originalPosition + byteCount));
            return (long)this.source.position() - originalPosition;
        }
    }
}

