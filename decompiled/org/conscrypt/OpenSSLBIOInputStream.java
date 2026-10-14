/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.conscrypt.NativeCrypto;

class OpenSSLBIOInputStream
extends FilterInputStream {
    private long ctx;

    OpenSSLBIOInputStream(InputStream is, boolean isFinite) {
        super(is);
        this.ctx = NativeCrypto.create_BIO_InputStream(this, isFinite);
    }

    long getBioContext() {
        return this.ctx;
    }

    void release() {
        NativeCrypto.BIO_free_all(this.ctx);
    }

    int gets(byte[] buffer) throws IOException {
        if (buffer == null || buffer.length == 0) {
            return 0;
        }
        int offset = 0;
        int inputByte = 0;
        while (offset < buffer.length && (inputByte = this.read()) != -1) {
            if (inputByte == 10) {
                if (offset != 0) break;
                continue;
            }
            buffer[offset++] = (byte)inputByte;
        }
        return offset;
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return this.read(buffer, 0, buffer.length);
    }

    @Override
    public int read(byte[] buffer, int offset, int len) throws IOException {
        int read;
        if (offset < 0 || len < 0 || len > buffer.length - offset) {
            throw new IndexOutOfBoundsException("Invalid bounds");
        }
        if (len == 0) {
            return 0;
        }
        int totalRead = 0;
        while ((read = super.read(buffer, offset + totalRead, len - totalRead - offset)) != -1 && offset + (totalRead += read) < len) {
        }
        return totalRead == 0 ? -1 : totalRead;
    }
}

