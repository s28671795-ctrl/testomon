/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.tcp;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TcpBufferedInputStream
extends FilterInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected byte[] internalBuffer;
    protected int count;
    protected int position;

    public TcpBufferedInputStream(InputStream in) {
        this(in, 8192);
    }

    public TcpBufferedInputStream(InputStream in, int size) {
        super(in);
        if (size <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        this.internalBuffer = new byte[size];
    }

    protected void fill() throws IOException {
        byte[] buffer = this.internalBuffer;
        this.count = 0;
        this.position = 0;
        int n = this.in.read(buffer, this.position, buffer.length - this.position);
        if (n > 0) {
            this.count = n + this.position;
        }
    }

    @Override
    public int read() throws IOException {
        if (this.position >= this.count) {
            this.fill();
            if (this.position >= this.count) {
                return -1;
            }
        }
        return this.internalBuffer[this.position++] & 0xFF;
    }

    private int readStream(byte[] b, int off, int len) throws IOException {
        int avail = this.count - this.position;
        if (avail <= 0) {
            if (len >= this.internalBuffer.length) {
                return this.in.read(b, off, len);
            }
            this.fill();
            avail = this.count - this.position;
            if (avail <= 0) {
                return -1;
            }
        }
        int cnt = avail < len ? avail : len;
        System.arraycopy(this.internalBuffer, this.position, b, off, cnt);
        this.position += cnt;
        return cnt;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        InputStream input;
        if ((off | len | off + len | b.length - (off + len)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return 0;
        }
        int n = 0;
        do {
            int nread;
            if ((nread = this.readStream(b, off + n, len - n)) <= 0) {
                return n == 0 ? nread : n;
            }
            if ((n += nread) < len) continue;
            return n;
        } while ((input = this.in) == null || input.available() > 0);
        return n;
    }

    @Override
    public long skip(long n) throws IOException {
        if (n <= 0L) {
            return 0L;
        }
        long avail = this.count - this.position;
        if (avail <= 0L) {
            return this.in.skip(n);
        }
        long skipped = avail < n ? avail : n;
        this.position = (int)((long)this.position + skipped);
        return skipped;
    }

    @Override
    public int available() throws IOException {
        return this.in.available() + (this.count - this.position);
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public void close() throws IOException {
        if (this.in != null) {
            this.in.close();
        }
    }

    public void unread(byte[] array) throws IOException {
        int avail = this.internalBuffer.length - this.position;
        if (array.length > avail) {
            throw new IOException("Buffer is full, can't unread");
        }
        System.arraycopy(array, this.position, this.internalBuffer, 0, array.length);
        this.count += array.length;
    }
}

