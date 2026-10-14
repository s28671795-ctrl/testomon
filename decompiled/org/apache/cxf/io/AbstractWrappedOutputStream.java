/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractWrappedOutputStream
extends OutputStream {
    protected OutputStream wrappedStream;
    protected boolean written;
    protected boolean allowFlush = true;

    protected AbstractWrappedOutputStream() {
    }

    protected AbstractWrappedOutputStream(OutputStream os) {
        this.wrappedStream = os;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (!this.written) {
            this.onFirstWrite();
            this.written = true;
        }
        if (this.wrappedStream != null) {
            this.wrappedStream.write(b, off, len);
        }
    }

    protected void onFirstWrite() throws IOException {
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(int b) throws IOException {
        if (!this.written) {
            this.onFirstWrite();
            this.written = true;
        }
        if (this.wrappedStream != null) {
            this.wrappedStream.write(b);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.wrappedStream != null) {
            this.wrappedStream.close();
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.written && this.wrappedStream != null && this.allowFlush) {
            this.wrappedStream.flush();
        }
    }

    public void allowFlush(boolean b) {
        this.allowFlush = b;
    }
}

