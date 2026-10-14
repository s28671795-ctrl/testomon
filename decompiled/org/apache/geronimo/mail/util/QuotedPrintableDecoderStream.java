/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.geronimo.mail.util.QuotedPrintableEncoder;

public class QuotedPrintableDecoderStream
extends FilterInputStream {
    protected QuotedPrintableEncoder decoder = new QuotedPrintableEncoder();

    public QuotedPrintableDecoderStream(InputStream in) {
        super(in);
    }

    public int read() throws IOException {
        return this.decoder.decode(this.in);
    }

    public int read(byte[] buffer, int offset, int length) throws IOException {
        for (int i = 0; i < length; ++i) {
            int ch = this.decoder.decode(this.in);
            if (ch == -1) {
                return i == 0 ? -1 : i;
            }
            buffer[offset + i] = (byte)ch;
        }
        return length;
    }

    public boolean markSupported() {
        return false;
    }

    public int available() throws IOException {
        return -1;
    }
}

