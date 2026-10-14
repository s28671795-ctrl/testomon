/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import java.io.IOException;
import java.io.InputStream;
import org.fusesource.hawtbuf.Buffer;

public final class BufferInputStream
extends InputStream {
    byte[] buffer;
    int limit;
    int pos;
    int mark;

    public BufferInputStream(byte[] data) {
        this(data, 0, data.length);
    }

    public BufferInputStream(Buffer sequence2) {
        this(sequence2.getData(), sequence2.getOffset(), sequence2.getLength());
    }

    public BufferInputStream(byte[] data, int offset, int size) {
        this.buffer = data;
        this.mark = offset;
        this.pos = offset;
        this.limit = offset + size;
    }

    public int read() throws IOException {
        if (this.pos < this.limit) {
            return this.buffer[this.pos++] & 0xFF;
        }
        return -1;
    }

    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len) {
        if (this.pos < this.limit) {
            len = Math.min(len, this.limit - this.pos);
            System.arraycopy(this.buffer, this.pos, b, off, len);
            this.pos += len;
            return len;
        }
        return -1;
    }

    public Buffer readBuffer(int len) {
        Buffer rc = null;
        if (this.pos < this.limit) {
            len = Math.min(len, this.limit - this.pos);
            rc = new Buffer(this.buffer, this.pos, len);
            this.pos += len;
        }
        return rc;
    }

    public long skip(long len) throws IOException {
        if (this.pos < this.limit) {
            if ((len = Math.min(len, (long)(this.limit - this.pos))) > 0L) {
                this.pos = (int)((long)this.pos + len);
            }
            return len;
        }
        return -1L;
    }

    public int available() {
        return this.limit - this.pos;
    }

    public boolean markSupported() {
        return true;
    }

    public void mark(int markpos) {
        this.mark = this.pos;
    }

    public void reset() {
        this.pos = this.mark;
    }
}

