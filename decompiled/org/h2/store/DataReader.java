/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class DataReader
extends Reader {
    private final InputStream in;

    public DataReader(InputStream inputStream2) {
        this.in = inputStream2;
    }

    public byte readByte() throws IOException {
        int n = this.in.read();
        if (n < 0) {
            throw new FastEOFException();
        }
        return (byte)n;
    }

    public int readVarInt() throws IOException {
        byte by = this.readByte();
        if (by >= 0) {
            return by;
        }
        int n = by & 0x7F;
        by = this.readByte();
        if (by >= 0) {
            return n | by << 7;
        }
        n |= (by & 0x7F) << 7;
        by = this.readByte();
        if (by >= 0) {
            return n | by << 14;
        }
        n |= (by & 0x7F) << 14;
        by = this.readByte();
        if (by >= 0) {
            return n | by << 21;
        }
        return n | (by & 0x7F) << 21 | this.readByte() << 28;
    }

    private char readChar() throws IOException {
        int n = this.readByte() & 0xFF;
        if (n < 128) {
            return (char)n;
        }
        if (n >= 224) {
            return (char)(((n & 0xF) << 12) + ((this.readByte() & 0x3F) << 6) + (this.readByte() & 0x3F));
        }
        return (char)(((n & 0x1F) << 6) + (this.readByte() & 0x3F));
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public int read(char[] cArray, int n, int n2) throws IOException {
        int n3;
        if (n2 == 0) {
            return 0;
        }
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                cArray[n + n3] = this.readChar();
            }
            return n2;
        }
        catch (EOFException eOFException) {
            if (n3 == 0) {
                return -1;
            }
            return n3;
        }
    }

    static class FastEOFException
    extends EOFException {
        private static final long serialVersionUID = 1L;

        FastEOFException() {
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return null;
        }
    }
}

