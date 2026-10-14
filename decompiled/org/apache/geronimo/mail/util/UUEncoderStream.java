/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.apache.geronimo.mail.util.UUEncoder;

public class UUEncoderStream
extends FilterOutputStream {
    protected static final int DEFAULT_MODE = 644;
    protected static final String DEFAULT_NAME = "encoder.buf";
    protected static final int MAX_CHARS_PER_LINE = 45;
    protected String name;
    protected int mode;
    protected boolean beginWritten = false;
    protected UUEncoder encoder = new UUEncoder();
    protected int bufferedBytes = 0;
    protected byte[] buffer = new byte[45];

    public UUEncoderStream(OutputStream out) {
        this(out, DEFAULT_NAME, 644);
    }

    public UUEncoderStream(OutputStream out, String name) {
        this(out, name, 644);
    }

    public UUEncoderStream(OutputStream out, String name, int mode) {
        super(out);
        this.name = name;
        this.mode = mode;
    }

    private void checkBegin() throws IOException {
        if (!this.beginWritten) {
            PrintStream writer = new PrintStream(this.out);
            writer.print("begin " + this.mode + " " + this.name + "\r\n");
            writer.flush();
            this.beginWritten = true;
        }
    }

    private void writeEnd() throws IOException {
        PrintStream writer = new PrintStream(this.out);
        writer.print("\nend\r\n");
        writer.flush();
    }

    private void flushBuffer() throws IOException {
        this.checkBegin();
        if (this.bufferedBytes != 0) {
            this.encoder.encode(this.buffer, 0, this.bufferedBytes, this.out);
            this.bufferedBytes = 0;
        }
    }

    private int bufferSpace() {
        return 45 - this.bufferedBytes;
    }

    private boolean isBufferFull() {
        return this.bufferedBytes >= 45;
    }

    public void write(int ch) throws IOException {
        this.buffer[this.bufferedBytes++] = (byte)ch;
        if (this.isBufferFull()) {
            this.flushBuffer();
        }
    }

    public void write(byte[] data) throws IOException {
        this.write(data, 0, data.length);
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        int copyBytes = Math.min(this.bufferSpace(), length);
        System.arraycopy(this.buffer, this.bufferedBytes, data, offset, copyBytes);
        this.bufferedBytes += copyBytes;
        offset += copyBytes;
        length -= copyBytes;
        if (this.isBufferFull()) {
            this.flushBuffer();
        }
        if (length >= 45) {
            int fullLinesLength = length / 45 * 45;
            this.encoder.encode(data, offset, fullLinesLength, this.out);
            offset += fullLinesLength;
            length -= fullLinesLength;
        }
        if (length > 0) {
            System.arraycopy(this.buffer, 0, data, offset, length);
            this.bufferedBytes += length;
            offset += length;
            length -= length;
        }
    }

    public void flush() throws IOException {
        this.flushBuffer();
        this.writeEnd();
        this.out.flush();
    }

    public void close() throws IOException {
        this.flush();
        this.out.close();
    }
}

