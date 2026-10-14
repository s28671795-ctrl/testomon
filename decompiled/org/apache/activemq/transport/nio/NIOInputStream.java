/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class NIOInputStream
extends InputStream {
    protected int count;
    protected int position;
    private final ByteBuffer in;

    public NIOInputStream(ByteBuffer in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        try {
            int rc = this.in.get() & 0xFF;
            return rc;
        }
        catch (BufferUnderflowException e) {
            return -1;
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (this.in.hasRemaining()) {
            int rc = Math.min(len, this.in.remaining());
            this.in.get(b, off, rc);
            return rc;
        }
        return len == 0 ? 0 : -1;
    }

    @Override
    public long skip(long n) throws IOException {
        int rc = Math.min((int)n, this.in.remaining());
        this.in.position(this.in.position() + rc);
        return rc;
    }

    @Override
    public int available() throws IOException {
        return this.in.remaining();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public void close() throws IOException {
    }
}

