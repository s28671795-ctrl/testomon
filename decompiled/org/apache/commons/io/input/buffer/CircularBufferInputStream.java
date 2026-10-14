/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.input.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.io.input.buffer.CircularByteBuffer;

public class CircularBufferInputStream
extends InputStream {
    protected final InputStream in;
    protected final CircularByteBuffer buffer;
    protected final int bufferSize;
    private boolean eofSeen;

    public CircularBufferInputStream(InputStream inputStream2, int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("Invalid bufferSize: " + bufferSize);
        }
        this.in = Objects.requireNonNull(inputStream2, "inputStream");
        this.buffer = new CircularByteBuffer(bufferSize);
        this.bufferSize = bufferSize;
        this.eofSeen = false;
    }

    public CircularBufferInputStream(InputStream inputStream2) {
        this(inputStream2, 8192);
    }

    protected void fillBuffer() throws IOException {
        if (this.eofSeen) {
            return;
        }
        int space = this.buffer.getSpace();
        byte[] buf = new byte[space];
        while (space > 0) {
            int res = this.in.read(buf, 0, space);
            if (res == -1) {
                this.eofSeen = true;
                return;
            }
            if (res <= 0) continue;
            this.buffer.add(buf, 0, res);
            space -= res;
        }
    }

    protected boolean haveBytes(int count) throws IOException {
        if (this.buffer.getCurrentNumberOfBytes() < count) {
            this.fillBuffer();
        }
        return this.buffer.hasBytes();
    }

    @Override
    public int read() throws IOException {
        if (!this.haveBytes(1)) {
            return -1;
        }
        return this.buffer.read();
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return this.read(buffer, 0, buffer.length);
    }

    @Override
    public int read(byte[] targetBuffer, int offset, int length) throws IOException {
        Objects.requireNonNull(targetBuffer, "Buffer");
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must not be negative");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative");
        }
        if (!this.haveBytes(length)) {
            return -1;
        }
        int result = Math.min(length, this.buffer.getCurrentNumberOfBytes());
        for (int i = 0; i < result; ++i) {
            targetBuffer[offset + i] = this.buffer.read();
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        this.eofSeen = true;
        this.buffer.clear();
    }
}

