/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.geronimo.mail.util.Base64Encoder;

public class Base64EncoderStream
extends FilterOutputStream {
    protected static final byte[] CRLF = new byte[]{13, 10};
    protected Base64Encoder encoder = new Base64Encoder();
    protected static final int DEFAULT_LINEBREAK = 76;
    protected int bufferedBytes = 0;
    protected byte[] buffer = new byte[3];
    protected int lineBreak;
    protected int outputCount;

    public Base64EncoderStream(OutputStream out) {
        this(out, 76);
    }

    public Base64EncoderStream(OutputStream out, int lineBreak) {
        super(out);
        this.lineBreak = lineBreak / 4 * 4;
    }

    public void write(int ch) throws IOException {
        this.buffer[this.bufferedBytes++] = (byte)ch;
        if (this.bufferedBytes == 3) {
            this.checkEOL(4);
            this.encoder.encode(this.buffer, 0, 3, this.out);
            this.bufferedBytes = 0;
            this.updateLineCount(4);
        }
    }

    public void write(byte[] data) throws IOException {
        this.write(data, 0, data.length);
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        while ((this.bufferedBytes > 0 || this.outputCount > 0) && length > 0) {
            this.write(data[offset++]);
            --length;
        }
        if (length > 0) {
            if (this.lineBreak == Integer.MAX_VALUE) {
                this.encoder.encode(data, offset, length, this.out);
            } else {
                int segmentSize = this.lineBreak / 4 * 3;
                while (length > segmentSize) {
                    this.encoder.encode(data, offset, segmentSize, this.out);
                    this.out.write(CRLF);
                    offset += segmentSize;
                    length -= segmentSize;
                }
                if (length > 0) {
                    while (length > 0) {
                        this.write(data[offset++]);
                        --length;
                    }
                }
            }
        }
    }

    public void close() throws IOException {
        this.flush();
        this.out.close();
    }

    public void flush() throws IOException {
        if (this.bufferedBytes > 0) {
            this.encoder.encode(this.buffer, 0, this.bufferedBytes, this.out);
            this.bufferedBytes = 0;
        }
    }

    private void checkEOL(int required) throws IOException {
        if (this.lineBreak != Integer.MAX_VALUE && this.outputCount + required > this.lineBreak) {
            this.out.write(CRLF);
            this.outputCount = 0;
        }
    }

    private void updateLineCount(int added) {
        if (this.lineBreak != Integer.MAX_VALUE) {
            this.outputCount += added;
        }
    }
}

