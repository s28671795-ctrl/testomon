/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.h2.util.IOUtils;

public final class RangeInputStream
extends FilterInputStream {
    private long limit;

    public RangeInputStream(InputStream inputStream2, long l, long l2) throws IOException {
        super(inputStream2);
        this.limit = l2;
        IOUtils.skipFully(inputStream2, l);
    }

    @Override
    public int read() throws IOException {
        if (this.limit <= 0L) {
            return -1;
        }
        int n = this.in.read();
        if (n >= 0) {
            --this.limit;
        }
        return n;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (this.limit <= 0L) {
            return -1;
        }
        if ((long)n2 > this.limit) {
            n2 = (int)this.limit;
        }
        if ((n3 = this.in.read(byArray, n, n2)) > 0) {
            this.limit -= (long)n3;
        }
        return n3;
    }

    @Override
    public long skip(long l) throws IOException {
        if (l > this.limit) {
            l = (int)this.limit;
        }
        l = this.in.skip(l);
        this.limit -= l;
        return l;
    }

    @Override
    public int available() throws IOException {
        int n = this.in.available();
        if ((long)n > this.limit) {
            return (int)this.limit;
        }
        return n;
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }

    @Override
    public void mark(int n) {
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
    }
}

