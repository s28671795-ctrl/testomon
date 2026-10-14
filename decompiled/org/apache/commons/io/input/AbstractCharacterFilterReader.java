/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.input;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public abstract class AbstractCharacterFilterReader
extends FilterReader {
    protected AbstractCharacterFilterReader(Reader reader) {
        super(reader);
    }

    @Override
    public int read() throws IOException {
        int ch;
        while (this.filter(ch = this.in.read())) {
        }
        return ch;
    }

    protected abstract boolean filter(int var1);

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int read = super.read(cbuf, off, len);
        if (read == -1) {
            return -1;
        }
        int pos = off - 1;
        for (int readPos = off; readPos < off + read; ++readPos) {
            if (this.filter(read) || ++pos >= readPos) continue;
            cbuf[pos] = cbuf[readPos];
        }
        return pos - off + 1;
    }
}

