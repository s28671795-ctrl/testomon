/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.stax.impl.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.xml.security.utils.I18n;

public class LimitingInputStream
extends FilterInputStream {
    private long limit;
    private long count;

    public LimitingInputStream(InputStream in, long limit) {
        super(in);
        this.limit = limit;
    }

    @Override
    public int read() throws IOException {
        int r = super.read();
        if (r >= 0) {
            this.incrementCountAndTestLimit(r);
        }
        return r;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int r = super.read(b, off, len);
        if (r >= 0) {
            this.incrementCountAndTestLimit(r);
        }
        return r;
    }

    private void incrementCountAndTestLimit(long read) throws IOException {
        this.count += read;
        if (this.count > this.limit) {
            throw new IOException(I18n.getExceptionMessage("secureProcessing.inputStreamLimitReached", new Object[]{this.limit}));
        }
    }
}

