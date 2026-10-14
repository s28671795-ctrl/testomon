/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.Reader;
import org.h2.util.IOUtils;

public final class RangeReader
extends Reader {
    private final Reader r;
    private long limit;

    public RangeReader(Reader reader, long l, long l2) throws IOException {
        this.r = reader;
        this.limit = l2;
        IOUtils.skipFully(reader, l);
    }

    @Override
    public int read() throws IOException {
        if (this.limit <= 0L) {
            return -1;
        }
        int n = this.r.read();
        if (n >= 0) {
            --this.limit;
        }
        return n;
    }

    @Override
    public int read(char[] cArray, int n, int n2) throws IOException {
        int n3;
        if (this.limit <= 0L) {
            return -1;
        }
        if ((long)n2 > this.limit) {
            n2 = (int)this.limit;
        }
        if ((n3 = this.r.read(cArray, n, n2)) > 0) {
            this.limit -= (long)n3;
        }
        return n3;
    }

    @Override
    public long skip(long l) throws IOException {
        if (l > this.limit) {
            l = (int)this.limit;
        }
        l = this.r.skip(l);
        this.limit -= l;
        return l;
    }

    @Override
    public boolean ready() throws IOException {
        if (this.limit > 0L) {
            return this.r.ready();
        }
        return false;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public void mark(int n) throws IOException {
        throw new IOException("mark() not supported");
    }

    @Override
    public void reset() throws IOException {
        throw new IOException("reset() not supported");
    }

    @Override
    public void close() throws IOException {
        this.r.close();
    }
}

