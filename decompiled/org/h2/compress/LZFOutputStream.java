/*
 * Decompiled with CFR 0.152.
 */
package org.h2.compress;

import java.io.IOException;
import java.io.OutputStream;
import org.h2.compress.CompressLZF;

public class LZFOutputStream
extends OutputStream {
    static final int MAGIC = 1211255123;
    private final OutputStream out;
    private final CompressLZF compress = new CompressLZF();
    private final byte[] buffer;
    private int pos;
    private byte[] outBuffer;

    public LZFOutputStream(OutputStream outputStream2) throws IOException {
        this.out = outputStream2;
        int n = 131072;
        this.buffer = new byte[n];
        this.ensureOutput(n);
        this.writeInt(1211255123);
    }

    private void ensureOutput(int n) {
        int n2 = (n < 100 ? n + 100 : n) * 2;
        if (this.outBuffer == null || this.outBuffer.length < n2) {
            this.outBuffer = new byte[n2];
        }
    }

    @Override
    public void write(int n) throws IOException {
        if (this.pos >= this.buffer.length) {
            this.flush();
        }
        this.buffer[this.pos++] = (byte)n;
    }

    private void compressAndWrite(byte[] byArray, int n) throws IOException {
        if (n > 0) {
            this.ensureOutput(n);
            int n2 = this.compress.compress(byArray, 0, n, this.outBuffer, 0);
            if (n2 > n) {
                this.writeInt(-n);
                this.out.write(byArray, 0, n);
            } else {
                this.writeInt(n2);
                this.writeInt(n);
                this.out.write(this.outBuffer, 0, n2);
            }
        }
    }

    private void writeInt(int n) throws IOException {
        this.out.write((byte)(n >> 24));
        this.out.write((byte)(n >> 16));
        this.out.write((byte)(n >> 8));
        this.out.write((byte)n);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        while (n2 > 0) {
            int n3 = Math.min(this.buffer.length - this.pos, n2);
            System.arraycopy(byArray, n, this.buffer, this.pos, n3);
            this.pos += n3;
            if (this.pos >= this.buffer.length) {
                this.flush();
            }
            n += n3;
            n2 -= n3;
        }
    }

    @Override
    public void flush() throws IOException {
        this.compressAndWrite(this.buffer, this.pos);
        this.pos = 0;
    }

    @Override
    public void close() throws IOException {
        this.flush();
        this.out.close();
    }
}

