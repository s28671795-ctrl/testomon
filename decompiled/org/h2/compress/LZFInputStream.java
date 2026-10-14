/*
 * Decompiled with CFR 0.152.
 */
package org.h2.compress;

import java.io.IOException;
import java.io.InputStream;
import org.h2.compress.CompressLZF;
import org.h2.mvstore.DataUtils;
import org.h2.util.Utils;

public class LZFInputStream
extends InputStream {
    private final InputStream in;
    private CompressLZF decompress = new CompressLZF();
    private int pos;
    private int bufferLength;
    private byte[] inBuffer;
    private byte[] buffer;

    public LZFInputStream(InputStream inputStream2) throws IOException {
        this.in = inputStream2;
        if (this.readInt() != 1211255123) {
            throw new IOException("Not an LZFInputStream");
        }
    }

    private static byte[] ensureSize(byte[] byArray, int n) {
        return byArray == null || byArray.length < n ? Utils.newBytes(n) : byArray;
    }

    private void fillBuffer() throws IOException {
        if (this.buffer != null && this.pos < this.bufferLength) {
            return;
        }
        int n = this.readInt();
        if (this.decompress == null) {
            this.bufferLength = 0;
        } else if (n < 0) {
            n = -n;
            this.buffer = LZFInputStream.ensureSize(this.buffer, n);
            this.readFully(this.buffer, n);
            this.bufferLength = n;
        } else {
            this.inBuffer = LZFInputStream.ensureSize(this.inBuffer, n);
            int n2 = this.readInt();
            this.readFully(this.inBuffer, n);
            this.buffer = LZFInputStream.ensureSize(this.buffer, n2);
            try {
                this.decompress.expand(this.inBuffer, 0, n, this.buffer, 0, n2);
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                throw DataUtils.convertToIOException(arrayIndexOutOfBoundsException);
            }
            this.bufferLength = n2;
        }
        this.pos = 0;
    }

    private void readFully(byte[] byArray, int n) throws IOException {
        int n2 = 0;
        while (n > 0) {
            int n3 = this.in.read(byArray, n2, n);
            n -= n3;
            n2 += n3;
        }
    }

    private int readInt() throws IOException {
        int n = this.in.read();
        if (n < 0) {
            this.decompress = null;
            return 0;
        }
        n = (n << 24) + (this.in.read() << 16) + (this.in.read() << 8) + this.in.read();
        return n;
    }

    @Override
    public int read() throws IOException {
        this.fillBuffer();
        if (this.pos >= this.bufferLength) {
            return -1;
        }
        return this.buffer[this.pos++] & 0xFF;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (n2 == 0) {
            return 0;
        }
        int n4 = 0;
        while (n2 > 0 && (n3 = this.readBlock(byArray, n, n2)) >= 0) {
            n4 += n3;
            n += n3;
            n2 -= n3;
        }
        return n4 == 0 ? -1 : n4;
    }

    private int readBlock(byte[] byArray, int n, int n2) throws IOException {
        this.fillBuffer();
        if (this.pos >= this.bufferLength) {
            return -1;
        }
        int n3 = Math.min(n2, this.bufferLength - this.pos);
        n3 = Math.min(n3, byArray.length - n);
        System.arraycopy(this.buffer, this.pos, byArray, n, n3);
        this.pos += n3;
        return n3;
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }
}

