/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.geronimo.mail.util.QuotedPrintableEncoder;

public class QuotedPrintableEncoderStream
extends FilterOutputStream {
    protected QuotedPrintableEncoder encoder;
    protected static final int DEFAULT_LINEBREAK = 76;
    protected int lineBreak;

    public QuotedPrintableEncoderStream(OutputStream out) {
        this(out, 76);
    }

    public QuotedPrintableEncoderStream(OutputStream out, int lineBreak) {
        super(out);
        this.lineBreak = lineBreak / 4 * 4;
        this.encoder = new QuotedPrintableEncoder(out, this.lineBreak);
    }

    public void write(int ch) throws IOException {
        this.encoder.encode(ch);
    }

    public void write(byte[] data) throws IOException {
        this.write(data, 0, data.length);
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        this.encoder.encode(data, offset, length);
    }

    public void close() throws IOException {
        this.out.close();
    }

    public void flush() throws IOException {
        this.out.flush();
    }
}

