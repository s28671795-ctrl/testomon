/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.activemq.util.ByteSequence;

public class ByteArrayInputStream
extends InputStream {
    byte[] buffer;
    int limit;
    int pos;
    int mark;

    public ByteArrayInputStream(byte[] data) {
        this(data, 0, data.length);
    }

    public ByteArrayInputStream(ByteSequence sequence2) {
        this(sequence2.getData(), sequence2.getOffset(), sequence2.getLength());
    }

    public ByteArrayInputStream(byte[] data, int offset, int size) {
        this.buffer = data;
        this.mark = offset;
        this.pos = offset;
        this.limit = offset + size;
    }

    @Override
    public int read() throws IOException {
        if (this.pos < this.limit) {
            return this.buffer[this.pos++] & 0xFF;
        }
        return -1;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) {
        if (this.pos < this.limit) {
            if ((len = Math.min(len, this.limit - this.pos)) > 0) {
                System.arraycopy(this.buffer, this.pos, b, off, len);
                this.pos += len;
            }
            return len;
        }
        return -1;
    }

    @Override
    public long skip(long len) throws IOException {
        if (this.pos < this.limit) {
            if ((len = Math.min(len, (long)(this.limit - this.pos))) > 0L) {
                this.pos = (int)((long)this.pos + len);
            }
            return len;
        }
        return -1L;
    }

    @Override
    public int available() {
        return this.limit - this.pos;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark(int markpos) {
        this.mark = this.pos;
    }

    @Override
    public void reset() {
        this.pos = this.mark;
    }
}

