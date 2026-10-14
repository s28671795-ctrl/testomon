/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.cxf.io.CachedOutputStream;

public class CacheAndWriteOutputStream
extends CachedOutputStream {
    OutputStream flowThroughStream;
    long count;
    long limit = Long.MAX_VALUE;

    public CacheAndWriteOutputStream(OutputStream stream) {
        if (stream == null) {
            throw new IllegalArgumentException("Stream may not be null");
        }
        this.flowThroughStream = stream;
    }

    public void setCacheLimit(long l) {
        this.limit = l;
    }

    public void closeFlowthroughStream() throws IOException {
        this.flowThroughStream.flush();
        this.flowThroughStream.close();
    }

    @Override
    protected void postClose() throws IOException {
        this.flowThroughStream.flush();
        this.flowThroughStream.close();
    }

    public OutputStream getFlowThroughStream() {
        return this.flowThroughStream;
    }

    @Override
    protected void onWrite() throws IOException {
    }

    @Override
    public void write(int b) throws IOException {
        this.flowThroughStream.write(b);
        if (this.count <= this.limit) {
            super.write(b);
        }
        ++this.count;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.flowThroughStream.write(b, off, len);
        if (this.count <= this.limit) {
            super.write(b, off, len);
        }
        this.count += (long)len;
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.flowThroughStream.write(b);
        if (this.count <= this.limit) {
            super.write(b);
        }
        this.count += (long)b.length;
    }
}

