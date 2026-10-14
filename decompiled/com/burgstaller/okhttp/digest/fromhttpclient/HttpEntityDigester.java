/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.digest.fromhttpclient;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public class HttpEntityDigester
implements BufferedSink {
    private final MessageDigest digester;
    private boolean closed;
    private byte[] digest;
    Buffer buffer;

    public HttpEntityDigester(MessageDigest digester) {
        this.digester = digester;
        this.digester.reset();
        this.buffer = new Buffer();
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    @Override
    public BufferedSink write(ByteString byteString) throws IOException {
        this.digester.update(byteString.toByteArray());
        return this;
    }

    @Override
    public BufferedSink write(byte[] source2) throws IOException {
        this.digester.update(source2);
        return this;
    }

    @Override
    public BufferedSink write(byte[] source2, int offset, int byteCount) throws IOException {
        this.digester.update(source2, offset, byteCount);
        return this;
    }

    @Override
    public long writeAll(Source source2) throws IOException {
        return 0L;
    }

    @Override
    public BufferedSink write(Source source2, long byteCount) throws IOException {
        if (this.closed) {
            throw new IOException("Stream has been already closed");
        }
        return null;
    }

    @Override
    public void write(Buffer source2, long byteCount) throws IOException {
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        return 0;
    }

    @Override
    public BufferedSink writeUtf8(String string) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeUtf8(String string, int beginIndex, int endIndex) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeUtf8CodePoint(int codePoint) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeString(String string, Charset charset) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeString(String string, int beginIndex, int endIndex, Charset charset) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeByte(int b) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeShort(int s2) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeShortLe(int s2) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeInt(int i) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeIntLe(int i) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeLong(long v) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeLongLe(long v) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeDecimalLong(long v) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeHexadecimalUnsignedLong(long v) throws IOException {
        return null;
    }

    @Override
    public BufferedSink emitCompleteSegments() throws IOException {
        return null;
    }

    @Override
    public BufferedSink emit() throws IOException {
        return this;
    }

    @Override
    public OutputStream outputStream() {
        return new OutputStream(){

            @Override
            public void write(int b) throws IOException {
                if (HttpEntityDigester.this.closed) {
                    throw new IOException("closed");
                }
                HttpEntityDigester.this.buffer.writeByte((byte)b);
                HttpEntityDigester.this.emitCompleteSegments();
            }

            @Override
            public void write(byte[] data, int offset, int byteCount) throws IOException {
                if (HttpEntityDigester.this.closed) {
                    throw new IOException("closed");
                }
                HttpEntityDigester.this.buffer.write(data, offset, byteCount);
                HttpEntityDigester.this.emitCompleteSegments();
            }

            @Override
            public void flush() throws IOException {
                if (!HttpEntityDigester.this.closed) {
                    HttpEntityDigester.this.flush();
                }
            }

            @Override
            public void close() throws IOException {
                HttpEntityDigester.this.close();
            }

            public String toString() {
                return HttpEntityDigester.this + ".outputStream()";
            }
        };
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public Timeout timeout() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.digest = this.digester.digest();
        this.buffer.close();
    }

    public byte[] getDigest() {
        return this.digest;
    }
}

