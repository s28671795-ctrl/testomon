/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import java.io.OutputStream;
import org.fusesource.hawtbuf.Buffer;

public class ByteArrayOutputStream
extends OutputStream {
    byte[] buffer;
    int size;

    public ByteArrayOutputStream() {
        this(1028);
    }

    public ByteArrayOutputStream(int capacity) {
        this.buffer = new byte[capacity];
    }

    public void write(int b) {
        int newsize = this.size + 1;
        this.checkCapacity(newsize);
        this.buffer[this.size] = (byte)b;
        this.size = newsize;
    }

    public void write(byte[] b, int off, int len) {
        int newsize = this.size + len;
        this.checkCapacity(newsize);
        System.arraycopy(b, off, this.buffer, this.size, len);
        this.size = newsize;
    }

    public void write(Buffer b) {
        this.write(b.data, b.offset, b.length);
    }

    private void checkCapacity(int minimumCapacity) {
        if (minimumCapacity > this.buffer.length) {
            byte[] b = new byte[Math.max(this.buffer.length << 1, minimumCapacity)];
            System.arraycopy(this.buffer, 0, b, 0, this.size);
            this.buffer = b;
        }
    }

    public void reset() {
        this.size = 0;
    }

    public Buffer toBuffer() {
        return new Buffer(this.buffer, 0, this.size);
    }

    public byte[] toByteArray() {
        return this.toBuffer().toByteArray();
    }

    public int size() {
        return this.size;
    }
}

