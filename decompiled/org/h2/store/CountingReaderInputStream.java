/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public class CountingReaderInputStream
extends InputStream {
    private final Reader reader;
    private final CharBuffer charBuffer = CharBuffer.allocate(4096);
    private final CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
    private ByteBuffer byteBuffer = ByteBuffer.allocate(0);
    private long length;
    private long remaining;

    public CountingReaderInputStream(Reader reader, long l) {
        this.reader = reader;
        this.remaining = l;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (!this.fetch()) {
            return -1;
        }
        n2 = Math.min(n2, this.byteBuffer.remaining());
        this.byteBuffer.get(byArray, n, n2);
        return n2;
    }

    @Override
    public int read() throws IOException {
        if (!this.fetch()) {
            return -1;
        }
        return this.byteBuffer.get() & 0xFF;
    }

    private boolean fetch() throws IOException {
        if (this.byteBuffer != null && this.byteBuffer.remaining() == 0) {
            this.fillBuffer();
        }
        return this.byteBuffer != null;
    }

    private void fillBuffer() throws IOException {
        int n = (int)Math.min((long)(this.charBuffer.capacity() - this.charBuffer.position()), this.remaining);
        if (n > 0) {
            n = this.reader.read(this.charBuffer.array(), this.charBuffer.position(), n);
        }
        if (n > 0) {
            this.remaining -= (long)n;
        } else {
            n = 0;
            this.remaining = 0L;
        }
        this.length += (long)n;
        this.charBuffer.limit(this.charBuffer.position() + n);
        this.charBuffer.rewind();
        this.byteBuffer = ByteBuffer.allocate(4096);
        boolean bl = this.remaining == 0L;
        this.encoder.encode(this.charBuffer, this.byteBuffer, bl);
        if (bl && this.byteBuffer.position() == 0) {
            this.byteBuffer = null;
            return;
        }
        this.byteBuffer.flip();
        this.charBuffer.compact();
        this.charBuffer.flip();
        this.charBuffer.position(this.charBuffer.limit());
    }

    public long getLength() {
        return this.length;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}

