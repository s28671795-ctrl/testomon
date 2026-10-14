/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

final class ExtendedBufferedReader
extends BufferedReader {
    private int lastChar = -2;
    private long eolCounter;
    private long position;
    private boolean closed;

    ExtendedBufferedReader(Reader reader) {
        super(reader);
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
        this.lastChar = -1;
        super.close();
    }

    long getCurrentLineNumber() {
        if (this.lastChar == 13 || this.lastChar == 10 || this.lastChar == -2 || this.lastChar == -1) {
            return this.eolCounter;
        }
        return this.eolCounter + 1L;
    }

    int getLastChar() {
        return this.lastChar;
    }

    long getPosition() {
        return this.position;
    }

    public boolean isClosed() {
        return this.closed;
    }

    int lookAhead() throws IOException {
        super.mark(1);
        int c = super.read();
        super.reset();
        return c;
    }

    char[] lookAhead(char[] buf) throws IOException {
        int n = buf.length;
        super.mark(n);
        super.read(buf, 0, n);
        super.reset();
        return buf;
    }

    char[] lookAhead(int n) throws IOException {
        char[] buf = new char[n];
        return this.lookAhead(buf);
    }

    @Override
    public int read() throws IOException {
        int current = super.read();
        if (current == 13 || current == 10 && this.lastChar != 13 || current == -1 && this.lastChar != 13 && this.lastChar != 10 && this.lastChar != -1) {
            ++this.eolCounter;
        }
        this.lastChar = current;
        ++this.position;
        return this.lastChar;
    }

    @Override
    public int read(char[] buf, int offset, int length) throws IOException {
        if (length == 0) {
            return 0;
        }
        int len = super.read(buf, offset, length);
        if (len > 0) {
            for (int i = offset; i < offset + len; ++i) {
                char ch = buf[i];
                if (ch == '\n') {
                    if (13 == (i > offset ? buf[i - 1] : this.lastChar)) continue;
                    ++this.eolCounter;
                    continue;
                }
                if (ch != '\r') continue;
                ++this.eolCounter;
            }
            this.lastChar = buf[offset + len - 1];
        } else if (len == -1) {
            this.lastChar = -1;
        }
        this.position += (long)len;
        return len;
    }

    @Override
    public String readLine() throws IOException {
        if (this.lookAhead() == -1) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        while (true) {
            int next;
            int current;
            if ((current = this.read()) == 13 && (next = this.lookAhead()) == 10) {
                this.read();
            }
            if (current == -1 || current == 10 || current == 13) break;
            buffer.append((char)current);
        }
        return buffer.toString();
    }
}

