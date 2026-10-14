/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.OutputStream;
import org.apache.activemq.util.ByteSequence;

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

    @Override
    public void write(int b) {
        int newsize = this.size + 1;
        this.checkCapacity(newsize);
        this.buffer[this.size] = (byte)b;
        this.size = newsize;
    }

    @Override
    public void write(byte[] b, int off, int len) {
        int newsize = this.size + len;
        this.checkCapacity(newsize);
        System.arraycopy(b, off, this.buffer, this.size, len);
        this.size = newsize;
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

    public ByteSequence toByteSequence() {
        return new ByteSequence(this.buffer, 0, this.size);
    }

    public byte[] toByteArray() {
        byte[] rc = new byte[this.size];
        System.arraycopy(this.buffer, 0, rc, 0, this.size);
        return rc;
    }

    public int size() {
        return this.size;
    }

    public boolean endsWith(byte[] array) {
        int i = 0;
        int start2 = this.size - array.length;
        if (start2 < 0) {
            return false;
        }
        while (start2 < this.size) {
            if (this.buffer[start2++] == array[i++]) continue;
            return false;
        }
        return true;
    }
}

