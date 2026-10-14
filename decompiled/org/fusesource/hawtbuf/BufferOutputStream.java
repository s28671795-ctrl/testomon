/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import org.fusesource.hawtbuf.Buffer;

public final class BufferOutputStream
extends OutputStream {
    byte[] buffer;
    int offset;
    int limit;
    int pos;

    public BufferOutputStream(int size) {
        this(new byte[size]);
    }

    public BufferOutputStream(byte[] buffer) {
        this.buffer = buffer;
        this.limit = buffer.length;
    }

    public BufferOutputStream(Buffer data) {
        this.buffer = data.data;
        this.pos = this.offset = data.offset;
        this.limit = data.offset + data.length;
    }

    public void write(int b) throws IOException {
        int newPos = this.pos + 1;
        this.checkCapacity(newPos);
        this.buffer[this.pos] = (byte)b;
        this.pos = newPos;
    }

    public void write(byte[] b, int off, int len) throws IOException {
        int newPos = this.pos + len;
        this.checkCapacity(newPos);
        System.arraycopy(b, off, this.buffer, this.pos, len);
        this.pos = newPos;
    }

    public Buffer getNextBuffer(int len) throws IOException {
        int newPos = this.pos + len;
        this.checkCapacity(newPos);
        return new Buffer(this.buffer, this.pos, len);
    }

    private void checkCapacity(int minimumCapacity) throws IOException {
        if (minimumCapacity > this.limit) {
            throw new EOFException("Buffer limit reached.");
        }
    }

    public void reset() {
        this.pos = this.offset;
    }

    public Buffer toBuffer() {
        return new Buffer(this.buffer, this.offset, this.pos);
    }

    public byte[] toByteArray() {
        return this.toBuffer().toByteArray();
    }

    public int size() {
        return this.offset - this.pos;
    }
}

